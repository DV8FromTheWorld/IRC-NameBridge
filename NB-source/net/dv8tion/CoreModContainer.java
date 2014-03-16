package net.dv8tion;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Provides functionality similar to a normal mod.
 * Has the ability to be shown in the Mods-List.
 * 
 * @author DV8FromTheWorld (Austin Keener)
 * @version v0.1.0 3/14/2014
 * @MC.Version 1.6.4
 */
public class CoreModContainer extends DummyModContainer
{
    public CoreModContainer()
    {
        super(new ModMetadata());
        DataLib.loadMetadataFromFile(getClass().getResourceAsStream("/mcmod.info"), getMetadata());
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }
    
    @EventHandler
    public void modConstruction(FMLConstructionEvent evt)
    {

    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt)
    {

    }

    @EventHandler
    public void init(FMLInitializationEvent evt)
    {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt)
    {

    }
}
