package booking;

import lombok.Data;

@Data
public class CreateAccommodationCommand {

    public String name;
    public String city;
    public int maxCapacity;
    public int price;

}
