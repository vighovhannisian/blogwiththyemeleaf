package com.example.blog.controller;

import com.example.blog.model.Article;
import com.example.blog.model.Category;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CategoryRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AdminController {
    @Value("${springBlog.user.pic.url}")
    private String articlePicture;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(value = "/adminHome")
    public String adminController(ModelMap map) {
        map.addAttribute("addArticle", new Article());
        map.addAttribute("addCategory", new Category());
        map.addAttribute("allCategories", categoryRepository.findAll());


        return "admin";
    }

    @GetMapping(value = "/addCategory")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryRepository.save(category);
        return "redirect:/adminHome";
    }

    @PostMapping(value = "/addArticle")
    public String addArticle(@ModelAttribute("article") Article article, @RequestParam("pict") MultipartFile multipartFile) throws IOException {
        File dir = new File(articlePicture);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(dir, picName));
        article.setImage(picName);
        articleRepository.save(article);
        return "redirect:/adminHome";
    }


    @GetMapping(value = "/articlePicture")
    public @ResponseBody
    byte[] userImage(@RequestParam("img") String picUrl) throws IOException {
        InputStream in = new FileInputStream(articlePicture + picUrl);
        return IOUtils.toByteArray(in);
    }

}
