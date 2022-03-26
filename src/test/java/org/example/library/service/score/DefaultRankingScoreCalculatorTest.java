package org.example.library.service.score;

import org.example.library.model.IndexDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class DefaultRankingScoreCalculatorTest {

   @Test
   public void shouldReturnRightScore(){

      RankingScoreCalculator calculator = new DefaultRankingScoreCalculator();
      IndexDocument indexDocument = Mockito.mock(IndexDocument.class);
      Mockito.when(indexDocument.containsWord(any())).thenReturn(true);

      String[] words ={"words","feo","todo"};
      int score = calculator.calculateScore(indexDocument, words);
      Assertions.assertEquals(100,score);

      Mockito.when(indexDocument.containsWord(any())).thenReturn(false);
      score = calculator.calculateScore(indexDocument, words);
      Assertions.assertEquals(0,score);

      Mockito.when(indexDocument.containsWord(ArgumentMatchers.matches("feo"))).thenReturn(true);
      score = calculator.calculateScore(indexDocument, words);
      Assertions.assertEquals(33,score);
   }



}