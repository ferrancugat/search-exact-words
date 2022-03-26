package org.example.library.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

public class FileUtils {
    public static final String FILE_EXTENSION = "txt";

    private FileUtils(){
    }

    public static File[] getFilteredFilesFromDirectory(File indexableDirectory, String fileExtension) throws FileNotFoundException {
        if (!indexableDirectory.isDirectory() || !indexableDirectory.exists()){
            throw new FileNotFoundException("No directory given.");
        }
        return indexableDirectory.listFiles(new ExtensionFilenameFilter(fileExtension));
    }


    private static class ExtensionFilenameFilter implements FilenameFilter {

        String extension;

        ExtensionFilenameFilter(String fileExtension){
            this.extension = "."+fileExtension.toLowerCase();
        }
        public boolean accept(File dir, String name) {
            String lowercaseName = name.toLowerCase();
            if (lowercaseName.endsWith(extension)) {
                return true;
            } else {
                return false;
            }
        }
    };
}
