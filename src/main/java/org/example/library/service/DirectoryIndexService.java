package org.example.library.service;

import java.io.File;
import java.io.IOException;

public interface DirectoryIndexService {

    void indexTextDirectory(File indexableDirectory) throws IOException;
    void indexTextFile(File indexableFile) throws IOException;
}
