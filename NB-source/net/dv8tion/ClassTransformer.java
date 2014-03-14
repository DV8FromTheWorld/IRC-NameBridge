package net.dv8tion;

import java.util.Iterator;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Provides functionality for ByteCode insertion.
 * 
 * @author DV8FromTheWorld (Austin Keener)
 * @version v0.1.0 3/14/2013
 * @MC.Version 1.6.4
 */
public class ClassTransformer implements IClassTransformer
{
    /**
     * Called when <a href="https://github.com/MinecraftForge/FML">FML</a> is
     * relaunching Minecraft.
     * This is called for every class in Minecraft that is loaded.
     * This means that if the Client is what is being loaded, this method will
     * not process
     * server only class, because they wont be loading.
     * 
     * Looks for the ServerConfigurationManager, which is also known as hn when
     * Minecraft is obfuscated. If the class passed to the method is not the
     * class we are looking for, we pass the classData back, unmodified.
     * Will only find it when the Server is loading (Dedicated or Integrated)
     * 
     * @param className
     *            The class name, proceeded by the package it is in, if it is in
     *            one.
     * @param transformedName
     *            Not really sure... Sorry.
     * @param classData
     *            The byte code of the class.
     */
    @Override
    public byte[] transform(String className, String transformedName, byte[] classData)
    {
        if (className.equals("hn"))
        {
            System.out.println("[IRC NameBridge] INSIDE OBFUSCATED ServerConfigurationManager TRANSFORMER ABOUT TO PATCH: " + className);
            return patchClassWithASM(className, classData, true);
        }

        if (className.equals("net.minecraft.server.management.ServerConfigurationManager"))
        {
            System.out.println("[IRC NameBridge] INSIDE ServerConfigurationManager TRANSFORMER ABOUT TO PATCH: " + className);
            return patchClassWithASM(className, classData, false);
        }
        return classData;
    }

    /**
     * Helper method to modify the getAllUsernames method in ServerConfigurationManager.
     * Takes into account obfuscated method names based on boolean.
     * 
     * Replaces the call "return astring" at the end of ServerConfigurationManager's
     * getAllUsernames method with "return NameLoader.loadNames(astring)"
     * Because of how The JVM and ByteCode work with arrays, we do not need to
     * remove any instructions, only inject code. The array "astring" in the
     * call "return astring" will be provided as the param for the loadNames method.
     * 
     * @param className
     *            The class name, proceeded by the package it is in, if it is in one.
     * @param classData
     *            The byte code of the class.
     * @param obfuscated
     *            Is the code obfuscated?
     * @return
     *         Returns the modified byte code of the class.
     */
    public byte[] patchClassWithASM(String className, byte[] classData, boolean obfuscated)
    {
        String methodName = obfuscated ? "d" : "getAllUsernames";

        ClassNode classNode = new ClassNode(Opcodes.ASM4);
        ClassReader classReader = new ClassReader(classData);
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        Iterator<MethodNode> methods = classNode.methods.iterator();
        while (methods.hasNext())
        {
            MethodNode m = methods.next();
            int arrayReturn_index = -1;

            if ((m.name.equals(methodName) && m.desc.equals("()[Ljava/lang/String;")))
            {
                AbstractInsnNode currentNode = null;
                Iterator<AbstractInsnNode> iter = m.instructions.iterator();

                int index = -1;

                while (iter.hasNext())
                {
                    index++;
                    currentNode = iter.next();

                    if (currentNode.getOpcode() == Opcodes.ARETURN)
                    {
                        arrayReturn_index = index;
                    }
                }

                //Calls NameLoader.loadNames(String[]) 
                m.instructions.insertBefore(m.instructions.get(arrayReturn_index), 
                        new MethodInsnNode(Opcodes.INVOKESTATIC, "net/dv8tion/NameLoader",
                        "loadNames", "([Ljava/lang/String;)[Ljava/lang/String;"));

                System.out.println("[IRC NameBridge] Patching Complete!");
                break;
            }
        }
        //ASM specific for cleaning up and returning the final bytes for JVM processing.
        //Use 0 here instead of like ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS because
        //We need to have ASM recalculate things.  
        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        return writer.toByteArray();  
    }
}
