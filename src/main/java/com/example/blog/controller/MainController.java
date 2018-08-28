package com.example.blog.controller;

import com.example.blog.model.*;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class MainController {

    private User user;
    @Value("${springBlog.user.pic.url}")
    private String userPicture;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping(value = "/home")
    public String main(ModelMap map) {

        map.addAttribute("addUser", new User());

        return "login";
    }

    @GetMapping(value = "/")
    public String index(ModelMap map) {
        map.addAttribute("allArticles", articleRepository.findAll());
        map.addAttribute("allCategories", categoryRepository.findAll());
        List<Article> topArticles = articleRepository.findAllByTop(true);
        map.addAttribute("topArticles", topArticles);
        map.addAttribute("article", new Article());
        return "index";
    }




    @PostMapping("/register")
    public String add(@ModelAttribute User user,
                      @RequestParam("pic") MultipartFile multipartFile) {
        File dir = new File(userPicture);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        try {
            multipartFile.transferTo(new File(dir, picName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setPicUrl(picName);
        user.setUserType(UserType.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/home";
    }

    @GetMapping(value = "/userPicture")
    public @ResponseBody
    byte[] userImage(@RequestParam("picture") String picUrl) throws IOException {
        InputStream in = new FileInputStream(userPicture + picUrl);
        return IOUtils.toByteArray(in);
    }


    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String login() {
        if (user == null) {
            return "loginError";

        }

        return null;
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess() {

        CurrentUser principal = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getUser().getUserType() == UserType.ADMIN) {
            return "redirect:/adminHome";
        } else {
            if (principal.getUser().getUserType() == UserType.USER) {
                return "redirect:/userHome";
            }
            return "redirect:/";
        }
    }



}
