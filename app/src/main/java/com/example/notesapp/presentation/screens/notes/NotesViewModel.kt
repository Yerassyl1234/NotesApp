@file:Suppress("OPT_IN_USAGE")

package com.example.notesapp.presentation.screens.notes

import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.DefaultTab.AlbumsTab.value
import androidx.compose.material3.SearchBar
import androidx.constraintlayout.helper.widget.Flow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.NotesRepositoryImpl
import com.example.notesapp.domain.AddNoteUseCase
import com.example.notesapp.domain.DeleteNoteUseCase
import com.example.notesapp.domain.EditNoteUseCase
import com.example.notesapp.domain.GetAllNotesUseCase
import com.example.notesapp.domain.GetNoteUseCase
import com.example.notesapp.domain.Note
import com.example.notesapp.domain.SearchNotesUseCase
import com.example.notesapp.domain.SwitchPinnedStatusUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {
    private val repository: NotesRepositoryImpl = NotesRepositoryImpl

    private val getAllNotesUseCase = GetAllNotesUseCase(repository)
    private val searchNotesUseCase = SearchNotesUseCase(repository)
    private val switchPinnedStatusUseCase = SwitchPinnedStatusUseCase(repository)

    private val query: MutableStateFlow<String> = MutableStateFlow(value = "")
    private val _state: MutableStateFlow<NotesScreenState> = MutableStateFlow(NotesScreenState())
    val state: StateFlow<NotesScreenState> = _state.asStateFlow()




    init {
        query
            .onEach { input ->
                _state.update { it.copy(query = input) }
            }
            .flatMapLatest { input ->
                if (input.isBlank()) {
                    getAllNotesUseCase()
                } else {
                    searchNotesUseCase(input)
                }
            }.onEach { notes ->
                val pinnedNotes = notes.filter { it.isPinned }
                val otherNotes = notes.filter { !it.isPinned }
                _state.update { it.copy(pinnedNotes = pinnedNotes, otherNotes = otherNotes) }
            }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: NotesCommand) {
        viewModelScope.launch {
        when (command) {
            is NotesCommand.InputSearchQuery -> {
                query.value=command.query
            }

            is NotesCommand.SwitchPinnedStatus -> {
                switchPinnedStatusUseCase(command.noteId)
            }
        }}
    }
}


sealed interface NotesCommand {
    data class InputSearchQuery(val query: String) : NotesCommand
    data class SwitchPinnedStatus(val noteId: Int) : NotesCommand
}


data class NotesScreenState(
    val query: String = "",
    val pinnedNotes: List<Note> = listOf(),
    val otherNotes: List<Note> = listOf()
)