package booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {

    public long id;
    public String name;
    public String city;
    public int maxCapacity;
    public int availableCapacity;
    public int price;
    public List<Integer> bookings = new ArrayList<>();

    public Accommodation(long id, String name, String city, int maxCapacity, int price) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.maxCapacity = maxCapacity;
        this.price = price;
        this.availableCapacity = maxCapacity;
    }
}
