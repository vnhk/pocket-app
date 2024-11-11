package com.bervan.pocketapp.pocketitem;

import com.bervan.history.model.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface PocketItemRepository extends BaseRepository<PocketItem, UUID> {

    @Query("SELECT pi FROM PocketItem pi JOIN pi.owners o WHERE pi.pocket.name = :pocketName AND pi.deleted = false AND o.id = :ownerId")
    Set<PocketItem> findAllByPocketNameAndOwnersId(@Param("pocketName") String pocketName, @Param("ownerId") UUID ownerId);

    Set<PocketItem> findAllByDeletedFalseAndOwnersId(UUID ownerId);
}
