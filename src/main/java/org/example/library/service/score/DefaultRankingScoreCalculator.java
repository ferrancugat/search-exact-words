package org.example.library.service.score;

import org.example.library.model.IndexDocument;

public class DefaultRankingScoreCalculator implements RankingScoreCalculator{

    @Override
    public int calculateScore(IndexDocument indexDocument, String[] words) {
        int totalMatches=0;
        for(String word:words){
            if (indexDocument.containsWord(word)){
                totalMatches++;
            }
        }
        int score = totalMatches*100/words.length;
        return score;
    }
}
