package com.steo.vocab.db;


/**
 * Class to represent an id-name pair such as Category or Set
 */
public class IDItem<T> {

    public IDItem(int id, T item) {
        this.id = id;
        this.item = item;
    }

    public int id;
    public T item;
}

