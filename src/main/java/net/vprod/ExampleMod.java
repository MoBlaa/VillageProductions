package net.vprod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.BlockContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import net.vprod.blocks.WhiteboardBlock;
import net.vprod.entities.WhiteboardEntity;
import net.vprod.screens.WhiteboardGeneratorController;
import net.vprod.screens.WhiteboardGeneratorScreen;

public class ExampleMod implements ModInitializer, ClientModInitializer {

    public static final String MOD_IDENTIFIER = "vprod";

    public static BlockEntityType<WhiteboardEntity> WHITEBOARD_BLOCK_ENTITY;

    // Management Block for a village
    public static final Block WHITEBOARD_BLOCK = new WhiteboardBlock();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        System.out.println("Hello Fabric world!");

        // Register a block
        Registry.register(Registry.BLOCK, WhiteboardBlock.BLOCK_ID, WHITEBOARD_BLOCK);
        // Register an blockitem for the block
        Registry.register(Registry.ITEM, WhiteboardBlock.BLOCK_ID, new BlockItem(WHITEBOARD_BLOCK, new Item.Settings().group(ItemGroup.MISC)));

        // Create a Whiteboard BlockEntity
        WHITEBOARD_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY, MOD_IDENTIFIER + ":whiteboard", BlockEntityType.Builder.create(WhiteboardEntity::new, WHITEBOARD_BLOCK).build(null));

        // Register UI controller for Whiteboard
        ContainerProviderRegistry.INSTANCE.registerFactory(WhiteboardBlock.BLOCK_ID, (syncId, id, player, buf) -> new WhiteboardGeneratorController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
    }

    @Override
    public void onInitializeClient() {
        System.out.println("Initialize client!");

        ScreenProviderRegistry.INSTANCE.registerFactory(WhiteboardBlock.BLOCK_ID, (syncId, identifier, player, buf) -> new WhiteboardGeneratorScreen(new WhiteboardGeneratorController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
    }
}
