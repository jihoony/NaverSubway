package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceArea {
    private String id;
    private String name;
}
