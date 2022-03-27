# search-exact-words

in memory structure to index files and search exact words

# Build

The jar package is build with the maven command from the root of the project:

```
mvn clean install
```

The Jar file can be executted from the target folder:

````
java -jar library-1.0-SNAPSHOT.jar <path-directory>
````
where it is needed to pass as argument the directory where the txt files are located.

- path-directory: /your/path/

Once executed, you will be able to search for words like

````
>search word1 word2 word3
````
and you will get as response the documents with a score ordered by ranking

```
search> consejo
file1.txt:100%
file4.txt:100%
file2.txt:0%
file3.txt:0%
```

# Indexing

The indexing of the documents is very simple at this moment. The following activities of cleanup are done

- Remove white spaces and tabulations
- Cleanup of the puntuation symbols .,",{},[]
- Remove of stopwords like (y,o, ..). A Spanish stopword list is embedded in the system.
- Cleanup of breaklines
- Convert to lowercase. On this implementation, words are considered equals when compared in lower case.

Once the clean up, transformation is done, a simple word counter is implemented. 
```
<document, word1, counter>
<file1, coche, 2>
<file2, coche, 1>
```
In the future, TF-IDF scoring could be explored.

# Scoring
The scoring of a search request is very simple. The following formula has been provided.

- All words found. Score:100%
- Some words are found. Score: words_matched*100 / total_words
- None word is found. Score: 0%

# Patterns

Some of the patterns applied

- Immutable pattern
- Singleton pattern, Context pattern
- Strategy pattern
- Static Factory Pattern



