package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SvgInfo {
    private String width;
    private String height;
}
