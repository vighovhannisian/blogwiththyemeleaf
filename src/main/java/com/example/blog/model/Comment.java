package com.example.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    @Column(name = "from_email")
    private String fromEmail;
    @Column(name = "to_email")
    private String toEmail;
    @Column
    private String text;
    @Column
    private String timestamp;
    @Column(name = "article_id")
    private int articleId;
}
