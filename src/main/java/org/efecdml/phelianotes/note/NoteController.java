package org.efecdml.phelianotes.note;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{ownerId}")
    public List<Note> getAllNotesByOwner(@PathVariable Long ownerId) {
        return noteService.getAllNotesByOwner(ownerId);
    }

    @GetMapping("/{ownerId}/{title}")
    public Optional<Note> getNoteByOwner(
            @PathVariable("ownerId") Long ownerId,
            @PathVariable("title") String title) {
        return noteService.getNoteByOwner(title, ownerId);
    }

    @PostMapping("/{ownerId}")
    public void addNote(
            @PathVariable("ownerId") Long ownerId,
            @RequestBody Note note
    ) {
        noteService.addNote(note, ownerId);
    }

    @PutMapping("/{ownerId}/{id}")
    public void updateNote(
            @PathVariable("ownerId") Long ownerId,
            @PathVariable("id") Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content
    ) {
        noteService.updateNoteByOwner(
                id,
                title,
                content,
                ownerId);
    }

    @DeleteMapping("/{ownerId}/{id}")
    public void deleteNote(
            @PathVariable("ownerId") Long ownerId,
            @PathVariable("id") Long id) {
        noteService.deleteNote(id, ownerId);
    }
}
