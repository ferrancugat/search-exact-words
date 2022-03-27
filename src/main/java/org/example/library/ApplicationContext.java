package org.example.library;

import org.example.library.service.CommandLineParser;
import org.example.library.service.DirectoryIndexService;
import org.example.library.service.RankingScoreService;
import org.example.library.service.impl.DirectoryIndexServiceImpl;
import org.example.library.service.impl.RankingScoreServiceImpl;
import org.example.library.store.InMemoryListIndexDocumentStore;
import org.example.library.store.IndexDocumentStore;

public class ApplicationContext {

    private static ApplicationContext INSTANCE;
    private final IndexDocumentStore indexDocumentStore;
    private final DirectoryIndexService directoryIndexService;
    private final RankingScoreService rankingScoreService;
    private final CommandLineParser commandLineParser;

    private ApplicationContext() {
        indexDocumentStore = new InMemoryListIndexDocumentStore();
        directoryIndexService = new DirectoryIndexServiceImpl(getIndexDocumentStore());
        rankingScoreService = new RankingScoreServiceImpl(getIndexDocumentStore());
        commandLineParser = new CommandLineParser(getRankingScoreService());
    }

    public static synchronized ApplicationContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationContext();
        }
        return INSTANCE;
    }

    public IndexDocumentStore getIndexDocumentStore() {
        return indexDocumentStore;
    }

    public DirectoryIndexService getDirectoryIndexService() {
        return directoryIndexService;
    }

    public RankingScoreService getRankingScoreService() {
        return rankingScoreService;
    }

    public CommandLineParser getCommandLineParser() {
        return commandLineParser;
    }
}
