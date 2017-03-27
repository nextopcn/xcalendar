package cn.nextop.thorin.rcp.support.swt.utility;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * 
 * @author Jingqi Xu
 */
public final class SwtUtils {
	
	/**
	 * 
	 */
	public static void dispose(GC v) {
		if(v != null && !v.isDisposed()) v.dispose();
	}
	
	/**
	 * Display
	 */
	public static boolean isUIThread() {
		return isUIThread(getDisplay());
	}
	
	public static boolean isUIThread(Display display) {
		return display.getThread() == Thread.currentThread();
	}
	
	public static final Display getDisplay() {
		final Display display = Display.getCurrent();
		return display != null ? display : Display.getDefault();
	}
	
	public static void sync(@Nullable Display display, Runnable task) {
		if(display == null) display = getDisplay(); display.syncExec(task);
	}
	
	public static void async(@Nullable Display display, Runnable task) {
		if(display == null) display = getDisplay(); display.asyncExec(task);
	}
	
	public static <T> T sync(@Nullable Display display, Supplier<T> task) {
		final AtomicReference<T> result = new AtomicReference<>();
		sync(display, () -> { result.set(task.get()); }); return result.get();
	}
	
	/**
	 * Dispatch
	 */
	public static void dispatch(final Supplier<Boolean> condition) {
		final Display display = Display.getCurrent();
		while (!condition.get()) if(!display.readAndDispatch()) display.sleep(); 
	}
	
	/**
	 * Slider
	 */
	public static void setValues(Slider s) {
		setValues(s, 0, 0, 1, 0);
	}
	
	public static void setValues(Slider s, int min, int max, int thumb) {
		setValues(s, min, max, thumb, s.getSelection());
	}
	
	public static void setValues(Slider s, int min, int max, int thumb, int selection) {
		setValues(s, min, thumb + max, thumb, selection, s.getIncrement(), s.getPageIncrement());
	}
	
	public static void setValues(Slider s, int min, int max, int thumb, int selection, int increment, int page) {
		s.setValues(selection, min, max, thumb, increment, page);
	}
	
	/**
	 * Shell
	 */
	public static void center(final Shell shell) {
	    final Rectangle r1 = shell.getMonitor().getBounds(), r2 = shell.getBounds();
	    shell.setLocation(r1.x + (r1.width - r2.width) / 2, r1.y + (r1.height - r2.height) / 2);
	}
	
	public static void resize(Shell shell, @Nullable Integer width, @Nullable Integer height) {
		final Point s = shell.getSize();
		int w = width == null ? s.x : width, h = height == null ? s.y : height; shell.setSize(new Point(w, h));
	}
	
	public static void resize(Shell shell, @Nullable Integer width, @Nullable Integer height, @Nullable Integer wMargin, @Nullable Integer hMargin) {
		final Rectangle r = shell.getMonitor().getBounds();
		final int wm = wMargin == null ? 0 : wMargin, hm = hMargin == null ? 0 : hMargin;
		final int w = width == null ? r.width : width, h = height == null ? r.height : height; shell.setSize(new Point(w - (wm << 1), h - (hm << 1)));
	}
}
