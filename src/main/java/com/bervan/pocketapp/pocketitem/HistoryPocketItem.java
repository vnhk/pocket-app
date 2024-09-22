package com.bervan.pocketapp.pocketitem;

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
public class HistoryPocketItem implements AbstractBaseHistoryEntity<UUID>, PersistableTableData, ExcelIEEntity<UUID> {
    @Id
    @GeneratedValue
    private UUID id;
    @HistoryField
    private String content;
    @HistoryField
    private Integer orderInPocket;
    private LocalDateTime updateDate;

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

    public Integer getOrderInPocket() {
        return orderInPocket;
    }

    public void setOrderInPocket(Integer order) {
        this.orderInPocket = order;
    }
}