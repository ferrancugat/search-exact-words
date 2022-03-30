package org.example.library.model;

import java.util.HashMap;
import java.util.Map;

public final class WordCounterIndexDocument implements IndexDocument {

    final private Map<String, Long> wordCounter;
    final private String name;
    private long totalWords;

    public WordCounterIndexDocument(String name) {
        this(new HashMap<>(),name);
    }

    public WordCounterIndexDocument(Map<String, Long> wordCounter, String name) {
        this.wordCounter = wordCounter;
        this.name = name;
        totalWords = wordCounter.values().stream().mapToLong(it -> it).sum();
    }

    @Override
    public long totalWords() {
        return totalWords;
    }

    @Override
    public String documentName() {
        return name;
    }

    @Override
    public void addWord(String word) {
        wordCounter.merge(word,1L,(a,b)->a+b);
        totalWords++;
    }

    @Override
    public boolean containsWord(String word) {
        return wordCounter.containsKey(word.toLowerCase());
    }
}
