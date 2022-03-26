package org.example.library.service;

import org.example.library.model.RankingScore;

import java.util.List;

public class CommandLineParser {

    final RankingScoreService rankingScoreService;

    public CommandLineParser(RankingScoreService rankingScoreService) {
        this.rankingScoreService = rankingScoreService;
    }

    public void handleCommand(String line) {
            if (line==null || line.isEmpty()){
                return;
            }
            if (":quit".equals(line)){
                exit();
            }
            processLine(line);
    }

    private void processLine(String line) {
        String[] words = line.split("\\s+");
        List<RankingScore> scores = rankingScoreService.getTopIndexScoring(words);
        scores.forEach(System.out::println);
    }

    private void exit(){
        System.exit(0);
    }
}
