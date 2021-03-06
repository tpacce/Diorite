package org.diorite.material.items.tool.armor;

import java.util.Map;

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.material.items.tool.ArmorMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Represents diamond chestplate.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class DiamondChestplateMat extends ArmorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondChestplateMat DIAMOND_CHESTPLATE = new DiamondChestplateMat();

    private static final Map<String, DiamondChestplateMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondChestplateMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<DiamondChestplateMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<DiamondChestplateMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected DiamondChestplateMat()
    {
        super("DIAMOND_CHESTPLATE", 311, "minecraft:diamond_chestplate", "NEW", (short) 0, ArmorMaterial.DIAMOND, ArmorType.CHESTPLATE);
    }

    protected DiamondChestplateMat(final int durability)
    {
        super(DIAMOND_CHESTPLATE.name(), DIAMOND_CHESTPLATE.getId(), DIAMOND_CHESTPLATE.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.DIAMOND, ArmorType.CHESTPLATE);
    }

    protected DiamondChestplateMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected DiamondChestplateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public DiamondChestplateMat[] types()
    {
        return new DiamondChestplateMat[]{DIAMOND_CHESTPLATE};
    }

    @Override
    public DiamondChestplateMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DiamondChestplateMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondChestplateMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public DiamondChestplateMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public DiamondChestplateMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of DiamondChestplate sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondChestplate.
     */
    public static DiamondChestplateMat getByID(final int id)
    {
        DiamondChestplateMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new DiamondChestplateMat(id);
            if ((id > 0) && (id < DIAMOND_CHESTPLATE.getBaseDurability()))
            {
                DiamondChestplateMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of DiamondChestplate sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of DiamondChestplate.
     */
    public static DiamondChestplateMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondChestplate-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondChestplate.
     */
    public static DiamondChestplateMat getByEnumName(final String name)
    {
        final DiamondChestplateMat mat = byName.get(name);
        if (mat == null)
        {
            final Integer idType = DioriteMathUtils.asInt(name);
            if (idType == null)
            {
                return null;
            }
            return getByID(idType);
        }
        return mat;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondChestplateMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DiamondChestplateMat[] diamondChestplateTypes()
    {
        return new DiamondChestplateMat[]{DIAMOND_CHESTPLATE};
    }

    static
    {
        DiamondChestplateMat.register(DIAMOND_CHESTPLATE);
    }
}
