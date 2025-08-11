package com.example.notesapp.domain

import android.adservices.adid.AdId
import kotlinx.coroutines.flow.Flow

class SearchNotesUseCase {
    operator fun invoke(query: String): Flow<List<Note>> {
        TODO()
    }
}