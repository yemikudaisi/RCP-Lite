package org.rcplite.platform.utils;

import java.util.*;
import java.text.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

/** Static convenience methods for GUIs which eliminate code duplication.*/
public final class ViewManager {

    public static final int ONE_SPACE = 5;
    public static final int TWO_SPACES = 11;
    public static final int THREE_SPACES = 17;
    public static final int STANDARD_BORDER = TWO_SPACES;

    /** Symbolic name for absence of keystroke mask.  */
    public static final int NO_KEYSTROKE_MASK = 0;

    /** Suggested width for a <tt>JTextField</tt>  */
    public static final int SIMPLE_FIELD_WIDTH = 20;

    //star * character for password fields
    //passwordField.setEchoChar('\u2022');

    /**
     * Maximum length for some <tt>JLabel</tt>s, beyond which the
     * text will be truncated. See {@link }.
     */
    public static final int MAX_LABEL_LENGTH = 35;

    /**
     * <tt>pack</tt>, center, and <tt>show</tt> a window on the screen.
     *
     * <P>If the size of <tt>aWindow</tt> exceeds that of the screen,
     * then the size of <tt>aWindow</tt> is reset to the size of the screen.
     */
    public static void centerAndShow(Window aWindow){
        //note that the order here is important

        aWindow.pack();
        /*
         * If called from outside the event dispatch thread (as is
         * the case upon startup, in the launch thread), then
         * in principle this code is not thread-safe: once pack has
         * been called, the component is realized, and (most) further
         * work on the component should take place in the event-dispatch
         * thread.
         *
         * In practice, it is exceedingly unlikely that this will lead
         * to an error, since invisible components cannot receive events.
         */
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = aWindow.getSize();
        //ensure that no parts of aWindow will be off-screen
        if (window.height > screen.height) {
            window.height = screen.height;
        }
        if (window.width > screen.width) {
            window.width = screen.width;
        }
        int xCoord = (screen.width/2 - window.width/2);
        int yCoord = (screen.height/2 - window.height/2);
        aWindow.setLocation( xCoord, yCoord );

        aWindow.setVisible(true);
    }

    /**
     * A window is packed, centered with respect to a parent, and then shown.
     *
     * <P>This method is intended for dialogs only.
     *
     * <P>If centering with respect to a parent causes any part of the dialog
     * to be off screen, then the centering is overidden, such that all of the
     * dialog will always appear fully on screen, but it will still appear
     * near the parent.
     *
     * @param aWindow must have non-null result for <tt>aWindow.getParent</tt>.
     */
    public static void centerOnParentAndShow(Window aWindow){
        aWindow.pack();

        Dimension parent = aWindow.getParent().getSize();
        Dimension window = aWindow.getSize();
        int xCoord =
                aWindow.getParent().getLocationOnScreen().x +
                        (parent.width/2 - window.width/2)
                ;
        int yCoord =
                aWindow.getParent().getLocationOnScreen().y +
                        (parent.height/2 - window.height/2)
                ;

        //Ensure that no part of aWindow will be off-screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xOffScreenExcess = xCoord + window.width - screen.width;
        if ( xOffScreenExcess > 0 ) {
            xCoord = xCoord - xOffScreenExcess;
        }
        if (xCoord < 0 ) {
            xCoord = 0;
        }
        int yOffScreenExcess = yCoord + window.height - screen.height;
        if ( yOffScreenExcess > 0 ) {
            yCoord = yCoord - yOffScreenExcess;
        }
        if (yCoord < 0) {
            yCoord = 0;
        }

        aWindow.setLocation( xCoord, yCoord );
        aWindow.setVisible(true);
    }

    /**
     * Return a border of dimensions recommended by the Java Look and Feel
     * Design Guidelines, suitable for many common cases.
     *
     *<P>Each side of the border has size {@link #STANDARD_BORDER}.
     */
    public static Border getStandardBorder(){
        return BorderFactory.createEmptyBorder(
            STANDARD_BORDER,
            STANDARD_BORDER,
            STANDARD_BORDER,
            STANDARD_BORDER
        );
    }


    /**
     * Make a horizontal row of buttons of equal size, whch are equally spaced,
     * and aligned on the right.
     *
     * <P>The returned component has border spacing only on the top (of the size
     * recommended by the Look and Feel Design Guidelines).
     * All other spacing must be applied elsewhere ; usually, this will only mean
     * that the dialog's top-level panel should use {@link #getStandardBorder}.
     *
     * @param aButtons contains the buttons to be placed in a row.
     */
    public static JComponent getCommandRow(java.util.List<JComponent> aButtons){
        equalizeSizes( aButtons );
        JPanel panel = new JPanel();
        LayoutManager layout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(layout);
        panel.setBorder(BorderFactory.createEmptyBorder(THREE_SPACES, 0, 0, 0));
        panel.add(Box.createHorizontalGlue());
        Iterator<JComponent> buttonsIter = aButtons.iterator();
        while (buttonsIter.hasNext()) {
            panel.add(buttonsIter.next());
            if (buttonsIter.hasNext()) {
                panel.add(Box.createHorizontalStrut(ONE_SPACE));
            }
        }
        return panel;
    }

