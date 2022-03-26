package org.example.library.store;

import org.example.library.model.IndexDocument;

import java.util.LinkedList;
import java.util.List;

public class InMemoryListIndexDocumentStore implements IndexDocumentStore {

    List<IndexDocument> indexDocuments= new LinkedList<>();

    @Override
    public void add(IndexDocument indexDocument) {
        indexDocuments.add(indexDocument);

    }

    @Override
    public List<IndexDocument> getAllIndexDocuments() {
        return indexDocuments;
    }
}
