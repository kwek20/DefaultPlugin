package net.brord.plugins.defaultplugin.util.counter;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.brord.plugins.defaultplugin.util.TimeUtil;
import net.brord.plugins.defaultplugin.util.counter.message.CounterChatMessageMode;
import net.brord.plugins.defaultplugin.util.counter.message.CounterMessageMode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.Counter.java<br/>
 * @author Brord
 * @since 14 jul. 2014, 21:16:50
 */
public class Counter implements Cancellable{
	
	private List<UUID> players = new LinkedList<UUID>();
	
	private boolean up;
	private int seconds;
	private String startText, countDownText, cancelText, doneText;
	private CheckMethod<Player> checker;
	private CheckMethod<Object> finalMethod;
	
	private CounterMessageMode mode;

	private List<Integer> broadCastSeconds;

	private BukkitTask task;

	private boolean cancelled;
	
	/**
	 * Counter time
	 */
	int amount;
	
	/**
	 * @param up
	 * @param seconds
	 * @param countDownText
	 * @param cancelText
	 * @param checker
	 * @param finalMethod
	 */
	public Counter(boolean up, int seconds, String startText, String countDownText, String cancelText, String doneText, CheckMethod<Player> checker, CheckMethod<Object> finalMethod, CounterMessageMode mode, List<Integer> broadCastSeconds) {
		this.up = up;
		this.seconds = seconds;
		this.startText = replaceTime(startText);
		this.countDownText = replaceTime(countDownText);
		this.doneText = replaceTime(doneText);
		this.cancelText = replaceTime(cancelText);
		this.checker = checker;
		this.finalMethod = finalMethod;
		this.mode = mode;
		this.broadCastSeconds = broadCastSeconds;
		
		this.amount = up ? 0 : seconds;
	}
	
	/**
	 * @param startText2
	 * @return
	 */
	private String replaceTime(String text) {
		if (text == null) return null;
		return text.replace("<fulltime>", TimeUtil.getStampFromSeconds(seconds));
	}

	/**
	 * 
	 * @param up
	 * @param partyStartDelay
	 * @param countDownText
	 * @param checkMethod
	 * @param doneMethod
	 * @param counterChatMessageMode
	 */
	public Counter(boolean up, int partyStartDelay, String countDownText, CheckMethod<Player> checkMethod, CheckMethod<Object> doneMethod, CounterChatMessageMode counterChatMessageMode, List<Integer> broadCastSeconds) {
		this(up, partyStartDelay, null, null, null, countDownText , checkMethod, doneMethod, counterChatMessageMode, broadCastSeconds);
	}

	private final void broadCast(String message){
		if (message == null)return;
		
		for (UUID id : players){
			Player p = Bukkit.getPlayer(id);
			if (p == null) return;
			mode.message(p, message);
		}
	}
	
	public void addPlayer(UUID p){
		players.add(p);
	}
	
	public void removePlayer(UUID p){
		players.remove(p);
	}
	
	/**
	 * @param keySet
	 */
	public <T> void addAll(Set<T> keySet) {
		for (T id : keySet){
			if (id instanceof Player){
				addPlayer(((Player)id).getUniqueId());
			} else if (id instanceof UUID){
				addPlayer((UUID)id);
			}
		}
	}
	
	public boolean checkMethod(){
		if (checker == null) return true;
		if (!checker.returnsBoolean()){
			checker.run();
		} else {
			for (UUID id : players){
				Player p = Bukkit.getPlayer(id);
				if (!(Boolean)checker.run(p)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 */
	protected void cancelCounter() {
		for (UUID id : players){
			Player p = Bukkit.getPlayer(id);
			if (p == null) return;
			mode.clear(p);
		}
		cancelled = true;
	}
	
	/**
	 * 
	 */
	protected void counterDone() {
		broadCast(doneText);
		if (finalMethod == null) return;
		finalMethod.run();
	}
	
	public BukkitTask run(JavaPlugin plugin){
		broadCast(startText);
		return task = getRun().runTaskTimer(plugin, 0, 20);
	}
	
	private CounterTask getRun(){
		return new CounterTask();
	}
	
	private class CounterTask extends BukkitRunnable {
		boolean cancel = false;
		
		@Override
		public void run() {
			if (cancel){
				cancel();
				cancelCounter();
				return;
			}
			
			if (!checkMethod()) {
				//cancelled
				broadCast(cancelText);
				cancel = true;
			} else {
				if (up ? amount == seconds : amount == 0){
					//done?
					counterDone();
					cancel = true;
				} else {
					//not done
					boolean broadcast = true;
					if (broadCastSeconds != null && !broadCastSeconds.isEmpty()){
						if (!broadCastSeconds.contains(amount)) broadcast = false;
					}
					
					if (broadcast)broadCast(countDownText.replace("<time>", TimeUtil.getStampFromSeconds(amount)));
					
					amount += up ? 1 : -1;
				}
			}
		}
	}
	
	/**
	 * @return the cancelText
	 */
	public String getCancelText() {
		return cancelText;
	}
	
	/**
	 * @return the checker
	 */
	public CheckMethod<Player> getChecker() {
		return checker;
	}
	
	/**
	 * @return the countDownText
	 */
	public String getCountDownText() {
		return countDownText;
	}
	
	/**
	 * @return the doneText
	 */
	public String getDoneText() {
		return doneText;
	}
	
	/**
	 * @return the finalMethod
	 */
	public CheckMethod<Object> getFinalMethod() {
		return finalMethod;
	}
	
	/**
	 * @return the players
	 */
	public List<UUID> getPlayers() {
		return players;
	}
	
	/**
	 * @return the seconds
	 */
	public int getSeconds() {
		return seconds;
	}
	
	/**
	 * @return the startText
	 */
	public String getStartText() {
		return startText;
	}
	
	protected static Method getMethod(Object o, String method){
		return getMethod(o, method, (Class<?>[])null);
	}
	
	protected static Method getMethod(Object o, String method, Class<?>... params){
		try {
			return o.getClass().getMethod(method, params);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 */
	public void cancel() {
		task.cancel();
		setCancelled(true);
	}

	/**
	 * @return
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	  * @see org.bukkit.event.Cancellable#setCancelled(boolean)
	  */
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * @return
	 */
	public int getTimeLeft() {
		return amount;
	}
}
