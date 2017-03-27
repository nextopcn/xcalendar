package cn.nextop.thorin.rcp.support.swt.utility.graphics;

import cn.nextop.thorin.rcp.support.util.collection.map.ConcurrentMultiKeyMap;
import com.patrikdufresne.fontawesome.FontAwesome;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

import java.util.function.Supplier;

/**
 * 
 * @author Jingqi Xu
 */
public final class Fonts {
	//
	public static final Font SYSTEM = Display.getDefault().getSystemFont();
	public static final Font AWESOME = FontAwesome.getFont(Math.max(10, getHeight(SYSTEM)));
	
	//
	protected static final ConcurrentMultiKeyMap<Class<?>, String, Font> FONTS = new ConcurrentMultiKeyMap<>();
	
	/**
	 * Font
	 */
	public static Font getSystemFont() {
		return SYSTEM;
	}
	
	public static Font getAwesomeFont() {
		return AWESOME;
	}
	
	/**
	 * 
	 */
	public static final int getHeight(Font font) {
		return font.getFontData()[0].getHeight();
	}
	
	public static final Font bold(final Font font) {
		FontDescriptor fd = FontDescriptor.createFrom(font);
		return fd.setStyle(SWT.BOLD).createFont(Display.getDefault());
	}
	
	public static final Font size(Font font, int delta) {
		FontDescriptor fd = FontDescriptor.createFrom(font);
		int height = fd.getFontData()[0].getHeight() + delta;
		return fd.setHeight(height).createFont(Display.getDefault());
	}

	/**
	 * 
	 */
	public static Font getFont(Class<?> clazz, String key, Supplier<Font> font) {
		Font r = FONTS.get(clazz, key); if(r != null) return r;
		Font existing = FONTS.putIfAbsent(clazz, key, r = font.get());
		if (existing != null) { r.dispose(); r = existing; } return r; // Dispose
	}
}
