package com.ucb.usecases

import com.ucb.domain.Book
import com.ucb.data.BookRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteBooks(private val repository: BookRepository) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.getFavoriteBooks()
    }
}
