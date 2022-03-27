package org.example.library.service.impl;

import org.example.library.model.IndexDocument;
import org.example.library.model.RankingScore;
import org.example.library.service.RankingScoreService;
import org.example.library.service.score.RankingScoreCalculatorFactory;
import org.example.library.store.IndexDocumentStore;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RankingScoreServiceImpl implements RankingScoreService {

    public static final int MAX_FILES = 10;

    final private IndexDocumentStore indexDocumentStore;

    public RankingScoreServiceImpl(IndexDocumentStore indexDocumentStore) {
        this.indexDocumentStore = indexDocumentStore;
    }

    public List<RankingScore> getTopIndexScoring(String[] words) {
        List<IndexDocument> indexes = indexDocumentStore.getAllIndexDocuments();
        List<RankingScore> scores = new LinkedList<>();
        indexes.forEach(indexDocument -> {
            RankingScore score = calculateScore(indexDocument, words);
            scores.add(score);
        });
        return scores.stream()
                .sorted((a, b) -> b.getScore() - a.getScore())
                .limit(MAX_FILES)
                .collect(Collectors.toList());
    }

    public RankingScore calculateScore(IndexDocument indexDocument, String[] words) {
        int score = indexDocument.calculateScore(RankingScoreCalculatorFactory.getScoreCalculator(), words);
        return new RankingScore(indexDocument.documentName(), score);
    }
}
