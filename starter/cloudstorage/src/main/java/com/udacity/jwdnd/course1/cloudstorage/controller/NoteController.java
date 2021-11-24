package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NotesService notesService;
    private UserService userService;

    public NoteController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrEditNote(Model model, Authentication authentication, Notes notes){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userID = user.getUserID();

        notes.setUserid(userID);
        try{
            notesService.addOrEditNote(notes);
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage", notes.getNotetitle() + " has been successfully uploaded");
        }
        catch(Exception e){
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "Error in adding note");
            e.printStackTrace();
        }

        return "result";
    }

    @GetMapping("delete/{noteid}")
    public String deleteNote(Model model, @PathVariable Integer noteId, Authentication authentication){
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userID = user.getUserID();
        Integer noteIsDeleted = notesService.deleteNote(noteId, userID);

        if(noteIsDeleted != null){
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage", "note has been successfully deleted");
        } else{
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "note deleteion failed");
        }

        return "result";
    }
}
