package com.bervan.pocketapp.pocket;

import com.bervan.common.model.BervanBaseEntity;
import com.bervan.common.model.PersistableTableData;
import com.bervan.common.model.VaadinTableColumn;
import com.bervan.history.model.HistoryCollection;
import com.bervan.history.model.HistorySupported;
import com.bervan.ieentities.ExcelIEEntity;
import com.bervan.pocketapp.pocketitem.PocketItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@HistorySupported
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "owner.id"})
)
public class Pocket extends BervanBaseEntity<UUID> implements PersistableTableData<UUID>, ExcelIEEntity<UUID> {
    @Id
    private UUID id;
    @Size(max = 100)
    @VaadinTableColumn(internalName = "name", displayName = "Name")
    private String name;
    private LocalDateTime modificationDate;
    private LocalDateTime creationDate;

    private Boolean deleted = false;



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pocket")
    private Set<PocketItem> pocketItems;

    @OneToMany(fetch = FetchType.EAGER)
    @HistoryCollection(historyClass = HistoryPocket.class)
    private Set<HistoryPocket> history = new HashSet<>();

    public Pocket() {

    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getTableFilterableColumnValue() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getPocketSize() {
        if(pocketItems != null) {
            return Math.toIntExact(pocketItems.stream().filter(e -> !e.getDeleted()).count());
        }

        return 0;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    @Override
    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Set<HistoryPocket> getHistory() {
        return history;
    }

    public void setHistory(Set<HistoryPocket> history) {
        this.history = history;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Set<PocketItem> getPocketItems() {
        return pocketItems;
    }

    public void setPocketItems(Set<PocketItem> pocketItems) {
        this.pocketItems = pocketItems;
    }

    public Boolean getDeleted() {
        if (deleted == null) {
            return false;
        }
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}