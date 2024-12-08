package com.example.plantproject2024.ui.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantproject2024.R
import com.example.plantproject2024.db.model.Message
import com.example.plantproject2024.ui.states.SearchUIState
import com.example.plantproject2024.ui.theme.PlantProject2024Theme
import com.example.plantproject2024.ui.theme.PurpleGrey80
import com.example.plantproject2024.vm.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBot(
    viewModel: MainViewModel = viewModel()
) {
    //Remember for bottom sheet
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = skipPartiallyExpanded)
    val scope = rememberCoroutineScope()
    //Remember for conversation
    val messages = remember { mutableStateListOf<Message>() }

    //Prompt input
    var inputText by remember { mutableStateOf("") }

    //    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = stringResource(R.string.results_placeholder)
    //    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
    var result by rememberSaveable { mutableStateOf(placeholderResult) }
    //Collect value
    val resultState by viewModel.generatedResponse.collectAsState()

    //Get local context
    val context = LocalContext.current

    FloatingActionButton(onClick = {showBottomSheet = !showBottomSheet}
    ) {
        Icon(Icons.Default.Face, contentDescription = "Chat")
    }
    if(showBottomSheet){
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false },
            modifier = Modifier.height(650.dp),
            sheetState = sheetState,
            ){
            var resultAdded = false
            //result = (resultState as SearchUIState.Success).outputContent
            LazyColumn{
                items (10){
                    Spacer(modifier = Modifier.padding(8.dp))
                    messages.forEach { message ->
                        MessageCard(message)
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }

            }
            //Push to bottom
            Spacer(modifier = Modifier.weight(0.5f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .background(PurpleGrey80)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = {inputText = it},
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message")},
                )
                IconButton(onClick = { viewModel.sendPrompt(inputText + "IMPORTANT:Response within 1 sentence, within 15 words"); messages.add(
                    Message(inputText, true)
                ); inputText = ""; resultAdded = false}, modifier = Modifier.padding(start = 8.dp)) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }
                if( resultState is SearchUIState.Loading) {
                    CircularProgressIndicator()
                } else {
                    if (resultState is SearchUIState.Success) {
                        result = (resultState as SearchUIState.Success).outputContent
                        if(messages.none{it.text == result}){
                            messages.add(Message(result, false))
                        }
                    } else if (resultState is SearchUIState.Error) {
                        result = (resultState as SearchUIState.Error).errorMessage
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun MessageCard(message: Message) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .align(if (message.isMe) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isMe) 48f else 0f,
                        bottomEnd = if (message.isMe) 0f else 48f
                    )
                )
                .background(PurpleGrey80)
                .padding(16.dp)
        ) {
            Text(text = message.text)
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun ChatBotPreview() {
    PlantProject2024Theme {
        MessageCard(Message("Hello", true))
    }
}