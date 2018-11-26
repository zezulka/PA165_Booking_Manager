package cz.muni.fi.pa165.utils;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDate;
import java.time.Month;
import cz.muni.fi.pa165.service.DateRange;
/**
 *
 * @author Miloslav Zezulka
 */
public class DateRangeUtilsTest {

    @Test
    public void sameRangesOverlap() {
        DateRange first = new DateRange(LocalDate.of(2015, Month.OCTOBER, 21), LocalDate.of(2015, Month.OCTOBER, 30));
        assertThat(DateRangeUtils.rangesOverlap(first, first)).isTrue();
    }
    
    @Test
    public void leftOverlap() {
        DateRange first = new DateRange(LocalDate.of(2015, Month.OCTOBER, 21), LocalDate.of(2015, Month.OCTOBER, 30));
        DateRange second = new DateRange(LocalDate.of(2015, Month.OCTOBER, 20), LocalDate.of(2015, Month.OCTOBER, 29));
        assertThat(DateRangeUtils.rangesOverlap(first, second)).isTrue();
    }
    
    @Test
    public void rightOverlap() {
        DateRange first = new DateRange(LocalDate.of(2015, Month.OCTOBER, 21), LocalDate.of(2015, Month.OCTOBER, 30));
        DateRange second = new DateRange(LocalDate.of(2015, Month.OCTOBER, 22), LocalDate.of(2015, Month.OCTOBER, 31));
        assertThat(DateRangeUtils.rangesOverlap(first, second)).isTrue();
    }
    
    @Test
    public void noOverlaps() {
        DateRange first = new DateRange(LocalDate.of(2015, Month.OCTOBER, 21), LocalDate.of(2015, Month.OCTOBER, 22));
        DateRange second = new DateRange(LocalDate.of(2015, Month.OCTOBER, 22), LocalDate.of(2015, Month.OCTOBER, 23));
        assertThat(DateRangeUtils.rangesOverlap(first, second)).isFalse();
    }
    
    
    @Test
    public void noOverlaps2() {
        DateRange first = new DateRange(LocalDate.of(2015, Month.OCTOBER, 10), LocalDate.of(2015, Month.OCTOBER, 11));
        DateRange second = new DateRange(LocalDate.of(2015, Month.OCTOBER, 8), LocalDate.of(2015, Month.OCTOBER, 9));
        assertThat(DateRangeUtils.rangesOverlap(first, second)).isFalse();
    }
}
