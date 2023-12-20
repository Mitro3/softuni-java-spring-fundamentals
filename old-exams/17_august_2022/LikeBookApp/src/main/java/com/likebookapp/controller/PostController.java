package com.likebookapp.controller;

import com.likebookapp.model.dto.CreatePostDTO;
import com.likebookapp.service.LoginService;
import com.likebookapp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {

    private final LoginService loginService;

    private final PostService postService;

    public PostController(LoginService loginService, PostService post) {
        this.loginService = loginService;
        this.postService = post;
    }
    @ModelAttribute("createPostDTO")
    public CreatePostDTO initCreatePostDTO() {
        return new CreatePostDTO();
    }

    @GetMapping("/post-add")
    public String addPost() {
        if (!this.loginService.isLoggedIn()) {
            return "redirect:/";
        }

        return "post-add";
    }

    @PostMapping("/post-add")
    public String addPost(@Valid CreatePostDTO createPostDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (!this.loginService.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors() || !this.postService.create(createPostDTO)) {
            redirectAttributes.addFlashAttribute("createPostDTO", createPostDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createPostDTO",
                    bindingResult);

            return "redirect:/post-add";
        }

        return "redirect:/home";
    }

    @GetMapping("/posts/like-post/{id}")
    public String likePost(@PathVariable Long id) {
        this.postService.likePostWithId(id, this.loginService.getLoggedUserId());
        return "redirect:/home";
    }

    @GetMapping("posts/remove/{id}")
    public String removePost(@PathVariable Long id) {
        this.postService.removePostById(id);
        return "redirect:/home";
    }
}
