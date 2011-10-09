package com.appspot.nabaztag

import org.junit.Test
import static org.hamcrest.CoreMatchers.containsString
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertThat

public class GinkoTest {

    @Test
    public void testTimeTableContent() {
        String timeTable = Ginko.readTimeTable("CANOT")
        assertFalse timeTable.isEmpty()
        assertThat timeTable, containsString("minutes")
        assertThat timeTable, not(containsString("min "))
    }
}
