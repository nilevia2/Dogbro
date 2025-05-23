package com.nilevia.dogbro.features.ui.learn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nilevia.dogbro.features.repository.models.Breed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LearnScreen(viewModel: LearnViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }
    LaunchedEffect(Unit) {
        viewModel.getBreeds()
    }
    when (uiState) {
        is LearnUiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is LearnUiState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error loading breeds")
                Button(onClick = { viewModel.getBreeds() }) {
                    Text("Retry")
                }
            }
        }

        is LearnUiState.Success -> {
            val breeds = (uiState as LearnUiState.Success).breeds
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(breeds) { breed ->
                    Column {
                        BreedItem(breed)
                        Divider(
                            Modifier.padding(horizontal = 24.dp),
                            color = Color.LightGray,
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BreedItem(breed: Breed) {
    val text = if (breed.subBreed != null) "${breed.breed} - ${breed.subBreed}" else breed.breed
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(24.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBreedList() {
    val sampleBreeds = listOf(
        Breed("Labrador", null),
        Breed("Poodle", "Miniature"),
        Breed("Bulldog", null)
    )
    MaterialTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(sampleBreeds) { breed ->
                Column {
                    BreedItem(breed)
                    Divider(
                        Modifier.padding(horizontal = 24.dp),
                        color = Color.Gray,
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
} 