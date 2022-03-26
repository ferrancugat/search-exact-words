package org.example.library.service;

import org.example.library.model.IndexDocument;
import org.example.library.service.impl.DirectoryIndexServiceImpl;
import org.example.library.store.InMemoryListIndexDocumentStore;
import org.example.library.store.IndexDocumentStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DirectoryIndexServiceTest {

    private IndexDocumentStore mockStore;
    private DirectoryIndexService service;

    @BeforeEach
    public void init(){
        mockStore = Mockito.mock(InMemoryListIndexDocumentStore.class);
        service = new DirectoryIndexServiceImpl(mockStore);
    }

    private File getFileResource(String resource) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(resource).getFile());
    }

    @Test
    public void shouldIndexDocumentAndStoreInDatabase() throws IOException {
        //given
        File directory = getFileResource("files/");
        //when
        service.indexTextDirectory(directory);
        // Then
        verify(mockStore, times(4)).add(any(IndexDocument.class)); // test passes

    }
    @Test
    public void shouldIndexDocumentContainWords() throws IOException {
        //given
        File document = getFileResource("files/file4.txt");

        //when
        service.indexTextFile(document);
        // Then
        ArgumentCaptor<IndexDocument> indexDocumentCaptor = ArgumentCaptor.forClass(IndexDocument.class);
        verify(mockStore, times(1)).add(indexDocumentCaptor.capture());

        IndexDocument indexDocument = indexDocumentCaptor.getValue();
        Assertions.assertTrue(indexDocument.containsWord("Consejo"));
        Assertions.assertTrue(indexDocument.containsWord("Sánchez"));
    }

    @Test
    public void shouldIndexDocumentNotContainPuntuation() throws IOException {
        //given
        File document = getFileResource("files/file2.txt");

        //when
        service.indexTextFile(document);
        // Then
        ArgumentCaptor<IndexDocument> indexDocumentCaptor = ArgumentCaptor.forClass(IndexDocument.class);
        verify(mockStore, times(1)).add(indexDocumentCaptor.capture());

        IndexDocument indexDocument = indexDocumentCaptor.getValue();
        Assertions.assertFalse(indexDocument.containsWord("."));
        Assertions.assertFalse(indexDocument.containsWord(","));
    }

    @Test
    public void shouldIndexDocumentNotContainStopWords() throws IOException {
        //given
        File document = getFileResource("files/file3.txt");

        //when
        service.indexTextFile(document);
        // Then
        ArgumentCaptor<IndexDocument> indexDocumentCaptor = ArgumentCaptor.forClass(IndexDocument.class);
        verify(mockStore, times(1)).add(indexDocumentCaptor.capture());

        IndexDocument indexDocument = indexDocumentCaptor.getValue();
        Assertions.assertFalse(indexDocument.containsWord("y"));
        Assertions.assertFalse(indexDocument.containsWord("a"));
    }

    @Test
    public void shouldIndexDocumentNotContainDigits() throws IOException {
        //given
        File document = getFileResource("files/file4.txt");

        //when
        service.indexTextFile(document);
        // Then
        ArgumentCaptor<IndexDocument> indexDocumentCaptor = ArgumentCaptor.forClass(IndexDocument.class);
        verify(mockStore, times(1)).add(indexDocumentCaptor.capture());

        IndexDocument indexDocument = indexDocumentCaptor.getValue();
        Assertions.assertFalse(indexDocument.containsWord("1972"));
    }
}