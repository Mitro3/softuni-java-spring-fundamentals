package com.likebookapp.controller;

import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.service.LoginService;
import com.likebookapp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class HomeController {

    private final LoginService loginService;
    private final PostService postService;

    public HomeController(LoginService loginService, PostService postService) {
        this.loginService = loginService;
        this.postService = postService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        if (this.loginService.isLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        if (!this.loginService.isLoggedIn()) {
            return "redirect:/";
        }

        long loggedUserId = this.loginService.getLoggedUserId();
        User user = this.loginService.findUserById(loggedUserId);
        model.addAttribute("currentUser", user);

        Set<Post> postsFromCurrentUser = postService.getUserPosts(loggedUserId);
        model.addAttribute("userPosts", postsFromCurrentUser);

        Set<Post> postsFromOtherUsers = postService.getOtherUsersPosts(loggedUserId);
        model.addAttribute("otherUsersPosts", postsFromOtherUsers);

        long totalPostsFromOtherUsers = postsFromOtherUsers.size();
        model.addAttribute("otherUsersPostsCount", totalPostsFromOtherUsers);

        return "home";
    }


}
