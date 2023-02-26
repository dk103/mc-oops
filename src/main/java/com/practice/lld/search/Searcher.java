package com.practice.lld.search;

import com.practice.lld.search.utils.SearchUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Searcher {
    IndexSearcher indexSearcher;
    QueryParser queryParser;
    Query query;

    public Searcher(String indexDirectoryPath)
            throws IOException {
        Directory indexDirectory =
                FSDirectory.open(Paths.get(indexDirectoryPath));
        IndexReader reader = DirectoryReader.open(indexDirectory);
        indexSearcher = new IndexSearcher(reader);
    }

    public TopDocs search(String searchQuery) throws IOException {
        Query nameQuery = new TermQuery(new Term("firstName", searchQuery));
        Query lastQuery = new TermQuery(new Term("lastName", searchQuery));
        Query phoneQuery = new TermQuery(new Term("phone", searchQuery));
        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(nameQuery, BooleanClause.Occur.SHOULD)
                .add(lastQuery, BooleanClause.Occur.SHOULD)
                .add(new BoostQuery(phoneQuery, 1.5F), BooleanClause.Occur.SHOULD)
                .build();
        return indexSearcher.search(booleanQuery, SearchUtils.MAX_SEARCH) ;
    }

    public Document getDoc(ScoreDoc scoreDoc) throws IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }
}
