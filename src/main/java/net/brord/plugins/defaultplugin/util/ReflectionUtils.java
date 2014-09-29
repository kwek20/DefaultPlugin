/**
 * 
 */
package net.brord.plugins.defaultplugin.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ReflectionUtils {

    public static Class<?> getCraftClass(String ClassName) {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1) + ".";
        String className = "net.minecraft.server." + version + ClassName;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        }
        catch (Exception e) { e.printStackTrace(); }
        return c;
    }

    public static Object getHandle(Entity entity) {
        try {
            return getMethod(entity.getClass(), "getHandle").invoke(entity);
        }
        catch (Exception e){
            e.printStackTrace(); 
            return null;
        }
    }

    public static Object getHandle(World world) {
        try {
            return getMethod(world.getClass(), "getHandle").invoke(world);
        }
        catch (Exception e){
            e.printStackTrace(); 
            return null;
        }
    }

    public static Field getField(Class<?> cl, String field_name) {
        try {
        	cl.getField(field_name).setAccessible(true);
            return cl.getField(field_name);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Object runMethod(Object cl, Method m, Object... args){
    	try {
			return m.invoke(cl, args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			return null;
		}
    }

    public static Method getMethod(Class<?> cl, String method, Class<?>... args) {
        for (Method m : cl.getMethods()) 
            if (m.getName().equals(method) && ClassListEqual(args, m.getParameterTypes()))
                return m;
        return null;
    }

    public static Method getMethod(Class<?> cl, String method) {
        for (Method m : cl.getMethods()) {
        	if (m.getName().equalsIgnoreCase(method))
                return m;
        }
        return null;
    }

    public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length)
            return false;
        for (int i = 0; i < l1.length; i++)
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        return equal;
    }

    /**
	 * @param plugin
	 * @param player
	 */
	public static void respawnPlayer(JavaPlugin plugin, final Player player) {
		new BukkitRunnable(){
            public void run(){
                try {
                    Object nmsPlayer = getHandle(player);
                    Object packet = getCraftClass("PacketPlayInClientCommand").newInstance();
                    Field a = packet.getClass().getDeclaredField("a");
                    a.setAccessible(true);
                    
                    a.set(packet, getCraftClass("EnumClientCommand").getEnumConstants()[0]);
                    
                    Object con = getField(nmsPlayer.getClass(), "playerConnection").get(nmsPlayer);
                    con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }.runTaskLater(plugin, 2L);
	}
}