    /**
     * Make a vertical row of buttons of equal size, whch are equally spaced,
     * and aligned on the right.
     *
     * <P>The returned component has border spacing only on the left (of the size
     * recommended by the Look and Feel Design Guidelines).
     * All other spacing must be applied elsewhere ; usually, this will only mean
     * that the dialog's top-level panel should use {@link #getStandardBorder}.
     *
     * @param aButtons contains the buttons to be placed in a column
     */
    public static JComponent getCommandColumn(java.util.List<JComponent> aButtons){
        equalizeSizes(aButtons);
        JPanel panel = new JPanel();
        LayoutManager layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.setBorder(
                BorderFactory.createEmptyBorder(0,THREE_SPACES, 0,0)
        );
        //(no for-each is used here, because of the 'not-yet-last' check)
        Iterator<JComponent> buttonsIter = aButtons.iterator();
        while (buttonsIter.hasNext()) {
            panel.add(buttonsIter.next());
            if (buttonsIter.hasNext()) {
                panel.add( Box.createVerticalStrut(ONE_SPACE) );
            }
        }
        panel.add(Box.createVerticalGlue());
        return panel;
    }



    /**
     * Sets the items in <tt>aComponents</tt> to the same size.
     *
     * <P>Sets each component's preferred and maximum sizes.
     * The actual size is determined by the layout manager, whcih adjusts
     * for locale-specific strings and customized fonts. (See this
     * <a href="http://java.sun.com/products/jlf/ed2/samcode/prefere.html">Sun doc</a>
     * for more information.)
     *
     * @param aComponents items whose sizes are to be equalized
     */
    public static void equalizeSizes(java.util.List<JComponent> aComponents) {
        Dimension targetSize = new Dimension(0,0);
        for(JComponent comp: aComponents ) {
            Dimension compSize = comp.getPreferredSize();
            double width = Math.max(targetSize.getWidth(), compSize.getWidth());
            double height = Math.max(targetSize.getHeight(), compSize.getHeight());
            targetSize.setSize(width, height);
        }
        setSizes(aComponents, targetSize);
    }


    /**
     * Return a <tt>String</tt>, suitable for presentation to the end user,
     * representing a percentage having two decimal places, using the default locale.
     *
     * <P>An example return value is "5.15%". The intent of this method is to
     * provide a standard representation and number of decimals for the entire
     * application. If a different number of decimal places is required, then
     * the caller should use <tt>NumberFormat</tt> instead.
     */
    public static String getLocalizedPercent( Number aNumber ){
        NumberFormat localFormatter = NumberFormat.getPercentInstance();
        localFormatter.setMinimumFractionDigits(2);
        return localFormatter.format(aNumber.doubleValue());
    }

    /**
     * Return a <tt>String</tt>, suitable for presentation to the end user,
     * representing an integral number with no decimal places, using the default
     * locale.
     *
     * <P>An example return value is "8,000". The intent of this method is to
     * provide a standard representation of integers for the entire
     * application.
     */
    public static String getLocalizedInteger( Number aNumber ) {
        NumberFormat localFormatter = NumberFormat.getNumberInstance();
        return localFormatter.format(aNumber.intValue());
    }

    /**
     * Return a <tt>String</tt>, suitable for presentation to the end user,
     * representing a date in <tt>DateFormat.SHORT</tt> and the default locale.
     */
    public static String getLocalizedTime(Date aDate){
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        return dateFormat.format(aDate);
    }

    /**
     * Make the sytem emit a beep.
     *
     * <P>May not beep unless the speakers are turned on, so this cannot
     * be guaranteed to work.
     */
    public static void beep(){
        Toolkit.getDefaultToolkit().beep();
    }


    /** Enumeration for horizontal alignment. */
    public enum AlignX {
        LEFT(Component.LEFT_ALIGNMENT),
        CENTER(Component.CENTER_ALIGNMENT),
        RIGHT(Component.RIGHT_ALIGNMENT);
        public float getValue(){
            return fValue;
        }
        private final float fValue;
        private AlignX(float aValue){
            fValue = aValue;
        }
    }


    /** Type-safe enumeration vertical alignment. */
    public enum AlignY {
        TOP(Component.TOP_ALIGNMENT),
        CENTER(Component.CENTER_ALIGNMENT),
        BOTTOM(Component.BOTTOM_ALIGNMENT);
        float getValue(){
            return fValue;
        }
        private final float fValue;
        private AlignY( float aValue){
            fValue = aValue;
        }
    }

    /**
     * Ensure that <tt>aRootPane</tt> has no default button associated with it.
     *
     * <P>Intended mainly for dialogs where the user is confirming a delete action.
     * In this case, an explicit Yes or No is preferred, with no default action being
     * taken when the user hits the Enter key.
     */
    public static void noDefaultButton(JRootPane aRootPane){
        aRootPane.setDefaultButton(null);
    }

    // PRIVATE

    private static final String BACK_SLASH = "/";


    private static void setSizes(java.util.List aComponents, Dimension aDimension){
        Iterator compsIter = aComponents.iterator();
        while ( compsIter.hasNext() ) {
            JComponent comp = (JComponent) compsIter.next();
            comp.setPreferredSize((Dimension)aDimension.clone());
            comp.setMaximumSize((Dimension)aDimension.clone());
        }
    }

    private static Dimension calcDimensionFromPercent(
            Dimension aSourceDimension, int aPercentWidth, int aPercentHeight
    ){
        int width = aSourceDimension.width * aPercentWidth/100;
        int height = aSourceDimension.height * aPercentHeight/100;
        return new Dimension(width, height);
    }

}