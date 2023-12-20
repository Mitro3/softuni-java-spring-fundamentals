package com.dictionaryapp.service;

import com.dictionaryapp.model.entity.LanguageEnum;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.model.entity.Word;
import com.dictionaryapp.repo.WordRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class WordService {

    private final WordRepo wordRepo;

    private final UserService userService;

    private final LanguageService languageService;

    private final DateTimeFormatter dateTimeFormatter;

    public WordService(WordRepo wordRepo, UserService userService, LanguageService languageService, DateTimeFormatter dateTimeFormatter) {
        this.wordRepo = wordRepo;
        this.userService = userService;
        this.languageService = languageService;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public void initWords() {
        if (this.wordRepo.count() != 0) {
            return;
        }

        User admin1 = userService.findUserById(Long.parseLong("1"));
        User test1 = userService.findUserById(Long.parseLong("2"));

        Word word1Baustelle = new Word()
                .setTerm("die Sollbruchstelle")
                .setTranslation("predetermined breaking point")
                .setExample("Das Mittelloch der Feder ist eine Sollbruchstelle.")
                .setInputDate(LocalDate.parse("15-04-2012", dateTimeFormatter))
                .setLanguage(languageService.findLanguage(LanguageEnum.GERMAN));
        assignWordToUser(admin1, word1Baustelle);

        Word word2Meerschweinchen = new Word()
                .setTerm("das Meerschweinchen")
                .setTranslation("guinea pig")
                .setExample("Der Tag an dem das Meerschweinchen um die Welt flog")
                .setInputDate(LocalDate.parse("15-04-2012", dateTimeFormatter))
                .setLanguage(languageService.findLanguage(LanguageEnum.GERMAN));
        assignWordToUser(admin1, word2Meerschweinchen);

        Word word3Homme = new Word()
                .setTerm("Onirique")
                .setTranslation("dreamlike")
                .setExample("Onirique symbole de liberté pour les surréalistes, la cage figure dans nombre de leurs tableaux.")
                .setInputDate(LocalDate.parse("20-01-2022", dateTimeFormatter))
                .setLanguage(languageService.findLanguage(LanguageEnum.FRENCH));
        assignWordToUser(admin1, word3Homme);

        Word word4 = new Word()
                .setTerm("Dadivoso")
                .setTranslation("generous")
                .setExample("Seas dadivoso, pero capaz de recibir.")
                .setInputDate(LocalDate.parse("22-11-2008", dateTimeFormatter))
                .setLanguage(languageService.findLanguage(LanguageEnum.SPANISH));
        assignWordToUser(admin1, word4);

        Word word5 = new Word()
                .setTerm("Perenne")
                .setTranslation("everlasting")
                .setExample("Questa è la realtà, questo è il perenne principio della difesa dei diritti umani e delle libertà universali.")
                .setInputDate(LocalDate.parse("14-02-2011", dateTimeFormatter))
                .setLanguage(languageService.findLanguage(LanguageEnum.ITALIAN));
        assignWordToUser(test1, word5);

        this.userService.saveUser(admin1);
        this.userService.saveUser(test1);

    }

    private void assignWordToUser(User user, Word word) {
        Set<Word> assignedWords = user.getAddedWords();
        assignedWords.add(word);
        user.setAddedWords(assignedWords);

        word.setAddedBy(user);

        wordRepo.save(word);
    }
}
