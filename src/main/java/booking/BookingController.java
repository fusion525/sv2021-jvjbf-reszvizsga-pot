package booking;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accommodations")
public class BookingController {

    public BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<AccommodationDto> listAccommodations(@RequestParam Optional<String> city) {
        return bookingService.listAccommodations(city);
    }

    @GetMapping("/{id}")
    public AccommodationDto getAccommodationsById(@PathVariable("id") Long id) {
        return bookingService.getAccommodationsById(id);
    }

    @PostMapping
    public AccommodationDto createAccommodation(
            @RequestBody CreateAccommodationCommand command
    ) {
        return bookingService.createAccommodation(command);
    }



}
