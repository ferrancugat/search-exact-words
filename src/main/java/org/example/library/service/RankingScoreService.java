package org.example.library.service;

import org.example.library.model.IndexDocument;
import org.example.library.model.RankingScore;

import java.util.List;

public interface RankingScoreService {

    public List<RankingScore> getTopIndexScoring(String[] words);

    public RankingScore calculateScore(IndexDocument indexDocument, String[] words) ;
}
