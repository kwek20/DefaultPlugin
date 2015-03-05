package net.brord.plugins.defaultplugin.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.brord.plugins.defaultplugin.BasicPlugin;
import net.brord.plugins.defaultplugin.interfaces.Managable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.managers.ExpireManager.java<br/>
 * @author Brord
 * @since 11 jul. 2014, 23:57:20
 */
public abstract class ExpireManager<T extends Managable> extends Manager<T> {

	private int runnableID;
	protected Map<T, Long> data = new HashMap<T, Long>();
	private long expireTime;
	private BasicPlugin plugin;
	
	/**
	 * @param type
	 */
	public ExpireManager(BasicPlugin plugin, long time, Class<T> type) {
		super(type);
		this.plugin = plugin;
		expireTime = time;
	}

	public void stop(){
		Bukkit.getScheduler().cancelTask(runnableID);
		runnableID = -1;
	}
	
	public void start(){
		if (runnableID != -1) stop();
		runnableID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, getTask(), 20, 20);
	}
	
	@Override
	public boolean add(T o){
		if (o == null)return false;
		if (data.containsKey(o)) return false;
		
		if (data.isEmpty()) start();
		data.put(o, System.currentTimeMillis());
		return true;
	}
	
	@Override
	public void remove(T o){
		if (o == null)return;
		if (!data.containsKey(o)) return;
		
		data.remove(o);
		if (data.isEmpty()) stop();
	}
	
	/**
	 * @param the object
	 * @return 
	 */
	public long getTimeLeft(T t) {
		if (!data.containsKey(t)) return 0;
		return (data.get(t) + expireTime) - System.currentTimeMillis();
	}
	
	/**
	  * @see net.brord.plugins.cenchant.managers.Manager#getAll()
	  */
	@Override
	public List<T> getAll() {
		return new java.util.LinkedList<T>(data.keySet());
	}
	
	/**
	  * @see net.brord.plugins.cenchant.managers.Manager#exists(java.lang.String)
	  */
	@Override
	public boolean exists(String name) {
		for (T t : getAll()){
			if (t.name().equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	
	/**
	 * @return
	 */
	private Runnable getTask() {
		return new BukkitRunnable() {
			
			@Override
			public void run() {
				Set<T> objects = new java.util.HashSet<T>(data.keySet());
				for (T o : objects){
					if (data.get(o) + expireTime <= System.currentTimeMillis()){
						onExpire(o);
						data.remove(o);
					}
				}
			}
		};
	}

	public abstract void onExpire(T o);
}
