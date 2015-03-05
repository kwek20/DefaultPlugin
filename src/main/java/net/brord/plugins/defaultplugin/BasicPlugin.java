package net.brord.plugins.defaultplugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.brord.plugins.defaultplugin.annotations.Precondition;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandBase;
import net.brord.plugins.defaultplugin.commands.commandutils.CommandHandler;
import net.brord.plugins.defaultplugin.commands.exceptions.CheckException;
import net.brord.plugins.defaultplugin.config.BasicConfig;
import net.brord.plugins.defaultplugin.config.Config;
import net.brord.plugins.defaultplugin.config.MainConfig;
import net.brord.plugins.defaultplugin.datastore.BasicPlayer;
import net.brord.plugins.defaultplugin.factories.ListenerFactory;
import net.brord.plugins.defaultplugin.listeners.DefaultListener;
import net.brord.plugins.defaultplugin.managers.BasicPlayerManager;
import net.brord.plugins.defaultplugin.managers.ConfigManager;
import net.brord.plugins.defaultplugin.managers.Manager;
import net.brord.plugins.defaultplugin.managers.ManagerManager;
import net.brord.plugins.defaultplugin.util.ReflectionUtils;

import org.apache.commons.lang.reflect.ConstructorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.GHPlugin.java<br/>
 * @author Brord
 * @since 21 jun. 2014, 01:38:10
 */
public abstract class BasicPlugin extends JavaPlugin{
	private List<DefaultListener> listeners = new LinkedList<DefaultListener>();

	protected String prefix;
	private boolean debug;
	
	protected CommandHandler handler;
	
	protected ManagerManager manager = new ManagerManager();
	protected Config config = new Config(getDataFolder());
	protected MainConfig mainconfig;
	
	/**
	 *###############################################
	 **********************MAIN**********************
	 *###############################################
	 */
	
	/**
	 *  Always set the local static plugin instance first, THEN call super.onEnable();
	  * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	  */
	@Override
	public void onEnable() {
		super.onEnable();
		debug = getConfig().getBoolean("debug", false);
		prefix = getName() + " ";
		
		logDebug("onEnable()");
		loadAll();
	}
	
	public abstract void onReload();
	
