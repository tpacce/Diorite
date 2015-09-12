package org.diorite.impl.pipelines.event.player;

import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.event.player.SkinPipeline;
import org.diorite.event.player.PlayerSkinEvent;
import org.diorite.utils.pipeline.SimpleEventPipeline;

/**
 * Created by Szymon on 2015-09-12 09:22.
 */
public class SkinPipelineImpl extends SimpleEventPipeline<PlayerSkinEvent> implements SkinPipeline
{
    @Override
    public void reset_()
    {
        this.addAfter(EventPriority.NORMAL, "Diorite|SetSkins", ((evt, pipeline) -> {
            evt.getSkin().load();
        }));
    }
}
