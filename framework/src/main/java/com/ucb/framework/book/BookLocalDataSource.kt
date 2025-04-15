package com.ucb.framework.book

import android.content.Context
import com.ucb.data.book.IBookLocalDataSource
import com.ucb.domain.Book
import com.ucb.framework.mappers.toEntity
import com.ucb.framework.mappers.toModel
import com.ucb.framework.persistence.BookRoomDatabase
import com.ucb.framework.persistence.IBookDAO

class BookLocalDataSource(val context: Context) : IBookLocalDataSource {
    private val bookDAO: IBookDAO = BookRoomDatabase.getDatabase(context).bookDAO()

    override suspend fun saveBook(book: Book) {
        bookDAO.insert(book.toEntity())
    }

    override suspend fun getSavedBooks(): List<Book> {
        return bookDAO.getAllBooks().map { it.toModel() }
    }
}
