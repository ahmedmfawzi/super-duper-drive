package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class HomeController {

    private NoteService noteService;
    private UserService userService;

    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {

        User user = userService.getUser(authentication.getName());

        // Adding the needed data for main page
        model.addAttribute("notes", this.noteService.getNotesByUserId(user.getUserId()));

        // Adding needed attributes for the home page tabs visualization (Initial focus is on files-tab)
        model.addAttribute("filestab", "true");
        model.addAttribute("notestab", "false");
        model.addAttribute("credentialstab", "false");

        model.addAttribute("filestabclass", "nav-item nav-link active");
        model.addAttribute("notestabclass", "nav-item nav-link");
        model.addAttribute("credentialstabclass", "nav-item nav-link");

        model.addAttribute("filecontentclass", "tab-pane fade show active");
        model.addAttribute("notescontentclass", "tab-pane fade");
        model.addAttribute("credentialscontentclass", "tab-pane fade");

        return "home";
    }

    @PostMapping("/note")
    public String postNote(Authentication authentication, Note note, Model model) {

        User user = userService.getUser(authentication.getName());
        this.noteService.addNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), user.getUserId()));
        note.setNoteTitle("");
        note.setNoteDescription("");

        model.addAttribute("notes", this.noteService.getNotesByUserId(user.getUserId()));

        model.addAttribute("files-tab", "false");
        model.addAttribute("notes-tab", "ture");
        model.addAttribute("credentials-tab", "false");

        model.addAttribute("filestabclass", "nav-item nav-link");
        model.addAttribute("notestabclass", "nav-item nav-link active");
        model.addAttribute("credentialstabclass", "nav-item nav-link");

        model.addAttribute("filecontentclass", "tab-pane fade");
        model.addAttribute("notescontentclass", "tab-pane fade show active");
        model.addAttribute("credentialscontentclass", "tab-pane fade");
        return "home";
    }
}