package org.example.library.model;

import org.example.library.service.score.RankingScoreCalculator;

public interface IndexDocument {

    long totalWords();

    String documentName();

    boolean containsWord(String word);

    default int calculateScore(RankingScoreCalculator scoreCalculator, String[] words){
        return  scoreCalculator.calculateScore(this, words);
    }
}
