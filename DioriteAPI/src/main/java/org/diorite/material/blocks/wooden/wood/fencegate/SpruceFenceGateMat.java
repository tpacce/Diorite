package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.material.WoodTypeMat;
import org.diorite.material.blocks.FenceGateMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SpruceFenceGate" and all its subtypes.
 */
public class SpruceFenceGateMat extends WoodenFenceGateMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final SpruceFenceGateMat SPRUCE_FENCE_GATE_SOUTH = new SpruceFenceGateMat();
    public static final SpruceFenceGateMat SPRUCE_FENCE_GATE_WEST  = new SpruceFenceGateMat(BlockFace.WEST, false);
    public static final SpruceFenceGateMat SPRUCE_FENCE_GATE_NORTH = new SpruceFenceGateMat(BlockFace.NORTH, false);
    public static final SpruceFenceGateMat SPRUCE_FENCE_GATE_EAST  = new SpruceFenceGateMat(BlockFace.EAST, false);

    public static final SpruceFenceGateMat SPRUCE_FENCE_GATE_SOUTH_OPEN = new SpruceFenceGateMat(BlockFace.SOUTH, true);
    public static final SpruceFenceGateMat SPRUCE_FENCE_GATE_WEST_OPEN  = new SpruceFenceGateMat(BlockFace.WEST, true);
    public static final SpruceFenceGateMat SPRUCE_FENCE_GATE_NORTH_OPEN = new SpruceFenceGateMat(BlockFace.NORTH, true);
    public static final SpruceFenceGateMat SPRUCE_FENCE_GATE_EAST_OPEN  = new SpruceFenceGateMat(BlockFace.EAST, true);

    private static final Map<String, SpruceFenceGateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpruceFenceGateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected SpruceFenceGateMat()
    {
        super("SPRUCE_FENCE_GATE", 183, "minecraft:spruce_fence_gate", WoodTypeMat.SPRUCE, BlockFace.SOUTH, false, 2, 15);
    }

    protected SpruceFenceGateMat(final BlockFace face, final boolean open)
    {
        super(SPRUCE_FENCE_GATE_SOUTH.name(), SPRUCE_FENCE_GATE_SOUTH.ordinal(), SPRUCE_FENCE_GATE_SOUTH.getMinecraftId(), WoodTypeMat.SPRUCE, face, open, SPRUCE_FENCE_GATE_SOUTH.getHardness(), SPRUCE_FENCE_GATE_SOUTH.getBlastResistance());
    }

    protected SpruceFenceGateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean open, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, face, open, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.SPRUCE_FENCE_GATE;
    }

    @Override
    public SpruceFenceGateMat getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGateMat.combine(face, this.open));
    }

    @Override
    public SpruceFenceGateMat getOpen(final boolean open)
    {
        return getByID(FenceGateMat.combine(this.face, open));
    }

    @Override
    public SpruceFenceGateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpruceFenceGateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public SpruceFenceGateMat getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGateMat.combine(face, open));
    }

    /**
     * Returns one of SpruceFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SpruceFenceGate or null
     */
    public static SpruceFenceGateMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SpruceFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SpruceFenceGate or null
     */
    public static SpruceFenceGateMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of SpruceFenceGate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param blockFace facing direction of gate.
     * @param open      if gate should be open.
     *
     * @return sub-type of SpruceFenceGate
     */
    public static SpruceFenceGateMat getSpruceFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGateMat.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpruceFenceGateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SpruceFenceGateMat[] types()
    {
        return SpruceFenceGateMat.spruceFenceGateTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SpruceFenceGateMat[] spruceFenceGateTypes()
    {
        return byID.values(new SpruceFenceGateMat[byID.size()]);
    }

    static
    {
        SpruceFenceGateMat.register(SPRUCE_FENCE_GATE_SOUTH);
        SpruceFenceGateMat.register(SPRUCE_FENCE_GATE_WEST);
        SpruceFenceGateMat.register(SPRUCE_FENCE_GATE_NORTH);
        SpruceFenceGateMat.register(SPRUCE_FENCE_GATE_EAST);
        SpruceFenceGateMat.register(SPRUCE_FENCE_GATE_SOUTH_OPEN);
        SpruceFenceGateMat.register(SPRUCE_FENCE_GATE_WEST_OPEN);
        SpruceFenceGateMat.register(SPRUCE_FENCE_GATE_NORTH_OPEN);
        SpruceFenceGateMat.register(SPRUCE_FENCE_GATE_EAST_OPEN);
    }
}
