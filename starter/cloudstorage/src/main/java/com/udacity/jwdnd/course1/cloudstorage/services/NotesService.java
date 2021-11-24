package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    private final NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List <Notes> getAllNotes(Integer userID){
        return this.notesMapper.selectNotes(userID);
    }

    public Integer addOrEditNote(Notes notes){
        if(notes.getNoteid() == null){
            return this.notesMapper.insertNotes(notes);
        }

        return this.notesMapper.updateNotes(notes);
    }

    public Integer deleteNote(Integer noteID, Integer userID){
        return this.notesMapper.deleteNotes(noteID, userID);
    }
}
