package com.example.demo.service;

import com.example.demo.domain.Words;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordService {

    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:ListOfAllWords.json")
    Resource resourceFile;

    public Set<Words> readJsonFromFile() {
        Set<Words> wordsSet = new HashSet<>();
        try {
            wordsSet =
                objectMapper.readValue(resourceFile.getFile(),
                    objectMapper.getTypeFactory().constructCollectionType(Set.class, Words.class));
        } catch (Exception e) {
            //log the error
        }
        return wordsSet;
    }

    public Set<String> readJsonFromFileAsString() {
        List<Words> wordsSet = new ArrayList<>();
        try {
            wordsSet =
                objectMapper.readValue(resourceFile.getFile(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Words.class));
        } catch (Exception e) {
            //log the error
        }
        Set<String> wordsSetAsString = new HashSet<>();
        for (Words word : wordsSet) {
            wordsSetAsString.add(word.getWord());
        }
        return wordsSetAsString;
    }

    public void addWord(String word) {
        Words w = new Words();
        w.setWord(word);
        try {
            JsonGenerator g = objectMapper.getFactory().createGenerator(new FileOutputStream(resourceFile.getFile()));
            objectMapper.writeValue(g, w);
            g.close();
        } catch (Exception e) {
            // log the error
        }
    }

    public Set<Words> listAllWords() {
        return readJsonFromFile();
    }

    public Set<String> findSubWords(String word) {
        Set<String> result = new HashSet<>();
        Set<String> wordFromJson = readJsonFromFileAsString();
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j <= i; j++) {
                String sub = word.substring(j, i + 1);
                if (wordFromJson.contains(sub)) {
                    result.add(sub);
                }
            }
        }
        return result;
    }
}
