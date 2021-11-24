package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FilesService filesService;
    private NotesService notesService;
    private CredentialsService credentialsService;
    private UserService userService;
    private EncryptionService encryptionService;

    public HomeController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping()
    public String homeView(Model model, Authentication authentication, Files files, Notes notes){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userID = user.getUserID();

        model.addAttribute("fileList", this.filesService.getAllFiles(userID));
        model.addAttribute("notesList", this.notesService.getAllNotes(userID));
        model.addAttribute("credentialsList", this.credentialsService.getAllCredentials(userID));
        model.addAttribute("encryptionService", this.encryptionService);
        return "home";
    }
}
