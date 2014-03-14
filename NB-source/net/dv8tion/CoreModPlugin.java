package net.dv8tion;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

/**
 * Provides information for FML so that the proper pieces of
 * the CoreMod can be loaded.
 * 
 * @author DV8FromTheWorld (Austin Keener)
 * @version v0.1.0 3/14/2014
 * @MC.Version 1.6.4
 */
@Name("IRC NameBridge")
@MCVersion("1.6.4")
@TransformerExclusions("net.dv8tion")
public class CoreModPlugin implements IFMLLoadingPlugin
{

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    public String[] getLibraryRequestClass()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Provides the class name of the CoreMod's transformation class,
     * proceeded by the package name, if it is in one.
     * 
     * @return
     *         Returns: net.dv8tion.ClassTransformer as a String array.
     */
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] { ClassTransformer.class.getName() };
    }

    /**
     * Provides the class name of the CoreMod's DummyContainer class,
     * proceeded by the package name, if it is in one.
     * 
     * @return
     *         Returns: net.dv8tion.CoreModContainer as a String.
     */
    @Override
    public String getModContainerClass()
    {
        return CoreModContainer.class.getName();
    }

    @Override
    public String getSetupClass()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        // TODO Auto-generated method stub 
    }

}
