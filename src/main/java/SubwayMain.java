import dto.UpAndDown;

import java.util.ArrayList;
import java.util.List;

public class SubwayMain {
    public static void main(String[] args) {

        NaverSubway naverSubway = new NaverSubway();
        List<String> destinations = new ArrayList<>();
        destinations.add("동두천");
        destinations.add("소요산");

        final List<UpAndDown> collect = naverSubway.getFromToDest("구로", true, destinations);
        collect.stream().forEach(System.out::println);
    }
}
