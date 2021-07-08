package booking;

import lombok.Data;

@Data
public class AccommodationDTO {

    public Long id;
    public String name;
    public String city;
    public int maxCapacity;
    public int availableCapacity;
    public int price;

}
