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
        super(RecipeType.SMELTING, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        WGridPanel root = (WGridPanel) getRootPanel();

        WLabel label = new WLabel("Inventory");
        root.add(label, 0, 0);

        int invHeight = this.blockInventory.getInvSize() / 9;
        WItemSlot invSlot = WItemSlot.of(this.blockInventory, 0, 9, invHeight);
        root.add(invSlot, 0, 1, 9, invHeight);


        root.add(this.createPlayerInventoryPanel(), 0, 1 + invHeight + 1);

        root.validate(this);
    }
}
