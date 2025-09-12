package com.example.notesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notesapp.presentation.screens.creation.CreateNoteScreen
import com.example.notesapp.presentation.screens.editing.EditNoteScreen
import com.example.notesapp.presentation.screens.navigation.NavGraph
import com.example.notesapp.presentation.screens.notes.NotesScreen
import com.example.notesapp.presentation.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
             NotesAppTheme {
                 NavGraph()
             }
        }
    }
}

