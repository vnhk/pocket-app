package com.bervan.pocketapp.api;

import com.bervan.core.model.BaseDTO;
import com.bervan.core.model.BaseModel;
import com.bervan.pocketapp.pocket.Pocket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class PocketDto implements BaseDTO<UUID> {
    private UUID id;
    private String name;
    private Integer pocketSize;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @Override
    public Class<? extends BaseModel<UUID>> dtoTarget() {
        return Pocket.class;
    }
}