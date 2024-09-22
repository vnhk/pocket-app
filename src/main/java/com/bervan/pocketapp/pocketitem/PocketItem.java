package com.bervan.pocketapp.pocketitem;

import com.bervan.common.model.PersistableTableData;
import com.bervan.common.model.VaadinTableColumn;
import com.bervan.history.model.AbstractBaseEntity;
import com.bervan.history.model.HistoryCollection;
import com.bervan.history.model.HistorySupported;
import com.bervan.ieentities.ExcelIEEntity;
import com.bervan.pocketapp.pocket.Pocket;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@HistorySupported
public class PocketItem implements AbstractBaseEntity<UUID>, PersistableTableData, ExcelIEEntity<UUID> {
    @Id
    @GeneratedValue
    private UUID id;
    @Lob
    @NotNull
    @VaadinTableColumn(internalName = "content", displayName = "Content")
    private String content;
    @NotNull
    private Integer orderInPocket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pocket_id")
    private Pocket pocket;

    private LocalDateTime modificationDate;
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.EAGER)
    @HistoryCollection(historyClass = HistoryPocketItem.class)
    private Set<HistoryPocketItem> history = new HashSet<>();

    public Integer getOrderInPocket() {
        return orderInPocket;
    }

    public void setOrderInPocket(Integer order) {
        this.orderInPocket = order;
    }

    public Pocket getPocket() {
        return pocket;
    }

    public void setPocket(Pocket pocket) {
        this.pocket = pocket;
    }

    public PocketItem() {

    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getTableFilterableColumnValue() {
        return content;
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

    public Set<HistoryPocketItem> getHistory() {
        return history;
    }

    public void setHistory(Set<HistoryPocketItem> history) {
        this.history = history;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}