package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.CurrentUser;
import com.example.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CommentController  {
    @Autowired
    private CommentRepository commentRepository;
    @GetMapping(value = "/addComment")
    public String addComment(@ModelAttribute("comment") Comment comment, @RequestParam("toEmail") String toEmail, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes, @RequestParam("idArticle")int articleId, @RequestParam("text") String text) {
        if (userDetails==null){
            return "redirect:/home";
        }
        comment.setFromEmail(userDetails.getUsername());
        comment.setToEmail(toEmail);
        comment.setArticleId(articleId);
        comment.setText(text);
        commentRepository.save(comment);
        redirectAttributes.addAttribute("id",articleId);
        return "redirect:/articleByArticleId";
    }
}
