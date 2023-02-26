package com.practice.lld.repository;

import com.practice.lld.domain.Contact;
import com.practice.lld.exception.NotFoundException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;


import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository implements IRepository<Contact> {
    private static Map<Key<PhoneNo>, Contact> MAP_STORAGE;

    public InMemoryRepository() {
        this.MAP_STORAGE =  new ConcurrentHashMap<>();
    }

    @Override
    public Contact save(Contact contact) {
       ZonedDateTime now = ZonedDateTime.now();
        Key<PhoneNo> phoneNoKey = new Key<>(PhoneNo.builder().phone(contact.getPhone()).build());
        MAP_STORAGE.computeIfAbsent(phoneNoKey, x -> contact.toBuilder()
                .id(UUID.randomUUID())
                .createdAt(now)
                .updatedAt(now)
                .build());

        return MAP_STORAGE.get(phoneNoKey);
    }

    @Override
    public void update(final Contact contact) throws NotFoundException {
        Key<PhoneNo> phoneNoKey = new Key<>(PhoneNo.builder().phone(contact.getPhone()).build());
        Optional.ofNullable(
               MAP_STORAGE.computeIfPresent(phoneNoKey, (x, oldContact)-> contact))
                .orElse(null);
    }

    @Override
    public Contact get(String id) throws NotFoundException {
        return MAP_STORAGE.values()
                .stream()
                .filter(contact -> Objects.equals(contact.getId(), UUID.fromString(id)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Contact findByPhoneNo(@NonNull String phone) throws NotFoundException {
        return MAP_STORAGE.values()
                .stream()
                .filter(contact -> Objects.equals(contact.getPhone(), phone))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isExist(@NonNull String phoneNumber) {
        return Optional.ofNullable(MAP_STORAGE
                .get(new Key<>(PhoneNo.builder().phone(phoneNumber).build())))
                .isEmpty();

    }

    @Override
    public Collection<Contact> getAll() {
        return MAP_STORAGE.values();
    }

    @SneakyThrows
    @Override
    public boolean delete(String id) {
        Contact deletedContact = get(id);
        Key<PhoneNo> phoneNoKey = new Key<>(PhoneNo.builder().phone(deletedContact.getPhone()).build());
        MAP_STORAGE.remove(phoneNoKey);
        return false;
    }

    @Builder
    @Getter
    static class PhoneNo implements Immutable {
        private final String phone;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PhoneNo phoneNo = (PhoneNo) o;
            return Objects.equals(this.phone, phoneNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(phone);
        }
    }
    static class Key<T extends Immutable> {
        private final T obj;
        Key(T obj) {
            this.obj = obj;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key<?> key = (Key<?>) o;
            return Objects.equals(obj, key.obj);
        }

        @Override
        public int hashCode() {
            return Objects.hash(obj);
        }
    }

    interface Immutable{
    }
}
