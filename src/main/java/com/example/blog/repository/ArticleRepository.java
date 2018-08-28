package com.example.blog.repository;

import com.example.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findAllByCategoryId (int id);

    @Query(value = "SELECT * FROM article WHERE LOWER(article.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(article.description) " +
            "LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY article.title ASC\n" + "  ",nativeQuery = true)
    List<Article> findAllByTitle(@Param("searchTerm") String find);

    Article findOneById(int id);

    List<Article> findAllByTop(boolean top);
}
