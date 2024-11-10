package com.bervan.pocketapp.pocket;

import com.bervan.history.model.BaseRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PocketRepository extends BaseRepository<Pocket, UUID> {
    Optional<Pocket> findByNameAndDeletedFalseAndOwnerId(String name, UUID ownerId);

    Set<Pocket> findByDeletedFalseAndOwnerId(UUID ownerId);
}
