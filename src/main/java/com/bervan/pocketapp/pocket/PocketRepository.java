package com.bervan.pocketapp.pocket;

import com.bervan.history.model.BaseRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PocketRepository extends BaseRepository<Pocket, UUID> {
    List<Pocket> findByNameAndDeletedFalseAndOwnersId(String name, UUID ownerId);

    Set<Pocket> findByDeletedFalseAndOwnersId(UUID ownerId);
}
