package com.bervan.pocketapp.pocketitem.api;

import com.bervan.core.model.DefaultCustomMapper;
import com.bervan.pocketapp.pocket.Pocket;
import com.bervan.pocketapp.pocket.PocketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToPocketMapper implements DefaultCustomMapper<String, Pocket> {

    private final PocketService pocketService;

    public ToPocketMapper(PocketService pocketService) {
        this.pocketService = pocketService;
    }

    @Override
    public Pocket map(String pocketName) {
        List<Pocket> pocket = pocketService.loadByName(pocketName);
        if (!pocket.isEmpty()) {
            return pocket.get(0);
        }
        return null;
    }

    @Override
    public Class<String> getFrom() {
        return String.class;
    }

    @Override
    public Class<Pocket> getTo() {
        return Pocket.class;
    }
}
