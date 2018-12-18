package cz.muni.fi.pa165.api.dto;

import java.util.List;
import java.util.Objects;

/**
 * @author Petr Valenta
 */
public class HotelDTO {
    private Long id;
    private String address;
    private String name;
    private List<RoomDTO> rooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> list) {
        this.rooms = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof HotelDTO)) {
            return false;
        }

        final HotelDTO that = (HotelDTO) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }

    @Override
    public String toString() {
        return "HotelDTO{" + "id=" + id + ", address=" + address + ", name=" + name + ", rooms=" + rooms + '}';
    }
}
