package com.example.notesapp.domain

import android.adservices.adid.AdId

class getNoteUseCase(
    private val repository: NotesRepository
) {
    operator fun invoke(noteId: Int) {
        return repository.getNote(noteId)
    }
}