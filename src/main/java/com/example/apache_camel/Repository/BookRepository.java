package com.example.apache_camel.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apache_camel.Entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);
}
