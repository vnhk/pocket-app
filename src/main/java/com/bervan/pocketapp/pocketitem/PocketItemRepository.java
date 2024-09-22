package com.bervan.pocketapp.pocketitem;

import com.bervan.history.model.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface PocketItemRepository extends BaseRepository<PocketItem, UUID> {

    @Query("SELECT pi FROM PocketItem pi WHERE pi.pocket.name = :pocketName")
    Set<PocketItem> findAllByPocketName(@Param("pocketName") String pocketName);
}
