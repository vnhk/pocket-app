package com.bervan.pocketapp.view;

import com.bervan.common.MenuNavigationComponent;

public final class PocketAppPageLayout extends MenuNavigationComponent {

    public PocketAppPageLayout(String currentRouteName) {
        super(currentRouteName);

        addButtonIfVisible(menuButtonsRow, AbstractPocketView.ROUTE_NAME, "Pockets");
        addButtonIfVisible(menuButtonsRow, AbstractAllPocketItemsView.ROUTE_NAME, "Pocket Items");

        add(menuButtonsRow);

    }
}
