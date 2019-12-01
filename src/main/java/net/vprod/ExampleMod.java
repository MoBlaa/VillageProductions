package net.vprod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.vprod.blocks.WhiteboardBlock;

public class ExampleMod implements ModInitializer {

    public static final String MOD_IDENTIFIER = "vprod";

    // Management Block for a village
    public static final Block WHITEBOARD_BLOCK = new WhiteboardBlock();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        System.out.println("Hello Fabric world!");

        // Register a block
        Registry.register(Registry.BLOCK, new Identifier(MOD_IDENTIFIER, WhiteboardBlock.BLOCK_ID), WHITEBOARD_BLOCK);
        // Register an blockitem for the block
        Registry.register(Registry.ITEM, new Identifier(MOD_IDENTIFIER, WhiteboardBlock.BLOCK_ID), new BlockItem(WHITEBOARD_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
    }
}
