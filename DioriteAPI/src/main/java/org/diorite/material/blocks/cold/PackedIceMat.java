package org.diorite.material.blocks.cold;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PackedIce" and all its subtypes.
 */
public class PackedIceMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final PackedIceMat PACKED_ICE = new PackedIceMat();

    private static final Map<String, PackedIceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PackedIceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PackedIceMat()
    {
        super("PACKED_ICE", 174, "minecraft:packet_ice", "PACKED_ICE", (byte) 0x00, 0.5f, 2.5f);
    }

    protected PackedIceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public PackedIceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PackedIceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of PackedIce sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PackedIce or null
     */
    public static PackedIceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PackedIce sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PackedIce or null
     */
    public static PackedIceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PackedIceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PackedIceMat[] types()
    {
        return PackedIceMat.packedIceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PackedIceMat[] packedIceTypes()
    {
        return byID.values(new PackedIceMat[byID.size()]);
    }

    static
    {
        PackedIceMat.register(PACKED_ICE);
    }
}
