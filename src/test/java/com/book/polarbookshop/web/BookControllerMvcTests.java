package com.book.polarbookshop.web;

import com.book.polarbookshop.controller.BookController;
import com.book.polarbookshop.exception.BookNotFoundException;
import com.book.polarbookshop.service.BookService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(BookControllerMvcTests.MockConfig.class)
class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public BookService bookService() {
            return mock(BookService.class);
        }
    }

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);
        mockMvc
                .perform(get("/books/" + isbn))
                .andExpect(status().isNotFound());
    }

}