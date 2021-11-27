package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String insertOrUpdateCredential (Model model, Credential credential, Authentication authentication){
        Integer userId = userService.getUserId(authentication.getName());
        credential.setUserId(userId);
        try{
            credentialService.insertOrUpdateCredential(credential);
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage",  "credential has been successfully inserted!");
        } catch (Exception e){
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "Error in adding credential");
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteNote (Model model, @PathVariable Integer credentialId, Authentication authentication){
        Integer userId = userService.getUserId(authentication.getName());
        Integer credentialIsDeleted = credentialService.deleteCredential(credentialId, userId);

        if (credentialIsDeleted != null){
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage", "credential has been successfully deleted!");
        } else {
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "credential deletion failed!");
        }
        return "result";
    }
}
