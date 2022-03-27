package org.example.library.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class FileUtilsTest {

    private File directory;

    @BeforeEach
    public void init() {
        if (directory == null) {
            ClassLoader classLoader = getClass().getClassLoader();
            directory = new File(Objects.requireNonNull(classLoader.getResource("files/")).getFile());
        }
    }

    @Test
    public void shouldFindTextDocuments() throws FileNotFoundException {
        List<String> fileNames = Arrays.asList("file1.txt", "file2.txt", "file3.txt", "file4.txt");

        File[] files = FileUtils.getFilteredFilesFromDirectory(directory, FileUtils.FILE_EXTENSION);

        Assertions.assertEquals(files.length, fileNames.size());
        for (File file : files) {
            Assertions.assertTrue(fileNames.contains(file.getName()));
        }
    }

    @Test
    public void shouldThrownWhenNotDirectory() {
        FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class, () -> {
            File file = new File("wrong/path");
            FileUtils.getFilteredFilesFromDirectory(file, FileUtils.FILE_EXTENSION);
        });
        Assertions.assertEquals("No directory given.", thrown.getMessage());
    }

    @Test
    public void shouldNotFindWithWrongExtension() throws FileNotFoundException {
        File[] files = FileUtils.getFilteredFilesFromDirectory(directory, "wrong");
        Assertions.assertEquals(files.length, 0);
    }
}