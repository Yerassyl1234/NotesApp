package com.example.notesapp.presentation.screens.editing

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.presentation.screens.editing.EditNoteCommand.InputTitle
import com.example.notesapp.presentation.ui.theme.Content
import com.example.notesapp.presentation.ui.theme.CustomIcons
import com.example.notesapp.presentation.utils.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    modifier: Modifier = Modifier,
    noteId: Int,
    viewModel: EditNoteViewModel = hiltViewModel(
        creationCallback = {factory:EditNoteViewModel.Factory-> factory.create(noteId)}

    ),
    onFinished: () -> Unit

) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.processCommand(EditNoteCommand.AddImage(it))
            }

        }
    )

    when (currentState) {
        is EditNoteState.Editing -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Edit Note",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                            actionIconContentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        actions = {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .clickable {
                                        imagePicker.launch("image/*")
                                    },
                                imageVector = CustomIcons.AddРhoto,
                                contentDescription = "Add photo from gallery",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                modifier = Modifier
                                    .padding( end = 16.dp)
                                    .clickable {
                                        viewModel.processCommand(EditNoteCommand.Delete)
                                    },
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete note"
                            )
                        },

                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 8.dp)
                                    .clickable {
                                        viewModel.processCommand(EditNoteCommand.Back)
                                    },
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding),
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        value = currentState.note.title,
                        onValueChange = {
                            viewModel.processCommand(InputTitle(it))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,

                            ),
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        placeholder = {
                            Text(
                                text = "Title",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                        }
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = DateFormatter.formatDateToString(currentState.note.updatedAt),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Content(
                        modifier=Modifier.weight(1f),
                        content = currentState.note.content,
                        onTextChanged = {index,text ->
                            viewModel.processCommand(EditNoteCommand.InputContent(text,index))
                        },
                        onDeleteImageClick = {
                            viewModel.processCommand(EditNoteCommand.DeleteImage(it))
                        }
                    )
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        onClick = {
                            viewModel.processCommand(EditNoteCommand.Save)
                        },
                        shape = RoundedCornerShape(10.dp),
                        enabled = currentState.isSaveEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),

                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimary
                        )


                    ) {
                        Text(
                            text = "Save Note"
                        )
                    }

                }

            }
        }

        is EditNoteState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }

        EditNoteState.Initial -> {

        }
    }
}

@Composable
private fun TextContent(
    modifier: Modifier=Modifier,
    text:String,
    onTextChanged:(String)-> Unit
){
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            ,
        value = text,
        onValueChange = onTextChanged,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,

            ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        placeholder = {
            Text(
                text = "Content",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        }
    )
}


