package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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
    private FileService fileService;
    private CredentialService credentialService;
    private NoteService noteService;
    private UserService userService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, CredentialService credentialService, NoteService noteService, UserService userService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String homeView(Model model, Authentication authentication, Note note, Credential credential, File file){
        String username = authentication.getName();
        Integer userId = userService.getUserId(username);

        model.addAttribute("fileList", this.fileService.getAllFiles(userId));
        model.addAttribute("noteList", this.noteService.getAllNotes(userId));
        model.addAttribute("credentialList", this.credentialService.getAllCredentials(userId));
        model.addAttribute("encryptionService", this.encryptionService);
        return "home";
    }


}
