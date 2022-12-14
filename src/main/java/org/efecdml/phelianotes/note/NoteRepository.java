package org.efecdml.phelianotes.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query(value = "SELECT * FROM note n WHERE n.owner_id = :ownerId", nativeQuery = true)
    List<Note> findAllByOwner(Long ownerId);

    @Query(value = "SELECT * FROM note n WHERE n.title = :title AND n.owner_id = :ownerId", nativeQuery = true)
    Optional<Note> findByTitleAndOwner(String title, Long ownerId);

    @Query(value = "SELECT * FROM note n WHERE n.title = :title AND n.owner_id = :ownerId AND NOT n.id = :noteId", nativeQuery = true)
    Optional<Note> findRepeatedTitleByOwner(String title, Long ownerId, Long noteId);
}
