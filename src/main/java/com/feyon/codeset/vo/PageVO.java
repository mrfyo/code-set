package com.feyon.codeset.vo;

import lombok.Data;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author Feng Yong
 */
@Data
public class PageVO<T> implements Iterable<T>{

    private Long total;

    private List<T> items;

    private Boolean last;



    public PageVO(Long total, List<T> items, Boolean last) {
        this.total = total;
        this.items = items;
        this.last = last;
    }

    public static <T> PageVO<T> of(long total, List<T> items) {
        return new PageVO<>(total, items, total <= items.size());
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        items.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return items.spliterator();
    }
}
