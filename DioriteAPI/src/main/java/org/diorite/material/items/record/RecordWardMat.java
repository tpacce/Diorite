package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RecordWardMat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RecordWardMat RECORD_WARD = new RecordWardMat();

    private static final Map<String, RecordWardMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RecordWardMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected RecordWardMat()
    {
        super("ward", 2265);
    }

    protected RecordWardMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected RecordWardMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public RecordWardMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RecordWardMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RecordWard sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RecordWard or null
     */
    public static RecordWardMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RecordWard sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RecordWard or null
     */
    public static RecordWardMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RecordWardMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RecordWardMat[] types()
    {
        return RecordWardMat.recordWardTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RecordWardMat[] recordWardTypes()
    {
        return byID.values(new RecordWardMat[byID.size()]);
    }

    static
    {
        RecordWardMat.register(RECORD_WARD);
    }
}

