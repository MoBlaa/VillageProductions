package net.vprod.entities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.vprod.ExampleMod;
import net.vprod.inventory.ProxiedInventory;
import net.vprod.utility.VillageScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WhiteboardEntity extends BlockEntity implements ProxiedInventory, SidedInventory {
    private static final Logger logger = LogManager.getLogger(WhiteboardEntity.class);

    private VillageScanner scanner;

    public WhiteboardEntity() {
        this(new VillageScanner());
    }

    public WhiteboardEntity(VillageScanner scanner) {
        super(ExampleMod.WHITEBOARD_BLOCK_ENTITY);
        this.scanner = scanner;
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


    @Override
    public List<Inventory> getSources() {
        Collection<Inventory> result = this.scanner.getFoundInventories();
        //logger.debug(String.format("Fetching sources. Found %d inventories", result.size()));
        return new LinkedList<>(result);
    }
}
