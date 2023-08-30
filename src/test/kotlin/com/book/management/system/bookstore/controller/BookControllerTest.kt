package com.book.management.system.bookstore.controller

import com.book.management.system.bookstore.model.Book
import com.book.management.system.bookstore.service.BookService
import com.book.management.system.bookstore.utils.BookFormatter
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BookController::class)
class BookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @MockBean
    private lateinit var bookFormatter: BookFormatter

    @Test
    fun `getAllBooks returns list of books`() {
        val books = listOf(Book(id = 1, title = "Test Title", author = "Test Author"))
        `when`(bookService.findAll()).thenReturn(books)

        mockMvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title").value("Test Title"))
            .andExpect(jsonPath("$[0].author").value("Test Author"))
    }

    @Test
    fun `getBookId returns a book for valid ID`() {
        val book = Book(id = 1, title = "Test Title", author = "Test Author")
        `when`(bookService.findById(1)).thenReturn(book)

        mockMvc.perform(get("/books/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Test Title"))
            .andExpect(jsonPath("$.author").value("Test Author"))
    }

    @Test
    fun `createBook saves and returns a book`() {
        val book = Book(title = "Test Title", author = "Test Author")
        val savedBook = Book(id = 1, title = "Test Title", author = "Test Author")

        `when`(bookService.save(book)).thenReturn(savedBook)

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(book))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Test Title"))
            .andExpect(jsonPath("$.author").value("Test Author"))

        verify(bookFormatter).format(savedBook)
    }

    @Test
    fun `deleteBook deletes a book by ID`() {
        doNothing().`when`(bookService).deleteById(1L)

        mockMvc.perform(delete("/books/1"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `findByTitle returns books with the specified title`() {
        val books = listOf(Book(id = 1, title = "Alice in Wonderland", author = "Lewis Carroll"))
        `when`(bookService.findByTitle("Alice in Wonderland")).thenReturn(books)

        val searchRequest = BookController.SearchTitleRequest(title = "Alice in Wonderland")

        mockMvc.perform(
            post("/books/search/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(searchRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title").value("Alice in Wonderland"))
    }

    @Test
    fun `findByAuthor returns books by the specified author`() {
        val books = listOf(Book(id = 1, title = "Alice in Wonderland", author = "Lewis Carroll"))
        `when`(bookService.findByAuthor("Lewis Carroll")).thenReturn(books)

        val searchRequest = BookController.SearchAuthorRequest(author = "Lewis Carroll")

        mockMvc.perform(
            post("/books/search/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(searchRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].author").value("Lewis Carroll"))
    }
}
