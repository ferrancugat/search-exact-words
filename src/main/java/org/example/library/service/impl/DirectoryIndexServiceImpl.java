package org.example.library.service.impl;

import org.example.library.model.IndexDocument;
import org.example.library.model.WordCounterIndexDocument;
import org.example.library.service.DirectoryIndexService;
import org.example.library.store.IndexDocumentStore;
import org.example.library.utils.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.library.utils.FileUtils.FILE_EXTENSION;

public class DirectoryIndexServiceImpl implements DirectoryIndexService {

    final private IndexDocumentStore indexDocumentStore;
    final private static String STOP_WORD_FILE = "stopwords.txt";
    private String stopWordRegex;

    public DirectoryIndexServiceImpl(IndexDocumentStore indexDocumentStore) {
        this.indexDocumentStore = indexDocumentStore;
        initStopWordRegex();
    }

    private void initStopWordRegex() {
        InputStream streamReader = getClass().getClassLoader().getResourceAsStream(STOP_WORD_FILE);
        stopWordRegex = new BufferedReader(new InputStreamReader(streamReader,
                StandardCharsets.UTF_8))
                .lines()
                .flatMap(line -> Stream.of(line.split("\\s+")))//all-white spaces including tab character
                .collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
    }

    @Override
    public void indexTextDirectory(File indexableDirectory) throws IOException {
        File[] files = FileUtils.getFilteredFilesFromDirectory(indexableDirectory, FILE_EXTENSION);
        if (files == null || files.length == 0) {
            throw new FileNotFoundException("No files found in the directory");
        }
        for (File fileDocument : files) {
            indexTextFile(fileDocument);
        }
    }

    @Override
    public void indexTextFile(File indexableFile) throws IOException {
        IndexDocument indexDocument = new WordCounterIndexDocument(indexableFile.getName()); // abstract factory missing
        Files.readAllLines(indexableFile.toPath())
                        .stream()
                        .map(this::cleanPuntuation)
                        .map(String::toLowerCase)
                        .map(this::cleanStopWords)
                        .flatMap(line -> Stream.of(line.split("\\s+")))//all-white spaces including tab character.
                        .filter(Predicate.not(String::isEmpty))
                        .filter(it -> !isDigit(it))
                        .forEach(it -> indexDocument.addWord(it));
        indexDocumentStore.add(indexDocument);
    }

    private boolean isDigit(String input) {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private String cleanPuntuation(String input) {
        return input.replaceAll("\\p{P}", ""); // remove Punctuation: One of !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
    }

    private String cleanStopWords(String it) {
        if (stopWordRegex != null) {
            return it.replaceAll(stopWordRegex, "");
        } else {
            return it;
        }
    }
}
