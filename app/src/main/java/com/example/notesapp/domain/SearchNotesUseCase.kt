package com.example.notesapp.domain

import android.adservices.adid.AdId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(query: String): Flow<List<Note>> {
        return repository.searchNote(query)
    }
}