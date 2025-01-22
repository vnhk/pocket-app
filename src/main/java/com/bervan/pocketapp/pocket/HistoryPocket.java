package com.bervan.pocketapp.pocket;

import com.bervan.common.model.BervanHistoryEntity;
import com.bervan.common.model.PersistableTableData;
import com.bervan.history.model.HistoryField;
import com.bervan.history.model.HistoryOwnerEntity;
import com.bervan.history.model.HistorySupported;
import com.bervan.ieentities.ExcelIEEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@HistorySupported
public class HistoryPocket extends BervanHistoryEntity<UUID> implements PersistableTableData<UUID>, ExcelIEEntity<UUID> {
    @HistoryField
    private String name;
    private LocalDateTime updateDate;
    @Id
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