package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stations {
    private String id;
    private String text;
}
