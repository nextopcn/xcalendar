package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;

/**
 * 
 * @author Jingqi Xu
 */
public class XCalendarFrame implements AutoCloseable {
	//
	private GC gc;
	private Transform transform;
	
	/**
	 * 
	 */
	public XCalendarFrame(GC gc, Transform transform) {
		this.gc = gc; this.transform = transform;
	}

	/**
	 * 
	 */
	public final GC getGc() {
		return gc;
	}
	
	public Transform getTransform() {
		return transform;
	}

	/**
	 * 
	 */
	@Override
	public void close() {
		if(this.gc.isDisposed()) return;
		gc.setClipping((Rectangle)null);
		if(transform != null && !transform.isDisposed()) transform.dispose();
	}
}
