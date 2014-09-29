package net.brord.plugins.defaultplugin.managers;


/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.managers.ManagerManager.java<br/>
 * @author Brord
 * @since 2 jul. 2014, 18:11:22
 */
@SuppressWarnings("rawtypes")
public class ManagerManager extends Manager<Manager> {

	/**
	 * Makes a manager
	 */
	public ManagerManager() {
		super(Manager.class);
	}

	/**
	 * 
	 * @param m
	 * @return 
	 */
	public boolean addManager(Manager m){
		return add(m);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public Manager get(String o){
		for(Manager m : data){
//			System.out.println("Comparing " + o + " with " + m.getClass().getSimpleName() + " and " + m.getType().getSimpleName());
			if (m.getClass().getSimpleName().equalsIgnoreCase(o)) return m;
			if (m.getType().getSimpleName().equalsIgnoreCase(o)) return m;
		}
		return null;
	}
}
