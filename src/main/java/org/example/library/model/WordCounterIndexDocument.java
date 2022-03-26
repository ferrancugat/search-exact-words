package org.example.library.model;

import java.util.Map;

public final class WordCounterIndexDocument implements IndexDocument{

    final Map<String,Long> wordCounter;
    final String name;
    final long totalWords;

    public WordCounterIndexDocument(Map<String,Long> wordCounter, String name){
        this.wordCounter = wordCounter;
        this.name = name;
        totalWords = wordCounter.values().stream().mapToLong(it->it).sum();
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
    public boolean containsWord(String word) {
        return wordCounter.containsKey(word.toLowerCase());
    }
}
