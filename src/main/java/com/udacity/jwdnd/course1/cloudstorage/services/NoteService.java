package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper)
    {
        this.noteMapper = noteMapper;
    }

    public void createNote(Note note) {
        noteMapper.createNote(note);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(Note note) {
        noteMapper.deleteNote(note);
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
