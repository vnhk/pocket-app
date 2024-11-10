package com.bervan.pocketapp.view;

import com.bervan.common.AbstractTableView;
import com.bervan.core.model.BervanLogger;
import com.bervan.pocketapp.pocket.Pocket;
import com.bervan.pocketapp.pocket.PocketService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public abstract class AbstractPocketView extends AbstractTableView<UUID, Pocket> {
    public static final String ROUTE_NAME = "pocket-app/pockets";
    private final PocketService service;

    public AbstractPocketView(@Autowired PocketService service, BervanLogger log) {
        super(new PocketAppPageLayout(ROUTE_NAME), service, log, Pocket.class);
        this.service = service;
        renderCommonComponents();
    }

    @Override
    protected Grid<Pocket> getGrid() {
        Grid<Pocket> grid = new Grid<>(Pocket.class, false);
        buildGridAutomatically(grid);

        grid.addColumn(new ComponentRenderer<>(item -> formatTextComponent(String.valueOf(item.getPocketSize()))))
                .setHeader("Size").setKey("size").setResizable(true);

        return grid;
    }
}