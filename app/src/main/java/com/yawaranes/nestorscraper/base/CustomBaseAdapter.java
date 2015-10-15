package com.yawaranes.nestorscraper.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Extends from the class of the ITEM, not the list
 *
 * @param <T> Class extended from {@link BaseEntity}
 */
public abstract class CustomBaseAdapter<T extends BaseEntity> extends BaseAdapter {

    protected Context context;
    protected List<T> list;

    public void init(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return !list.isEmpty() ? list.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return !list.isEmpty() ? list.get(position) : null;
    }
}

