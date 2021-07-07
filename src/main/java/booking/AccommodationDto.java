package booking;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccommodationDto {

    public Long id;
    public String name;
    public String city;
    public int maxCapacity;
    public int availableCapacity;
    public int price;
    public List<Integer> bookings = new ArrayList<>();


}
