package booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @ResponseStatus(HttpStatus.CREATED)
    public AccommodationDTO createAccommodation(@Valid @RequestBody CreateAccommodationCommand command) {
        return bookingService.createAccommodation(command);
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
    public AccommodationDTO updateAccommodationPrice(@PathVariable("id") long id, @RequestBody UpdatePriceCommand command) {
        return bookingService.updateAccommodationPrice(id, command);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> invalidId(IllegalArgumentException iae) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("accommodation/not-found")).withTitle("No accommodation found on given id").withStatus(Status.NOT_FOUND)
                        .withDetail(iae.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleInvalidRequests(MethodArgumentNotValidException exp) {
        List<Violation> violations =
                exp.getBindingResult().getFieldErrors().stream()
                .map(fe -> new Violation(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());

        Problem problem =
            Problem.builder().withType(URI.create("accommodation/bad-reservation"))
            .withTitle("validationError").withStatus(Status.BAD_REQUEST).withDetail(exp.getMessage()).with("violation", violations).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }

}
