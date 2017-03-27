package cn.nextop.thorin.rcp.support.swt.utility.graphics;

import cn.nextop.thorin.rcp.support.util.collection.map.IntHashMap;
import cn.nextop.thorin.rcp.support.util.collection.map.LruMap;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import java.util.Map;

/**
 * 
 * @author Jingqi Xu
 */
public final class Extents {
	//
	private static final IntHashMap<LruMap<String, Point>> EXTENTS = new IntHashMap<>(256);
	
	/**
	 * Any better idea about EXTENTS' key?
	 */
	public static Point extent(GC gc, String text) {
		return extent(gc, text, getExtents(gc, 20480));
	}
	
	public static Point extent(GC gc, String text, Map<String, Point> cache) {
		Point r = cache.get(text); if (r != null) return r;
		cache.put(text, r = gc.textExtent(text)); return r;
	}
	
	protected static Map<String, Point> getExtents(final GC gc, int capacity) {
		final int hash = System.identityHashCode(gc.getFont());
		LruMap<String, Point> r = EXTENTS.get(hash); if (r != null) return r;
		EXTENTS.put(hash, r = new LruMap<String, Point>(capacity)); return r;
	}
}
