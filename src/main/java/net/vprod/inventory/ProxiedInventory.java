package net.vprod.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public interface ProxiedInventory extends Inventory {

    Logger logger = LogManager.getLogger(ProxiedInventory.class);

    List<Inventory> getSources();

    // Creation
    static ProxiedInventory of(List<Inventory> sources) {
        return () -> sources;
    }

    default DefaultedList<ItemStack> getItems() {
        DefaultedList<ItemStack> result = DefaultedList.of();
        for (Inventory inv : this.getSources()) {
            for (int i = 0; i < inv.getInvSize(); i++) {
                result.add(inv.getInvStack(i));
            }
        }
        return result;
    }

    @Override
    default int getInvSize() {
        return this.getSources().stream()
                .mapToInt(Inventory::getInvSize)
                .sum();
    }

    @Override
    default boolean isInvEmpty() {
        return this.getSources().isEmpty() || this.getSources().stream().allMatch(Inventory::isInvEmpty);
    }

    @Override
    default ItemStack getInvStack(int slot) {
        //logger.debug(String.format("Trying to get InvStack at slot %d", slot));
        int remaining = slot;
        for (Inventory inventory : this.getSources()) {
            int invSize = inventory.getInvSize();
            if (invSize <= remaining) {
                remaining -= invSize;
                continue;
            }

            //logger.debug(String.format("Trying to get InvStack at slot %d from inventory with size %d", remaining, inventory.getInvSize()));

            // Otherwise get inventory at this slot
            ItemStack stack = inventory.getInvStack(remaining);
            //logger.debug("Slot " + slot + " contains itemstack: " + stack.toString());
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack takeInvStack(int slot, int amount) {
        logger.debug(String.format("Trying to take %d items from InvStack at slot %d", amount, slot));
        int remaining = slot;
        for (Inventory inventory : this.getSources()) {
            int invSize = inventory.getInvSize();
            if (invSize <= remaining) {
                remaining -= invSize;
                continue;
            }

            logger.debug(String.format("Trying to take %d items from InvStack at slot %d from inventory with size %d", amount, remaining, inventory.getInvSize()));

            // Otherwise get inventory at this slot
            return inventory.takeInvStack(remaining, amount);
        }
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeInvStack(int slot) {
        logger.debug(String.format("Trying to remove InvStack at slot %d", slot));
        int remaining = slot;
        for (Inventory inventory : this.getSources()) {
            int invSize = inventory.getInvSize();
            if (invSize <= remaining) {
                remaining -= invSize;
                continue;
            }

            logger.debug(String.format("Trying to remove InvStack at slot %d from inventory with size %d", remaining, inventory.getInvSize()));

            // Otherwise get inventory at this slot
            return inventory.removeInvStack(remaining);
        }
        return ItemStack.EMPTY;
    }

    @Override
    default void setInvStack(int slot, ItemStack stack) {
        logger.debug(String.format("Trying to set InvStack at slot %d", slot));
        int remaining = slot;
        for (Inventory inventory : this.getSources()) {
            int invSize = inventory.getInvSize();
            if (invSize <= remaining) {
                remaining -= invSize;
                continue;
            }

            logger.debug(String.format("Trying to set InvStack at slot %d from inventory with size %d", remaining, inventory.getInvSize()));

            // Otherwise get inventory at this slot
            inventory.setInvStack(remaining, stack);
        }
    }

    @Override
    default void markDirty() {
    }

    @Override
    default boolean canPlayerUseInv(PlayerEntity player) {
        return true;
    }

    @Override
    default void clear() {
        for (Inventory inv : this.getSources()) {
            inv.clear();
        }
    }
}