	/**
	 * Loads everything
	 * @return
	 */
	public boolean loadAll(){
		logDebug("loadAll()");
		handler = new CommandHandler(this);
		if (load()){
			loadManagers();
			loadConfig();
			loadConfigs();
			loadCommands();
			loadListeners();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onDisable(){
		logDebug("onDisable()");
		for (DefaultListener l : listeners){
			l.stop();
		}
		
		for (BasicConfig c : getConfigManager().getAll()){
			c.save(config);
		}
		
		for (Manager<?> manager : this.manager.getAll()){
			manager.clear();
		}
	}
	
	/**'
	 * Load method, gets run on startup and on reload
	 * Must always set the main config
	 * @return if load was successful
	 */
	public boolean load(){
		logDebug("load()");
		saveDefaultConfig();
		checkConfig("config.yml");
		return true;
	}
	
	/**
	 *###############################################
	 ********************LISTENER********************
	 *###############################################
	 */
	
	/**
	 * Loads all the listeners
	 */
	public abstract void loadListeners();
	
	/**
	 * 
	 * @param identifier
	 * @param listener
	 * @throws CheckException 
	 */
	public void addListener(String identifier, Class<? extends DefaultListener> listener){
		logDebug("addListener(" + identifier + ")");
		try {
			addListener((DefaultListener) ConstructorUtils.getMatchingAccessibleConstructor(listener, new Class[]{this.getClass()}).newInstance(this));
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Attemps to add this listener to the game<br/>
	 * @param listener
	 * @return <code>boolean</code> if it was added
	 */
	public boolean addListener(DefaultListener listener){
		try {
			add(listener);
		} catch (CheckException e) {
			log("Listener " + listener.getClass().getSimpleName() + " failed to load because of missing precondition " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public void loadConfigListeners(ListenerFactory factory){
		logDebug("loadConfigListeners()");
		java.util.Map<String, Object> listeners = mainconfig.getListeners();
		for (String listener : listeners.keySet()){
			if (!(listeners.get(listener) instanceof Boolean)) {
				logDebug("Listener " + listener + " does not have a valid boolean in the config(true/false)");
				continue;
			}
			if (!(Boolean)listeners.get(listener)) {
				logDebug("Listener " + listener + " is disabled and wont be loaded");
				continue;
			}
			
			Class<? extends DefaultListener> c = factory.getListener(listener);
			if (c != null){
				addListener(listener, c);
			}
		}
	}
	
	/**
	 * Returns the instantiated class from the listener
	 * 
	 * @param listenerClass
	 * @return the class or <code>null</code>
	 */
	public <T> T getListener(Class<T> listenerClass) {
		for (DefaultListener listener : listeners) {
			if (listener.getClass().equals(listenerClass))
				return (T) listener;
		}

		return null;
	}
	
	/**
	 * Adds a listener
	 * @param listener
	 * @throws CheckException 
	 */
	private void add(DefaultListener listener) throws CheckException {
		logDebug("add()");
		CheckPreconditions(listener);
		Bukkit.getServer().getPluginManager().registerEvents(listener, this);
		log("Added listener " + listener.getClass().getSimpleName());
		listeners.add(listener);
	}
	
	/**
	 * Checks for preconditions on a listener
	 * @param listenerz
	 * @throws CheckException 
	 */
	private void CheckPreconditions(DefaultListener listener) throws CheckException {
		log(Arrays.toString(listener.getClass().getAnnotations()));
		if (listener.getClass().isAnnotationPresent(Precondition.class)){
			Precondition pre = listener.getClass().getAnnotation(Precondition.class);
			Method check = ReflectionUtils.getMethod(listener.getClass(), pre.value());
			Object ret = ReflectionUtils.runMethod(listener, check);
			if (ret instanceof Boolean){
				if (!(Boolean)ret){
					throw new CheckException(pre.value());
				}
			}
		}
	}

	/**
	 *###############################################
	 ********************MESSAGES********************
	 *###############################################
	 */
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public String getMessage(String msg) {
		if (!getConfigManager().get("messages").contains(msg)) return "Message not found: \"" + msg + "\"";
		return ChatColor.translateAlternateColorCodes('&', getConfigManager().get("messages").getString(msg));
	}

	/**
	 * @param sender
	 * @param string
	 */
	public void msg(CommandSender sender, String message) {
		if (message == null || message.length() == 0 || message.equals(" ")) return;
		
		if (sender instanceof Player && sender != null){
			((Player)sender).sendMessage(prefix + message);
		} else {
			log(message);
		}
	}
	
	/**
	 * @param sender
	 * @param message
	 */
	@SuppressWarnings("deprecation")
	public void msg(String sender, String message) {
		msg((CommandSender)getServer().getPlayer(sender), message);
	}
	
	/**
	 * @param sender
	 * @param string
	 */
	public void msg(Player sender, String message) {
		msg((CommandSender)sender, message);
	}
	
	/**
	 *###############################################
	 *******************CONFIGS**********************
	 *###############################################
	 */
	
	/**
	 * Loads all the configs
	 */
	public abstract void loadConfigs();

	/**
	 * @return 
	 */
	public ConfigManager getConfigManager() {
		return (ConfigManager) getManager(BasicConfig.class.getSimpleName());
	}
	
	/**
	 * Loads values from the main config to the custom fields for quicker access<br/>
	 * Dont forget to call super.loadConfig() when overriding
	 */
	public void loadConfig() {
		logDebug("loadConfig()");
		prefix = mainconfig.getPrefix();
		debug = mainconfig.isDebugOn();
		
		addManager(new ConfigManager());
		addConfig("messages");
	}
	
	/**
	 * Adds a config to the list
	 * @param config The name of the config
	 */
	public void addConfig(String config){
		addConfig(new BasicConfig(config));
	}
	
	/**
	 * Adds a config to the list
	 * @param config
	 */
	public void addConfig(BasicConfig config){
		logDebug("addConfig(" + config.getName() + ")");
		log("Loading in config " + config.getName());
		createOrChechConfig(config.getName() + ".yml");
		config.setConfig(this.config.getConfig(config.getName()));
		getConfigManager().addConfig(config.getName(), config);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private String createOrChechConfig(String name){
		if(!config.containsConfig(name)){
			if(getResource(name) != null){
				//doesnt exist on disk, but does in the jar
				saveResource(name, false);
			} else {
				config.createConfig(name);
			}
		} else {
			checkConfig(name);
		}
		return name;
	}
	
	/**
	 * @return 
	 * 
	 */
	public boolean checkConfig(String configname) {
		if (getResource(configname) == null)return true;
		boolean changed = false;
		
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(getResource(configname)));
		YamlConfiguration fconfig = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + configname));
		for (String key : config.getKeys(true)){
			if (!fconfig.contains(key)){
				fconfig.set(key, config.get(key));
				log("Added the config value: " + key);
				changed = true;
			}
		}
		
		if (changed){
			try {
				fconfig.save(getDataFolder() + File.separator + configname);
			} catch (IOException e) {e.printStackTrace();}
			reloadConfig();
		}
		
		return !changed;
	}
	
	/**
	 *###############################################
	 ***********************LOGGIN*******************
	 *###############################################
	 */
	
	/**
	 * Logs to the console, attempts to use colors
	 * @param message The message to log
	 */
	public void log(String message) {
		try{
			Bukkit.getServer().getConsoleSender().sendMessage(prefix + message);
		} catch (Exception e){
			System.out.println(ChatColor.stripColor(prefix) + message);
		}
	}
	
	/**
	 * Logs to the console, attempts to use colors, but only when debug mode is on
	 * @param message The message to log
	 */
	public void logDebug(String message) {
		if (debug) log(message);
	}
	
	/**
	 *###############################################
	 ***********************COMMANDS*****************
	 *###############################################
	 */
	
	/**
	 * Loads all the commands
	 */
	public abstract void loadCommands();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return handler.handle(sender, command, args);
	}
	
	/**
	 * 
	 * @param name Name of the command
	 * @param clazz class of the command
	 * 
	 * @see {@link net.brord.plugins.ghastlegionutil.commands.commandutils.CommandHandler#addCmd(String, Class)}
	 */
	public void addCommand(String name, Class<? extends CommandBase> clazz){
		logDebug("addCommand(" + name + ")");
		handler.addCmd(name, clazz);
	}
	
	/**
	 *###############################################
	 ***********************MANAGER******************
	 *###############################################
	 */
	
	/**
	 * Loads all the managers
	 * @return 
	 */
	public abstract boolean loadManagers();
	
	/**
	 * @param <T>
	 * @return the manager
	 */
	public <T extends Manager<?>> T getManager(Class<T> c) {
		return c.cast(getManager(c.getSimpleName()));
	}
	
	/**
	 * @return the manager
	 */
	public Manager<?> getManager(String c) {
		logDebug("Getting manager " + c);
		return manager.get(c);
	}
	
	/**
	 * @param manager the manager to set
	 * @return 
	 */
	public boolean addManager(Manager<?> manager) {
		logDebug("Adding manager " + manager.getClass().getSimpleName());
		return this.manager.addManager(manager);
	}

	/**
	 * @return
	 */
	public BasicPlayerManager<?> getPlayerManager() {
		return (BasicPlayerManager<?>) getManager(BasicPlayer.class.getSimpleName());
	}
	
	/**
	 * @param string
	 */
	public void removeManager(String string) {
		manager.remove(manager.get(string));
	}
	
	/**
	 *###############################################
	 ***********************GETTERS******************
	 *###############################################
	 */
	
	/**
	 * 
	 */
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * 
	 * @return
	 */
	public CommandHandler getCommandHandler() {
		return handler;
	}

	/**
	 * @return
	 */
	public Config getConfigParser() {
		return config;
	}

	/**
	 * @return
	 */
	public MainConfig getMainConfig() {
		return mainconfig;
	}
	
	/**
	 * @return
	 */
	public boolean isDebug() {
		return debug;
	}
	
	/**
	 *###############################################
	 ***********************OTHERS******************
	 *###############################################
	 */
	
	public boolean loadCustomEnchantment(Enchantment ench){
		/*further down in your main class, in the onEnable to be exactly:*/
		try{
			try {
				/* boolean that goes to false whne bukkit loaded it's own enchantlents */
				java.lang.reflect.Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true); //setting to true
			} catch (Exception e) {
				return false;
			}
			
			try {
				/* registring our enchantment to the bukkit default enchantments.*/
				Enchantment.registerEnchantment(ench);
			} catch (IllegalArgumentException e){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
