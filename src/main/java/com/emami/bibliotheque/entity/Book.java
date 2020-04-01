package com.emami.bibliotheque.entity;

import com.emami.bibliotheque.util.InvalidInputException;
import com.emami.bibliotheque.util.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Book extends BaseEntity {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 240)
    private String title;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 240)
    private String author;
    @NotNull
    @Min(100000000L)
    @Max(value = 9999999999999L)
    private Long isbn;
    private Date created_at = Date.from(Instant.now());

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<UserBook> userBooks = new HashSet<>();

    public void addBook(UserBook userBook) {
        getUserBooks().add(userBook);
        userBook.setBook(this);
    }


    public Book(@NotNull @NotBlank @Size(min = 2, max = 240) String title,
                @NotNull @NotBlank @Size(min = 2, max = 240) String author,
                @NotNull @Min(100000000L) @Max(value = 9999999999999L) Long isbn) {
        setTitle(title);
        setAuthor(author);
        setIsbn(isbn);
    }

    public void setTitle(String title) {
        if (StringUtil.checkIfStringBoundariesAreInvalid(title)) {
            throw new InvalidInputException();
        }
        this.title = title.trim();
    }

    public void setAuthor(String author) {
        if (StringUtil.checkIfStringBoundariesAreInvalid(author)) {
            throw new InvalidInputException();
        }
        this.author = author.trim();
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }
}
