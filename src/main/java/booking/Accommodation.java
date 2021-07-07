package booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {

    public Long id;
    public String name;
    public String city;
    public int maxCapacity;
    public int availableCapacity;
    public int price;
    public List<Integer> bookings = new ArrayList<>();

    public Accommodation(String name, String city, int availableCapacity, int price) {
        this.name = name;
        this.city = city;
        this.availableCapacity = availableCapacity;
        this.price = price;
    }
}
