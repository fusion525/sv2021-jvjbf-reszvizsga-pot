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
    public List<BookingDto> listAccommodations(@RequestParam Optional<String> city) {
        return bookingService.listAccommodations(city);
    }

    @GetMapping("/{id}")
    public BookingDto getAccommodationsById(@PathVariable("id") Long id) {
        return bookingService.getAccommodationsById(id);
    }



}
