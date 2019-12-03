package net.vprod.blocks;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.vprod.ExampleMod;
import net.vprod.entities.WhiteboardEntity;
import net.vprod.utility.VillageScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WhiteboardBlock extends Block implements BlockEntityProvider {
    public static final String BLOCK_ID_STRING = "whiteboard_block";
    public static final Identifier BLOCK_ID = new Identifier(ExampleMod.MOD_IDENTIFIER, BLOCK_ID_STRING);

    public static final Logger logger = LogManager.getLogger(WhiteboardBlock.class);

    private VillageScanner scanner;

    public WhiteboardBlock() {
        super(Block.Settings.of(Material.METAL).strength(5.0f, 5.0f));
        this.scanner = new VillageScanner();
    }

    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return true;

        this.scanner.scan(world, pos);

        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof WhiteboardEntity) {
            ContainerProviderRegistry.INSTANCE.openContainer(BLOCK_ID, player, (buf) -> {
                buf.writeBlockPos(pos);
            });
        }
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new WhiteboardEntity(this.scanner);
    }
}
