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
 * Represents chain boots.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class ChainmailBootsMat extends ArmorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final ChainmailBootsMat CHAINMAIL_BOOTS = new ChainmailBootsMat();

    private static final Map<String, ChainmailBootsMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ChainmailBootsMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<ChainmailBootsMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<ChainmailBootsMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected ChainmailBootsMat()
    {
        super("CHAINMAIL_BOOTS", 305, "minecraft:chainmail_boots", "NEW", (short) 0, ArmorMaterial.CHAINMAIL, ArmorType.BOOTS);
    }

    protected ChainmailBootsMat(final int durability)
    {
        super(CHAINMAIL_BOOTS.name(), CHAINMAIL_BOOTS.getId(), CHAINMAIL_BOOTS.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.CHAINMAIL, ArmorType.BOOTS);
    }

    protected ChainmailBootsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected ChainmailBootsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public ChainmailBootsMat[] types()
    {
        return new ChainmailBootsMat[]{CHAINMAIL_BOOTS};
    }

    @Override
    public ChainmailBootsMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public ChainmailBootsMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ChainmailBootsMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public ChainmailBootsMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public ChainmailBootsMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of ChainBoots sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of ChainBoots.
     */
    public static ChainmailBootsMat getByID(final int id)
    {
        ChainmailBootsMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new ChainmailBootsMat(id);
            if ((id > 0) && (id < CHAINMAIL_BOOTS.getBaseDurability()))
            {
                ChainmailBootsMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of ChainBoots sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of ChainBoots.
     */
    public static ChainmailBootsMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of ChainBoots-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of ChainBoots.
     */
    public static ChainmailBootsMat getByEnumName(final String name)
    {
        final ChainmailBootsMat mat = byName.get(name);
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
    public static void register(final ChainmailBootsMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ChainmailBootsMat[] chainBootsTypes()
    {
        return new ChainmailBootsMat[]{CHAINMAIL_BOOTS};
    }

    static
    {
        ChainmailBootsMat.register(CHAINMAIL_BOOTS);
    }
}
