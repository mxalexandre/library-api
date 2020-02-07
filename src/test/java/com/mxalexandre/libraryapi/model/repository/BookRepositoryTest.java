package com.mxalexandre.libraryapi.model.repository;

import com.mxalexandre.libraryapi.model.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado.")
    @Test
    public void returnTrueWhenIsbnExists() {
        String isbn = "123";
        Book book = createNewBook(isbn);
        entityManager.persist(book);

        boolean exists = repository.existsByIsbn(isbn);

        assertThat(exists).isTrue();

    }

    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado.")
    @Test
    public void returFalseWhenIsbnDoesntExists() {
        String isbn = "123";

        boolean exists = repository.existsByIsbn(isbn);

        assertThat(exists).isFalse();

    }

    @Test
    @DisplayName("Deve obeter um livro pelo Id.")
    public void findByIdTest() {
        Book book = createNewBook("123");
        entityManager.persist(book);

        Optional<Book> foundBook = repository.findById(book.getId());

        assertThat(foundBook.isPresent()).isTrue();
    }

    public static Book createNewBook(String isbn) {
        return Book.builder().title("Aventuras").author("Fulano").isbn(isbn).build();
    }
}
