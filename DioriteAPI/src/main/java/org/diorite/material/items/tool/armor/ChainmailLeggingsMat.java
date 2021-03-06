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
 * Represents chain leggings.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class ChainmailLeggingsMat extends ArmorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final ChainmailLeggingsMat CHAINMAIL_LEGGINGS = new ChainmailLeggingsMat();

    private static final Map<String, ChainmailLeggingsMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ChainmailLeggingsMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<ChainmailLeggingsMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<ChainmailLeggingsMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected ChainmailLeggingsMat()
    {
        super("CHAINMAIL_LEGGINGS", 304, "minecraft:chainmail_leggings", "NEW", (short) 0, ArmorMaterial.CHAINMAIL, ArmorType.LEGGINGS);
    }

    protected ChainmailLeggingsMat(final int durability)
    {
        super(CHAINMAIL_LEGGINGS.name(), CHAINMAIL_LEGGINGS.getId(), CHAINMAIL_LEGGINGS.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.CHAINMAIL, ArmorType.LEGGINGS);
    }

    protected ChainmailLeggingsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected ChainmailLeggingsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public ChainmailLeggingsMat[] types()
    {
        return new ChainmailLeggingsMat[]{CHAINMAIL_LEGGINGS};
    }

    @Override
    public ChainmailLeggingsMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public ChainmailLeggingsMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ChainmailLeggingsMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public ChainmailLeggingsMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public ChainmailLeggingsMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of ChainLeggings sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of ChainLeggings.
     */
    public static ChainmailLeggingsMat getByID(final int id)
    {
        ChainmailLeggingsMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new ChainmailLeggingsMat(id);
            if ((id > 0) && (id < CHAINMAIL_LEGGINGS.getBaseDurability()))
            {
                ChainmailLeggingsMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of ChainLeggings sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of ChainLeggings.
     */
    public static ChainmailLeggingsMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of ChainLeggings-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of ChainLeggings.
     */
    public static ChainmailLeggingsMat getByEnumName(final String name)
    {
        final ChainmailLeggingsMat mat = byName.get(name);
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
    public static void register(final ChainmailLeggingsMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ChainmailLeggingsMat[] chainLeggingsTypes()
    {
        return new ChainmailLeggingsMat[]{CHAINMAIL_LEGGINGS};
    }

    static
    {
        ChainmailLeggingsMat.register(CHAINMAIL_LEGGINGS);
    }
}
