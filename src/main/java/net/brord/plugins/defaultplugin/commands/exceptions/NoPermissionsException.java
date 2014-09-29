/**
 * 
 */
package net.brord.plugins.defaultplugin.commands.exceptions;

/**
 * @author Brord
 * 
 */
public class NoPermissionsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7439318558283377884L;

	/**
	 * @param permission
	 */
	public NoPermissionsException(String permission) {
		super(permission);
	}

}
