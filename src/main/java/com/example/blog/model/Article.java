package com.example.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @NotNull
    private int id;
    @Column
    @NotNull
    private String title;
    @Column
    @NotNull
    private String description;
    @NotNull
    @Column(name = "pic_url")
    private String image;
    @ManyToOne
    private Category category;
    @Column
    private boolean top;
}
