package cz.muni.fi.pa165.service;

import java.time.LocalDate;

/**
 * Represents an immutable date interval.
 *
 * @author Miloslav Zezulka
 */
public final class DateRange {

    private final LocalDate fromDate;
    private final LocalDate toDate;

    /**
     * Constructs a new date interval whose starting date is strictly before the
     * end date.
     *
     * @throws IllegalArgumentException {@code to} or {@code from} is null or
     * {@code to} is equal to or before {@code from}.
     * @param from lefthand side of the interval
     * @param to righthand side of the interval
     */
    public DateRange(LocalDate from, LocalDate to) {
        if (from == null) {
            throw new IllegalArgumentException("From cannot be null.");
        }
        if (to == null) {
            throw new IllegalArgumentException("To cannot be null.");
        }
        if (!to.isAfter(from)) {
            throw new IllegalArgumentException("From must precede to.");
        }
        this.fromDate = from;
        this.toDate = to;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + fromDate.hashCode();
        hash = 19 * hash + toDate.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof DateRange)) {
            return false;
        }
        DateRange otherRange = (DateRange) other;
        return fromDate.equals(otherRange.getFromDate())
                && toDate.equals(otherRange.getToDate());
    }

    @Override
    public String toString() {
        return "(" + fromDate + "; " + toDate + ")";
    }
}
