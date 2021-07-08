package booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
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
    public List<AccommodationDTO> listAccommodations(@RequestParam Optional<String> city) {
        return bookingService.listAccommodations(city);
    }

    @GetMapping("/{id}")
    public AccommodationDTO getAccommodationsById(@PathVariable("id") Long id) {
        return bookingService.getAccommodationsById(id);
    }

    @PostMapping
    public AccommodationDTO createAccommodation(@RequestBody CreateAccommodationCommand command) {
        return bookingService.createAccommodation(command);
    }

    @PutMapping("/{id}")
    public AccommodationDTO updateAccommodation(@PathVariable("id") long id, @RequestBody UpdateAccommodationsCommand updateAccommodationsCommand) {
        return bookingService.updateAccommodation(id, updateAccommodationsCommand);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccommodations() {
        bookingService.deleteAccommodations();
    }

    @PostMapping("/{id}/book")
    public AccommodationDTO createNewReservation(@PathVariable("id") long id, @RequestBody CreateReservationCommand createReservationCommand) {
        return bookingService.createNewReservation(id, createReservationCommand);
    }

    @PostMapping("/{id}")
    public AccommodationDTO updateAccommodationPrice(@PathVariable("id") long id, @RequestBody UpdateAccommodationsCommand command) {
        return bookingService.updateAccommodationPrice(id, command);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(IllegalStateException ise) {
        Problem problem =
                Problem.builder()
                .withType(URI.create("accommodation/not-found")).withTitle("Not found").withStatus(Status.NOT_FOUND)
                .withDetail(ise.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> invalidParameters(IllegalArgumentException ise) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("invalidParameters")).withTitle("Invalid Parameter").withStatus(Status.BAD_REQUEST)
                        .withDetail(ise.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> invalidNumberOfPeople(IndexOutOfBoundsException ibe) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("accommodation/bad-reservation")).withTitle("Available capacity less than new guests").withStatus(Status.BAD_REQUEST)
                        .withDetail(ibe.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }

}
