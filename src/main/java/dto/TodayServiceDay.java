package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodayServiceDay {
    private String id;
    private String name;
}
