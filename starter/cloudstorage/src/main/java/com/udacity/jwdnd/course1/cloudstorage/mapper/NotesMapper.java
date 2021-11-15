package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES (#{notetitle}, #{notedescription}, #{userid});")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNotes(Notes notes);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid};")
    List <Notes> selectNotes(int userid);

    @Update("UPDATE NOTES " +
            "SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} " +
            "WHERE noteid = #{noteid};")
    int updateNotes(Notes note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid};")
    int deleteNotes(int noteid, int userid);
}
