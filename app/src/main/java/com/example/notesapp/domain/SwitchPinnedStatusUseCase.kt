package com.example.notesapp.domain

import android.adservices.adid.AdId
import javax.inject.Inject

class SwitchPinnedStatusUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int) {
        return repository.switchPinnedStatus(noteId)
    }
}