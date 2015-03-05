package net.brord.plugins.defaultplugin.config;

import java.util.Random;

import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Project MagicBox<br/>
 * Class me.rotm.ConfifFirework.java<br/>
 * @author Brord
 * @since 5 mrt. 2015, 09:53:29
 */
public class ConfigFirework extends ConfigColor { 
	
	private Type type;
	private boolean flicker;
	
	/**
	 * @param red
	 * @param green
	 * @param blue
	 * @param type
	 */
	public ConfigFirework(String type, boolean flicker, int red, int green, int blue) {
		super(red, green, blue);
		
		this.flicker = flicker;
		try { this.type = Type.valueOf(type);
		} catch (Exception e){}
		
		if (type == null && r == null){
			r = new Random();
		}
	}
	
	/**
	 * @return the type
	 */
	public Type getType() {
		if (type == null){
			return Type.values()[r.nextInt(Type.values().length)];
		} else {
			return type;
		}
	}
	
	public FireworkEffect getEffect(){
		return FireworkEffect.builder().flicker(flicker).with(getType()).withColor(getColor()).build();
	}

	/**
	 *   flicker: true
		  type: BALL
		  trail: 
		  red: -1
		  green: -1
		  blue: -1

	 * @param configurationSection
	 * @return
	 */
	public static ConfigFirework load(ConfigurationSection conf) {
		if (conf == null) return null;
		
		return new ConfigFirework(conf.getString("type", "BALL"), conf.getBoolean("flicker", false), 
				conf.getInt("red", -1), 
				conf.getInt("green", -1), 
				conf.getInt("blue", -1));
	}
}
