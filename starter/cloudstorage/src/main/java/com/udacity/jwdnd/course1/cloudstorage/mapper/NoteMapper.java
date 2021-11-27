package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES (#{noteTitle},#{noteDescription},#{userId});")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer addNote(Note note);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle},noteDescription=#{noteDescription}, userId=#{userId} " +
            "WHERE noteId=#{noteId};")
    Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId} AND userId=#{userId};")
    Integer deleteNote(Integer noteId, Integer userId);

    @Select("SELECT noteId FROM NOTES INNER JOIN USERS on NOTES.userId=USERS.userId " +
            "WHERE USERS.username=#{username};")
    Integer getNoteId(String username);

    @Select("SELECT * FROM NOTES WHERE userId=#{userId};")
    List<Note> getAllNotes(Integer userId);
}
