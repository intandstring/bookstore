package com.book.management.system.bookstore.service

import com.book.management.system.bookstore.model.Book
import com.book.management.system.bookstore.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    fun findAll(): List<Book> = bookRepository.findAll()

    fun findById(id: Long): Book? = bookRepository.findById(id).orElse(null)

    fun save(book: Book): Book = bookRepository.save(book)

    fun deleteById(id: Long) = bookRepository.deleteById(id)

    fun findByTitle(title: String): List<Book> = bookRepository.findByTitle(title)

    fun findByAuthor(author: String): List<Book> = bookRepository.findByAuthor(author)
}
