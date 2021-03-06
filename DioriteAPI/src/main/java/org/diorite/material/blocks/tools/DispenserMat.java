package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.material.blocks.PowerableMat;
import org.diorite.material.blocks.stony.StonyMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Dispenser" and all its subtypes.
 */
public class DispenserMat extends StonyMat implements DirectionalMat, PowerableMat
{
    /**
     * Bit flag defining if Dispenser is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES = 12;

    public static final DispenserMat DISPENSER_DOWN  = new DispenserMat();
    public static final DispenserMat DISPENSER_UP    = new DispenserMat(BlockFace.UP, false);
    public static final DispenserMat DISPENSER_NORTH = new DispenserMat(BlockFace.NORTH, false);
    public static final DispenserMat DISPENSER_SOUTH = new DispenserMat(BlockFace.SOUTH, false);
    public static final DispenserMat DISPENSER_WEST  = new DispenserMat(BlockFace.WEST, false);
    public static final DispenserMat DISPENSER_EAST  = new DispenserMat(BlockFace.EAST, false);

    public static final DispenserMat DISPENSER_DOWN_POWERED  = new DispenserMat(BlockFace.DOWN, true);
    public static final DispenserMat DISPENSER_UP_POWERED    = new DispenserMat(BlockFace.UP, true);
    public static final DispenserMat DISPENSER_NORTH_POWERED = new DispenserMat(BlockFace.NORTH, true);
    public static final DispenserMat DISPENSER_SOUTH_POWERED = new DispenserMat(BlockFace.SOUTH, true);
    public static final DispenserMat DISPENSER_WEST_POWERED  = new DispenserMat(BlockFace.WEST, true);
    public static final DispenserMat DISPENSER_EAST_POWERED  = new DispenserMat(BlockFace.EAST, true);

    private static final Map<String, DispenserMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DispenserMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace facing;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected DispenserMat()
    {
        super("DISPENSER", 23, "minecraft:dispenser", "DOWN", (byte) 0x00, 3.5f, 17.5f);
        this.facing = BlockFace.DOWN;
        this.powered = false;
    }

    protected DispenserMat(final BlockFace facing, final boolean powered)
    {
        super(DISPENSER_DOWN.name(), DISPENSER_DOWN.ordinal(), DISPENSER_DOWN.getMinecraftId(), facing.name() + (powered ? "_POWERED" : ""), combine(facing, powered), DISPENSER_DOWN.getHardness(), DISPENSER_DOWN.getBlastResistance());
        this.facing = facing;
        this.powered = powered;
    }

    protected DispenserMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.facing = facing;
        this.powered = powered;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.DISPENSER;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public DispenserMat getPowered(final boolean powered)
    {
        return getByID(combine(this.facing, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public DispenserMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.powered));
    }

    public DispenserMat getType(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    @Override
    public DispenserMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DispenserMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("facing", this.facing).append("powered", this.powered).toString();
    }

    private static byte combine(final BlockFace facing, final boolean powered)
    {
        byte result = powered ? POWERED_FLAG : 0x00;
        switch (facing)
        {
            case UP:
                result |= 0x01;
                break;
            case NORTH:
                result |= 0x02;
                break;
            case SOUTH:
                result |= 0x03;
                break;
            case WEST:
                result |= 0x04;
                break;
            case EAST:
                result |= 0x05;
                break;
            case DOWN:
            default:
                return result;
        }
        return result;
    }

    /**
     * Returns one of Dispenser sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dispenser or null
     */
    public static DispenserMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dispenser sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dispenser or null
     */
    public static DispenserMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Dispenser sub-type based on {@link BlockFace} and powered state
     * It will never return null.
     *
     * @param face    facing direction of Dispenser.
     * @param powered if Dispenser should be powered/
     *
     * @return sub-type of Dispenser
     */
    public static DispenserMat getDispenser(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DispenserMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DispenserMat[] types()
    {
        return DispenserMat.dispenserTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DispenserMat[] dispenserTypes()
    {
        return byID.values(new DispenserMat[byID.size()]);
    }

    static
    {
        DispenserMat.register(DISPENSER_DOWN);
        DispenserMat.register(DISPENSER_UP);
        DispenserMat.register(DISPENSER_NORTH);
        DispenserMat.register(DISPENSER_SOUTH);
        DispenserMat.register(DISPENSER_WEST);
        DispenserMat.register(DISPENSER_EAST);
        DispenserMat.register(DISPENSER_DOWN_POWERED);
        DispenserMat.register(DISPENSER_UP_POWERED);
        DispenserMat.register(DISPENSER_NORTH_POWERED);
        DispenserMat.register(DISPENSER_SOUTH_POWERED);
        DispenserMat.register(DISPENSER_WEST_POWERED);
        DispenserMat.register(DISPENSER_EAST_POWERED);
    }
}
