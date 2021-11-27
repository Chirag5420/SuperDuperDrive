package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (fileId,fileName,contentType,fileSize,userId,fileData) " +
            "VALUES (#{fileId},#{fileName},#{contentType},#{fileSize},#{userId},#{fileData});")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId} AND userId=#{userId};")
    Integer deleteFile(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId} AND userId=#{userId};")
    File getFile(Integer fileId, Integer userId);

    @Select("SELECT CASE WHEN EXISTS (" +
            "SELECT * FROM FILES WHERE fileName=#{fileName} AND userId=#{userId}" +
            ") " +
            "THEN TRUE " +
            "ELSE FALSE END AS bool;")
    boolean isExistingFile (String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE userId=#{userId};")
    List<File> getAllFiles(Integer userId);
}
