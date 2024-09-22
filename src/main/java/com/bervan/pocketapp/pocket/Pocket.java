package com.bervan.pocketapp.pocket;

import com.bervan.common.model.PersistableTableData;
import com.bervan.common.model.VaadinTableColumn;
import com.bervan.history.model.AbstractBaseEntity;
import com.bervan.history.model.HistoryCollection;
import com.bervan.history.model.HistorySupported;
import com.bervan.ieentities.ExcelIEEntity;
import com.bervan.pocketapp.pocketitem.PocketItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@HistorySupported
public class Pocket implements AbstractBaseEntity<UUID>, PersistableTableData, ExcelIEEntity<UUID> {
    @Id
    @GeneratedValue
    private UUID id;
    @Size(max = 100)
    @Unique
    @VaadinTableColumn(internalName = "name", displayName = "Name")
    private String name;
    private LocalDateTime modificationDate;
    private LocalDateTime creationDate;

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
            return pocketItems.size();
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
}