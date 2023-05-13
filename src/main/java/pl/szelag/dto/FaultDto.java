package pl.szelag.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class FaultDto implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @ToString.Exclude
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 200, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^(\\w+ ?)[a-zA-Z0-9 \\._'-]*$", message = "{constraint.string.incorrectchar}")
    @Setter
    @ToString.Include
    private String faultDescription;

    @NotNull(message = "{constraint.notnull}")
    @Setter
    @ToString.Exclude
    private FaultStatusDto status = FaultStatusDto.NEW;

    @NotNull(message = "{constraint.notnull}")
    @Setter
    @ToString.Exclude
    private StationDto station;

    @Setter
    @ToString.Exclude
    private ElectricianDto electrician;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^(\\w+ ?)[a-zA-Z\\'-]*$", message = "{constraint.string.incorrectchar}")
    @Setter
    @ToString.Exclude
    private String reported;

    @NotNull(message = "{constraint.notnull}")
    @Setter
    @ToString.Exclude
    private SupervisionDto supervisionAccepted;

    @NotNull(message = "{constraint.notnull}")
    @Setter
    @ToString.Exclude
    private SupervisionDto supervisionAssigned;

    @NotNull(message = "{constraint.notnull}")
    @ToString.Exclude
    @Future
    private Date createTimestamp;

    @ToString.Exclude
    @Future
    private Date modificationTimestamp;

    public FaultDto(Date createTimestamp, Long id, String faultDescription, FaultStatusDto status,
                    StationDto station, ElectricianDto electrician, String reported, SupervisionDto supervisionAccepted,
                    SupervisionDto supervisionAssigned, Date modificationTimestamp) {
        this.createTimestamp = createTimestamp;
        this.id = id;
        this.faultDescription = faultDescription;
        this.status = status;
        this.station = station;
        this.electrician = electrician;
        this.reported = reported;
        this.supervisionAccepted = supervisionAccepted;
        this.supervisionAssigned = supervisionAssigned;
        this.modificationTimestamp = modificationTimestamp;
    }

    public enum FaultStatusDto {
        ASSIGNED("ASSIGNED"), ENDED("ENDED"), NEW("NEW");

        private final String description;

        FaultStatusDto(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public boolean isAssigned() {
        return status.name().equals("ASSIGNED");
    }

    public boolean isEnded() {
        return status.name().equals("ENDED");
    }

    public boolean isNew() {
        return status.name().equals("NEW");
    }

}
