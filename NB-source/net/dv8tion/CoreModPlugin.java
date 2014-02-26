package net.dv8tion;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

/**
 * Provides information for FML so that the proper pieces of
 *  the CoreMod can be loaded.
 * 
 * @author DV8FromTheWorld (Austin Keener)
 * @version v1.0  2/26/2013
 * @MC.Version 1.6.4
 */
@Name(value = "IRC NameBridge")
@MCVersion(value = "1.6.4")
public class CoreModPlugin implements IFMLLoadingPlugin
{
    
    @Override
    @Deprecated
    public String[] getLibraryRequestClass()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Provides the class name of the CoreMod's transformation class,
     *  proceeded by the package name, if it is in one.
     * 
     * @return
     *          Returns:  net.dv8tion.ClassTransformer as a String array.
     */
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] {ClassTransformer.class.getName()};
    }
    
    /**
     * Provides the class name of the CoreMod's DummyContainer class,
     *  proceeded by the package name, if it is in one.
     *  
     * @return
     *          Returns:  net.dv8tion.CoreModContainer as a String.
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
