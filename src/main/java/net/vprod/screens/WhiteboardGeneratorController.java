package net.vprod.screens;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;

public class WhiteboardGeneratorController extends CottonScreenController {
    public WhiteboardGeneratorController(int syncId, PlayerInventory playerInventory, BlockContext context) {
        super(RecipeType.CRAFTING, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        WGridPanel root = (WGridPanel) getRootPanel();
        setRootPanel(root);
        root.setSize(100, 100);

        WLabel label = new WLabel("Inventory");
        root.add(label, 0, 0, 1, 1);
        WItemSlot inventory = WItemSlot.of(this.blockInventory, 0, 9, 9);
        root.add(inventory, 0, 1, 9, 9);

        root.add(this.createPlayerInventoryPanel(), 0, 11, 9, 4);

        root.validate(this);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return -1; //There's no real result slot
    }
}
