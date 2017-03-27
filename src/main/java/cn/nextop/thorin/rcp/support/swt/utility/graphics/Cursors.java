package cn.nextop.thorin.rcp.support.swt.utility.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;

import static org.eclipse.swt.widgets.Display.getDefault;

/**
 * 
 * @author Jingqi Xu
 */
public final class Cursors {
	//
	public static final Cursor CURSOR_WAIT = getDefault().getSystemCursor(SWT.CURSOR_WAIT);
	public static final Cursor CURSOR_HAND = getDefault().getSystemCursor(SWT.CURSOR_HAND);
	public static final Cursor CURSOR_IBEAM = getDefault().getSystemCursor(SWT.CURSOR_IBEAM);
	public static final Cursor CURSOR_ARROW = getDefault().getSystemCursor(SWT.CURSOR_ARROW);
	public static final Cursor CURSOR_SIZENS = getDefault().getSystemCursor(SWT.CURSOR_SIZENS);
	public static final Cursor CURSOR_SIZEWE = getDefault().getSystemCursor(SWT.CURSOR_SIZEWE);
}
