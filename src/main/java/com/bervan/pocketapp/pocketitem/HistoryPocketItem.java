package com.bervan.pocketapp.pocketitem;

import com.bervan.common.model.PersistableTableData;
import com.bervan.common.user.User;
import com.bervan.history.model.AbstractBaseHistoryEntity;
import com.bervan.history.model.HistoryField;
import com.bervan.history.model.HistoryOwnerEntity;
import com.bervan.history.model.HistorySupported;
import com.bervan.ieentities.ExcelIEEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@HistorySupported

public class HistoryPocketItem implements AbstractBaseHistoryEntity<UUID>, PersistableTableData<UUID>, ExcelIEEntity<UUID> {
    @Id
    @GeneratedValue
    private UUID id;
    @HistoryField
    private String summary;
    @HistoryField
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;
    private LocalDateTime updateDate;

    @ManyToOne
    private User owner;

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public void setOwner(User user) {
        this.owner = user;
    }

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
}