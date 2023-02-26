package com.practice.lld.search;

import com.practice.lld.domain.Contact;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;


public class Indexer {
    private IndexWriter indexWriter;

    public Indexer(String indexDirectoryPath) throws IOException {
        Directory indexDirectory =
                FSDirectory.open(Paths.get(indexDirectoryPath));

        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(indexDirectory, iwc);
    }

    public void createIndex(Contact contact) throws IOException {
      indexWriter.addDocument(asDocument(contact));
    }

    public void updateIndex(Contact contact) throws IOException {
        indexWriter.updateDocument(new Term("id", contact.getId().toString()), asDocument(contact));
    }

    public void deleteIndex(Contact contact) throws IOException {
        indexWriter.deleteDocuments(new Term(contact.getId().toString()));
    }

    public void close() throws CorruptIndexException, IOException {
        indexWriter.close();
    }

    private static Document asDocument(Contact contact) {
        Document document = new Document();
        //index file contents
        document.add(new StringField("phone", contact.getPhone(), Field.Store.YES));
        document.add(new StringField("id", contact.getId().toString(), Field.Store.YES));
        document.add(new TextField("firstName", contact.getFirstname(), Field.Store.YES));
        document.add(new TextField("lastName", contact.getLastname(), Field.Store.YES));
        return document;
    }
}
