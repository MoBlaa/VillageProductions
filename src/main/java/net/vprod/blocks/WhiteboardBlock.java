package net.vprod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vprod.screens.WhiteboardGui;
import net.vprod.screens.WhiteboardScreen;
import net.vprod.utility.VillageScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.stream.Stream;

public class WhiteboardBlock extends Block {
    public static final String BLOCK_ID = "whiteboard_block";

    public static final Logger logger = LogManager.getLogger(WhiteboardBlock.class);

    public WhiteboardBlock() {
        super(Block.Settings.of(Material.METAL).strength(5.0f, 5.0f));
    }

    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient() && hand == Hand.MAIN_HAND) {
            VillageScanner scanner = new VillageScanner(world);
            scanner.scan(pos);
            Map<String, Block> blocks = scanner.getFound();
            Stream<Map.Entry<String, Block>> blockStream = blocks.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals("minecraft:grass_path") && !entry.getKey().equals("minecraft:smooth_sandstone"));
            blockStream.forEach((entry) -> {
                logger.debug(String.format("Found: %s", entry.getValue().toString()));
            });
            MinecraftClient.getInstance().openScreen(new WhiteboardScreen(new WhiteboardGui()));
        }
        return super.activate(state, world, pos, player, hand, hit);
    }
}
