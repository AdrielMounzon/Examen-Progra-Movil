package com.ucb.framework.mappers

import com.ucb.domain.Gitalias
import com.ucb.domain.Movie
import com.ucb.framework.dto.AvatarResponseDto
import com.ucb.framework.dto.MovieDto
import com.ucb.framework.persistence.GitAccount
import com.ucb.domain.Transaction
import com.ucb.framework.persistence.TransactionEntity
import com.ucb.domain.Book
import com.ucb.framework.dto.BookDto
import com.ucb.framework.persistence.BookEntity

fun AvatarResponseDto.toModel(): Gitalias {
    return Gitalias(
        login = login,
        avatarUrl = url
    )
}

fun Gitalias.toEntity(): GitAccount {
    return GitAccount(login)
}

fun GitAccount.toModel(): Gitalias {
    return Gitalias(
        alias,
        ""
    )
}

fun MovieDto.toModel(): Movie {
    return Movie(
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        tipo = when (this) {
            is Transaction.Income -> "Income"
            is Transaction.Expense -> "Expense"
        },
        nombre = this.nombre,
        precio = this.precio,
        descripcion = this.descripcion,
        fecha = this.fecha
    )
}

fun TransactionEntity.toModel(): Transaction {
    return when (this.tipo) {
        "Income" -> Transaction.Income(
            id = this.id,
            nombre = this.nombre,
            precio = this.precio,
            descripcion = this.descripcion,
            fecha = this.fecha
        )
        "Expense" -> Transaction.Expense(
            id = this.id,
            nombre = this.nombre,
            precio = this.precio,
            descripcion = this.descripcion,
            fecha = this.fecha
        )
        else -> throw IllegalArgumentException("Tipo desconocido")
    }
}

fun BookDto.toModel(): Book {
    return Book(
        title = this.title ?: "Sin título",
        authors = this.authors ?: listOf("Autor desconocido"),
        publishYear = this.firstPublishYear ?: 0
    )
}

fun Book.toEntity(): BookEntity {
    return BookEntity(
        title = this.title,
        authors = this.authors.joinToString(", "),
        publishYear = this.publishYear
    )
}

fun BookEntity.toModel(): Book {
    return Book(
        title = this.title,
        authors = this.authors.split(", "),
        publishYear = this.publishYear
    )
}
