package com.bervan.pocketapp.view;

import com.bervan.common.AbstractTableView;
import com.bervan.core.model.BervanLogger;
import com.bervan.pocketapp.pocket.Pocket;
import com.bervan.pocketapp.pocket.PocketService;
import com.bervan.pocketapp.pocketitem.PocketItem;
import com.bervan.pocketapp.pocketitem.PocketItemService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.QueryParameters;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractAllPocketItemsView extends AbstractTableView<UUID, PocketItem> {
    public static final String ROUTE_NAME = "pocket-app/all-pocket-items";
    private String pocketName = "";
    private final PocketItemService itemService;
    private final PocketService pocketService;
    private ComboBox<String> pocketSelector;

    public AbstractAllPocketItemsView(PocketItemService itemService, PocketService pocketService, BervanLogger log) {
        super(new PocketAppPageLayout(ROUTE_NAME), itemService, log, PocketItem.class);
        this.pocketService = pocketService;
        this.itemService = itemService;

        addClassName("pocket-item-view");

        Set<String> pocketsName = pocketService.load().stream().map(Pocket::getName).collect(Collectors.toSet());
        pocketSelector = new ComboBox<>("Pockets", pocketsName);
        pocketSelector.addValueChangeListener(comboBoxStringComponentValueChangeEvent -> {
            UI.getCurrent().navigate(ROUTE_NAME, QueryParameters.of("pocket-name", comboBoxStringComponentValueChangeEvent.getValue()));
            this.loadData();
            this.refreshData();
        });

        contentLayout.add(pocketSelector);

        renderCommonComponents();
    }

    @Override
    protected Grid<PocketItem> getGrid() {
        Grid<PocketItem> grid = new Grid<>(PocketItem.class, false);
        buildGridAutomatically(grid);
        return grid;
    }

    @Override
    protected Set<PocketItem> loadData() {
        getUI().ifPresent(ui -> {
            QueryParameters queryParameters = ui.getInternals().getActiveViewLocation().getQueryParameters();
            Map<String, String> parameters = queryParameters.getParameters()
                    .entrySet()
                    .stream()
                    .collect(
                            java.util.stream.Collectors.toMap(
                                    Map.Entry::getKey,
                                    e -> String.join("", e.getValue())
                            )
                    );
            pocketName = parameters.getOrDefault("pocket-name", "");
        });

        if (pocketName == null || pocketName.equals("")) {
            addButton.setVisible(false);
            return new HashSet<>();
        }

        addButton.setVisible(true);

        pocketSelector.setEnabled(false);
        pocketSelector.setValue(pocketName);
        pocketSelector.setEnabled(true);

        return itemService.loadByPocketName(pocketName);
    }

    @Override
    protected PocketItem customizeSavingInCreateForm(PocketItem newItem) {
        Pocket pocket = pocketService.loadByName(pocketName).get(0);
        int size = pocket.getPocketItems().size();
        newItem.setPocket(pocket);
        newItem.setOrderInPocket(size);
        return super.customizeSavingInCreateForm(newItem);
    }
}