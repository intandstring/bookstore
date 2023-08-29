package com.book.management.system.bookstore.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "books")
data class Book (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @get: NotBlank
        @get: Size(min = 2, max = 100)
        var title: String,

        @get: NotBlank
        @get: Size(min = 2, max = 100)
        var author: String,

        var description: String? = null,

        var publishedYear: Int? = null

)