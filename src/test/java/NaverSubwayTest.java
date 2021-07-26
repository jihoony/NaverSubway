import dto.UpAndDown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NaverSubwayTest {

    private NaverSubway naverSubway;

    @BeforeEach
    void setUp(){
        naverSubway = new NaverSubway();
    }

    @Test
    void getUpDirection1() {
        List<String> destinations = new ArrayList<>();
        destinations.add("동두천");
        destinations.add("소요산");

        final List<UpAndDown> fromToDest = naverSubway.getFromToDest("구로", true, destinations);
        assertNotNull(fromToDest);
        fromToDest.stream().forEach(System.out::println);
    }

    @Test
    void getUpDirection2() {
        List<String> destinations = new ArrayList<>();

        final List<UpAndDown> fromToDest = naverSubway.getFromToDest("가산디지털단지", true, destinations);
        assertNotNull(fromToDest);
        fromToDest.stream().forEach(System.out::println);
    }
}