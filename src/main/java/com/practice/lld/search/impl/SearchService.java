package com.practice.lld.search.impl;

import com.practice.lld.core.IContactService;
import com.practice.lld.domain.Contact;
import com.practice.lld.exception.NotFoundException;
import com.practice.lld.search.ISearch;
import com.practice.lld.search.Searcher;
import lombok.SneakyThrows;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SearchService implements ISearch {

    Searcher searcher;
    IContactService<Contact> service;

    private CompletableFuture<List<Contact>> findByPhone(String phone) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return List.of(service.findByPhone(phone));
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        });
     }

    private  CompletableFuture<List<Contact>> findByFirstName(String name) {
        return CompletableFuture.supplyAsync(() -> service.getAll()
                       .stream()
                       .filter(contact -> !contact.getPhone().equalsIgnoreCase(name))
                       .filter(contact -> contact.getFirstname().equalsIgnoreCase(name)
                               || contact.getLastname().equalsIgnoreCase(name))
                       .collect(Collectors.toList()));
    }

    public SearchService(Searcher searcher, IContactService<Contact> service) {
        this.searcher = searcher;
        this.service = service;
    }

    @Override
    public Collection<Contact> doSearch(String query) throws IOException {
        TopDocs docs = searcher.search(query);
        List<String> docIds = Arrays.asList(docs.scoreDocs)
                .stream()
                .map(scoreDoc -> {
                    try {
                        return searcher.getDoc(scoreDoc).getField("phone").stringValue();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());

        if (docIds.isEmpty()) {
            return findFromRepoAsFallback(query);
        }
        return docIds.stream()
                .map(phone -> {
                    try {
                        return service.findByPhone(phone);
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    @SneakyThrows
    private List<Contact> findFromRepoAsFallback(String query) {
        List<CompletableFuture<List<Contact>>> futures = List.of(findByFirstName(query), findByPhone(query));
        CompletableFuture<List<Contact>> matchedContacts = CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(v -> {
                    return futures.stream()
                            .flatMap(pageContentFuture -> pageContentFuture.join().stream())
                            .collect(Collectors.toList());
                });
        return matchedContacts.get();
    }

}
