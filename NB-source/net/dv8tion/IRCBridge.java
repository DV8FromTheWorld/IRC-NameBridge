/**
 * Copyright 2014 DV8FromTheWorld (Austin Keener)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dv8tion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.FMLLog;

/**
 * Provides functionality to retrieve names from IRC from specific mods.
 * Supported mods are:
 * <ul>
 *  <li><a href="http://dev.bukkit.org/bukkit-plugins/craftirc/">CraftIRC 3</a>
 *   - <b>REQUIRES <a href="http://dev.bukkit.org/bukkit-plugins/vault/">VAULT</a></b></li>
 *  <li><a href="http://www.minecraftforum.net/topic/1964989-"><strike>EiraIRC</strike></a></li>
 * </ul>
 * 
 * @author DV8FromTheWorld (Austin Keener)
 * @version v0.1.0 3/14/2014
 * @MC.Version 1.6.4
 */
public class IRCBridge
{
    private static IrcType ircType = null;

    /**
     * These Collections store the references to the name lists that the respective
     * mod/plugin uses to store the names.  They start out as null and are only 
     * populated with a reference if the mod/plugin is present and the reflection
     * that is executed in their setup method successfully finds their name lists.
     */
    private static Collection craftIrcCollection = null;
    private static Collection eiraIrcCollection = null;

    public enum IrcType
    {
        CRAFT_IRC, EIRA_IRC, FORGE_IRC, NONE;
    };
    
    public static String[] getIRCUsernames() throws Exception
    {
        if (ircType == null)
        {
            determineIRCType();
            System.out.println("DETERMINE IRC FOUND: " + ircType);
        }
        switch (ircType)
        {
            case CRAFT_IRC:
                return craftIRCNames();
            case EIRA_IRC:
                return eiraIRCNames();
            case FORGE_IRC:
                return forgeIRCNames();
            case NONE:
                return null;
            default:
                throw new Exception("[IRC NameBridge] Welp.. Something went wrong " 
                        + "trying to get names. :" + ircType);
        }
    }

    private static String[] craftIRCNames()
    {
        ArrayList<String> nameList = new ArrayList<String>();
        if (craftIrcCollection == null)
        {
            craftIRCSetup();
        }
        if (craftIrcCollection.size() > 0)
        {
            try
            {
                for (Object object : craftIrcCollection)
                {
                    nameList.addAll((List) object.getClass().getMethod("listUsers").invoke(object));
                }
            }
            catch (Exception e)
            {
                FMLLog.severe("%s Something went seriously wrong with the reflection in "
                        + "the craftIRCNames() method in IRCBridge.\n"
                        + "The Exception Message: %s\nThe Cause: %s",
                        "[IRC NameBridge]", e.getMessage(), e.getCause());
                e.printStackTrace();
            }
            return nameList.toArray(new String[nameList.size()]);
        }
        return null;
    }

    private static String[] eiraIRCNames()
    {
        ArrayList<String> nameList = new ArrayList<String>();
        if (eiraIrcCollection == null)
        {
            eiraIRCSetup();
        }
        if (eiraIrcCollection.size() > 0)
        {
            Collection channels;
            Collection ircUsers;
            try
            {
                for (Object ircConnection : eiraIrcCollection)
                {
                    channels = (Collection) ircConnection.getClass().getMethod("getChannels").invoke(ircConnection);
                    for (Object channel : channels)
                    {
                        ircUsers = (Collection) channel.getClass().getMethod("getUserList").invoke(channel);
                        for (Object ircUser : ircUsers)
                        {
                            nameList.add((String) ircUser.getClass().getMethod("getName").invoke(ircUser));
                        }
                    }
                }
            }
            catch (Exception e)
            {
                FMLLog.severe("%s Something went seriously wrong with the reflection in "
                        + "the craftIRCNames() method in IRCBridge.\n"
                        + "The Exception Message: %s\nThe Cause: %s",
                        "[IRC NameBridge]", e.getMessage(), e.getCause());
                e.printStackTrace();
            }
            return nameList.toArray(new String[nameList.size()]);
        }
        return null;
    }

    private static String[] forgeIRCNames()
    {
        return null;
    }

