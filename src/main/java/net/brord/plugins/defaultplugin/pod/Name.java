package net.brord.plugins.defaultplugin.pod;

import net.brord.plugins.defaultplugin.interfaces.Managable;


/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.pod.Name.java<br/>
 * @author Brord
 * @since 17 sep. 2014, 00:48:29
 */
public class Name implements Managable {

	private String name;

	/**
	 * 
	 */
	public Name(String name) {
		this.name = name;
	}

	/**
	 * @see net.brord.plugins.ghastlegionutil.interfaces.Managable#name()
	 */
	@Override
	public String name() {
		return name;
	}

}
