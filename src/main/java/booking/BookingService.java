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


    public List<AccommodationDto> listAccommodations(Optional<String> city) {
        Type targetListType = new TypeToken<List<AccommodationDto>>(){}.getType();
        List<Accommodation> filteredAccommodations = accommodations.stream()
                .filter(a -> city.isEmpty() || a.getCity().toLowerCase().startsWith(city.get().toLowerCase()))
                    .collect(Collectors.toList());
        return modelMapper.map(filteredAccommodations, targetListType);
    }


    public AccommodationDto getAccommodationsById(Long id) {
        return modelMapper.map(accommodations.stream()
                .filter(a -> a.getId() == id).
                        findAny().orElseThrow(() -> new IllegalArgumentException("Accommodation not found " + id))
        , AccommodationDto.class);
    }

    public AccommodationDto createAccommodation(CreateAccommodationCommand command) {
        Accommodation accommodation = new Accommodation(idGenerator.incrementAndGet(), command.getName(), command.getCity(), command.getMaxCapacity(), command.getPrice());
        accommodations.add(accommodation);
        return modelMapper.map(accommodation, AccommodationDto.class);
    }
}
