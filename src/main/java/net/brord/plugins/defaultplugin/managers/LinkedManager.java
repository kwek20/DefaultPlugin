package net.brord.plugins.defaultplugin.managers;

import java.util.HashMap;

import net.brord.plugins.defaultplugin.interfaces.Managable;
import net.brord.plugins.defaultplugin.pod.Name;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.managers.LinkedManager.java<br/>
 * @author Brord
 * @since 17 sep. 2014, 00:22:27
 */
public class LinkedManager<T extends Managable> extends Manager<Name>{
	
	private HashMap<Name, Manager<T>> storage;
	private Class<T> clazz;
	
	/**
	 * @param type
	 */
	public LinkedManager(Class<T> c) {
		super(Name.class); 
		this.clazz = c;
	}
	
	public Manager<T> getManagerFor(String name){
		return storage.get(get(name));
	}
	
	/**
	 * Adds the value to the linkedManager.<br/>
	 * If no link exists a new manager is created
	 * @param name the name
	 * @param value the value
	 */
	public void addValueFor(String name, T value){
		Name n = new Name(name);
		if (!exists(name) || !storage.containsKey(n)){
			add(n);
			storage.put(n, new Manager<>(clazz));
		}
		
		storage.get(n).add(value);
	}
	
	/**
	 * @param selectedMap
	 * @param name
	 * @return
	 */
	public boolean exists(String selectedMap, String name) {
		return getManagerFor(selectedMap).exists(name);
	}
}
