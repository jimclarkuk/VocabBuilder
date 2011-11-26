package com.steo.vocab.db;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Takes a list of IDItems and returns the list of each ones item
     */
    public static <Z> List<Z> getItems(List<IDItem<Z>> idItems) {

        ArrayList<Z> list = new ArrayList<Z>();
        for(IDItem<Z> idItem : idItems) {
            list.add(idItem.item);
        }

        return list;
    }
}

