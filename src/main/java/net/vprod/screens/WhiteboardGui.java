package net.vprod.screens;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.text.LiteralText;

public class WhiteboardGui extends LightweightGuiDescription {
    public WhiteboardGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 140);

        WLabel label = new WLabel(new LiteralText("This is a Text!"), 0xFFFFFF);
        root.add(label, 0, 4, 2, 1);
    }
}
