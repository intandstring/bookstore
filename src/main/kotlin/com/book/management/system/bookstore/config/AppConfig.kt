package com.book.management.system.bookstore.config

import com.book.management.system.bookstore.utils.BookFormatter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun bookFormatter(): BookFormatter {
        return BookFormatter()
    }
}
