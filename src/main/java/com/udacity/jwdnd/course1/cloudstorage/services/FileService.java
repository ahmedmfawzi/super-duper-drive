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

    public List<File> getAllFiles(){
        return fileMapper.getAllFiles();
    }

    public List<File> getFilesByUserId(Integer userId){
        return fileMapper.getFilesByUserId(userId);
    }

    public File getFileByFileId(Integer userId){
        return fileMapper.getFileByFileId(userId);
    }

    public boolean IsFileNameAvailable(MultipartFile file, Integer userId) {
        return fileMapper.getFileName(file.getOriginalFilename(), userId) == null;
    }

    public int createFile(MultipartFile file, Integer userId)  {

        byte[] data = null;

        try {
            data = file.getBytes();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return fileMapper.createFile(new File(null,file.getOriginalFilename(), file.getContentType(), data, userId ));
    }

    public int deleteFile(File file){
        return fileMapper.deleteFile(file);
    }
}
