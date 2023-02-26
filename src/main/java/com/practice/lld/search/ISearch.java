package com.practice.lld.search;


import com.practice.lld.domain.Contact;

import java.io.IOException;
import java.util.Collection;

public interface ISearch {
    Collection<Contact> doSearch(String query) throws IOException;

}
