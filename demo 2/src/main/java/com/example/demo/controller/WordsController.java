package com.example.demo.controller;

import com.example.demo.domain.Words;
import com.example.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("words")
public class WordsController {

    @Autowired
    private WordService wordService;

    @PostMapping("/add-word")
    public String addWord(@RequestBody String word) {

        wordService.addWord(word);

        return word;
    }

    @GetMapping("/all-words")
    public Set<Words> listAllWords() {

        return wordService.listAllWords();
    }

    @GetMapping("/find-sub-words/{word}")
    public Set<String> findSubWords(@PathVariable("word") String word) {
        Set<String> subWords = wordService.findSubWords(word);
        if (subWords.isEmpty()) {
            return null;
        } else {
            return subWords;
        }
    }
}
