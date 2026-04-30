package com.bervan.pocketapp.pocketitem.api;

import com.bervan.core.model.BaseDTO;
import com.bervan.core.model.BaseModel;
import com.bervan.pocketapp.pocketitem.PocketItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PocketItemDto implements BaseDTO<UUID> {
    private UUID id;
    private String summary;
    private String content;
    private Boolean encrypted;
    private Integer orderInPocket;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @Override
    public Class<? extends BaseModel<UUID>> dtoTarget() {
        return PocketItem.class;
    }
}
