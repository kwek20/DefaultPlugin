/**
 *
 */
package net.brord.plugins.defaultplugin.enums;

import java.lang.reflect.Field;

import net.brord.plugins.defaultplugin.util.ReflectionUtils;

import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public enum Localizer {

	// English variants have been merged for sake of simplicity.
	AUSTRALIAN_ENGLISH("Australian English", "en_CA"/* "en_AU" */), 
	AFRIKAANS("Afrikaans", "af_ZA"), 
	ARABIC("Ø§Ù„Ø¹Ø±Ø¨ÙŠØ©", "ar_SA"),
	BULGARIAN("Ð‘ÑŠÐ»Ð³Ð°Ñ€Ñ�ÐºÐ¸", "bg_BG"), 
	CATALAN("CatalÃ ", "ca_ES"), 
	CZECH("ÄŒeÅ¡tina", "cs_CZ"), 
	CYMRAEG("Cymraeg", "cy_GB"), 
	DANISH("Dansk", "da_DK"), 
	GERMAN("Deutsch", "de_DE"),
	GREEK("Î•Î»Î»Î·Î½Î¹ÎºÎ¬", "el_GR"), 
	CANADIAN_ENGLISH("Canadian English", "en_CA"), 
	ENGLISH("English", "en_CA"/* "en_GB" */), 
	PIRATE_SPEAK("Pirate Speak", "en_PT"), 
	ESPERANTO("Esperanto", "eo_EO"), 
	ARGENTINEAN_SPANISH("EspaÃ±ol Argentino", "es_AR"), 
	SPANISH("EspaÃ±ol", "es_ES"), 
	MEXICO_SPANISH("EspaÃ±ol MÃ©xico", "es_MX"), 
	URUGUAY_SPANISH("EspaÃ±ol Uruguay","es_UY"), 
	VENEZUELA_SPANISH("EspaÃ±ol Venezuela", "es_VE"), 
	ESTONIAN("Eesti", "et_EE"), 
	EUSKARA("Euskara", "eu_ES"), 
	ENGLISH1("Ø²Ø¨Ø§Ù† Ø§Ù†Ú¯Ù„ÛŒØ³ÛŒ", "en_CA"/* "fa_IR" */), 
	FINNISH("Suomi", "fi_FI"), 
	TAGALOG("Tagalog", "fil_PH"), 
	FRENCH_CA("FranÃ§ais", "fr_CA"),
	FRENCH("FranÃ§ais", "fr_FR"), 
	GAEILGE("Gaeilge", "ga_IE"), 
	GALICIAN("Galego", "gl_ES"), 
	HEBREW("×¢×‘×¨×™×ª", "he_IL"), 
	ENGLISH2("à¤…à¤‚à¤—à¥�à¤°à¥‡à¥›à¥€", "en_CA"/* "hi_IN" */), 
	CROATIAN("Hrvatski", "hr_HR"), 
	HUNGARIAN("Magyar", "hu_HU"), 
	ARMENIAN("Õ€Õ¡ÕµÕ¥Ö€Õ¥Õ¶", "hy_AM"), 
	BAHASA_INDONESIA("Bahasa Indonesia", "id_ID"), 
	ICELANDIC("Ã�slenska", "is_IS"), 
	ITALIAN("Italiano", "it_IT"), 
	JAPANESE("æ—¥æœ¬èªž", "ja_JP"), 
	GEORGIAN("áƒ¥áƒ�áƒ áƒ—áƒ£áƒšáƒ˜", "ka_GE"), 
	KOREAN("í•œêµ­ì–´", "ko_KR"), 
	KERNEWEK("Kernewek", "kw_GB"), 
	ENGLISH3("à¤…à¤‚à¤—à¥�à¤°à¥‡à¥›à¥€", "en_CA"/* "ky_KG" */), 
	LINGUA_LATINA("Lingua latina", "la_LA"), 
	LETZEBUERGESCH("LÃ«tzebuergesch", "lb_LU"), 
	LITHUANIAN("LietuviÅ³", "lt_LT"), 
	LATVIAN("LatvieÅ¡u", "lv_LV"), 
	MALAY_NZ("Bahasa Melayu", "mi_NZ"), 
	MALAY_MY("Bahasa Melayu", "ms_MY"), 
	MALTI("Malti", "mt_MT"), 
	NORWEGIAN("Norsk", "nb_NO"), 
	DUTCH("Nederlands", "nl_NL"), 
	NORWEGIAN_NYNORSK("Norsk nynorsk", "nn_NO"), 
	NORWEGIAN1("Norsk", "no_NO"), 
	OCCITAN("Occitan", "oc_FR"), 
	PORTUGUESE_BR("PortuguÃªs", "pt_BR"), 
	PORTUGUESE_PT("PortuguÃªs", "pt_PT"), 
	QUENYA("Quenya", "qya_AA"), 
	ROMANIAN("RomÃ¢nÄƒ", "ro_RO"), 
	RUSSIAN("Ð ÑƒÑ�Ñ�ÐºÐ¸Ð¹", "ru_RU"), 
	ENGLISH4("AngliÄ�tina", "en_CA"/* "sk_SK" */), 
	SLOVENIAN("SlovenÅ¡Ä�ina", "sl_SI"), 
	SERBIAN("Ð¡Ñ€Ð¿Ñ�ÐºÐ¸", "sr_SP"), 
	SWEDISH("Svenska", "sv_SE"), 
	THAI("à¸ à¸²à¸©à¸²à¹„à¸—à¸¢", "th_TH"), 
	tlhIngan_Hol("tlhIngan Hol", "tlh_AA"), 
	TURKISH("TÃ¼rkÃ§e", "tr_TR"), 
	UKRAINIAN("Ð£ÐºÑ€Ð°Ñ—Ð½Ñ�ÑŒÐºÐ°", "uk_UA"), 
	VIETNAMESE("Tiáº¿ng Viá»‡t", "vi_VI"), 
	SIMPLIFIED_CHINESE("ç®€ä½“ä¸­æ–‡", "zh_CN"), 
	TRADITIONAL_CHINESE("ç¹�é«”ä¸­æ–‡", "zh_TW"), 
	POLISH("Polski", "pl_PL");

	private String name;
	private String code;

	private Localizer(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}
	
//	@Override
//	public String toString() {
//		return name;
//	}

	public String getCode() {
		return code;
	}

	private static Field field;

	public static Localizer getLocale(Player inPlayer) {
		try {
			Object nms = ReflectionUtils.getHandle(inPlayer);
			if (field == null) {
				field = nms.getClass().getDeclaredField("locale");
				field.setAccessible(true);
			}
			Localizer code = getByCode((String) field.get(nms));
			return code;
		} catch (Exception exc) {
			exc.printStackTrace();
			return Localizer.ENGLISH;
		}
	}

	public static Localizer getByCode(String code) {
		for (Localizer l : values()) {
			if (l.getCode().equalsIgnoreCase(code))
				return l;
		}
		return Localizer.ENGLISH;
	}
}
