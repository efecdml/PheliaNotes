package org.efecdml.phelianotes.note;

import lombok.AllArgsConstructor;
import org.efecdml.phelianotes.appuser.AppUser;
import org.efecdml.phelianotes.appuser.AppUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final AppUserRepository appUserRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public List<Note> getAllNotesByOwner(Long ownerId) {
        return noteRepository.findAllByOwner(ownerId);
    }

    public Optional<Note> getNoteByOwner(String title, Long ownerId) {
        return noteRepository.findByTitleAndOwner(title, ownerId);
    }

    public void addNote(Note note, Long ownerId) {
        Optional<Note> noteOptional = noteRepository.findByTitleAndOwner(note.getTitle(), ownerId);

        if (noteOptional.isPresent()) {
            throw new IllegalStateException("Note with title " + note.getTitle() + " already exists.");
        }

        AppUser appUser = appUserRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalStateException("User couldn't found.."));

        note.setOwner(appUser);
        note.setDateCreated(LocalDateTime.now());

        noteRepository.save(note);
    }

    public void updateNoteByOwner(
            Long noteId,
            String title,
            String content,
            Long ownerId
    ) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalStateException("Note with ID " + noteId + " couldn't found.."));

        Optional<Note> noteOptional = noteRepository.findRepeatedTitleByOwner(title, noteId, ownerId);

        if (isOwner(noteId, ownerId)) {
            if (!noteOptional.isPresent()) {
                if (title != null && title.length() > 0) {
                    note.setTitle(title);
                    note.setDateUpdated(LocalDateTime.now());
                }
            } else {
                throw new IllegalStateException("This title is already exists..");
            }
        }

        if (content != null && content.length() > 0 && !Objects.equals(content, note.getContent())) {
            note.setContent(content);
            note.setDateUpdated(LocalDateTime.now());
        }

        noteRepository.save(note);
    }

    public void deleteNote(Long noteId, Long ownerId) {
        if (isOwner(noteId, ownerId)) {
            noteRepository.deleteById(noteId);
        }
    }

    private boolean isOwner(Long noteId, Long ownerId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalStateException("Note with ID " + noteId + " couldn't found.."));

        if (Objects.equals(ownerId, note.getOwner().getId())) {
            return true;
        }

        throw new IllegalStateException("Ownership with note didn't match..");
    }
}
