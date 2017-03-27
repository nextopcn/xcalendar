package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme;

import cn.nextop.thorin.rcp.support.swt.utility.graphics.Fonts;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import static cn.nextop.thorin.rcp.support.swt.utility.graphics.Colors.getColor;

/**
 * 
 * @author Jingqi Xu
 */
public abstract class XCalendarTheme {
	
	private static final String[][] HOUR = { { "00", "01", "02", "03" }, 
			                                 { "04", "05", "06", "07" }, 
			                                 { "08", "09", "10", "11" }, 
			                                 { "12", "13", "14", "15" }, 
			                                 { "16", "17", "18", "19" }, 
			                                 { "20", "21", "22", "23" } };

	private static final String[][] MINUTE = { { "00", "05", "10", "15" }, 
                                               { "20", "25", "30", "35" }, 
                                               { "40", "45", "50", "55" } };

	private static final String[][] SECOND = { { "00", "05", "10", "15" }, 
                                               { "20", "25", "30", "35" }, 
                                               { "40", "45", "50", "55" } };
	
	//
	protected int arc = 8;
	protected int margin = 5;
	protected int width = 220;
	protected Font font = Fonts.getSystemFont();
	protected Font bold = Fonts.getFont(XCalendarTheme.class, "bold", () -> Fonts.bold(this.font));
	
	protected Color[] grid = new Color[] { getColor(0xc8, 0xc8, 0xc8), getColor(0x8f, 0xbc, 0xee) };
	protected Color[] toolbar = new Color[] { getColor(0x00, 0x00, 0x00), getColor(0x40, 0x87, 0xcc) };
	protected Color[] foreground = new Color[] { getColor(0x00, 0x00, 0x00), getColor(0x9f, 0x9f, 0x9f), getColor(0xff, 0xff, 0xff) };
	protected Color[] background = new Color[] { getColor(0xff, 0xff, 0xff), getColor(0xe0, 0xe0, 0xe0), getColor(0x00, 0x69, 0xcf), getColor(0xfe, 0xde, 0x00) };
	
	/**
	 * 
	 */
	public abstract String[][] getMonthTheme();
	public abstract String[] getDayOfWeekTheme();
	public abstract String[] getMonthOfYearTheme();
	public abstract String header(int month, int year);
	
	public String[][] getHourTheme(){ return HOUR; }
	public String[][] getMinuteTheme(){ return MINUTE; }
	public String[][] getSecondTheme(){ return SECOND; }

	/**
	 * 
	 */
	public int getArc() {
		return arc;
	}
	
	public Font getBold() {
		return bold;
	}
	
	public Font getFont() {
		return font;
	}

	public int getWidth() {
		return width;
	}
	
	public int getMargin() {
		return margin;
	}
	
	public Color[] getToolbar() {
		return toolbar;
	}
	
	public Color[] getForeground() {
		return foreground;
	}
	
	public Color[] getBackground() {
		return background;
	}
	
	/**
	 * 
	 */
	public void setArc(int arc) {
		this.arc = arc;
	}
	
	public void setBold(Font bold) {
		this.bold = bold;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setMargin(int margin) {
		this.margin = margin;
	}

	public void setToolbar(Color[] toolbar) {
		this.toolbar = toolbar;
	}
	
	public final void setGrid(Color[] colors) {
		this.grid = colors;
	}
	
	public void setForeground(Color[] foreground) {
		this.foreground = foreground;
	}

	public void setBackground(Color[] background) {
		this.background = background;
	}

	/**
	 * 
	 */
	public Color getGrid(boolean focused) {
		return focused ? grid[1] : grid[0];
	}
	
	public Color getToolBarBackground(boolean hovered){
		return hovered ? toolbar[0] : toolbar[1];
	}
	
	public Color getForeground(boolean enabled, boolean selected, boolean same) {
		return !enabled? foreground[1] : selected ? foreground[2] : same ? foreground[0] : foreground[1];
	}
	
	public Color getBackground(boolean enabled, boolean selected, boolean same, boolean hovered) {
		return !enabled ? background[0] : selected ? background[2] : same ? background[3] : hovered? background[1] : background[0];
	}
}
