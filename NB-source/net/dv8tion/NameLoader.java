package net.dv8tion;

/**
 * Controls the loading of names of IRC users for Auto-Completion in minecraft.
 * 
 * @author DV8FromTheWorld (Austin Keener)
 * @version v0.1.0  3/14/2014
 * @MC.Version 1.6.4
 */
public class NameLoader
{
    /**
     * Takes the provided String array of names and merges it with the
     *  array of names that is in the IRC.
     *  
     * @param currentNames
     *              The names of the users currently on the Minecraft server.
     * @return
     *              Returns a String array containing MC usernames and IRC usernames.
     * @throws Exception 
     */
    public static String[] loadNames(String[] currentNames) throws Exception
    {
        String[] nameArray = IRCBridge.getIRCUsernames();
        if (nameArray != null)
        {
            String[] newNames = new String[currentNames.length + nameArray.length];
            System.arraycopy(currentNames, 0, newNames, 0, currentNames.length);
            System.arraycopy(nameArray, 0, newNames, currentNames.length, nameArray.length);
            return newNames;
        }
        return currentNames;
    }
}
