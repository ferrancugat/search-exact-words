package org.example.library.service.score;

import org.example.library.model.IndexDocument;
import org.example.library.model.RankingScore;

public interface RankingScoreCalculator {

    int calculateScore(IndexDocument indexDocument, String[] words);
}
