package net.vprod.entities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.vprod.ExampleMod;
import net.vprod.inventory.ImplementedInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WhiteboardEntity extends BlockEntity implements ImplementedInventory {
    private static final Logger logger = LogManager.getLogger(WhiteboardEntity.class);

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public WhiteboardEntity() {
        super(ExampleMod.WHITEBOARD_BLOCK_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        super.toTag(compoundTag);

        return Inventories.toTag(compoundTag, this.items);
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        super.fromTag(compoundTag);
        Inventories.fromTag(compoundTag, this.items);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
}
