package com.dictionaryapp.init;

import com.dictionaryapp.service.LanguageService;
import com.dictionaryapp.service.UserService;
import com.dictionaryapp.service.WordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
public class Init implements CommandLineRunner {
    private final UserService userService;

    private final WordService wordService;

    private final LanguageService languageService;

    public Init(UserService userService, WordService wordService, LanguageService languageService) {
        this.userService = userService;
        this.wordService = wordService;
        this.languageService = languageService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.initAdmin();
        this.userService.initTestUser();
        this.languageService.initLanguages();
        this.wordService.initWords();
    }
}
