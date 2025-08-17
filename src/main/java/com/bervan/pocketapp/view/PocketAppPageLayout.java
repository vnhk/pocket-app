package com.bervan.pocketapp.view;

import com.bervan.common.MenuNavigationComponent;
import com.vaadin.flow.component.icon.VaadinIcon;

public final class PocketAppPageLayout extends MenuNavigationComponent {

    public PocketAppPageLayout(String currentRouteName) {
        super(currentRouteName);

        addButtonIfVisible(menuButtonsRow, AbstractPocketView.ROUTE_NAME, "Pockets", VaadinIcon.CLIPBOARD.create());
        addButtonIfVisible(menuButtonsRow, AbstractAllPocketItemsView.ROUTE_NAME, "Pocket Items", VaadinIcon.CLIPBOARD_TEXT.create());

        add(menuButtonsRow);

    }
}
