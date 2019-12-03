package net.vprod.screens;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.RecipeType;
import net.vprod.inventory.ProxiedInventory;

import java.util.List;

public class WhiteboardGeneratorController extends CottonScreenController {
    public WhiteboardGeneratorController(int syncId, PlayerInventory playerInventory, BlockContext context) {
        super(RecipeType.SMELTING, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        WGridPanel root = (WGridPanel) getRootPanel();
        setRootPanel(root);
        root.setSize(100, 100);

        WLabel label = new WLabel("Inventory");
        root.add(label, 0, 0, 1, 1);

        if (this.blockInventory instanceof ProxiedInventory) {
            ProxiedInventory inv = (ProxiedInventory) this.blockInventory;

            WGridPanel invPanel = new WGridPanel();
            List<Inventory> sources = inv.getSources();
            for (int i = 0, j = 0, o = 0; i < sources.size(); i++) {
                Inventory inventory = sources.get(i);
                WLabel l = new WLabel("Inv. " + i);
                invPanel.add(l, 0, j);

                WItemSlot invSlot = WItemSlot.of(this.blockInventory, o, 9, inventory.getInvSize() / 9);
                invPanel.add(invSlot, 0, j+1);
                j += 1 + inventory.getInvSize() / 9;
                o += inventory.getInvSize();
            }
            root.add(invPanel, 0, 1);
        }

        //root.add(new WPlayerInvPanel(playerInventory), 0, 11, 9, 4);

        root.validate(this);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return -1; //There's no real result slot
    }
}
