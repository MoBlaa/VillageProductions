package net.vprod.utility;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class VillageScanner {

    /*
    As Some blocks are not registered as classes the id has to be used
     */

    // General Village specific blocks
    public static final Set<String> VILLAGE_BLOCKS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "minecraft:barrel", // Profession = Fisherman + Storage
            "minecraft:brewing_stand", // Profession = Cleric
            "minecraft:cartography_table", // Profession = Cartographer
            "minecraft:cauldron", // Profession = Leatherworker
            "minecraft:composter", // Profession = Farmer
            "minecraft:fletching_table", // Profession = Fletcher
            "minecraft:grindstone", // Profession = Weaponsmith
            "minecraft:lectern", // Profession = Librarian
            "minecraft:loom", // Profession = Shepherd
            "minecraft:smithing_table", // Profession = Toolsmith
            "minecraft:stonecutter", // Profession = Stonemason/Mason
            "minecraft:blast_furnace", // Profession = Armorer
            "minecraft:smoker", // Profession = Butcher

            "minecraft:bell", // Marks a town
            "minecraft:chest", // For Storage
            "minecraft:crafting_table", // For autocrafting crafting blocks have to be present

            // Beds are also necessary for villagers to spawn
            "minecraft:white_bed",
            "minecraft:orange_bed",
            "minecraft:magenta_bed",
            "minecraft:light_blue_bed",
            "minecraft:yellow_bed",
            "minecraft:lime_bed",
            "minecraft:pink_bed",
            "minecraft:gray_bed",
            "minecraft:light_gray_bed",
            "minecraft:cyan_bed",
            "minecraft:purple_bed",
            "minecraft:blue_bed",
            "minecraft:brown_bed",
            "minecraft:green_bed",
            "minecraft:red_bed",
            "minecraft:black_bed",
            "minecraft:bed"
    )));
    // Village specific blocks for a desert village
    public static final Set<String> DESERT_VILLAGE_BLOCKS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "minecraft:smooth_sandstone"
    )));
    // Village specific blocks for a plains village
    public static final Set<String> PLAINS_VILLAGE_BLOCKS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "minecraft:grass_path"
    )));
    // Village specific blocks for a savanna village
    public static final Set<String> SAVANNA_VILLAGE_BLOCKS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "minecraft:grass_path"
    )));
    // Village specific blocks for a savanna village
    public static final Set<String> TAIGA_VILLAGE_BLOCKS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "minecraft:grass_path"
    )));
    // Village specific blocks for a snowy village
    public static final Set<String> SNOWY_VILLAGE_BLOCKS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "minecraft:grass_path"
    )));

    public static final Logger logger = LogManager.getLogger(VillageScanner.class);
    public static final int SEARCH_RADIUS = 10; // in Blocks

    private final Map<BlockPos, Boolean> visited = new HashMap<>();
    private final Map<String, Block> found = new HashMap<>();
    private final Set<Inventory> foundInventories = new HashSet<>();

    public Map<String, Block> getFound() {
        return Collections.unmodifiableMap(this.found);
    }

    public Collection<Inventory> getFoundInventories() {
        return Collections.unmodifiableSet(this.foundInventories);
    }

    public void scan(IWorld world, BlockPos root) {
        // Clear visited to allow rescanning
        this.visited.clear();
        this.found.clear();
        this.foundInventories.clear();
        this.recursiveScan(world, root);
    }

    private void recursiveScan(IWorld world, BlockPos root) {
        // Search 21 * 21 * 21 Area for a villager block, chest barrell or other things inside a town
        for (int xOff = -SEARCH_RADIUS; xOff <= SEARCH_RADIUS; xOff++) {
            for (int yOff = -SEARCH_RADIUS; yOff <= SEARCH_RADIUS; yOff++) {
                for (int zOff = -SEARCH_RADIUS; zOff <= SEARCH_RADIUS; zOff++) {
                    BlockPos pos = new BlockPos(root.getX() + xOff, root.getY() + yOff, root.getZ() + zOff);

                    if (world.isAir(pos) || this.visited.containsKey(pos)) {
                        continue;
                    }

                    Block block = world.getBlockState(pos).getBlock();
                    String id = Registry.BLOCK.getId(block).toString();


                    this.visited.put(pos, true);
                    if (VILLAGE_BLOCKS.contains(id) ||
                            (world.getBiome(pos) instanceof DesertBiome && DESERT_VILLAGE_BLOCKS.contains(id)) ||
                            (world.getBiome(pos) instanceof SavannaBiome && SAVANNA_VILLAGE_BLOCKS.contains(id)) ||
                            (world.getBiome(pos) instanceof TaigaBiome && TAIGA_VILLAGE_BLOCKS.contains(id)) ||
                            (world.getBiome(pos) instanceof SnowyTundraBiome && SNOWY_VILLAGE_BLOCKS.contains(id)) ||
                            (world.getBiome(pos) instanceof PlainsBiome && PLAINS_VILLAGE_BLOCKS.contains(id))
                    ) {
                        logger.debug(String.format("%s @ x:%d y:%d z:%d", Registry.BLOCK.getId(block), pos.getX(), pos.getY(), pos.getZ()));
                        BlockEntity entity = world.getBlockEntity(pos);
                        if (entity instanceof Inventory) {
                            Inventory inv = (Inventory) entity;
                            logger.debug("Found inventory {}", inv);
                            this.foundInventories.add(inv);
                        }

                        this.found.put(id, block);
                        recursiveScan(world, pos);
                    }
                }
            }
        }
    }
}
