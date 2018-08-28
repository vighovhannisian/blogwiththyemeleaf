package com.example.blog.controller;

import com.example.blog.model.Article;
import com.example.blog.model.Comment;
import com.example.blog.model.User;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(value = "/articleByArticleId")
    public String findArticleByArticleId(@RequestParam("id") int id, ModelMap map, Article article) {
        Article art=articleRepository.findOneById(id);
        List<Comment> comments = commentRepository.findAllByArticleId(id);
        map.addAttribute("commentsByArticle",comments);
        map.addAttribute("articleByIds", art);
        map.addAttribute("newComment", new Comment());
        map.addAttribute("userId", new User());
        article.setId(id);
        map.addAttribute("articleId", article);
        return "articleResult";
    }
    @GetMapping(value = "/articleById")
    public String findArticleById(@RequestParam("id") int id, ModelMap map, Article article, @AuthenticationPrincipal UserDetails userDetails) {
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        map.addAttribute("articleByIds", articles);
        return "articleResultes";
    }

    @RequestMapping(value = "/searchResult", method = RequestMethod.GET)
    public String result(ModelMap modelMap, @RequestParam(name = "search", required = false) String search) {
        List<Article> articleList = articleRepository.findAllByTitle(search);
        if (search != null && articleList.isEmpty()) {
            modelMap.addAttribute("mess", "Search Result with " + "' " + search + " '" + " not Found");
        } else {
            modelMap.addAttribute("result", articleList);
        }
        return "searchResult";
    }



}
