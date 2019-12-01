package net.vprod.entities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.vprod.ExampleMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WhiteboardEntity extends BlockEntity {
    private static final Logger logger = LogManager.getLogger(WhiteboardEntity.class);

    private int number = 7;

    public WhiteboardEntity() {
        super(ExampleMod.WHITEBOARD_BLOCK_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        super.toTag(compoundTag);

        compoundTag.putInt("number", this.number);

        return compoundTag;
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        super.fromTag(compoundTag);
        this.number = compoundTag.getInt("number");
    }
}
