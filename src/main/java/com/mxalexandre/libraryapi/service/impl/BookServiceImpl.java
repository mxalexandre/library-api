package com.mxalexandre.libraryapi.service.impl;

import com.mxalexandre.libraryapi.exception.BusinessException;
import com.mxalexandre.libraryapi.model.entity.Book;
import com.mxalexandre.libraryapi.model.repository.BookRepository;
import com.mxalexandre.libraryapi.service.BookService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    @CachePut("bookGetById")
    public Book save(Book book) {
        if (repository.existsByIsbn(book.getIsbn())) {
            throw new BusinessException("Isbn já cadastrado.");
        }
        return repository.save(book);
    }

    @Override
    @Cacheable("bookGetById")
    public Optional<Book> getById(long id) {
        return this.repository.findById(id);
    }

    @Override
    @CacheEvict("bookGetById")
    public void delete(Book book) {
        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book id can't be null.");
        }

        this.repository.delete(book);
    }

    @Override
    public Book update(Book book) {
        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book id can't be null.");
        }

        return this.repository.save(book);
    }

    @Override
    public Page<Book> find(Book filter, Pageable pageRequest) {
        Example<Book> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        ) ;
        return repository.findAll(example, pageRequest);
    }

}
