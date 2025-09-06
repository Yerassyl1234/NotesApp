package com.example.notesapp.presentation.screens.notes

import com.example.notesapp.domain.Note

data class NotesScreenState(
    val query: String = "",
    val pinnedNotes: List<Note> = listOf(),
    val otherNotes: List<Note> = listOf()
)