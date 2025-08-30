package com.bervan.pocketapp.pocketitem;

import com.bervan.common.search.SearchService;
import com.bervan.common.service.AuthService;
import com.bervan.common.service.BaseService;
import com.bervan.common.user.User;
import com.bervan.encryption.DataCipherException;
import com.bervan.encryption.EncryptionService;
import com.bervan.pocketapp.pocket.Pocket;
import com.bervan.pocketapp.pocket.PocketRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PocketItemService extends BaseService<UUID, PocketItem> {
    private final PocketRepository pocketRepository;
    private final PocketItemRepository repository;
    private final PocketItemHistoryRepository historyRepository;
    private final EncryptionService encryptionService;

    public PocketItemService(PocketRepository pocketRepository, PocketItemRepository repository, PocketItemHistoryRepository historyRepository,
                             SearchService searchService, EncryptionService encryptionService) {
        super(repository, searchService);
        this.pocketRepository = pocketRepository;
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.encryptionService = encryptionService;
    }

    public String decryptContent(PocketItem pocketItem, String password) {
        return encryptionService.decrypt(pocketItem.getContent(), password);
    }

    @Override
    public void save(List<PocketItem> data) {
        repository.saveAll(data);
    }

    public PocketItem save(PocketItem pocketItem) {
        if (pocketItem.isEncrypted()) {
            encryptContent(pocketItem);
        }

        return repository.save(pocketItem);
    }

    private void encryptContent(PocketItem pocketItem) {
        if (!encryptionService.isEncrypted(pocketItem.getContent())) {
            User user = AuthService.getLoggedUser().get();
            String dataCipherPassword = user.getDataCipherPassword();
            if (dataCipherPassword != null && !dataCipherPassword.isBlank()) {
                pocketItem.setContent(encryptionService.encrypt(pocketItem.getContent(), dataCipherPassword));
            } else {
                throw new DataCipherException("User data cipher password is not present!");
            }
        }
    }

    @Transactional
    public PocketItem save(PocketItem pocketItem, String pocketName) {
        Pocket pocket = pocketRepository.findByNameAndDeletedFalseAndOwnersId(pocketName, AuthService.getLoggedUserId()).get(0);
        pocketItem.setPocket(pocket);
        pocketItem.setOrderInPocket(pocket.getPocketSize());
        pocket.getPocketItems().add(pocketItem);
        return save(pocketItem);
    }

    @Transactional
    public PocketItem save(PocketItem pocketItem, String pocketName, UUID ownerId) {
        Pocket pocket = pocketRepository.findByNameAndDeletedFalseAndOwnersId(pocketName, ownerId).get(0);
        pocketItem.setPocket(pocket);
        pocketItem.setOrderInPocket(pocket.getPocketSize());
        pocket.getPocketItems().add(pocketItem);
        return save(pocketItem);
    }

    @Override
    public void delete(PocketItem item) {
        item.setDeleted(true);
        repository.save(item);
    }

    @PostFilter("(T(com.bervan.common.service.AuthService).hasAccess(filterObject.owners))")
    public List<HistoryPocketItem> loadHistory() {
        return historyRepository.findAll();
    }


    @PostFilter("(T(com.bervan.common.service.AuthService).hasAccess(filterObject.owners))")
    public Set<PocketItem> loadByPocketName(String pocketName) {
        return repository.findAllByPocketNameAndOwnersId(pocketName, AuthService.getLoggedUserId());
    }
}
