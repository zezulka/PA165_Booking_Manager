package cz.muni.fi.pa165.service;

import java.util.List;

/**
 * Service used when the administrator wishes to search for a certain
 * hotel room availability at a certain time range.
 *
 * The administrator should also be able to see the list of bookings made for
 * a given time period.
 */
public interface RoomAvailabilityService {
    boolean isRoomAvailableDuring(TimeRange range, Room room);
    List<Booking> getBookingsInRange(TimeRange range, Room room);
}
