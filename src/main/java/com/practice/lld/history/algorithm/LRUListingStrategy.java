package com.practice.lld.history.algorithm;

import com.practice.lld.history.DialListingStrategy;
import lombok.Getter;

import java.util.Collection;

public class LRUListingStrategy<T> implements DialListingStrategy<T> {
    private Node<T> head;

    private static final int CAPPING = 10;
    private int size;
    private final int capacity;
    private final Object MUTEX = new Object();

    public LRUListingStrategy(int size) {
        this.head = null;
        if (size < 0) {
            throw new IllegalArgumentException("size should be positive number");
        }
        if (size > 5) {
            throw new IllegalArgumentException("Min size should be greater than 5");
        }
        this.capacity = size;
    }

    @Override
    public void put(T object) {
        synchronized(MUTEX) {
            Node<T> currNode = new Node<>(object, null);
            Node[] foundExistingRef = checkIfPresent(object, head);
            if (!(foundExistingRef == null || foundExistingRef[1] == null)) {
               currNode = foundExistingRef[1];
               foundExistingRef[0].next = currNode.next;
               currNode.next = null;
            }
            if (size == capacity) {
                Node[] parChildLastNode = getLastNode(head);
                parChildLastNode[0].next = null;
                parChildLastNode[1] = null;
                size--;
            }
            if (size == 0) {
                head = currNode;
            } else {
                Node prevHead = head;
                head.next = currNode;
                currNode.next = prevHead;
            }
            size++;
        }
    }

    private Node[] checkIfPresent(T object, Node curHead) {
        if (head == null) return null;
        Node found = null;
        Node prev = null;
        while (curHead != null) {
            prev = curHead;
            if (curHead.data.equals(object)) {
                found = curHead;
                break;
            }
            curHead = curHead.next;
        }
        return new Node[]{prev, curHead};
    }
    private Node[] getLastNode(Node curHead) {
        Node[] res = new Node[2];
        Node lastNode = curHead;
        Node par = null;
        while (lastNode.next != null) {
            par = lastNode;
            lastNode = lastNode.next;
        }
        res[0] = par;
        res[1] = lastNode;
        return res;
    }

    @Override
    public Collection getAll() {
        return null;
    }

    @Override
    public String strategyMethod() {
        return "least-recently-used";
    }

    @Getter
    private static class Node<T> {
        private Node<T> next;
        private T data;
        public Node(T data, Node<T> next) {
            this.next = next;
            this.data = data;
        }


    }
}
