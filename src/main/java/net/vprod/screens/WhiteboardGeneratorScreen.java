package net.vprod.screens;

import io.github.cottonmc.cotton.gui.client.CottonScreen;
import net.minecraft.entity.player.PlayerEntity;

public class WhiteboardGeneratorScreen extends CottonScreen<WhiteboardGeneratorController> {
    public WhiteboardGeneratorScreen(WhiteboardGeneratorController container, PlayerEntity player) {
        super(container, player);
    }
}
