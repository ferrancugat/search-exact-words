package org.example.library.service.impl;

import org.example.library.model.IndexDocument;
import org.example.library.model.WordCounterIndexDocument;
import org.example.library.service.DirectoryIndexService;
import org.example.library.store.IndexDocumentStore;
import org.example.library.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.library.utils.FileUtils.FILE_EXTENSION;

public class DirectoryIndexServiceImpl implements DirectoryIndexService {

    private final IndexDocumentStore indexDocumentStore;
    private final String STOP_WORD_FILE ="stopwords.txt";
    private String stopWordRegex;

    public DirectoryIndexServiceImpl(IndexDocumentStore indexDocumentStore) {
        this.indexDocumentStore = indexDocumentStore;
        initStopWordRegex();
    }

    private void initStopWordRegex() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(STOP_WORD_FILE).getFile());
        if (file != null) {
            try {
                stopWordRegex = Files.readAllLines(file.toPath())
                        .stream()
                        .flatMap(line -> Stream.of(line.split("\\s+")))//all-white spaces including tab character
                        .collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
            } catch (IOException e) {
                System.out.println("Stop words have not been initialized");
            }
        }
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
        Map<String, Long> wordCounter =
                Files.readAllLines(indexableFile.toPath())
                        .stream()
                        .flatMap(line -> Stream.of(line.split("\\s+")))//all-white spaces including tab character.
                        .map(String::trim)//remove break line
                        .map(String::toLowerCase)
                        .map(this::cleanPuntuation)
                        .map(this::cleanStopWords)
                        .filter(Predicate.not(String::isEmpty))
                        .filter(it -> !isDigit(it))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        indexDocumentStore.add(new WordCounterIndexDocument(wordCounter, indexableFile.getName()));
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
