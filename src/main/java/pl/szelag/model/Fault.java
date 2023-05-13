package pl.szelag.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@NamedQuery(name = "Fault.findSupervisionAccepted", query = "SELECT s FROM Fault s where s.supervisionAccepted.login = :login")
@NamedQuery(name = "Fault.findSupervisionAssigned", query = "SELECT s FROM Fault s where s.supervisionAssigned.login = :login")
@NamedQuery(name = "Fault.findElectrician", query = "SELECT e FROM Fault e where e.electrician.login = :login")
@NamedQuery(name = "Fault.countOfElectrician", query = "SELECT COUNT (i) FROM Fault i where i.electrician = :electrician AND i.status=:status")
public class Fault extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAULT_GENERATOR")
    @SequenceGenerator(name = "FAULT_GENERATOR", sequenceName = "FAULT_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "fault_description", nullable = false, length = 200)
    @Size(min = 3, max = 200, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^(\\w+ ?)[a-zA-Z0-9 \\._'-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    @ToString.Include
    private String faultDescription;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "status", nullable = false)
    @Getter
    @Setter
    @ToString.Exclude
    private FaultStatus status = FaultStatus.NEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", referencedColumnName = "id", nullable = false, updatable = false)
    @Getter
    @Setter
    @ToString.Exclude
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electrician_id", referencedColumnName = "id")
    @Getter
    @Setter
    @ToString.Exclude
    private Electrician electrician;

    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp = "^(\\w+ ?)[a-zA-Z\\'-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    @ToString.Exclude
    private String reported;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accepted_id", referencedColumnName = "id")
    @Getter
    @Setter
    @ToString.Exclude
    private Supervision supervisionAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_id", referencedColumnName = "id")
    @Getter
    @Setter
    @ToString.Exclude
    private Supervision supervisionAssigned;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return id;
    }

    public enum FaultStatus {
        NEW("NEW"), ASSIGNED("ASSIGNED"), ENDED("ENDED");

        private final String description;

        FaultStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
