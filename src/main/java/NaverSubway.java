import dto.RealInfo;
import dto.ScheduleResponse;
import dto.UpAndDown;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NaverSubway {

    private final String HTTPS_SUBWAY_MAP_NAVER_COM = "https://map.naver.com/";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Map<String, Object> map;
    private NaverSubwayClient target;

    public NaverSubway() {
        target = getNaverSubwayClient();

        map = new HashMap<>();
        map.put("readPath", "1000");
        map.put("version", "6.24");
        map.put("requestFile", "metaData.json");
    }

    public List<UpAndDown> getFromToDest(String fromStation, boolean direction, List<String> destStationList) {

        final String stationId = getSubwayId(fromStation);

        final LocalDateTime now = LocalDateTime.now();

        return getUpDownTimeSchedule(now, stationId, direction, destStationList);
    }

    private String getSubwayId(String stationName) {

        final List<RealInfo> collect = target.getSubwayNumberList(map).stream()
                .map(subwayNumberObject ->
                        subwayNumberObject.getRealInfo().stream()
                                .filter(realInfo -> realInfo.getName().equals(stationName))
                                .collect(Collectors.toList()))
                .collect(Collectors.toList())
                .stream().flatMap(List::stream)
                .collect(Collectors.toList());

        final RealInfo resultInfo = collect.stream().findFirst().orElseThrow(() -> new RuntimeException("No Such SubwayName [" + stationName+"]"));
        System.out.println("resultInfo = " + resultInfo);

        return resultInfo.getId();
    }

    private NaverSubwayClient getNaverSubwayClient() {

        final NaverSubwayClient target = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NaverSubwayClient.class, HTTPS_SUBWAY_MAP_NAVER_COM);
        return target;
    }

    private List<UpAndDown> getUpDownTimeSchedule(LocalDateTime after, String stationId, boolean direction, List<String> stationList) {

        final String formatDateTime = after.format(formatter);
        System.out.println("formatDateTime = " + formatDateTime);

        final ScheduleResponse schedule = target.getSchedule(stationId);

        List<UpAndDown> directionCollection =
                direction ? schedule.getWeekdaySchedule().getUp() : schedule.getWeekdaySchedule().getDown();

        return directionCollection.stream()
                .filter(train-> filterTimeAfter(formatDateTime, train))
                .filter(train-> filterStationNames(train, stationList))
                .collect(Collectors.toList())
                ;
    }

    private boolean filterTimeAfter(String formatDateTime, UpAndDown train) {
        return train.getDepartureTime().compareTo(formatDateTime) >= 0;
    }

    private boolean filterStationNames(UpAndDown train, List<String> stationList) {
        return stationList == null || stationList.size() == 0 || stationList.stream().anyMatch(station->station.equals(train.getHeadsign()));
    }
}
