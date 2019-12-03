package net.vprod.entities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.Direction;
import net.vprod.ExampleMod;
import net.vprod.inventory.ImplementedInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WhiteboardEntity extends BlockEntity implements ImplementedInventory, SidedInventory {
    private static final Logger logger = LogManager.getLogger(WhiteboardEntity.class);

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(9 * 9, ItemStack.EMPTY);

    public WhiteboardEntity() {
        super(ExampleMod.WHITEBOARD_BLOCK_ENTITY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, items);
        return super.toTag(tag);
    }

    @Override
    public int[] getInvAvailableSlots(Direction var1) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction direction) {
        return direction != Direction.UP;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction direction) {
        return true;
    }
}
