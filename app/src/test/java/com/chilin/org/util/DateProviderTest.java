package com.chilin.org.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

class DateProviderTest {

    @Test
    public void givenDayOfTheWeek_WhenMethodCalled_ThenTrueWillBeReturned(){
        LocalDateTime sundayForSure = LocalDateTime.of(2020,Month.MARCH,22,13,00);
        boolean weekend = DateProvider.isWeekend(sundayForSure);
        MatcherAssert.assertThat(weekend,Matchers.is(true));
    }

    @Test
    public void testConversion(){
        String miPrueba = "30-03-2020,12:13,15:13|30-04-2020,12:13,15:13|30-05-2020,12:13,15:13";
        byte[] bytes = DateProvider.convertStringToByteArray(miPrueba);
        String result = DateProvider.convertByteArrayToString(bytes);
        MatcherAssert.assertThat(result,Matchers.is(miPrueba));
    }

    @Test
    public void testListToString(){
        String result = DateProvider.convertList();
    }

}