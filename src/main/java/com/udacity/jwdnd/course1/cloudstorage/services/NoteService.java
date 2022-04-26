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
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper)
    {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating Note bean");
    }

    public void addNote(String username, Note note) {
        Note newNote = new Note();
        newNote.setUserId(userMapper.getUser(username).getUserId());
        System.out.println(userMapper.getUser(username).getUserId());
        System.out.println(userMapper.getUser(username).getUsername());

        newNote.setNoteTitle(note.getNoteTitle());
        System.out.println(note.getNoteTitle());

        newNote.setNoteDescription(note.getNoteDescription());
        System.out.println(note.getNoteDescription());

        noteMapper.addNote(newNote);
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public List<Note> getNotesByUserId(String username) {
        try {
            Integer userId = userMapper.getUser(username).getUserId();
            System.out.println("Used ID: " + userId.toString());
            return noteMapper.getNotesByUserId(userId);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
