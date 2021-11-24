package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private final FilesService filesService;
    private final UserService userService;

    public FileController(FilesService filesService, UserService userService) {
        this.filesService = filesService;
        this.userService = userService;
    }

    @PostMapping
    public String uploadFile (Model model, @RequestParam("fileUpload")MultipartFile multipartFile, Authentication authentication){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userID = user.getUserID();

        String filename = multipartFile.getOriginalFilename();

        try{
            if(!filename.isEmpty()){
                if(!filesService.isDuplicateFile(multipartFile, userID)){
                    if(multipartFile.getSize() < 10485760){
                        filesService.uploadFiles(multipartFile, userID);
                        model.addAttribute("isSuccessful", true);
                        model.addAttribute("successMessage", filename + " has been successfully uploaded!");
                    }else {
                        model.addAttribute("hasAnError", true);
                        model.addAttribute("errorMessage", "File size must be smaller than 10485760, current file size= (" + multipartFile.getSize() + ")");
                    }
                }
                else {
                    model.addAttribute("hasAnError", true);
                    model.addAttribute("errorMessage", "File with " + filename + " exists!");
                }
            }
            else {
                model.addAttribute("hasAnError", true);
                model.addAttribute("errorMessage", "Uploading empty file is not possible");
            }
        } catch (IOException e) {
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "Error in uploading file!");
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("download/{fileId}")
    public ResponseEntity downloadFile(@PathVariable Integer fileId, Authentication authentication){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userID = user.getUserID();

        Files files = filesService.getFile(fileId, userID);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(files.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + files.getFilename() + "\"")
                .body(files.getFiledata());
    }

    @GetMapping("delete/{fileId}")
    public String deleteFile(Model model, @PathVariable Integer fileId, Authentication authentication){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userID = user.getUserID();

        Integer fileIsDeleted = filesService.deleteFiles(fileId, userID);

        if(fileIsDeleted != null){
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage", "file has been successfully deleted");
        }
        else{
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "file deletion failed");
        }

        return "result";
    }
}
