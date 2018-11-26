package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.api.DateRange;
import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author Miloslav Zezulka
 */
public class DateRangeTest {

    @Test
    public void testThrowsNullArgs() {
        LocalDate date = LocalDate.of(2000, Month.MARCH, 1);
        assertThatThrownBy(() -> new DateRange(null, date))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DateRange(date, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testPrecedence() {
        LocalDate from = LocalDate.of(2000, Month.MARCH, 1);
        LocalDate to = LocalDate.of(2000, Month.MARCH, 2);
        assertThatThrownBy(() -> new DateRange(from, from))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DateRange(to, from))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testHappyScenario() {
        LocalDate from = LocalDate.of(2000, Month.MARCH, 1);
        LocalDate to = LocalDate.of(2000, Month.MARCH, 2);
        DateRange dr = new DateRange(from, to);
        assertThat(dr.getFromDate()).isEqualTo(from);
        assertThat(dr.getToDate()).isEqualTo(to);
    }

}
