package com.octopus.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String author;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Quote> quotes = new ArrayList<>();

    protected Author() {

    }

    public Author(final Integer id, final String author) {
        this.id = id;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }
}
