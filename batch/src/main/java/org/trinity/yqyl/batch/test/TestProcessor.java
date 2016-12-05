package org.trinity.yqyl.batch.test;

import org.springframework.batch.item.ItemProcessor;

public class TestProcessor implements ItemProcessor<String, String> {

    @Override
    public String process(final String item) throws Exception {
        return item;
    }

}
