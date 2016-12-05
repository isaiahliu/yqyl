package org.trinity.yqyl.batch.test;

import java.util.Date;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class TestReader implements ItemReader<String> {
    int i = 0;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        i++;

        if (i > 3) {
            i = 0;
            return null;
        } else {
            Thread.sleep(2000);
            return new Date().toString();
        }
    }
}
