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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }


    @GetMapping("/file")
    public ResponseEntity<byte[]> viewFile(RedirectAttributes redirectAttributes, Authentication authentication, Integer fileId) {
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

        if (!fileUpload.isEmpty()) {
            fileService.createFile(fileUpload, user.getUserId());
        }

        HomeController.selectedTab = "files";
        HomeController.successMessage = "File was successfully added!";

        return "redirect:/home";
    }

    @RequestMapping(value ="/file", method = RequestMethod.DELETE )
    public String deleteFile(@ModelAttribute("file") File file) {

        fileService.deleteFile(file);

        HomeController.selectedTab = "files";
        HomeController.successMessage = "File was successfully deleted!";

        return "redirect:/home";
    }
}
