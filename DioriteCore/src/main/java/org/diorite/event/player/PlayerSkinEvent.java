package org.diorite.event.player;

import org.diorite.impl.entity.PlayerImpl;
import org.diorite.entity.Player;
import org.diorite.utils.skins.Skin;

/**
 * Created by Szymon on 2015-09-12 09:19.
 */
public class PlayerSkinEvent extends PlayerEvent
{
    private final Skin skin;

    /**
     * Construct new PlayerSkinEvent.
     *
     * @param player player related to event, can't be null.
     */
    public PlayerSkinEvent(final Player player)
    {
        super(player);
        this.skin = new Skin((PlayerImpl) player);
    }

    /**
     * @return player related to event.
     */
    public Skin getSkin()
    {
        return this.skin;
    }
}
