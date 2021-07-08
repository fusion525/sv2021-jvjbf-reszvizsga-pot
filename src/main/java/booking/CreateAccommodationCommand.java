package booking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateAccommodationCommand {

    public String name;
    public String city;
    public int maxCapacity;
    public int price;

    public CreateAccommodationCommand(String name, String city, int maxCapacity, int price) {
        this.name = name;
        this.city = city;
        this.maxCapacity = maxCapacity;
        this.price = price;
    }
}
