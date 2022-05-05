package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> viewFile(Authentication authentication, Integer fileId) {

        User user = userService.getUser(authentication.getName());

        File file = fileService.getFileByFileId(fileId);
        String filename = file.getFileName();
        ResponseEntity<byte[]> responseEntity = ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\""+filename+"\"")
                .body(file.getFileData());
        return responseEntity;
    }

    @PostMapping("/file")
    public String createFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload) {

        User user = userService.getUser(authentication.getName());

        try {
            if (!fileUpload.isEmpty()) {
                if (fileService.IsFileNameAvailable(fileUpload, user.getUserId())) {
                    fileService.createFile(fileUpload, user.getUserId());
                    HomeController.selectedTab = "files";
                    HomeController.successMessage = "SUCCESS: File was successfully added!";
                } else {
                    HomeController.selectedTab = "files";
                    HomeController.errorMessage = "ERROR: File already exists!";
                }
            } else {
                HomeController.selectedTab = "files";
                HomeController.errorMessage = "ERROR: There is no file to upload!";
            }
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
            HomeController.selectedTab = "files";
            HomeController.errorMessage = "ERROR: There was an error adding file!";
        }

        return "redirect:/home";
    }

    @RequestMapping(value ="/file", method = RequestMethod.DELETE )
    public String deleteFile(@ModelAttribute("file") File file) {

        try {
            fileService.deleteFile(file);
            HomeController.selectedTab = "files";
            HomeController.successMessage = "SUCCESS: File was successfully deleted!";
        }
        catch (Exception ex) {
            HomeController.selectedTab = "files";
            HomeController.errorMessage = "ERROR: File was not deleted!";
        }

        return "redirect:/home";
    }
}
