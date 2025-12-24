package com.andrea.eleves_note.ripository;

import com.andrea.eleves_note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note , Long> {
}
