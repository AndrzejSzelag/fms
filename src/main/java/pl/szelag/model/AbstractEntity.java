package pl.szelag.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    @Version
    @NotNull(message = "{constraint.notnull}")
    @Column(name = "version", nullable = false)
    @Getter
    private int version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp", nullable = false)
    @NotNull(message = "{constraint.notnull}")
    @Getter
    private Date creationTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_timestamp")
    @Getter
    private Date modificationTimestamp;

    protected abstract Object getId();

    protected abstract Object getBusinessKey();

    @PrePersist
    public void creationTimestamp() {
        creationTimestamp = new Date();
    }

    @PreUpdate
    public void updateTimestamp() {
        modificationTimestamp = new Date();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        return this.getClass().isAssignableFrom(object.getClass()) && this.getBusinessKey().equals(((AbstractEntity) object).getBusinessKey());
    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": ID=" + getId() + ", BusinessKey=" + getBusinessKey();
    }
}
