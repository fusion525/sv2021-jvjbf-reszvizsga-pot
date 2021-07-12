package booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccommodationCommand {

    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "City can not be blank")
    private String city;
    @Min(value = 10)
    private int maxCapacity;
    private int price;

}
