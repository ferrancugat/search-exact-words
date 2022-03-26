package org.example.library;

import org.example.library.service.CommandLineParser;
import org.example.library.service.DirectoryIndexService;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }
        if (indexDirectory(args)) {
            startListeningCommands();
        }
    }

    private static void startListeningCommands() {
        CommandLineParser commandLineParser = ApplicationContext.getInstance().getCommandLineParser();
        try (Scanner keyboard = new Scanner(System.in)) {
            while (true) {
                System.out.print("search> ");
                final String line = keyboard.nextLine();
                commandLineParser.handleCommand(line);
            }
        }
    }

    private static boolean indexDirectory(String[] args) {
        final File indexableDirectory = new File(args[0]);
        try {
            DirectoryIndexService directoryIndexService = ApplicationContext.getInstance().getDirectoryIndexService();
            directoryIndexService.indexTextDirectory(indexableDirectory);
        } catch (IOException e) {
            System.out.println("There has been an error indexing directory: "+e.getMessage());
            return false;
        }
        return true;
    }
}
