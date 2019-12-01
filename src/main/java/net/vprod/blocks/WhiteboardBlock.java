package net.vprod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class WhiteboardBlock extends Block {
    public static final String BLOCK_ID = "whiteboard_block";

    public WhiteboardBlock() {
        super(Block.Settings.of(Material.METAL).strength(5.0f, 5.0f));
    }


}
