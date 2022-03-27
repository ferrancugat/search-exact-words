package org.example.library.service.score;

public class RankingScoreCalculatorFactory {

    final static private DefaultRankingScoreCalculator DEFAULT_SCORE_CALCULATOR = new DefaultRankingScoreCalculator();

    public static RankingScoreCalculator getScoreCalculator() {
        return DEFAULT_SCORE_CALCULATOR;
    }
}
