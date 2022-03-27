package org.example.library.service;

import org.example.library.model.IndexDocument;
import org.example.library.model.RankingScore;

import java.util.List;

public interface RankingScoreService {

    List<RankingScore> getTopIndexScoring(String[] words);

    RankingScore calculateScore(IndexDocument indexDocument, String[] words);
}
