package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimilarStations {
    private String stationId;
    private String stationName;
}
