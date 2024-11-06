package com.bervan.pocketapp.view;

import com.bervan.common.MenuNavigationComponent;

public final class PocketAppPageLayout extends MenuNavigationComponent {

    public PocketAppPageLayout(String currentRouteName) {
        super(currentRouteName);

        addButton(menuButtonsRow, AbstractPocketView.ROUTE_NAME, "Pockets");
        addButton(menuButtonsRow, AbstractAllPocketItemsView.ROUTE_NAME, "Pocket Items");

        add(menuButtonsRow);

    }
}
