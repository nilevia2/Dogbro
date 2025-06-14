package com.nilevia.dogbro.features.learn.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.ui.uistate.LearnDetailUiState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.ui.tooling.preview.Preview
import com.nilevia.dogbro.features.learn.domain.mapper.getTitle
import com.nilevia.dogbro.utils.components.ErrorComponent
import com.nilevia.dogbro.utils.components.LoadingComponent
import com.nilevia.dogbro.utils.components.SubScreen

@Composable
fun LearnDetailScreen(
    breed: Breed,
    viewModel: LearnViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val detailUiState by viewModel.detailUiState.collectAsState()

    LaunchedEffect(breed) {
        viewModel.getBreedDetail(breed)
    }

    LearnDetailScreenContent(
        breed = breed,
        detailUiState = detailUiState,
        onBack = onBack,
        onReload = { viewModel.getBreedDetail(breed) }
    )
}

@Composable
private fun LearnDetailScreenContent(
    breed: Breed,
    detailUiState: LearnDetailUiState,
    onBack: () -> Unit,
    onReload: () -> Unit = {}
) {

    SubScreen(
        breed.getTitle(),
        onBack
    ) {
        when (detailUiState) {
            is LearnDetailUiState.Loading -> LoadingComponent()
            is LearnDetailUiState.Error -> ErrorComponent("Error loading images") {
                onReload.invoke()
            }

            is LearnDetailUiState.Success -> {
                val images = detailUiState.img
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(images) { imageUrl ->
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun LearnDetailScreenPreview() {
    val breed = Breed("Labrador", "Retriever")
    val mockImages = listOf(
        "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg",
        "https://images.dog.ceo/breeds/labrador/n02099712_1234.jpg"
    )
    val mockUiState = LearnDetailUiState.Success(img = mockImages)
    LearnDetailScreenContent(
        breed = breed,
        detailUiState = mockUiState,
        onBack = {},
        onReload = {}
    )
}