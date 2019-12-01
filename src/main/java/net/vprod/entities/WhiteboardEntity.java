package net.vprod.entities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.vprod.ExampleMod;
import net.vprod.inventory.ProxiedInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class WhiteboardEntity extends BlockEntity implements ProxiedInventory {
    private static final Logger logger = LogManager.getLogger(WhiteboardEntity.class);

    private List<Inventory> sources = Collections.emptyList();

    public WhiteboardEntity() {
        super(ExampleMod.WHITEBOARD_BLOCK_ENTITY);
    }

    public void setSources(List<Inventory> sources) {
        this.sources = sources;
    }

    // TODO: toTag and fromTag

    @Override
    public List<Inventory> getSources() {
        return this.sources;
    }
}
