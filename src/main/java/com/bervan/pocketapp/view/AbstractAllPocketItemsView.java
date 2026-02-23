package com.bervan.pocketapp.view;

import com.bervan.common.config.BervanViewConfig;
import com.bervan.common.view.AbstractBervanTableView;
import com.bervan.logging.JsonLogger;
import com.bervan.pocketapp.pocket.Pocket;
import com.bervan.pocketapp.pocket.PocketService;
import com.bervan.pocketapp.pocketitem.PocketItem;
import com.bervan.pocketapp.pocketitem.PocketItemService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.QueryParameters;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractAllPocketItemsView extends AbstractBervanTableView<UUID, PocketItem> {
    public static final String ROUTE_NAME = "pocket-app/all-pocket-items";
    private static JsonLogger logger = JsonLogger.getLogger(AbstractAllPocketItemsView.class, "pocket");
    private final PocketItemService itemService;
    private final PocketService pocketService;
    private String pocketName = "";
    private ComboBox<String> pocketSelector;

    public AbstractAllPocketItemsView(PocketItemService itemService, PocketService pocketService, BervanViewConfig bervanViewConfig) {
        super(new PocketAppPageLayout(ROUTE_NAME), itemService, bervanViewConfig, PocketItem.class);
        this.pocketService = pocketService;
        this.itemService = itemService;

        addClassName("pocket-item-view");

        Set<String> pocketsName = pocketService.load(Pageable.ofSize(10000))
                .stream().map(Pocket::getName).collect(Collectors.toSet());
        pocketSelector = new ComboBox<>("Pockets", pocketsName);
        pocketSelector.addValueChangeListener(event -> {
            if (!event.isFromClient()) return;
            String selected = event.getValue();
            pocketName = selected != null ? selected : "";
            UI.getCurrent().navigate(ROUTE_NAME, QueryParameters.of("pocket-name", pocketName));
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
    protected List<PocketItem> loadData() {
        try {
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
                String urlPocketName = parameters.getOrDefault("pocket-name", "");
                if (!urlPocketName.isEmpty()) {
                    pocketName = urlPocketName;
                }
            });

            if (pocketName == null || pocketName.isEmpty()) {
                getUI().ifPresent(ui -> ui.access(() -> newItemButton.setVisible(false)));
                return new ArrayList<>();
            }

            String currentPocketName = pocketName;
            getUI().ifPresent(ui -> ui.access(() -> {
                newItemButton.setVisible(true);
                pocketSelector.setValue(currentPocketName);
            }));

            return itemService.loadByPocketName(pocketName).stream().toList();
        } catch (Exception e) {
            logger.error("Failed to load pocket data!", e);
            throw e;
        }
    }

    @Override
    protected PocketItem preSaveActions(PocketItem newItem) {
        Pocket pocket = pocketService.loadByName(pocketName).get(0);
        int size = pocket.getPocketItems().size();
        newItem.setPocket(pocket);
        newItem.setOrderInPocket(size);
        return super.preSaveActions(newItem);
    }
}