package org.example.library.service;

import org.example.library.model.RankingScore;
import org.example.library.service.impl.DirectoryIndexServiceImpl;
import org.example.library.service.impl.RankingScoreServiceImpl;
import org.example.library.store.InMemoryListIndexDocumentStore;
import org.example.library.store.IndexDocumentStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class RankingScoreServiceTest {

    private IndexDocumentStore indexDocumentStore;
    private RankingScoreService rankingScoreService;

    @BeforeEach
    public void init() throws IOException {
        if (indexDocumentStore == null) {
            ClassLoader classLoader = getClass().getClassLoader();
            File directory = new File(Objects.requireNonNull(classLoader.getResource("files/")).getFile());
            indexDocumentStore = new InMemoryListIndexDocumentStore();
            var directoryIndexService = new DirectoryIndexServiceImpl(indexDocumentStore);
            directoryIndexService.indexTextDirectory(directory);
            rankingScoreService = new RankingScoreServiceImpl(indexDocumentStore);
        }
    }

    @Test
    public void shouldReturnRankingListInScoreOrder() {

        String[] words = {"Consejo", "Sanchez"};

        List<RankingScore> rankingScoreList = rankingScoreService.getTopIndexScoring(words);

        for (int index = 1; index < rankingScoreList.size(); index++) {
            Assertions.assertTrue(rankingScoreList.get(index).getScore() <= rankingScoreList.get(index - 1).getScore());
        }
    }

}