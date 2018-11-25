package cz.muni.fi.pa165.api.dto;

/**
 * @author Petr Valenta
 */
public class HotelCreateDTO {
    private String address;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
