/**
 * 
 */
package net.dv8tion;

import java.io.InputStream;
import java.util.Arrays;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.ModMetadata;

/**
 * @author Austin
 *
 */
public class DataLib
{
    public static final String MOD_ID = "NameBridge";
    public static final String MOD_NAME = "IRC NameBridge";
    public static ModMetadata metadata = null;
    
    public static void loadMetadataFromFile(InputStream mcmodInfo, ModMetadata meta)
    {
        if (mcmodInfo != null)
        {
            metadata = MetadataCollection.from(mcmodInfo, MOD_NAME).getMetadataForId(MOD_ID, null);
        }
        else
        {
            FMLLog.severe("Could not find IRC NameBridge's mcmod.info.  Please report this error.", new Object[0]);
            FMLLog.severe("Note:  The mod will continue to function properly, version control will be running off defaults.", new Object[0]);
            metadata = new ModMetadata();
            metadata.modId = MOD_ID;
            metadata.name = MOD_NAME;
            metadata.version = "@VERSION@: Running on defaults.";
            metadata.credits = "Created by DV8FromTheWorld.";
            metadata.authorList = Arrays.asList("DV8FromTheWorld");
            metadata.description = "Provides the ability to Auto-complete names of people that are in IRC (CraftIRC, EiraIRC, ForgeIRC).\n"
                    + "The mcmod.info for this mod did not load correctly, so the versioning will not be displayed correctly.";
            metadata.url = "http://code.dv8tion.net";
            metadata.updateUrl = "";
            metadata.screenshots = new String[0];
            metadata.logoFile = "";
        }
        imprintMetadata(meta);
    }
    
    public static void imprintMetadata(ModMetadata toThis)
    {
        toThis.modId = metadata.modId;
        toThis.name = metadata.name;
        toThis.version = metadata.version;
        toThis.credits = metadata.credits;
        toThis.authorList = metadata.authorList;
        toThis.description = metadata.description;
        toThis.url = metadata.url;
        toThis.updateUrl = metadata.updateUrl;
        toThis.screenshots = metadata.screenshots;
        toThis.logoFile = metadata.logoFile;
    }
}
