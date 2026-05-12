package com.bervan.pocketapp.pocketitem.api;

import com.bervan.common.config.EntityConfigValidator;
import com.bervan.common.controller.BaseOwnedController;
import com.bervan.common.controller.ImportResult;
import com.bervan.common.mapper.BervanDTOMapper;
import com.bervan.common.search.SearchRequest;
import com.bervan.common.search.model.SearchOperation;
import com.bervan.pocketapp.pocketitem.PocketItem;
import com.bervan.pocketapp.pocketitem.PocketItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pocket-app/all-pocket-items")
public class PocketItemRestController extends BaseOwnedController {

    protected PocketItemRestController(PocketItemService service, BervanDTOMapper mapper, EntityConfigValidator validator) {
        super(service, mapper, validator, "PocketItem");
    }

    @GetMapping
    public ResponseEntity<Page<PocketItemDto>> listByPocket(
            @RequestParam MultiValueMap<String, String> allParams,
            @RequestParam("pocket-name") String pocketName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "orderInPocket") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        SearchRequest baseRequest = new SearchRequest();
        baseRequest.addCriterion("POCKET_NAME", com.bervan.pocketapp.pocketitem.PocketItem.class,
                "pocket.name", SearchOperation.EQUALS_OPERATION, pocketName);
        return super.search(baseRequest, allParams, page, size, PocketItemDto.class, com.bervan.pocketapp.pocketitem.PocketItem.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PocketItemDto> getById(@PathVariable UUID id) {
        return super.getById(id, PocketItemDto.class);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PocketItemCreateRequest req) {

        if (req.getPocketName() == null) {
            return ResponseEntity.badRequest().build();
        }

        return super.create(req, PocketItemDto.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PocketItemDto req) {

        return super.update(req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        return super.delete(id);
    }

    @PostMapping("/{id}/decrypt")
    public ResponseEntity<DecryptResponse> decrypt(@PathVariable UUID id, @RequestBody DecryptRequest req) {
        Optional<PocketItem> match = service.loadById(id);
        if (match.isEmpty()) return ResponseEntity.notFound().build();
        PocketItem item = match.get();
        if (!item.isEncrypted()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            String decrypted = ((PocketItemService) service).decryptContent(item, req.password());
            return ResponseEntity.ok(new DecryptResponse(decrypted));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    record DecryptRequest(String password) {
    }

    record DecryptResponse(String content) {
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@RequestParam MultiValueMap<String, String> allParams) {
        return super.exportAll(allParams, PocketItemDto.class, "pocket-items", PocketItem.class);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportResult> importData(@RequestParam("file") MultipartFile file) {
        return super.importAll(file, PocketItemDto.class);
    }
}
