package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.tracker;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEvent;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;

/**
 * 
 * @author Jingqi Xu
 */
public class XCalendarMouseTracker {
	//
	protected static final int MASK_ENTERED = (1 << 0);
	protected static final int MASK_PRESSED = (1 << 1);
	
	//
	private int x, y;
	private byte state;
	
	/**
	 * 
	 */
	public final int getX() {
		return x;
	}
	
	public final void setX(int x) {
		this.x = x;
	}
	
	public final int getY() {
		return y;
	}
	
	public final void setY(int y) {
		this.y = y;
	}
	
	/**
	 * 
	 */
	public void reset() {
		this.state = 0;
		this.x = this.y = -1;
	}
	
	public void reset(XCalendarMouseTracker rhs) {
		this.state = rhs.state;
		this.x = rhs.x; this.y = rhs.y;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return new StringBuilder()
				.append("x:" + x).append("y:" + y)
				.append("entered:" + isEntered())
				.append("pressed:" + isPressed()).toString();
	}
	
	/**
	 * 
	 */
	public boolean track(final XCalendarEvent event) {
		if(!(event instanceof XCalendarMouseEvent)) return false;
		this.x = event.getSource().x; this.y = event.getSource().y;
		if (event instanceof XCalendarMouseUpEvent) setPressed(false);
		else if(event instanceof XCalendarMouseDownEvent) setPressed(true);
		else if(event instanceof XCalendarMouseEnterEvent) setEntered(true);
		else if(event instanceof XCalendarMouseLeaveEvent) setEntered(false);
		return true;
	}
	
	/**
	 * 
	 */
	public boolean isEntered() {
		return (this.state & MASK_ENTERED) != 0;
	}
	
	public void setEntered(boolean entered) {
		if(entered) this.state |= MASK_ENTERED; else this.state &= ~MASK_ENTERED;
	}
	
	public boolean isPressed() {
		return (this.state & MASK_PRESSED) != 0;
	}
	
	public void setPressed(boolean pressed) {
		if(pressed) this.state |= MASK_PRESSED; else this.state &= ~MASK_PRESSED;
	}
}
