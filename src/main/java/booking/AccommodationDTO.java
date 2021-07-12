package booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccommodationDTO {

    private Long id;
    private String name;
    private String city;
    private int maxCapacity;
    private int availableCapacity;
    private int price;
    private List<Integer> bookings = new ArrayList<>();

}
