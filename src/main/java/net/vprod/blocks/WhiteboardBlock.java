package net.vprod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.vprod.entities.WhiteboardEntity;
import net.vprod.screens.WhiteboardGui;
import net.vprod.screens.WhiteboardScreen;
import net.vprod.utility.VillageScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WhiteboardBlock extends Block implements BlockEntityProvider {
    public static final String BLOCK_ID = "whiteboard_block";

    public static final Logger logger = LogManager.getLogger(WhiteboardBlock.class);

    public WhiteboardBlock() {
        super(Block.Settings.of(Material.METAL).strength(5.0f, 5.0f));
    }

    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        WhiteboardEntity entity = (WhiteboardEntity) world.getBlockEntity(pos);
        if (entity == null) return true;
        if (world.isClient) {
            // Open Gui to show entity
            MinecraftClient.getInstance().openScreen(new WhiteboardScreen(new WhiteboardGui(entity)));
            return true;
        } else {
            // Scan village
            VillageScanner scanner = this.scanVillage(world, pos);
            entity.setSources(scanner.getFoundInventories());
            return true;
        }
    }

    private VillageScanner scanVillage(World world, BlockPos pos) {
        VillageScanner scanner = new VillageScanner(world);
        scanner.scan(pos);
        return scanner;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new WhiteboardEntity();
    }
}
