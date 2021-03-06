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
 * Represents chain helmet.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class ChainmailHelmetMat extends ArmorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final ChainmailHelmetMat CHAINMAIL_HELMET = new ChainmailHelmetMat();

    private static final Map<String, ChainmailHelmetMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ChainmailHelmetMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<ChainmailHelmetMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<ChainmailHelmetMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected ChainmailHelmetMat()
    {
        super("CHAINMAIL_HELMET", 302, "minecraft:chainmail_helmet", "NEW", (short) 0, ArmorMaterial.CHAINMAIL, ArmorType.HELMET);
    }

    protected ChainmailHelmetMat(final int durability)
    {
        super(CHAINMAIL_HELMET.name(), CHAINMAIL_HELMET.getId(), CHAINMAIL_HELMET.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.CHAINMAIL, ArmorType.HELMET);
    }

    protected ChainmailHelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected ChainmailHelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public ChainmailHelmetMat[] types()
    {
        return new ChainmailHelmetMat[]{CHAINMAIL_HELMET};
    }

    @Override
    public ChainmailHelmetMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public ChainmailHelmetMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ChainmailHelmetMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public ChainmailHelmetMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public ChainmailHelmetMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of ChainHelmet sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of ChainHelmet.
     */
    public static ChainmailHelmetMat getByID(final int id)
    {
        ChainmailHelmetMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new ChainmailHelmetMat(id);
            if ((id > 0) && (id < CHAINMAIL_HELMET.getBaseDurability()))
            {
                ChainmailHelmetMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of ChainHelmet sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of ChainHelmet.
     */
    public static ChainmailHelmetMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of ChainHelmet-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of ChainHelmet.
     */
    public static ChainmailHelmetMat getByEnumName(final String name)
    {
        final ChainmailHelmetMat mat = byName.get(name);
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
    public static void register(final ChainmailHelmetMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ChainmailHelmetMat[] chainHelmetTypes()
    {
        return new ChainmailHelmetMat[]{CHAINMAIL_HELMET};
    }

    static
    {
        ChainmailHelmetMat.register(CHAINMAIL_HELMET);
    }
}
