package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer deleteFile(Integer fileId, Integer userId){
        return fileMapper.deleteFile(fileId, userId);
    }

    public boolean isDuplicate (MultipartFile multipartFile, Integer userId){
        return fileMapper.isExistingFile(multipartFile.getOriginalFilename(), userId);
    }

    public void uploadFile(MultipartFile multipartFile, Integer userId) throws IOException {
        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setFileSize(multipartFile.getSize());
        file.setFileData(multipartFile.getBytes());
        file.setContentType(multipartFile.getContentType());
        file.setUserId(userId);
        fileMapper.uploadFile(file);
    }

    public File getFile(Integer fileId, Integer userId){
        return fileMapper.getFile(fileId, userId);
    }

    public List<File> getAllFiles(Integer userId){
        return fileMapper.getAllFiles(userId);
    }
}
