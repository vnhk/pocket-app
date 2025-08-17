package com.bervan.pocketapp.view;

import com.bervan.common.MenuNavigationComponent;
import com.vaadin.flow.component.icon.VaadinIcon;

public final class PocketAppPageLayout extends MenuNavigationComponent {

    public PocketAppPageLayout(String currentRouteName) {
        super(currentRouteName);

        addButtonIfVisible(menuButtonsRow, AbstractPocketView.ROUTE_NAME, "Pockets", VaadinIcon.HOME.create());
        addButtonIfVisible(menuButtonsRow, AbstractAllPocketItemsView.ROUTE_NAME, "Pocket Items", VaadinIcon.HOME.create());

        add(menuButtonsRow);

    }
}
