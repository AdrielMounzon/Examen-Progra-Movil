package com.ucb.ucbtest.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.Book
import com.ucb.usecases.SearchBooks
import com.ucb.usecases.SaveBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksFromAPI: SearchBooks,
    private val addBookToFavorites: SaveBook
) : ViewModel() {

    sealed class BookUIState {
        object Loading : BookUIState()
        class Loaded(val list: List<Book>) : BookUIState()
        class Error(val message: String) : BookUIState()
    }

    private val _state = MutableStateFlow<BookUIState>(BookUIState.Loading)
    val state: StateFlow<BookUIState> = _state

    fun loadBooks(query: String) {
        _state.value = BookUIState.Loading
        viewModelScope.launch {
            val result = getBooksFromAPI.invoke(query)
            when (result) {
                is NetworkResult.Error -> {
                    _state.value = BookUIState.Error(result.error)
                }
                is NetworkResult.Success -> {
                    _state.value = BookUIState.Loaded(result.data)
                }
            }
        }
    }

    fun addBookToFavorites(book: Book) {
        viewModelScope.launch {
            addBookToFavorites.invoke(book) // Guardas el libro en la base de datos local
        }
    }
}
