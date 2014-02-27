package net.dv8tion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.FMLLog;

public class IRCBridge
{
    private static IrcType ircType = null;
    
    private static Collection craftIrcCollection; 
    public enum IrcType
    {
        CRAFT_IRC, EIRA_IRC, FORGE_IRC, NONE;
    };
    
    public static String[] getIRCUsernames() throws Exception
    {
        if (ircType == null)
        {
            determineIRCType();
        }
        switch(ircType)
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
    
    private static String[] craftIRCNames() {
        ArrayList<String> nameList = new ArrayList<String>();
        if (craftIrcCollection == null)
        {
            doReflectionMadness();
        }
        if (craftIrcCollection.size() > 0)
        {
            Object object;
            Iterator iterator = craftIrcCollection.iterator();
            while (iterator.hasNext())
            {
                object = iterator.next();
                try
                {
                    nameList.addAll((List) object.getClass().getMethod("listUsers").invoke(object));
                } 
                catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException
                        | SecurityException e)
                {
                    FMLLog.severe("%s Something went seriously wrong with the reflection in "
                            + "the craftIRCNames() method in IRCBridge.\n"
                            + "The Exception Message: %s\nThe Cause: %s",
                            "[IRC NameBridge]", e.getMessage(), e.getCause());
                    e.printStackTrace();
                }
            }
            return (String[]) nameList.toArray();
        }
        return null;
    }
    
    private static void doReflectionMadness()
    {
        try
        {
            Class clazz;
            Method method;
            Object object;
            Field field;
            List list;
            Map map;
            clazz = Class.forName("org.bukkit.Bukkit");                     //get  Bukkit.java
            method = clazz.getMethod("getServer", new Class[0]);            //get  getServer() :  Bukkit.java
            object = method.invoke(null);                                   //call getServer() :  Bukkit.java     
            method = object.getClass().getMethod("getPluginManager");       //get  getPluginManager() : Server.java
            object = method.invoke(object);                                 //call getPluginManager() : Server.java
            method = object.getClass().getMethod("getPlugin", String.class);//get  getPlugin(String)  : PluginManager.java
            object = method.invoke(object, "CraftIRC");                     //call getPlugin(String) : PluginManager
            field = object.getClass().getField("instances");                //get  instances :   CraftIRC.java
            field.setAccessible(true);                                      //Make instances public
            object = field.get(object);                                     //Gets the value of instances : CraftIRC.java
            list = (List) object;                                           //Cast instances to a List (it is one)
            object = list.get(0);                                           //Gets the first MineBot.java instance.
            field = object.getClass().getField("channels");                 //get  channels : MineBot.java
            object = field.get(object);                                     //Gets the value for channels : MineBot.java
            map = (Map) object;                                             //Cast channels to a Map (it is one)
            craftIrcCollection = map.values();                              //Stores the values (no keys) from channels

        } 
        catch (ClassNotFoundException | NoSuchMethodException
                | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchFieldException e)
        {
            FMLLog.severe("%s Something went seriously wrong with the reflection in "
                    + "the doReflectionMadness() method in IRCBridge.\n"
                    + "The Exception Message: %s\nThe Cause: %s",
                    "[IRC NameBridge]", e.getMessage(), e.getCause());
            e.printStackTrace();
        }
        
    }
    
    private static String[] eiraIRCNames()
    {
        return null;
    }
    
    private static String[] forgeIRCNames()
    {
        return null;
    }
    
    private static void determineIRCType()
    {  
        if (classExists("com.ensifera.animosity.craftirc.Minebot"))
        {
            ircType = IrcType.CRAFT_IRC;
        }
        else if (classExists(""))
        {
            ircType = IrcType.FORGE_IRC;
        }
        else if (classExists(""))
        {
            ircType = IrcType.EIRA_IRC;
        }
        else
        {
            ircType = IrcType.NONE;
        }
    }
    
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
