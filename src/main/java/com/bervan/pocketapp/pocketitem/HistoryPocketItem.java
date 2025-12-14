package com.bervan.pocketapp.pocketitem;

import com.bervan.common.model.BervanHistoryOwnedEntity;
import com.bervan.common.model.PersistableTableOwnedData;
import com.bervan.history.model.HistoryField;
import com.bervan.history.model.HistoryOwnerEntity;
import com.bervan.history.model.HistorySupported;
import com.bervan.ieentities.ExcelIEEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@HistorySupported
public class HistoryPocketItem extends BervanHistoryOwnedEntity<UUID> implements PersistableTableOwnedData<UUID>, ExcelIEEntity<UUID> {
    @Id
    private UUID id;
    @HistoryField
    private String summary;
    @HistoryField
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;
    private LocalDateTime updateDate;
    @Column(name = "is_encrypted")
    private Boolean encrypted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @HistoryOwnerEntity
    private PocketItem pocketItem;

    public HistoryPocketItem() {

    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getTableFilterableColumnValue() {
        return content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public PocketItem getCodingTask() {
        return pocketItem;
    }

    public void setCodingTask(PocketItem pocketItem) {
        this.pocketItem = pocketItem;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }
}