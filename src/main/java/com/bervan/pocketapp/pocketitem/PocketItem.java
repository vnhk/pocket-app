package com.bervan.pocketapp.pocketitem;

import com.bervan.common.model.BervanOwnedBaseEntity;
import com.bervan.common.model.PersistableTableOwnedData;
import com.bervan.history.model.HistoryCollection;
import com.bervan.history.model.HistorySupported;
import com.bervan.ieentities.ExcelIEEntity;
import com.bervan.pocketapp.pocket.Pocket;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@HistorySupported
public class PocketItem extends BervanOwnedBaseEntity<UUID> implements PersistableTableOwnedData<UUID>, ExcelIEEntity<UUID> {
    @Id
    private UUID id;

    @NotNull
    @Size(max = 100)
    private String summary;

    @Lob
    @NotNull
    @Size(max = 5000000)
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @NotNull
    private Integer orderInPocket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pocket_id")
    private Pocket pocket;

    @Column(name = "is_encrypted")
    private Boolean encrypted = false;

    private Boolean deleted = false;
    private LocalDateTime modificationDate;
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @HistoryCollection(historyClass = HistoryPocketItem.class)
    private Set<HistoryPocketItem> history = new HashSet<>();

    public PocketItem() {

    }

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

    @Override
    public String getTableFilterableColumnValue() {
        return content;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Boolean isDeleted() {
        if (deleted == null) {
            return false;
        }
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean isEncrypted() {
        if (encrypted == null) {
            return false;
        }
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }
}