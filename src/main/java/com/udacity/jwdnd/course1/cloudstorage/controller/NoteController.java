package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public String createNote(Authentication authentication, @ModelAttribute("note") Note note) {

        User user = userService.getUser(authentication.getName());

        noteService.createNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), user.getUserId()));

        HomeController.selectedTab = "notes";
        HomeController.successMessage = "Note was successfully added!";

        return "redirect:/home";
    }

    @RequestMapping(value ="/note", method = { RequestMethod.PATCH , RequestMethod.GET })
    public String updateNote(@ModelAttribute("note") Note note) {

        noteService.updateNote(note);

        HomeController.selectedTab = "notes";
        HomeController.successMessage = "Note was successfully updated!";

        return "redirect:/home";
    }

    @RequestMapping(value ="/note", method = RequestMethod.DELETE )
    public String deleteNote(@ModelAttribute("note") Note note) {

        noteService.deleteNote(note);

        HomeController.selectedTab = "notes";
        HomeController.successMessage = "Note was successfully deleted!";

        return "redirect:/home";
    }

}
