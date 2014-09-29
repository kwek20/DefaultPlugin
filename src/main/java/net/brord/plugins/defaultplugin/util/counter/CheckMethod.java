package net.brord.plugins.defaultplugin.util.counter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.util.org.apache.commons.lang3.Validate;

/**
 * Create a counter method for checking<br/>
 * 
 * 
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.counter.CheckMethod.java<br/>
 * @author Brord
 * @since 15 jul. 2014, 14:48:59
 */
public class CheckMethod<T> {
	private Method method;
	private Object clazz;
	
	/**
	 * @param m
	 * @param clazz
	 */
	public CheckMethod(Method method, Object clazz) throws IllegalArgumentException{
		Validate.notNull(method);
		
		this.method = method;
		this.clazz = clazz;
	}
	
	public boolean returnsBoolean(){
		return method.getGenericReturnType().toString().equals("boolean");
	}
	
	public Object run(T... parameter){
		try {
			return method.invoke(clazz, parameter);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @return 
	 * 
	 */
	public Object run() {
		return run(null);
	}
}
