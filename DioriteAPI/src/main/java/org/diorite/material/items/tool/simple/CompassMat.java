package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class CompassMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CompassMat COMPASS = new CompassMat();

    private static final Map<String, CompassMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CompassMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected CompassMat()
    {
        super("COMPASS", 345, "minecraft:compass", "COMPASS", (short) 0x00);
    }

    protected CompassMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected CompassMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public CompassMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CompassMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Compass sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Compass or null
     */
    public static CompassMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Compass sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Compass or null
     */
    public static CompassMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CompassMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CompassMat[] types()
    {
        return CompassMat.compassTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CompassMat[] compassTypes()
    {
        return byID.values(new CompassMat[byID.size()]);
    }

    static
    {
        CompassMat.register(COMPASS);
    }
}

