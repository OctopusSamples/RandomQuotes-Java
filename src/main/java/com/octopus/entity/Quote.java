package com.octopus.entity;

import javax.persistence.*;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String quote;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public Quote() {

    }

    public Long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getQuote() {
        return quote;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                '}';
    }
}
