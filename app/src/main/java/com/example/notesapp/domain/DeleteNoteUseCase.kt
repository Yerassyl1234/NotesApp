package com.example.notesapp.domain


class DeleteNoteUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int) {
        repository.deleteNote(noteId=noteId)
    }
}