    private static void craftIRCSetup()
    {
        try
        {
            Class clazz;
            Method method;
            Object object;
            Field field;
            List list;
            Map map;
            
            //Gets the instance of CraftIRC from Bukkit's plugin manager
            clazz = Class.forName("org.bukkit.Bukkit");                     //get  Bukkit.java
            method = clazz.getMethod("getServer");                          //get  getServer() :  Bukkit.java
            object = method.invoke(null);                                   //call getServer() :  Bukkit.java     
            method = object.getClass().getMethod("getPluginManager");       //get  getPluginManager() : Server.java
            object = method.invoke(object);                                 //call getPluginManager() : Server.java
            method = object.getClass().getMethod("getPlugin", String.class);//get  getPlugin(String)  : PluginManager.java
            object = method.invoke(object, "CraftIRC");                     //call getPlugin(String) : PluginManager
            
            //Gets the reference to the Collection in CraftIRC that holds names.
            field = object.getClass().getDeclaredField("instances");        //get  instances :   CraftIRC.java
            field.setAccessible(true);                                      //Make instances public
            object = field.get(object);                                     //Gets the value of instances : CraftIRC.java
            list = (List) object;                                           //Cast instances to a List (it is one)
            object = list.get(0);                                           //Gets the first MineBot.java instance.
            field = object.getClass().getDeclaredField("channels");         //get  channels : MineBot.java
            field.setAccessible(true);                                      //Make channels public
            object = field.get(object);                                     //Gets the value for channels : MineBot.java
            map = (Map) object;                                             //Cast channels to a Map (it is one)
            craftIrcCollection = map.values();                              //Stores the values (no keys) from channels
        }
        catch (Exception e)
        {
            FMLLog.severe("%s Something went seriously wrong with the reflection in "
                    + "the craftIRCSetup() method in IRCBridge.\n"
                    + "The Exception Message: %s\nThe Cause: %s",
                    "[IRC NameBridge]", e.getMessage(), e.getCause());
            e.printStackTrace();
        }
    }

    private static void eiraIRCSetup()
    {
        try
        {
            Object object;
            object = Class.forName("blay09.mods.eirairc.EiraIRC").getField("instance").get(null);
            eiraIrcCollection = (Collection) object.getClass().getMethod("getConnections").invoke(object);
        }
        catch (Exception e)
        {
            FMLLog.severe("%s Something went seriously wrong with the reflection in "
                    + "the eiraIRCSetup() method in IRCBridge.\n"
                    + "The Exception Message: %s\nThe Cause: %s",
                    "[IRC NameBridge]", e.getMessage(), e.getCause());
            e.printStackTrace();
        }
    }

    /**
     * Determines which IRC mod / plugin is installed.
     * If none are installed, defaults to IrcType.NONE.
     */
    private static void determineIRCType()
    {
        if (pluginExists("CraftIRC"))
        {
            ircType = IrcType.CRAFT_IRC;
        }
//        else if (classExists(""))
//        {
//            ircType = IrcType.FORGE_IRC;
//        }
        else if (classExists("blay09.mods.eirairc.EiraIRC"))
        {
            ircType = IrcType.EIRA_IRC;
        }
        else
        {
            ircType = IrcType.NONE;
        }
    }
    
    /**
     * Checks if the plugin exists.
     * Uses reflection to get Bukkit's PluginManager to find a plugin based 
     * on the name of the plugin.
     * Note, this will only work if the we are using <a href="https://github.com/MinecraftPortCentral/MCPC-Plus">MCPC+</a> (because we need bukkit).
     * 
     * @param pluginName
     *      The name of the plugin (This is the name that the Plugin registered with bukkit).
     * @return
     *      True if the plugin is currently loaded.
     */
    private static boolean pluginExists(String pluginName)
    {
        try
        {
            Object object;
            object = Class.forName("org.bukkit.Bukkit").getMethod("getServer").invoke(null);
            object = object.getClass().getMethod("getPluginManager").invoke(object);
            object = object.getClass().getMethod("getPlugin", String.class).invoke(object, pluginName);
            if (object != null)
            {
                return true;
            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * Helper method that checks if a class exists.
     * 
     * @param fullClassPath
     *      The class name, preceded by the package name.
     * @return
     *      True if the provided string points to an existing class.
     */
    private static boolean classExists(String fullClassPath)
    {
        try
        {
            Class.forName(fullClassPath);
            return true;
        }
        catch (ClassNotFoundException e)
        {
            return false;
        }
    }
}
