package net.vprod.screens;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.vprod.entities.WhiteboardEntity;

public class WhiteboardGui extends LightweightGuiDescription {
    public WhiteboardGui(WhiteboardEntity entity) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 140);

        for (int i=0; i < entity.getSources().size(); i++) {
            WLabel label = new WLabel("Inv" + i);
            root.add(label, 0, i, 2, 1);
            WItemSlot slot = new WItemSlot(entity.getSources().get(i), 0, 1, 1, true, true);
            root.add(slot, 1, i, 2, 1);
        }

        if (entity.getSources().isEmpty()) {
            WLabel label = new WLabel("No Inventories currently found!");
            root.add(label, 0, 0, 2, 1);
        }
    }
}
