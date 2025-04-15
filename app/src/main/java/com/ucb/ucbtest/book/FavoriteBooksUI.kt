package com.ucb.ucbtest.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucb.domain.Book
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text


@Composable
fun FavoriteBooksUI(viewModel: FavoriteBooksViewModel = hiltViewModel()) {
    val favoriteBooks by viewModel.favoriteBooks.observeAsState(emptyList())

    LaunchedEffect(true) {
        viewModel.loadFavoriteBooks()
    }

    LazyColumn {
        items(favoriteBooks) { book ->
            BookItem(book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = book.title, style = MaterialTheme.typography.titleLarge)
        Text(text = "Author: ${book.authors}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Year: ${book.publishYear}", style = MaterialTheme.typography.bodyMedium)
    }
}
