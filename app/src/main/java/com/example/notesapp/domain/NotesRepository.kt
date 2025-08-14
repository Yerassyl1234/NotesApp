package com.example.notesapp.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun addNote(title:String, content:String)
    fun deleteNote(noteId:Int)
    fun editNote(note: Note)
    fun getAllNotes(): Flow<List<Note>>
    fun getNote(noteId: Int):Note
    fun searchNote(query:String):Flow<List<Note>>
    fun switchPinnedStatus(noteId: Int)
}