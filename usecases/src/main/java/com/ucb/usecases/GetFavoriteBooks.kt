package com.ucb.usecases

import com.ucb.domain.Book
import com.ucb.data.BookRepository
import com.ucb.data.NetworkResult

class GetFavoriteBooks(private val repository: BookRepository) {
    suspend fun invoke(): List<Book> {
        return repository.getFavoriteBooks()
    }
}
