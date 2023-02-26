package com.practice.lld.core;

import com.practice.lld.domain.Contact;

import java.util.List;
import java.util.Map;

public interface ISearchService {

   List<Contact> search(Map<String, String> request);
}
