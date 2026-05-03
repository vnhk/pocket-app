package com.bervan.pocketapp.pocketitem.api;


import com.bervan.core.model.BaseDTO;
import com.bervan.core.model.BaseModel;
import com.bervan.core.model.FieldMapperConfig;
import com.bervan.pocketapp.pocketitem.PocketItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class PocketItemCreateRequest implements BaseDTO<UUID> {
    private String summary;
    private String content;
    private Boolean encrypted;
    private Integer orderInPocket;
    @FieldMapperConfig(mapper = ToPocketMapper.class, targetFieldNames = "pocket")
    private String pocketName;

    @Override
    public void setId(UUID uuid) {

    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public Class<? extends BaseModel<UUID>> dtoTarget() {
        return PocketItem.class;
    }
}