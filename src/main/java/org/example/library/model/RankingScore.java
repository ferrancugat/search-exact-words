package org.example.library.model;

public final class RankingScore {

    final String document;
    final int score;

    public RankingScore(String document, int score) {
        this.document = document;
        this.score = score;
    }

    public String getDocument() {
        return document;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return document+":"+score + '%';
    }
}
