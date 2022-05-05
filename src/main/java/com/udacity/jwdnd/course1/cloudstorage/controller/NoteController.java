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

        try {
            note.setUserId(user.getUserId());
            noteService.createNote(note);

            HomeController.selectedTab = "notes";
            HomeController.successMessage = "SUCCESS: Note was successfully added!";

        }
        catch (Exception ex) {
            HomeController.selectedTab = "notes";
            HomeController.errorMessage = "ERROR: Note was not added!";
        }

        return "redirect:/home";
    }

    @RequestMapping(value ="/note", method = { RequestMethod.PATCH , RequestMethod.GET })
    public String updateNote(Authentication authentication, @ModelAttribute("note") Note note) {

        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());

        try {
            if(noteService.updateNote(note) == 1){
                HomeController.selectedTab = "notes";
                HomeController.successMessage = "SUCCESS: Note was successfully updated!";
            }
            else {
                HomeController.selectedTab = "notes";
                HomeController.errorMessage = "ERROR: Note was was not updated!";
            }
        }
        catch (Exception ex) {
            HomeController.selectedTab = "notes";
            HomeController.errorMessage = "ERROR: Note was was not updated!";
        }


        return "redirect:/home";
    }

    @RequestMapping(value ="/note", method = RequestMethod.DELETE )
    public String deleteNote(Authentication authentication, @ModelAttribute("note") Note note) {

        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());

        try {
            if(noteService.deleteNote(note) == 1) {
                HomeController.selectedTab = "notes";
                HomeController.successMessage = "SUCCESS: Note was successfully deleted!";
            }
            else {
                HomeController.selectedTab = "notes";
                HomeController.errorMessage = "ERROR: Note was not deleted!";
            }

        }
        catch (Exception ex) {
            HomeController.selectedTab = "notes";
            HomeController.errorMessage = "ERROR: Note was not deleted!";
        }

        return "redirect:/home";
    }

}
