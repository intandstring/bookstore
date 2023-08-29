package com.book.management.system.bookstore.repository

import com.book.management.system.bookstore.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {

    fun findByTitle(title: String): List<Book>

    fun findByAuthor(author: String): List<Book>
}