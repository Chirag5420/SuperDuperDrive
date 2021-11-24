package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
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
    private final CredentialsService credentialsService;
    private final UserService userService;

    public CredentialController(CredentialsService credentialsService, UserService userService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    @PostMapping
    public String insertOrUpdate(Model model, Credentials credentials, Authentication authentication){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserID();

        credentials.setUserid(userId);
        try{
            credentialsService.insertOrUpdateCredential(credentials);
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage",  "credential has been successfully inserted!");
        } catch (Exception e){
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "Error in adding credential");
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("delete/{credentialId}")
    public String deleteCredential(Model model, Authentication authentication, @PathVariable Integer credentialId){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserID();

        Integer credentialIsDeleted = credentialsService.deleteCredential(credentialId, userId);

        if(credentialIsDeleted != null){
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMessage", "credential has been successfully deleted!");
        }
        else{
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "credential deletion failed!");
        }

        return "result";
    }

}
