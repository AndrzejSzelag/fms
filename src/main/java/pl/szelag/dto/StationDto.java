package pl.szelag.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class StationDto implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @ToString.Exclude
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, max = 3, message = "{constraint.string.length.notinrange}")
    @Setter
    @ToString.Include
    private String stationName;
}
