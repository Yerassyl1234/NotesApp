package com.example.notesapp.domain

import android.adservices.adid.AdId
import kotlinx.coroutines.flow.Flow

class SearchNotesUseCase(
    private val repository: NotesRepository
) {
    operator fun invoke(query: String): Flow<List<Note>> {
        return repository.searchNote(query)
    }
}