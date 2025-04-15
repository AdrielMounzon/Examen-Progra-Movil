package com.ucb.data.book

import com.ucb.domain.Book

interface IBookLocalDataSource {
    suspend fun saveBook(book: Book)
    suspend fun getSavedBooks(): List<Book>
}