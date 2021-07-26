package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogicalLine {
    private String code;
    private String name;
}