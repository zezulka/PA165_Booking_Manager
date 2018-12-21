package cz.muni.fi.pa165.sampledata;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.BookingService;
import cz.muni.fi.pa165.service.HotelService;
import cz.muni.fi.pa165.service.RoomService;
import cz.muni.fi.pa165.service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    private static final String JPEG = "image/jpeg";

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Resource(name="noCheck")
    private BookingService bookingService;

    @Override
    public void loadData() throws IOException {
        User john = user("john", "John", "Doe", "john.doe@gmail.com");
        User alice = user("alice", "Alice", "Foobar", "alice.foobar@gmail.com");
        User peter = user("peter", "Peter", "Gabriel", "peter.gabriel@gene.sis");
        user("admin", "Admin", "Istrator", "admin@gmail.com");

        DateRange past = pastRange();
        DateRange ongoing = ongoingRange();
        DateRange future = futureRange();

        Room first = room(101, "One large bed. Free Wi-Fi included.", new BigDecimal("1000.0"), "1.jpeg", RoomType.SINGLE_ROOM);
        Room second = room(102, "One large double bed. Flat-screen TV available.", new BigDecimal("1200.0"), "2.jpeg", RoomType.DOUBLE_ROOM);
        Room third = room(103, "The most luxurious suite you will find around. Free champagne and stunning city view.",
                new BigDecimal("1800.0"), "3.jpeg", RoomType.SUITE);
        hotelAndRooms("First World Hotel", "Malaysia, Genting Islands", Arrays.asList(first, second, third));
        booking(past, alice, first);
        booking(future, alice, first);
        booking(ongoing, john, second);

        first = room(101, "One large bed. Basic.", new BigDecimal("800.0"), "4.jpeg", RoomType.SINGLE_ROOM);
        second = room(102, "Two single beds. Entire unit is wheelchair accessible.", new BigDecimal("1200.0"), "5.jpeg", RoomType.DOUBLE_ROOM);
        third = room(103, "Very comfy double bed. It is also possible to admire the capital from a terrace.", 
                new BigDecimal("2500.0"), "6.jpeg", RoomType.COMFORT_DOUBLE_ROOM);
        hotelAndRooms("Izmailovo Hotel", "Russia, Moscow", Arrays.asList(first, second, third));
        booking(past, peter, third);
        booking(past, john, first);

        first = room(72, "No alcohol. Just a double bed.", new BigDecimal("1050.0"), "7.jpeg", RoomType.DOUBLE_ROOM);
        second = room(100, "A place to sleep.", new BigDecimal("1100.0"), "8.jpeg", RoomType.DOUBLE_ROOM);
        third = room(101, "A place to sleep, pray and sit.", new BigDecimal("1200.0"), "9.jpeg", RoomType.DOUBLE_ROOM);
        hotelAndRooms("Makkah Abraj Al Tayseer", "Saudi Arabia, Mecca", Arrays.asList(first, second, third));
        booking(future, john, first);
        booking(future, alice, second);
        booking(future, peter, third);

        first = room(101, "Lots of alcohol in the minibar.", new BigDecimal("2500.0"), "10.jpeg", RoomType.SINGLE_ROOM);
        second = room(102, "Lots of alcohol in the minibar. As a free gift, you'll get up to 100 chips "
                + "to any casino of your choice.", new BigDecimal("3000.0"), "11.jpeg", RoomType.DOUBLE_ROOM);
        third = room(103, "Lots of alcohol in the minibar. As a free gift, you'll get up to 100 chips "
                + "to any casino of your choice.", new BigDecimal("3000.0"), "12.jpeg", RoomType.DOUBLE_ROOM);
        hotelAndRooms("City Center", "USA, Las Vegas", Arrays.asList(first, second, third));

        // Let's leave those without any description to see what it does with the frontend.
        first = room(123, "", new BigDecimal("1260.0"), "13.jpeg", RoomType.SINGLE_ROOM);
        second = room(456, "", new BigDecimal("1500.0"), "14.jpeg", RoomType.DOUBLE_ROOM);
        third = room(789, "", new BigDecimal("1350.0"), "15.jpeg", RoomType.COMFORT_SINGLE_ROOM);
        hotelAndRooms("Caesars Palace", "USA, Las Vegas", Arrays.asList(first, second, third));
        booking(ongoing, john, second);
    }

    private void hotelAndRooms(String name, String address, List<Room> rooms) {
        Hotel h = new Hotel();
        h.setName(name);
        h.setAddress(address);
        h.setRooms(rooms);
        hotelService.create(h);
        assert(h.getId() != null);
        for (Room r : rooms) {
            r.setHotel(h);
            roomService.createRoom(r);
        }
    }

    private Room room(Integer number, String description, BigDecimal recommendedPrice, String imageFile, RoomType type) {
        Room r = new Room();
        r.setNumber(number);
        r.setDescription(description);
        r.setImage(readImage(imageFile));
        r.setImageMimeType(JPEG);
        r.setRecommendedPrice(recommendedPrice);
        r.setType(type);
        return r;
    }

    private User user(String password, String firstName, String surname, String email) {
        User u = new User();
        u.setFirstName(firstName);
        u.setSurname(surname);
        u.setEmail(email);
        u.setAdministrator(password.equals("admin"));
        userService.register(u, password);
        return u;
    }

    private void booking(DateRange range, User user, Room room) {
        Booking b = new Booking();
        b.setFromDate(range.getFromDate());
        b.setToDate(range.getToDate());
        b.setRoom(room);
        b.setUser(user);
        b.setTotal(room.getRecommendedPrice());
        bookingService.book(b);
    }

    private DateRange pastRange() {
        LocalDate now = LocalDate.now();
        return new DateRange(now.minusWeeks(1), now.minusDays(1));
    }

    private DateRange ongoingRange() {
        LocalDate now = LocalDate.now();
        return new DateRange(now.minusDays(2), now.plusDays(1));
    }

    private DateRange futureRange() {
        LocalDate now = LocalDate.now();
        return new DateRange(now.plusDays(1), now.plusDays(6));
    }

    private byte[] readImage(String file) {
        try (InputStream is = this.getClass().getResourceAsStream("/" + file)) {
            int nRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
        catch (IOException e) {
            return null;
        }
    }
}
