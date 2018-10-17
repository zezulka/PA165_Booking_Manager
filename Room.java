package model;


/**
* @generated
*/
public class Room {
    
    /**
    * @generated
    */
    private int number;
    
    /**
    * @generated
    */
    private int capacity;
    
    /**
    * @generated
    */
    private BigDecimal price;
    
    
    /**
    * @generated
    */
    private Set<Booking> booking;
    
    /**
    * @generated
    */
    private Hotel hotel;
    
    

    /**
    * @generated
    */
    public int getNumber() {
        return this.number;
    }
    
    /**
    * @generated
    */
    public int setNumber(Integer number) {
        this.number = number;
    }
    
    
    /**
    * @generated
    */
    public int getCapacity() {
        return this.capacity;
    }
    
    /**
    * @generated
    */
    public int setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    
    /**
    * @generated
    */
    public BigDecimal getPrice() {
        return this.price;
    }
    
    /**
    * @generated
    */
    public BigDecimal setPrice(BigDecimal price) {
        this.price = price;
    }
    
    
    
    /**
    * @generated
    */
    public Set<Booking> getBooking() {
        return this.booking;
    }
    
    /**
    * @generated
    */
    public Set<Booking> setBooking(Booking booking) {
        this.booking = booking;
    }
    
    
    /**
    * @generated
    */
    public Hotel getHotel() {
        return this.hotel;
    }
    
    /**
    * @generated
    */
    public Hotel setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    
    
}
