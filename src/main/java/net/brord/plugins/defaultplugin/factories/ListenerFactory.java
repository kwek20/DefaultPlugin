package net.brord.plugins.defaultplugin.factories;

import java.util.HashMap;
import java.util.Map;

import net.brord.plugins.defaultplugin.listeners.DefaultListener;

/**
 * Project Arena<br/>
 * Class net.brord.plugins.arena.factory.GameModeFactory.java<br/>
 * @author Brord
 * @since 12 jul. 2014, 01:23:15
 */
public class ListenerFactory {

	Map<String, Class<? extends DefaultListener>> map = new HashMap<>();

	public Class<? extends DefaultListener> getListener(String name){
		return map.get(name.toLowerCase());
	}
	
	public void addListener(String name, Class<? extends DefaultListener> listener){ map.put(name.toLowerCase(), listener);}
}
