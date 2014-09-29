package net.brord.plugins.defaultplugin.datastore;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import net.brord.plugins.defaultplugin.interfaces.Managable;
import net.brord.plugins.defaultplugin.util.Parse;
import net.brord.plugins.defaultplugin.util.ReflectionUtils;


/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.datastore.Command.java<br/>
 * @author Brord
 * @since 22 jun. 2014, 01:16:45
 */
public class SubCommand implements Comparable<SubCommand>, Managable{
	private String name;
	private String functionName;
	
	private List<String> aliases = new LinkedList<String>();
	private String permission;
	
	private Object clazz;
	
	/**
	 * @param name
	 */
	public SubCommand(String name, Object clazz) {
		this(name, name, clazz);
	}
	
	public SubCommand(String name, String functionName, Object clazz){
		this.name = name;
		this.functionName = functionName;
		this.clazz = clazz;
	}

	/**
	 * @return the aliases
	 */
	public List<String> getAliases() {
		return aliases;
	}

	/**
	 * @param aliases the aliases to set
	 * @return 
	 */
	public SubCommand setAliases(List<String> aliases) {
		this.aliases = aliases;
		return this;
	}
	
	/**
	 * 
	 * @param alias the alias to add
	 * @return
	 */
	public SubCommand addAlias(String alias){
		aliases.add(alias);
		return this;
	}

	public boolean hasAliases(){
		return aliases.size() > 0;
	}
	
	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}
	
	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param permission the permission to set
	 * @return 
	 */
	public SubCommand setPermission(String permission) {
		this.permission = permission;
		return this;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public Method getMethod(){
		return ReflectionUtils.getMethod(clazz.getClass(), functionName);
	}
	
	/**
	  * @see java.lang.Object#toString()
	  */
	@Override
	public String toString() {
		return name + (hasAliases() ? "(" + Parse.arrayToString(aliases.toArray()) + ")" : "");
	}

	/**
	  * @see java.lang.Comparable#compareTo(java.lang.Object)
	  */
	@Override
	public int compareTo(SubCommand o) {
		return o.getName().compareTo(getName());
	}
	
	public Object run(){
		return run(new Object[0]);
	}
	
	public Object run(Object ... args){
		return ReflectionUtils.runMethod(clazz, getMethod(), args);
	}

	/**
	  * @see net.brord.plugins.ghastlegionutil.interfaces.Managable#name()
	  */
	@Override
	public String name() {
		return name;
	}

	/**
	 * @return the clazz
	 */
	public Object getClazz() {
		return clazz;
	}
}


