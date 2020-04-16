package com.chilin.org.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class TextProcessorTest {

    @Test
    public void testConversion(){
        String miPrueba = "30-03-2020,12:13,15:13|30-04-2020,12:13,15:13|30-05-2020,12:13,15:13";
        byte[] bytes = TextProcessor.convertStringToByteArray(miPrueba);
        String result = TextProcessor.convertByteArrayToString(bytes);
        MatcherAssert.assertThat(result, Matchers.is(miPrueba));
    }

    @Test
    public void testListToString(){
        String result = TextProcessor.convertList();
    }

}