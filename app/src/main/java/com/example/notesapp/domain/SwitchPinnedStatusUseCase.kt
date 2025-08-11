package com.example.notesapp.domain

import android.adservices.adid.AdId

class SwitchPinnedStatusUseCase(
    private val repository: NotesRepository
) {
    operator fun invoke(noteId: Int) {
        return repository.switchPinnedStatus(noteId)
    }
}