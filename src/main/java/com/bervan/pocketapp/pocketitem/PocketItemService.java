package com.bervan.pocketapp.pocketitem;

import com.bervan.common.service.AuthService;
import com.bervan.common.service.BaseService;
import com.bervan.core.model.BervanLogger;
import com.bervan.ieentities.ExcelIEEntity;
import com.bervan.pocketapp.pocket.Pocket;
import com.bervan.pocketapp.pocket.PocketRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PocketItemService implements BaseService<UUID, PocketItem> {
    private final PocketRepository pocketRepository;
    private final PocketItemRepository repository;
    private final PocketItemHistoryRepository historyRepository;
    private final BervanLogger logger;

    public PocketItemService(PocketRepository pocketRepository, PocketItemRepository repository, PocketItemHistoryRepository historyRepository, BervanLogger logger) {
        this.pocketRepository = pocketRepository;
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.logger = logger;
    }

    @Override
    public void save(List<PocketItem> data) {
        repository.saveAll(data);
    }

    public PocketItem save(PocketItem pocketItem) {
        return repository.save(pocketItem);
    }

    @Transactional
    public PocketItem save(PocketItem pocketItem, String pocketName) {
        Pocket pocket = pocketRepository.findByNameAndDeletedFalseAndOwnerId(pocketName, AuthService.getLoggedUserId()).get();
        pocketItem.setPocket(pocket);
        pocketItem.setOrderInPocket(pocket.getPocketSize());
        pocket.getPocketItems().add(pocketItem);
        return repository.save(pocketItem);
    }

    @Override
    @PostFilter("filterObject.owner != null && filterObject.owner.getId().equals(T(com.bervan.common.service.AuthService).getLoggedUserId())")
    public Set<PocketItem> load() {
        return new HashSet<>(repository.findAllByDeletedFalseAndOwnerId(AuthService.getLoggedUserId()));
    }

    @Override
    public void delete(PocketItem item) {
        item.setDeleted(true);
        repository.save(item);
    }

    @PostFilter("filterObject.owner != null && filterObject.owner.getId().equals(T(com.bervan.common.service.AuthService).getLoggedUserId())")
    public List<HistoryPocketItem> loadHistory() {
        return historyRepository.findAll();
    }

    public void saveIfValid(List<? extends ExcelIEEntity> objects) {
        List<? extends ExcelIEEntity> list = objects.stream().filter(e -> e instanceof PocketItem).toList();
        logger.debug("Filtered Pocket Items to be imported: " + list.size());
        for (ExcelIEEntity excelIEEntity : list) {
            repository.save(((PocketItem) excelIEEntity));
        }
    }

    @PostFilter("filterObject.owner != null && filterObject.owner.getId().equals(T(com.bervan.common.service.AuthService).getLoggedUserId())")
    public Set<PocketItem> loadByPocketName(String pocketName) {
        return repository.findAllByPocketNameAndOwnerId(pocketName, AuthService.getLoggedUserId());
    }
}
