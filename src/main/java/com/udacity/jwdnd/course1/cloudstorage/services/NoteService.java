package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper)
    {
        this.noteMapper = noteMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating Note bean");
    }

    public void addNote(Note note) {
        noteMapper.addNote(note);
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public List<Note> getNotesByUserId(Integer userId) {
        try {
            return noteMapper.getNotesByUserId(userId);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
