package com.bervan.pocketapp.pocket;

import com.bervan.common.service.AuthService;
import com.bervan.common.service.BaseService;
import com.bervan.common.user.User;
import com.bervan.core.model.BervanLogger;
import com.bervan.ieentities.ExcelIEEntity;
import com.bervan.pocketapp.pocketitem.PocketItem;
import com.bervan.pocketapp.pocketitem.PocketItemService;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PocketService implements BaseService<UUID, Pocket> {
    private final PocketRepository repository;
    private final PocketItemService pocketItemService;
    private final PocketHistoryRepository historyRepository;
    private final BervanLogger logger;

    public PocketService(PocketRepository repository, PocketItemService pocketItemService, PocketHistoryRepository historyRepository, BervanLogger logger) {
        this.repository = repository;
        this.pocketItemService = pocketItemService;
        this.historyRepository = historyRepository;
        this.logger = logger;
    }

    @Override
    public void save(List<Pocket> data) {
        repository.saveAll(data);
    }

    public Pocket save(Pocket pocket) {
        return repository.save(pocket);
    }

    @Override
    @PostFilter("(T(com.bervan.common.service.AuthService).hasAccess(filterObject.owners))")
    public Set<Pocket> load() {
        return new HashSet<>(repository.findByDeletedFalseAndOwnersId(AuthService.getLoggedUserId()));
    }

    public Set<Pocket> loadForOwner(User user) {
        return new HashSet<>(repository.findByDeletedFalseAndOwnersId(user.getId()));
    }

    @PostFilter("(T(com.bervan.common.service.AuthService).hasAccess(filterObject.owners))")
    public List<Pocket> loadByName(String pocketName) {
        return repository.findByNameAndDeletedFalseAndOwnersId(pocketName, AuthService.getLoggedUserId());
    }

    @Override
    public void delete(Pocket item) {
        item.setDeleted(true);
        if (item.getPocketItems() != null) {
            for (PocketItem pocketItem : item.getPocketItems()) {
                pocketItemService.delete(pocketItem);
            }
        }
        save(item);
    }

    @PostFilter("(T(com.bervan.common.service.AuthService).hasAccess(filterObject.owners))")
    public List<HistoryPocket> loadHistory() {
        return historyRepository.findAll();
    }

    public void saveIfValid(List<? extends ExcelIEEntity> objects) {
        List<? extends ExcelIEEntity> list = objects.stream().filter(e -> e instanceof Pocket).toList();
        logger.debug("Filtered Pockets to be imported: " + list.size());
        for (ExcelIEEntity excelIEEntity : list) {
            repository.save(((Pocket) excelIEEntity));
        }
    }
}
