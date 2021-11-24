package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FilesService {
    private final FilesMapper filesMapper;

    public FilesService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public void uploadFiles(MultipartFile multipartFile, Integer userID) throws IOException {
        Files files = new Files();
        files.setFilename(multipartFile.getOriginalFilename());
        files.setFilesize(multipartFile.getSize());
        files.setFiledata(multipartFile.getBytes());
        files.setContenttype(multipartFile.getContentType());
        files.setUserid(userID);
        filesMapper.insertFiles(files);
    }

    public Files getFile(Integer fileID, Integer userID){
        return filesMapper.selectFile(userID, fileID);
    }

    public List <Files> getAllFiles(Integer userID){
        return filesMapper.selectFiles(userID);
    }

    public Integer deleteFiles(Integer fileID, Integer userID){
        return filesMapper.deleteFiles(fileID, userID);
    }

    public boolean isDuplicateFile(MultipartFile multipartFile, Integer userID){
        return filesMapper.isExistingFile(multipartFile.getOriginalFilename(), userID);
    }
}
