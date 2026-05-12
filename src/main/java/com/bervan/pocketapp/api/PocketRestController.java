package com.bervan.pocketapp.api;

import com.bervan.common.config.EntityConfigValidator;
import com.bervan.common.controller.BaseOwnedController;
import com.bervan.common.controller.ImportResult;
import com.bervan.common.mapper.BervanDTOMapper;
import com.bervan.common.service.BaseService;
import com.bervan.pocketapp.pocket.PocketService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/pocket-app/pockets")
public class PocketRestController extends BaseOwnedController {


    protected PocketRestController(PocketService service, BervanDTOMapper mapper, EntityConfigValidator validator) {
        super(service, mapper, validator, "Pocket");
    }

    @GetMapping
    public ResponseEntity<Page<PocketDto>> list(
            @RequestParam MultiValueMap<String, String> allParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return super.search(allParams, page, size, PocketDto.class, com.bervan.pocketapp.pocket.Pocket.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PocketDto> getById(@PathVariable UUID id) {
        return super.getById(id, PocketDto.class);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PocketDto req) {
        return super.create(req);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PocketDto req) {
        return super.update(req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@RequestParam MultiValueMap<String, String> allParams) {
        return super.exportAll(allParams, PocketDto.class, "pockets", com.bervan.pocketapp.pocket.Pocket.class);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportResult> importData(@RequestParam("file") MultipartFile file) {
        return super.importAll(file, PocketDto.class);
    }
}
