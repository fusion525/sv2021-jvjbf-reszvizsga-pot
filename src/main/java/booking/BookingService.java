package booking;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private ModelMapper modelMapper;

    private List<Accommodation> accommodations = Collections.synchronizedList(new ArrayList<>());

    public BookingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<BookingDto> listAccommodations(Optional<String> city) {
        Type targetListType = new TypeToken<List<BookingDto>>(){}.getType();
        List<Accommodation> filteredAccommodations = accommodations.stream()
                .filter(a -> city.isEmpty() || a.getCity().toLowerCase().startsWith(city.get().toLowerCase()))
                    .collect(Collectors.toList());
        return modelMapper.map(filteredAccommodations, targetListType);
    }


    public BookingDto getAccommodationsById(Long id) {
        return modelMapper.map(accommodations.stream()
                .filter(a -> a.getId() == id).
                        findAny().orElseThrow(() -> new IllegalArgumentException("Accommodation not found " + id))
        , BookingDto.class);
    }
}
