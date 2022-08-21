package com.example.checkfalldown;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    /*@Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }*/

    @Test
    public void test() {
        String test = "old man\n fell.Location:N113.367,E:23:00";
        String[] split = test.split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }
}