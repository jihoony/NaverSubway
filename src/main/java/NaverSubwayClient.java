import dto.ScheduleResponse;
import dto.SubwayNumberObject;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

public interface NaverSubwayClient {

    @RequestLine("GET /v5/api/transit/subway/stations/{stationId}/schedule")
    ScheduleResponse getSchedule(@Param("stationId") String stationID);

    @RequestLine("GET /v5/api/subway/provide")
    List<SubwayNumberObject> getSubwayNumberList(@QueryMap Map<String, Object> queryMap);
}
