package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class HomeController {

    public static String selectedTab;
    public static String successMessage;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final UserService userService;

    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        selectedTab = "files"; // setting files as the default selected tab
        successMessage = null;
    }

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model) {

        User user = userService.getUser(authentication.getName());

        // Adding the needed data for main page
        model.addAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
        model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));

        // Success Message Check
        if (successMessage != null) {
            model.addAttribute("showSuccessMessage", true);
            model.addAttribute("successMessage", successMessage);
        }

        // Adding needed attributes for the home page tabs visualization (Initial focus is on files-tab)
        if (Objects.equals(selectedTab, "files")) {
            model.addAttribute("filestab", "true");
            model.addAttribute("notestab", "false");
            model.addAttribute("credentialstab", "false");

            model.addAttribute("filestabclass", "nav-item nav-link active");
            model.addAttribute("notestabclass", "nav-item nav-link");
            model.addAttribute("credentialstabclass", "nav-item nav-link");

            model.addAttribute("filecontentclass", "tab-pane fade show active");
            model.addAttribute("notescontentclass", "tab-pane fade");
            model.addAttribute("credentialscontentclass", "tab-pane fade");
        }
        else if (Objects.equals(selectedTab, "notes")) {
            model.addAttribute("filestab", "false");
            model.addAttribute("notestab", "true");
            model.addAttribute("credentialstab", "false");

            model.addAttribute("filestabclass", "nav-item nav-link");
            model.addAttribute("notestabclass", "nav-item nav-link active");
            model.addAttribute("credentialstabclass", "nav-item nav-link");

            model.addAttribute("filecontentclass", "tab-pane fade");
            model.addAttribute("notescontentclass", "tab-pane fade show active");
            model.addAttribute("credentialscontentclass", "tab-pane fade");
        }
        else if (Objects.equals(selectedTab, "credentials")) {
            model.addAttribute("filestab", "false");
            model.addAttribute("notestab", "false");
            model.addAttribute("credentialstab", "true");

            model.addAttribute("filestabclass", "nav-item nav-link");
            model.addAttribute("notestabclass", "nav-item nav-link");
            model.addAttribute("credentialstabclass", "nav-item nav-link active");

            model.addAttribute("filecontentclass", "tab-pane fade");
            model.addAttribute("notescontentclass", "tab-pane fade");
            model.addAttribute("credentialscontentclass", "tab-pane fade show active");
        }

        successMessage = null;

        return "home";
    }

}