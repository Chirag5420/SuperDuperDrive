package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userId){
        return this.noteMapper.getAllNotes(userId);
    }

    public Integer addOrEditNote(Note note){
        if (note.getNoteId() == null){
            return this.noteMapper.addNote(note);
        }
        return this.noteMapper.updateNote(note);
    }

    public Integer deleteNote (Integer noteId, Integer userId){
        return this.noteMapper.deleteNote(noteId, userId);
    }

}
