package net.vprod.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface ProxiedInventory extends Inventory {

    public List<Inventory> getSources();

    // Creation
    static ProxiedInventory of(List<Inventory> sources) {
        return () -> sources;
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
        int remaining = slot;
        for (Inventory inventory : this.getSources()) {
            int invSize = inventory.getInvSize();
            if (invSize < remaining) {
                remaining -= invSize;
                continue;
            }

            // Otherwise get inventory at this slot
            return inventory.getInvStack(remaining);
        }
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack takeInvStack(int slot, int amount) {
        int remaining = slot;
        for (Inventory inventory : this.getSources()) {
            int invSize = inventory.getInvSize();
            if (invSize < remaining) {
                remaining -= invSize;
                continue;
            }

            // Otherwise get inventory at this slot
            return inventory.takeInvStack(remaining, amount);
        }
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeInvStack(int slot) {
        int remaining = slot;
        for (Inventory inventory : this.getSources()) {
            int invSize = inventory.getInvSize();
            if (invSize < remaining) {
                remaining -= invSize;
                continue;
            }

            // Otherwise get inventory at this slot
            return inventory.removeInvStack(remaining);
        }
        return ItemStack.EMPTY;
    }

    @Override
    default void setInvStack(int slot, ItemStack stack) {
        int remaining = slot;
        for (Inventory inventory : this.getSources()) {
            int invSize = inventory.getInvSize();
            if (invSize < remaining) {
                remaining -= invSize;
                continue;
            }

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
