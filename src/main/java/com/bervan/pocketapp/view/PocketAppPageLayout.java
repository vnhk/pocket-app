package com.bervan.pocketapp.view;

import com.bervan.common.AbstractPageLayout;
import com.vaadin.flow.component.html.Hr;

public final class PocketAppPageLayout extends AbstractPageLayout {

    public PocketAppPageLayout(String currentRouteName) {
        super(currentRouteName);

        addButton(menuButtonsRow, AbstractPocketView.ROUTE_NAME, "Pockets");
        addButton(menuButtonsRow, AbstractAllPocketItemsView.ROUTE_NAME, "Pocket Items");

        add(menuButtonsRow);
        add(new Hr());

    }
}
