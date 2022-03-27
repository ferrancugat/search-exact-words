package org.example.library.service.score;

import org.example.library.model.IndexDocument;

public interface RankingScoreCalculator {

    int calculateScore(IndexDocument indexDocument, String[] words);
}
