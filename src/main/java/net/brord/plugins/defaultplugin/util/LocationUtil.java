package net.brord.plugins.defaultplugin.util;

import org.bukkit.util.Vector;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.LocationUtil.java<br/>
 * @author Brord
 * @since 29 sep. 2014, 02:30:21
 */
public class LocationUtil {

	private LocationUtil() {
	}
	
	 
	/**
	 * Converts 2 vectors into lowest  and highest point
	 * @param v1
	 * @param v2
	 */
	public static void smallLarge(Vector v1, Vector v2){
		Vector c1 = v1.clone(), c2 = v2.clone();
		
		if (v1.getX() < v2.getX()){v1.setX(c1.getX());v2.setX(c2.getX());
		} else {v2.setX(c1.getX());v1.setX(c2.getX());
		}
		
		if (v1.getY() < v2.getY()){v1.setY(c1.getY());v2.setY(c2.getY());
		} else {v2.setY(c1.getY());v1.setY(c2.getY());
		}
		
		if (v1.getZ() < v2.getZ()){v1.setZ(c1.getZ());v2.setZ(c2.getZ());
		} else {v2.setZ(c1.getZ());v1.setZ(c2.getZ());
		}
	}
}
