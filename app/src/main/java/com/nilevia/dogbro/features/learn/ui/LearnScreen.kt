package com.nilevia.dogbro.features.learn.ui

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
import com.nilevia.dogbro.features.learn.domain.models.Breed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nilevia.dogbro.features.learn.ui.uistate.LearnUiState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import com.nilevia.dogbro.features.learn.domain.mapper.getTitle
import com.nilevia.dogbro.utils.components.ErrorComponent
import com.nilevia.dogbro.utils.components.LoadingComponent

@Composable
fun LearnScreen(viewModel: LearnViewModel = hiltViewModel(), onBreedSelected: (Breed) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }
    LaunchedEffect(Unit) {
        viewModel.getBreeds()
    }
    when (uiState) {
        is LearnUiState.Loading -> LoadingComponent()
        is LearnUiState.Error -> ErrorComponent("Error loading breeds") {
            viewModel.getBreeds()
        }
        is LearnUiState.Success -> {
            val breeds = (uiState as LearnUiState.Success).breeds
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(breeds) { breed ->
                    Column {
                        BreedItem(breed) { onBreedSelected(breed) }
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
fun BreedItem(breed: Breed, onClick: () -> Unit = {}) {
    val text = breed.getTitle()
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(24.dp).fillMaxWidth().clickable { onClick() }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBreedList() {
    val sampleBreeds = listOf(
        Breed("Labrador", null,),
        Breed("Poodle", "Miniature"),
        Breed("Bulldog", null, )
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

@Preview(showBackground = true)
@Composable
fun PreviewLearnScreen() {
    MaterialTheme {
        LearnScreen(onBreedSelected = {})
    }
} 