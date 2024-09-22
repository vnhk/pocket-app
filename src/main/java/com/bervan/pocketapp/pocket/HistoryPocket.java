package com.bervan.pocketapp.pocket;

import com.bervan.common.model.PersistableTableData;
import com.bervan.history.model.AbstractBaseHistoryEntity;
import com.bervan.history.model.HistoryField;
import com.bervan.history.model.HistoryOwnerEntity;
import com.bervan.history.model.HistorySupported;
import com.bervan.ieentities.ExcelIEEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@HistorySupported
public class HistoryPocket implements AbstractBaseHistoryEntity<UUID>, PersistableTableData, ExcelIEEntity<UUID> {
    @HistoryField
    private String name;
    private LocalDateTime updateDate;
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @HistoryOwnerEntity
    private Pocket pocket;

    public HistoryPocket() {

    }

    public String getTableFilterableColumnValue() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Pocket getCodingTask() {
        return pocket;
    }

    public void setCodingTask(Pocket pocket) {
        this.pocket = pocket;
    }
}