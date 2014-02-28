package net.dv8tion;

/**
 * Controls the loading of names of IRC users for Autocompletion in minecraft.
 * 
 * @author DV8FromTheWorld (Austin Keener)
 * @version v1.0  2/26/2013
 * @MC.Version 1.6.4
 */
public class NameLoader
{
    /**
     * Change this to be the names of the currently connected people
     * in the IRC channel.
     */
    public static String[] nameArray = {"Austin", "DV8", "Auzie"};
        
    /**
     * Takes the provided String array of names and merges it with the
     *  array of names that is in the IRC.
     *  
     * @param currentNames
     *              The names of the users currently on the Minecraft server.
     * @return
     *              Returns a String array containing MC usernames and IRC usernames.
     */
    public static String[] loadNames(String[] currentNames)
    {
        String[] newNames = new String[currentNames.length + nameArray.length];
        System.arraycopy(currentNames, 0, newNames, 0, currentNames.length);
        System.arraycopy(nameArray, 0, newNames, currentNames.length, nameArray.length);
        return newNames;
    }
}