package com.ucb.usecases

import com.ucb.domain.Book
import com.ucb.data.BookRepository

class SearchBooks(private val repository: BookRepository) {
    suspend fun invoke(query: String): List<Book> {
        return repository.searchBooks(query)
    }
}
