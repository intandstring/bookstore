package com.book.management.system.bookstore.utils

import com.book.management.system.bookstore.model.Book

class BookFormatter {
    fun format(book: Book): String {
        return """
            Title: ${book.title}
            Author: ${book.author}
            Published Year: ${book.publishedYear ?: "N/A"}
            Description: ${book.description ?: "N/A"}
        """.trimIndent()
    }
}
