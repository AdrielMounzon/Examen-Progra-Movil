package com.ucb.framework.mappers

import com.ucb.domain.Gitalias
import com.ucb.domain.Movie
import com.ucb.framework.dto.AvatarResponseDto
import com.ucb.framework.dto.MovieDto
import com.ucb.framework.persistence.GitAccount
import com.ucb.domain.Transaction
import com.ucb.framework.persistence.TransactionEntity

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