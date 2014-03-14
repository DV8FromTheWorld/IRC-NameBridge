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
 * @version v0.1.0 3/14/2013
 * @MC.Version 1.6.4
 */
public class CoreModContainer extends DummyModContainer
{
    public CoreModContainer()
    {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "IRCNameBridge";
        meta.name = "IRC NameBridge";
        meta.version = "0.1.0";
        meta.credits = "Roll Credits ...";
        meta.authorList = Arrays.asList("DV8FromTheWorld");
        meta.description = "Provides the ability to Autocomplete names of people that are in IRC (CraftIRC, EiraIRC, ForgeIRC).";
        meta.url = "code.dv8tion.net";
        meta.updateUrl = "";
        meta.screenshots = new String[0];
        meta.logoFile = "";
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
