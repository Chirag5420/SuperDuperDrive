package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata});")
    int insertFiles(Files files);

    @Select("SELECT * FROM FILES WHERE userid = #{userid};")
    List <Files> selectFiles(int userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND fileId = #{fileId};")
    Files selectFile(Integer userid, Integer fileId);

    @Update("UPDATE FILES" +
            "SET filename = #{filename}, contenttype = #{contenttype}, filedata = #{filedata}" +
            "WHERE fileId = #{fileId};")
    int updateFiles(Files files);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid} AND userid = #{userid};")
    int deleteFiles(int fileid, int userid);

    @Select("SELECT CASE WHEN EXISTS (" +
            "SELECT * FROM FILES WHERE fileName=#{fileName} AND userId=#{userId}" +
            ") " +
            "THEN TRUE " +
            "ELSE FALSE END AS bool;")
    boolean isExistingFile (String fileName, Integer userId);
}
