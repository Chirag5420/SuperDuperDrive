package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrEditNote (Model model, Authentication authentication, Note note){
        String username = authentication.getName();
        Integer userId = userService.getUserId(username);
        note.setUserId(userId);
        try{
            noteService.addOrEditNote(note);
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage", note.getNoteTitle() + " has been successfully uploaded!");
        } catch (Exception e){
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "Error in adding note");
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("delete/{noteId}")
    public String deleteNote (Model model, @PathVariable Integer noteId, Authentication authentication){
        String username = authentication.getName();
        Integer userId = userService.getUserId(username);
        Integer noteIsDeleted = noteService.deleteNote(noteId, userId);

        if (noteIsDeleted != null){
            model.addAttribute("isSuccessful", true);
            model.addAttribute("successMessage", "note has been successfully deleted!");
        } else {
            model.addAttribute("hasAnError", true);
            model.addAttribute("errorMessage", "note deletion failed!");
        }
        return "result";
    }
}