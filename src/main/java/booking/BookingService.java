package booking;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private ModelMapper modelMapper;

    private AtomicLong idGenerator = new AtomicLong();

    private List<Accommodation> accommodations = Collections.synchronizedList(new ArrayList<>());

    public BookingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<AccommodationDTO> listAccommodations(Optional<String> city) {
        Type targetListType = new TypeToken<List<AccommodationDTO>>(){}.getType();
        List<Accommodation> filteredAccommodations = accommodations.stream()
                .filter(a -> city.isEmpty() || a.getCity().toLowerCase().startsWith(city.get().toLowerCase()))
                    .collect(Collectors.toList());
        return modelMapper.map(filteredAccommodations, targetListType);
    }


    public AccommodationDTO getAccommodationsById(long id) {
        return modelMapper.map(accommodations.stream()
                .filter(a -> a.getId() == id).
                        findFirst().orElseThrow(() -> new IllegalArgumentException("Accommodation not found " + id))
        , AccommodationDTO.class);
    }

    public AccommodationDTO createAccommodation(CreateAccommodationCommand command) {
        Accommodation accommodation = new Accommodation(idGenerator.incrementAndGet(), command.getName(), command.getCity(), command.getMaxCapacity(),command.getPrice());
        accommodations.add(accommodation);
        return modelMapper.map(accommodation, AccommodationDTO.class);
    }

    public void deleteAccommodations() {
        accommodations.clear();
    }

    public AccommodationDTO createNewReservation(long id, CreateReservationCommand reservationCommand) {

        Accommodation accommodation = accommodations.stream().filter(a -> a.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Accommodation not found: " + id));

        accommodation.getBookings().add(reservationCommand.getNumberOfPeople());
        accommodation.decreaseAvailableCapacity(reservationCommand.getNumberOfPeople());

        return modelMapper.map(accommodation, AccommodationDTO.class);
    }

    public AccommodationDTO updateAccommodationPrice(long id, UpdatePriceCommand command) {
        Accommodation accommodation = accommodations.stream().filter(a -> a.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Accommodation not found: " + id));

        accommodation.setPrice(command.getPrice());

        return modelMapper.map(accommodation, AccommodationDTO.class);
    }
}
