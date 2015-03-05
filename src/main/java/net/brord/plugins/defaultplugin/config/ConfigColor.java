package net.brord.plugins.defaultplugin.config;

import java.util.Random;

import org.bukkit.Color;

/**
 * Project MagicBox<br/>
 * Class me.rotm.ConfigColor.java<br/>
 * @author Brord
 * @since 5 mrt. 2015, 10:02:37
 */
public class ConfigColor {
	
	protected Random r;
	
	private int red, green, blue;
	
	/**
	 * @param red
	 * @param green
	 * @param blue
	 */
	public ConfigColor(int red, int green, int blue) {
		this.red = red > 255 ? 255 : red;
		this.green = green > 255 ? 255 : green;
		this.blue = blue > 255 ? 255 : blue;
		
		if (red < 0 || green < 0 || blue < 0 ){
			r = new Random();
		}
	}
	
	public Color getColor(){
		return Color.fromRGB(getRed(), getGreen(), getBlue());
	}

	/**
	 * @return the red
	 */
	public int getRed() {
		return get(red);
	}
	
	/**
	 * @return the green
	 */
	public int getGreen() {
		return get(green);
	}
	
	/**
	 * @return the blue
	 */
	public int getBlue() {
		return get(blue);
	}
	
	private int get(int var){
		if (var < 0){
			return getRandom();
		} else {
			return var;
		}
	}
	
	private int getRandom(){
		return r.nextInt(255);
	}
}
