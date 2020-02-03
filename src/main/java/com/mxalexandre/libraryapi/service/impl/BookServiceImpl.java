package com.mxalexandre.libraryapi.service.impl;

import com.mxalexandre.libraryapi.exception.BusinessException;
import com.mxalexandre.libraryapi.model.entity.Book;
import com.mxalexandre.libraryapi.model.repository.BookRepository;
import com.mxalexandre.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        if(repository.existsByIsbn(book.getIsbn())) {
            throw new BusinessException("Isbn já cadastrado.");
        }
        return repository.save(book);
    }

}
