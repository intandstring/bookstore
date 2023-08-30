package com.book.management.system.bookstore.controller

import com.book.management.system.bookstore.model.Book
import com.book.management.system.bookstore.service.BookService
import com.book.management.system.bookstore.utils.BookFormatter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService, private val bookFormatter: BookFormatter) {

    @GetMapping
    fun getAllBooks(): ResponseEntity<List<Book>> {
        return ResponseEntity.ok(bookService.findAll())
    }

    @GetMapping("/{id}")
    fun getBookId(@PathVariable id: Long): ResponseEntity<Book> {
        val book = bookService.findById(id)
        return if (book != null) ResponseEntity.ok(book) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createBook(@RequestBody book: Book): Book {
        val savedBook = bookService.save(book)
        println(bookFormatter.format(savedBook))
        return savedBook
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long): ResponseEntity<Void> {
        bookService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    data class SearchTitleRequest(val title: String)

    @PostMapping("/search/title")
    fun findByTitle(@RequestBody request: SearchTitleRequest): List<Book> {
        return bookService.findByTitle(request.title)
    }

    data class SearchAuthorRequest(val author: String)

    @PostMapping("/search/author")
    fun findByAuthor(@RequestBody request: SearchAuthorRequest): List<Book> {
        return bookService.findByAuthor(request.author)
    }
}
