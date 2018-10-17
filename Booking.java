package model;


/**
* @generated
*/
public class Booking {
    
    /**
    * @generated
    */
    private LocalDateTime from;
    
    /**
    * @generated
    */
    private LocalDateTime to;
    
    /**
    * @generated
    */
    private BigDecimal finalPrice;
    
    
    /**
    * @generated
    */
    private Customer customer;
    
    /**
    * @generated
    */
    private Room room;
    
    

    /**
    * @generated
    */
    public LocalDateTime getFrom() {
        return this.from;
    }
    
    /**
    * @generated
    */
    public LocalDateTime setFrom(LocalDateTime from) {
        this.from = from;
    }
    
    
    /**
    * @generated
    */
    public LocalDateTime getTo() {
        return this.to;
    }
    
    /**
    * @generated
    */
    public LocalDateTime setTo(LocalDateTime to) {
        this.to = to;
    }
    
    
    /**
    * @generated
    */
    public BigDecimal getFinalPrice() {
        return this.finalPrice;
    }
    
    /**
    * @generated
    */
    public BigDecimal setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
    
    
    
    /**
    * @generated
    */
    public Room getRoom() {
        return this.room;
    }
    
    /**
    * @generated
    */
    public Room setRoom(Room room) {
        this.room = room;
    }
    
    
    /**
    * @generated
    */
    public Customer getCustomer() {
        return this.customer;
    }
    
    /**
    * @generated
    */
    public Customer setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
    
}
