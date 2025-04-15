package com.ucb.usecases

import com.ucb.domain.Book
import com.ucb.data.BookRepository

class SaveBook(private val repository: BookRepository) {
    suspend fun invoke(book: Book) {
        repository.saveBook(book)
    }
}
