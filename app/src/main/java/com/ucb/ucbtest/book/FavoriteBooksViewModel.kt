package com.ucb.ucbtest.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.Book
import com.ucb.usecases.GetFavoriteBooks
import com.ucb.usecases.SearchBooks
import com.ucb.usecases.SaveBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteBooksViewModel @Inject constructor(
    private val getFavoriteBooks: GetFavoriteBooks
) : ViewModel() {

    private val _favoriteBooks = MutableLiveData<List<Book>>()
    val favoriteBooks: LiveData<List<Book>> = _favoriteBooks

    fun loadFavoriteBooks() {
        viewModelScope.launch {
            _favoriteBooks.value = getFavoriteBooks.invoke()
        }
    }
}
