package org.trinity.yqyl.batch.test;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class TestWriter implements ItemWriter<String> {

    @Override
    public void write(final List<? extends String> items) throws Exception {
        items.forEach(item -> System.out.println(item));
    }

}
