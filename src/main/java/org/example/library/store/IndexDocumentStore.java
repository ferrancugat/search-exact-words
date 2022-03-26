package org.example.library.store;

import org.example.library.model.IndexDocument;

import java.util.List;

public interface IndexDocumentStore {

    void add(IndexDocument indexDocument);

    List<IndexDocument> getAllIndexDocuments();
}
