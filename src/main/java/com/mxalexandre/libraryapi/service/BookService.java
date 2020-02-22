package com.mxalexandre.libraryapi.service;

import com.mxalexandre.libraryapi.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {

    Book save(Book any);

    Optional<Book> getById(long id);

    void delete(Book book);

    Book update(Book book);

    Page<Book> find(Book filter, Pageable pageRequest);
}
