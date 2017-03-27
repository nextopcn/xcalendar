package cn.nextop.thorin.rcp.support.swt.widget.xcalendar;

import cn.nextop.thorin.rcp.support.swt.utility.SwtUtils;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEvent;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.external.XCalendarSelectEvent;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.listener.XCalendarEventListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

public class XDateTimeSnippet {

    public static void main(String[] args) {
        //
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(null);

        //
        Object[] o1 = calendar(shell, SWT.TIME, v -> v.before(new Date()), true);
        Text text1 = (Text) o1[0];
        Button button1 = (Button) o1[1];

        Object[] o2 = calendar(shell, SWT.SHORT, v -> v.before(new Date()), true);
        Text text2 = (Text) o2[0];
        Button button2 = (Button) o2[1];

        Object[] o3 = calendar(shell, SWT.MEDIUM, v -> v.before(new Date()), true);
        Text text3 = (Text) o3[0];
        Button button3 = (Button) o3[1];

        Object[] o4 = calendar(shell, SWT.DATE, v -> v.before(new Date()), true);
        Text text4 = (Text) o4[0];
        Button button4 = (Button) o4[1];

        Object[] o5 = calendar(shell, SWT.TIME, v -> true, false);
        Text text5 = (Text) o5[0];
        Button button5 = (Button) o5[1];

        Object[] o6 = calendar(shell, SWT.MEDIUM, v -> true, true);
        Text text6 = (Text) o6[0];
        Button button6 = (Button) o6[1];

        Object[] o7 = calendar(shell, SWT.SHORT, v -> true, true);
        Text text7 = (Text) o7[0];
        Button button7 = (Button) o7[1];

        shell.addControlListener(new ControlListener() {
            @Override
            public void controlMoved(ControlEvent e) {
            }

            @Override
            public void controlResized(ControlEvent e) {
                final Rectangle r = shell.getClientArea();
                final int h = (r.height - 5) / 9;
                text1.setBounds(5, 5, r.width - 60, h - 5);
                button1.setBounds(5 + r.width - 60, 5, 40, h - 5);

                text2.setBounds(5, 1 * h + 5, r.width - 60, h - 5);
                button2.setBounds(5 + r.width - 60, 1 * h + 5, 40, h - 5);

                text3.setBounds(5, 2 * h + 5, r.width - 60, h - 5);
                button3.setBounds(5 + r.width - 60, 2 * h + 5, 40, h - 5);

                text4.setBounds(5, 3 * h + 5, r.width - 60, h - 5);
                button4.setBounds(5 + r.width - 60, 3 * h + 5, 40, h - 5);

                text5.setBounds(5, 4 * h + 5, r.width - 60, h - 5);
                button5.setBounds(5 + r.width - 60, 4 * h + 5, 40, h - 5);

                text6.setBounds(5, 5 * h + 5, r.width - 60, h - 5);
                button6.setBounds(5 + r.width - 60, 5 * h + 5, 40, h - 5);

                text7.setBounds(5, 6 * h + 5, r.width - 60, h - 5);
                button7.setBounds(5 + r.width - 60, 6 * h + 5, 40, h - 5);
            }
        });
        //
        shell.setSize(300, 300);
        shell.layout();
        shell.open();
        SwtUtils.center(shell);
        while (!shell.isDisposed()) if (!display.readAndDispatch()) display.sleep();
        display.dispose();
    }

    private static Object[] calendar(Shell shell, int style, Predicate<Date> test, boolean nullable) {
        Text text1 = new Text(shell, SWT.BORDER);
        Button button1 = new Button(shell, SWT.PUSH);
        button1.setText("click");
        button1.addSelectionListener(new XSelectionAdapter(text1, style, test, nullable));
        return new Object[]{text1, button1};
    }

    private static class XSelectionAdapter extends SelectionAdapter {
        private final Text text;
        private final int style;
        private final boolean nullable;
        private final Predicate<Date> test;

        public XSelectionAdapter(Text text, int style, Predicate<Date> test, boolean nullable) {
            this.text = text;
            this.style = style;
            this.test = test;
            this.nullable = nullable;
        }

        @Override
        public void widgetSelected(SelectionEvent selectionEvent) {
            XCalendar x = new XCalendar(text.getParent(), style);
            x.setup(test, nullable);
            x.addXCalendarEventListener(new XCalendarEventListener() {
                @Override
                public void onEvent(XCalendarEvent event) {
                    if (!(event instanceof XCalendarSelectEvent)) return;
                    XCalendarSelectEvent s = (XCalendarSelectEvent) event;
                    text.setData(s.getValue());
                    String v = "";
                    if (s.getValue() != null || s.getYear() != null || s.getYearMonth() != null) {
                        if (x.getModel().isTime()) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            v = format.format(s.getValue());
                        } else if (x.getModel().isDate()) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            v = format.format(s.getValue());
                        } else if (x.getModel().isYear()) {
                            v = String.valueOf(s.getYear());
                        } else if (x.getModel().isYearMonth()) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                            v = format.format(s.getValue());
                        }
                    }
                    text.setText(v);
                }
            });
            x.show(text.getParent(), text.getBounds());
        }
    }
}
