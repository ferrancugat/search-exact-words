package org.example.library.model;


import java.util.HashMap;

public class PrefixTrieIndexDocument implements IndexDocument{

    private TrieNode root;
    private String document;
    private long totalWords;

    public PrefixTrieIndexDocument(String document) {
        this.root = new TrieNode();
        this.document = document;
    }

    @Override
    public long totalWords() {
        return totalWords;
    }

    @Override
    public String documentName() {
        return document;
    }

    @Override
    public void addWord(String word) {
        TrieNode current = root;

        for (char l: word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(l, c -> new TrieNode());
        }
        current.setWord(true);
        current.setCounter(current.getCounter()+1);
        totalWords++;
    }

    @Override
    public boolean containsWord(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isWord();
    }

    /**
     * Structure of a Prefix Trie structure. The computational cost of finding a word
     * is the length of the word.
     */
    public static class TrieNode {

        private HashMap<Character, TrieNode> children;
        private String content;
        private boolean isWord;
        private int counter;

        public HashMap<Character, TrieNode> getChildren() {
            return children;
        }

        public void setChildren(HashMap<Character, TrieNode> children) {
            this.children = children;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isWord() {
            return isWord;
        }

        public void setWord(boolean word) {
            isWord = word;
        }

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }
    }
}
