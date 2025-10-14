package com.example.apache_camel.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.apache_camel.Entity.Book;
import com.example.apache_camel.Repository.BookRepository;

@Service
public class BookService {
    private final BookRepository books;

    public BookService(BookRepository books) {
        this.books = books;
    }

    public Book save(Book b) { return books.save(b); }

    public List<Book> findAll() { return books.findAll(); }

    public Book findByName(String name) { return books.findByName(name).orElse(null); }

    public void deleteById(Long id) { books.deleteById(id); }
}
