package cn.nextop.thorin.rcp.support.swt.widget.xcalendar;


import cn.nextop.thorin.rcp.support.swt.utility.SwtUtils;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEventBus;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.XCalendarDefaultEventBus;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.handler.impl.XCalendarDefaultEventHandler;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.listener.XCalendarEventListener;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.XCalendarReactor;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.XCalendarDefaultRector;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action.XCalendarFeedAction;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action.XCalendarResizeAction;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action.XCalendarSetupAction;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.layout.XCalendarLayout;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.XCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.XCalendarWidgetFactory;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static cn.nextop.thorin.rcp.support.util.Objects.cast;

/**
 * 
 * @author Jingqi Xu
 */
public class XCalendar extends Canvas {
	//
	protected final XCalendarModel model;
	protected final XCalendarEventBus bus;
	protected final XCalendarReactor reactor;
	protected volatile boolean editable = true;
	protected final Map<Object, Object> cookies;
	protected Map<XCalendarState, List<XCalendarWidget>> widgets;
	
	//
	public final XCalendarEventBus getBus() { return this.bus; }
	public final XCalendarModel getModel() { return this.model; }
	public final XCalendarReactor getReactor() {return this.reactor; }

	/**
	 * 
	 */
	public XCalendar(Composite parent, int style) {
		//
		super(shell(parent), checkStyle(style | SWT.DOUBLE_BUFFERED));
		this.cookies = new HashMap<>();
		this.model = new XCalendarModel(style);
		this.bus = new XCalendarDefaultEventBus(this);
		this.reactor = new XCalendarDefaultRector(this);
		this.bus.addHandler(new XCalendarDefaultEventHandler(this));
		
		//
		getShell().setLayout(new FillLayout());
		addPaintListener(new XPaintListener());
		this.addListener(SWT.MouseUp, new XListener());
		this.addListener(SWT.MouseDown, new XListener());
		this.addListener(SWT.MouseMove, new XListener());
		this.addListener(SWT.MouseExit, new XListener());
		this.addListener(SWT.MouseEnter, new XListener());
		getShell().addListener(SWT.Deactivate, new XDeactivateListener());
	}
	
	/**
	 * 
	 */
	@Override
	public boolean setFocus() {
		super.forceFocus(); 
		return true;
	}
	
	public boolean isFocused() {
		return isFocusControl();
	}

	public boolean getEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable; redraw();
	}
	
	/**
	 * Cookie
	 */
	public final <T> T getCookie() {
		return getCookie("$DEFAULT");
	}
	
	public final void setCookie(Object value) {
		setCookie("$DEFAULT", value);
	}
	
	public final <T> T getCookie(final Object key) {
		return cast(this.cookies.get(key));
	}
	
	public void setCookie(Object key, Object value) {
		this.cookies.put(key, value);
	}
	
	/**
	 * 
	 */
	public void hide() {
		if(!isDisposed()) getShell().dispose(); 
	}
	
	public void show(Composite parent, Rectangle bounds) {
		this.widgets = XCalendarWidgetFactory.create(this);
		XCalendarLayout layout = model.getConfig().getLayout();
		layout.layout(this, parent, bounds); getShell().open();
	}
	
	/**
	 * 
	 */
	public void feed(Date date) {
		this.reactor.submit(new XCalendarFeedAction(date));
	}
	
	public void setup(Predicate<Date> predicate) {
		this.reactor.submit(new XCalendarSetupAction(predicate, false));
	}
	
	public void resize(Composite parent, Rectangle bounds) {
		this.reactor.submit(new XCalendarResizeAction(parent, bounds));
	}
	
	public void setup(Predicate<Date> predicate, boolean nullable) {
		this.reactor.submit(new XCalendarSetupAction(predicate, nullable));
	}
	
	/**
	 * 
	 */
	public List<XCalendarWidget> getWidgets() {
		return this.widgets.get(getModel().getState());
	}

	public XCalendarWidget getWidget(int x, int y) {
		return filter((v) -> v.getBounds().contains(x, y));
	}
	
	public void addXCalendarEventListener(XCalendarEventListener listener) {
		this.getBus().addListener(listener);
	}
	
	public void delXCalendarEventListener(XCalendarEventListener listener) {
		this.getBus().delListener(listener);
	}
	
	public XCalendarWidget filter(Predicate<XCalendarWidget> test) {
		for(XCalendarWidget v : getWidgets()) if(test.test(v)) return v; return null;
	}
	
	/**
	 * 
	 */
	protected void handle(Event event) {
		this.bus.handle(event);
	}

	protected void paint(final GC gc) {
		this.model.getConfig().getRender().render(this, gc);
	}

	protected static Composite shell(Composite parent) {
		return new Shell(parent.getShell(), SWT.NO_TRIM | SWT.ON_TOP);
	}
	
	protected final class XListener implements Listener {
		@Override public void handleEvent(Event event) { handle(event); }
	}

	protected final class XPaintListener implements PaintListener {
		@Override public final void paintControl(final PaintEvent event) { paint(event.gc); }
	}
	
	protected final class XDeactivateListener implements Listener {
		@Override public void handleEvent(Event event) { SwtUtils.async(null, () -> { handle(event); }); }
	}

	protected static int checkStyle(int style) {
		if((style & SWT.TIME) != 0 || (style & SWT.DATE) != 0 || 
		   (style & SWT.SHORT) != 0 || (style & SWT.MEDIUM) != 0) return style; else throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
	}
}
