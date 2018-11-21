package cz.muni.fi.pa165.service;

import java.util.List;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;

/**
 * Service used when the administrator wishes to search for a certain
 * hotel room availability at a certain time range.
 *
 * The administrator should also be able to see the list of bookings made for
 * a given time period.
 */
public interface RoomAvailabilityService {
    List<Booking> getBookingsInRange(DateRange range, Room room);
}
