package pl.szelag.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Station implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATION_GENERATOR")
    @SequenceGenerator(name = "STATION_GENERATOR", sequenceName = "STATION_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "station_name", nullable = false, length = 3, unique = true)
    @Size(min = 2, max = 3, message = "{constraint.string.length.notinrange}")
    @Setter
    @ToString.Include
    private String stationName;
}
