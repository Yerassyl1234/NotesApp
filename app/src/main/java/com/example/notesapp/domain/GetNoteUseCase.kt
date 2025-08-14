package com.example.notesapp.domain

class GetNoteUseCase(
    private val repository: NotesRepository
) {
    operator fun invoke(noteId: Int) {
        return repository.getNote(noteId)
    }
}