package id.anhs.footballapps.utils

import id.anhs.footballapps.utils.MyDateFormat.dateEn
import id.anhs.footballapps.utils.MyDateFormat.gregorianDateTimeInMillis
import id.anhs.footballapps.utils.MyDateFormat.time
import org.junit.Assert.*
import org.junit.Test

class MyDateFormatTest {

    @Test
    fun testDateEn() {
        assertEquals("Sat, 20 Oct 2018", dateEn("2018-10-20"))
    }

    @Test
    fun testTime() {
        assertEquals("10:30", time("10:30:00+00:00"))
    }

    @Test
    fun testGregorianDateTimeInMillis() {
        assertEquals(
                1540018800000L,
                gregorianDateTimeInMillis("2018-10-20","14:00:00+00:00")
        )
    }


}