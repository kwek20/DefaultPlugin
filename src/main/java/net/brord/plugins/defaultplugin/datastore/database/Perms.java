package net.brord.plugins.defaultplugin.datastore.database;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Project Default<br/>
 * Class net.brord.plugins.commands.Perms.java<br/>
 * @author Brord
 * @since 29 mei 2014, 22:29:18
 */
public class Perms {
	private static final String BUNDLE_NAME = "net.brord.plugins.commands.perms"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Perms() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
