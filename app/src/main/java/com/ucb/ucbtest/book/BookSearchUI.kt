package com.ucb.ucbtest.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ucb.domain.Book
import androidx.navigation.NavController
import com.ucb.ucbtest.navigation.Screen

@Composable
fun BookSearchUI(
    navController: NavController,
    bookViewModel: BookViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    onBookClick: (Book) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val uiState by bookViewModel.state.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        // Botón para navegar a la pantalla de favoritos
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                navController.navigate(Screen.FavoriteBooksScreen.route)
            }) {
                Text("Favoritos")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Buscar libros", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Título del libro") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { bookViewModel.loadBooks(query) }) {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val ui = uiState) {
            is BookViewModel.BookUIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is BookViewModel.BookUIState.Error -> {
                Text(
                    ui.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            is BookViewModel.BookUIState.Loaded -> {
                LazyColumn {
                    items(ui.list.size) { index ->
                        val book = ui.list[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onBookClick(book) }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Título: ${book.title}", style = MaterialTheme.typography.bodyLarge)
                                Text("Autor(es): ${book.authors.joinToString()}", style = MaterialTheme.typography.bodyMedium)
                                Text("Año de publicación: ${book.publishYear}", style = MaterialTheme.typography.bodySmall)

                                IconButton(
                                    onClick = { bookViewModel.addBookToFavorites(book) },
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Icon(Icons.Default.Favorite, contentDescription = "Me gusta")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
