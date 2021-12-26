package com.feyon.codeset.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

/**
 * @author Feng Yong
 */
public interface Pageable {
    /**
     * the max size of a page.
     */
    int MAX_SIZE = 100;

    /**
     * return number of page start from 1.
     *
     * @return number of page.
     */
    @Nullable
    Integer getPage();

    /**
     * return size of a page.
     *
     * @return size of a page.
     */
    @Nullable
    Integer getSize();

    /**
     * return the offset in table according the page and size.
     *
     * @return the offset in table.
     */
    @JsonIgnore
    default int getOffset() {
        Integer page = getPage();
        Integer size = getSize();
        return (page == null ? 0 : Math.max(page - 1, 0)) * (size == null || size > MAX_SIZE ? 0 : size);
    }

    /**
     * return the limit in table according the size.
     *
     * @return the limit in table according the size.
     */
    @JsonIgnore
    default int getLimit() {
        Integer size = getSize();
        return size == null ? 0 : size;
    }

    /**
     * return a default pageable.
     *
     * @param page number of page start from 1.
     * @param size size of a page.
     * @return pageable object.
     */
    static Pageable of(int page, int size) {
        return new DefaultPageable(page, size);
    }


    class DefaultPageable implements Pageable {
        private final Integer page;

        private final Integer size;

        public DefaultPageable(Integer page, Integer size) {
            this.page = page;
            this.size = size;
        }

        @Override
        public Integer getPage() {
            return page;
        }

        @Override
        public Integer getSize() {
            return size;
        }
    }

}
