package com.ucb.data

import com.ucb.data.book.IBookLocalDataSource
import com.ucb.data.book.IBookRemoteDataSource
import com.ucb.domain.Book

class BookRepository(
    private val remoteDataSource: IBookRemoteDataSource,
    private val localDataSource: IBookLocalDataSource
) {

    suspend fun searchBooks(query: String): NetworkResult<List<Book>> {
        return remoteDataSource.searchBooks(query)
    }

    suspend fun saveBook(book: Book) {
        localDataSource.saveBook(book)
    }

    suspend fun getFavoriteBooks(): List<Book> {
        return localDataSource.getSavedBooks()
    }
}
