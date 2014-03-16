package net.dv8tion;

import java.util.Arrays;

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
 * @version v0.3.1 3/15/2014
 * @MC.Version 1.6.4
 */
public class CoreModContainer extends DummyModContainer
{
    public CoreModContainer()
    {
        super(new ModMetadata());
        ModMetadata metadata = getMetadata();
        metadata.modId = DataLib.MOD_ID;
        metadata.name = DataLib.MOD_NAME;
        metadata.version = DataLib.VERSION;
        metadata.credits = "Created by DV8FromTheWorld.";
        metadata.authorList = Arrays.asList("DV8FromTheWorld");
        metadata.description = "Provides the ability to Auto-complete names of people that are in IRC (CraftIRC, EiraIRC, ForgeIRC).";
        metadata.url = "http://code.dv8tion.net";
        metadata.updateUrl = "";
        metadata.screenshots = new String[0];
        metadata.logoFile = "";
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
