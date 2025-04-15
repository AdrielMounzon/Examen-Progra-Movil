package com.ucb.usecases

import com.ucb.domain.Book
import com.ucb.data.BookRepository
import com.ucb.data.NetworkResult

class SearchBooks(private val repository: BookRepository) {
    suspend fun invoke(query: String): NetworkResult<List<Book>> {
        return repository.searchBooks(query)
    }
}
