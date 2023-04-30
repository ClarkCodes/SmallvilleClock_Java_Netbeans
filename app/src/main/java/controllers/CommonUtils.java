package controllers;

/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

import java.awt.Frame;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import views.FrmMiniMode;
import views.FrmSmallvilleClockMain;

/** Hosts pretty common used objects and useful functions for tasks used in many
 * places and classes to not repeat them and avoid boilerplate code and also for
 * keeping the code atomicity principle, to centralize its declaration, declare
 * them once, use them anywhere.
 *
 * @author Clark - ClarkCodes
 * @since 1.0
 */
public final class CommonUtils
{   // Cell Center Renderer. This is to center content of each horizontal and vertical cell in a table
    private static final DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
    private static final int SECONDS_IN_HOUR = 3600, SECONDS_IN_MINUTE = 60, MILLIS_IN_SECOND = 1000, HUNDRETHS_IN_SECOND = 10;
    private static final FrmSmallvilleClockMain MAIN_FRAME = mainFrameLocator();
    
    /** Gets the constant that indicate the milliseconds quantity in a second
     */
    public static int getMILLIS_IN_SECOND ()
    {
        return MILLIS_IN_SECOND;
    }

    /** Gets the constant that indicate the seconds quantity in an hour.
     */
    public static int getSECONDS_IN_HOUR ()
    {
        return SECONDS_IN_HOUR;
    }
    /** Gets the constant that indicate the seconds quantity in a minute
     */
    public static int getSECONDS_IN_MINUTE ()
    {
        return SECONDS_IN_MINUTE;
    }
    /** Gets the constant that indicate the tenths quantity in a second
     */
    public static int getHUNDRETHS_IN_SECOND ()
    {
        return HUNDRETHS_IN_SECOND;
    }
    
    
    /** Enum Constants for indicating the Date format required */
    public static enum TypeDateFormat
    {
        SHORT_DATE,
        SHORT_DATE_ALTERNATE,
        LONG_DATE,
        COMPACT_DATE,
        COMPACT_STD_DATE
    }
    
    /** Enum Constants for indicating the Time format required */
    public static enum TypeTimeFormat
    {
        HOURS_MINUTES,
        HOURS_MINUTES_24,
        HOURS_MINUTES_SECONDS,
        HOURS_MINUTES_SECONDS_24,
        MINUTES_SECONDS_HUNDRETHS,
        MINUTES_SECONDS,
        DURATION
    }

    /** Enum Constants for indicating File type on open and save operations
     * @see CtrlSettings
     */
    public static enum FileType
    {
        TEXT ( "key_file_type_text" ),
        BINARY ( "key_file_type_binary" );

        private final String key;

        /** Enum Constructor for its constants parameters */
        FileType( String key )
        {
            this.key = key;
        }

        /** Gets the corresponding {@code String} that contains the localized
         * language key associated to its corresponding {@code enum} constant
         *
         * @return The associated {@code String} language key
         */
        public String getLangKey()
        {
            return key;
        }

        /** Parses a {@code String} to its corresponding {@code FileType enum} constant
         *
         * @param str A specified {@code String} that contains a shown
         *            {@code FileType} text in the current application language
         * @return    The {@code FileType enum} constant that matches with the
         *            given str
         * @see CtrlSettings
         */
        public static FileType valueOfLocalizedString ( String str )
        {
            FileType result = null;

            for ( FileType c : values() )
            {
                if ( str.equalsIgnoreCase( CtrlSettings.getLanguageBundle().getString( c.getLangKey() ) ) )
                {
                    result = c;
                    break;
                }
            }

            return result;
        }
    }

    /** Enum Constants for indicating Time Elements */
    public static enum ChronoType implements Serializable
    {
        WORLD_CLOCK,
        TIMER_TIME,
        TIMER_OVER_TIME,
        STOPWATCH,
        LAP
    }
    /** Enum Constants for indicating Timer and Stopwatch states */
    public static enum State implements Serializable
    {
        RUNNING,
        PAUSED,
        STOPPED
    }

    // Singleton Design Pattern
    private static CommonUtils controller = null;

    /** Constructor */
    private CommonUtils() {}

    /** Gets the unique instance of this class, following Singleton Design Pattern in Development
     *
     * @return The Unique {@code CommonUtils} instance
     */
    public static CommonUtils getController()
    {
        if ( controller == null )
            controller = new CommonUtils();
        return controller;
    }

    /** Gets the respective {@code DateTimeFormatter} object with the proper
     * formatter string set, following a given enum constant indicator specifying
     * a required date format
     *
     * @param formatType A specified {@code TypeDateFormat} enum indicator to 
     *                   set properly the formatter and return in consequence 
     *                   the right formatter set
     *
     * @return The {@code DateTimeFormatter} object with the proper date format
     */
    public static DateTimeFormatter getDateFormat ( TypeDateFormat formatType )
    {
        String strDateFormat = "";

        switch ( CtrlSettings.getAppLanguage() )
        {
            case SPANISH ->
            {
                strDateFormat = switch ( formatType )
                        {
                            case SHORT_DATE -> "EEE ', ' d/MM/yyyy";
                            case SHORT_DATE_ALTERNATE -> "EEE ', ' MMMM d 'del' yyyy";
                            case LONG_DATE -> "EEEE ', ' d 'de' MMMM 'del' yyyy";
                            case COMPACT_DATE -> "yyMMdd";
                            case COMPACT_STD_DATE -> "d/MM/yy";
                        };
            }

            case ENGLISH ->
            {
                strDateFormat = switch ( formatType )
                        {
                            case SHORT_DATE -> "EEE ', ' M/d/yyyy";
                            case SHORT_DATE_ALTERNATE -> "EEE ', ' MMMM d ', ' yyyy";
                            case LONG_DATE -> "EEEE ', ' MMMM d ', ' yyyy";
                            case COMPACT_DATE -> "MMddyy";
                            case COMPACT_STD_DATE -> "M/d/yy";
                        };
            }
        }
        // Formatter for Dates
        return DateTimeFormatter.ofPattern( strDateFormat );
    }
    
    /** Gets the respective {@code DateTimeFormatter} object with the proper
     * formatter string set, following a given enum constant indicator specifying
     * a required time format
     *
     * @param formatType A specified {@code TypeTimeFormat} enum indicator to 
     *                   set properly the formatter and return in consequence 
     *                   the right formatter set
     *
     * @return The {@code DateTimeFormatter} object with the proper time format
     */
    public static DateTimeFormatter getTimeFormat ( TypeTimeFormat formatType )
    {   // Formatter for times
        return DateTimeFormatter.ofPattern( switch ( formatType )
                        {
                            case HOURS_MINUTES -> "hh:mma";
                            case HOURS_MINUTES_24 -> "HH:mm";
                            case HOURS_MINUTES_SECONDS -> "hh:mm:ssa";
                            case HOURS_MINUTES_SECONDS_24 -> "HH:mm:ss";
                            case MINUTES_SECONDS_HUNDRETHS -> "mm:ss.SS";
                            case MINUTES_SECONDS -> "mm:ss";
                            case DURATION -> "h'h 'mm'm'";
                        } );
    }

    /** Centers a given {@code JTable} object with its
     * {@code DefaultTableColumnModel} and sets a
     * {@code DefaultTableCellRenderer} to it
     *
     * @param table The given {@code JTable} object to be centered
     */
    public static void centerTableColumns ( JTable table )
    {
        CommonUtils.tcr.setHorizontalAlignment( SwingConstants.CENTER );
        CommonUtils.tcr.setVerticalAlignment( SwingConstants.CENTER );
        DefaultTableColumnModel columnModel = ( DefaultTableColumnModel ) table.getColumnModel();
        for ( int i = 0; i < table.getColumnCount(); i++ )
            columnModel.getColumn( i ).setCellRenderer( tcr );
    }

    /** Converts a word to camel case, this is the first character in this
     * String to upper case and the other characters if existing, to lower case,
     * using the rules of the default locale
     * @param word A {@code String} word to convert to camel case
     * @return The given word converted to camel case
     */
    public static String toCamelCase ( String word )
    {
        if( word != null && !word.isBlank() )
        {
            String result = "";
            boolean upperPending = false;

            if( word.length() > 1 )
            {
                result = String.valueOf( Character.toUpperCase( word.charAt( 0 ) ) );

                for ( int i = 1; i < word.length(); i++ )
                {
                    if ( upperPending )
                    {
                        result += Character.toUpperCase( word.charAt( i ) );
                        upperPending = false;
                    }
                    else
                        result += word.charAt( i );

                    if ( Character.isWhitespace( word.charAt( i ) ) && i != ( word.length() - 1 ) )
                        upperPending = true;
                }

                word = result;
            }
            else
            {
                word = word.toUpperCase();
            }
        }

        return word;
    }
    
    /** Convert a {@code int} number to a 2 digit format {@code String}, adding
     * a 0 on the left if the number has only 1 digit, otherwise it returns the 
     * same number as a {@code String}
     * 
     * @param number A {@code int} given number to be formatted
     * 
     * @return The 2 digit formatted {@code String} of the given number
     */
    public static String twoDigitsFormat( int number )
    {
        String strNumber = String.valueOf( number );
        return strNumber.length() == 1 ? "0" + strNumber : strNumber;
    }
    
    /** Gets a duration in a certain format
     * @param duration A given {@code Duration} to be formatted
     * @param type     A {@code ChronoType} enum that indicates the format time needed
     * @return         The {@code String} time representation especially formatted 
     * for the indicated type
     */
    public static String getFormattedDuration ( Duration duration, ChronoType type )
    {       
        return switch (type)
        {
            case WORLD_CLOCK -> { yield ""; } //TODO: PENDING TO COMPLETE
            case TIMER_TIME -> twoDigitsFormat( Integer.parseInt( duration.toHours() + "" ) ) + ":" + LocalTime.of( 0, 
                Integer.parseInt( duration.toMinutesPart() + "" ), 
                Integer.parseInt( duration.toSecondsPart() + "" ) ).
                format( CommonUtils.getTimeFormat( CommonUtils.TypeTimeFormat.MINUTES_SECONDS ) );
            
            case TIMER_OVER_TIME -> LocalTime.of( 0, Integer.parseInt( duration.toMinutesPart() + "" ), 
                Integer.parseInt( duration.toSecondsPart() + "" ) ).
                format( CommonUtils.getTimeFormat( CommonUtils.TypeTimeFormat.MINUTES_SECONDS ) );
            
            case STOPWATCH -> LocalTime.of( 0, Integer.parseInt( duration.toMinutesPart() + "" ), 
                Integer.parseInt( duration.toSecondsPart() + "" ), 
                Integer.parseInt( duration.toNanosPart() + "" ) ).
                format( CommonUtils.getTimeFormat( TypeTimeFormat.MINUTES_SECONDS_HUNDRETHS ) );
            
            case LAP -> { yield ""; }
        };
    }
    
    /** Localizes and gets the current Smallville Clock Mini window instance
     *
     * @return The actual {@code FrmMiniMode} current window instance
     * @see views.FrmMiniMode
     */
    public static FrmMiniMode getMiniWindow()
    {
        FrmMiniMode miniWindow = null;

        for( Frame frame : FrmMiniMode.getFrames() )
        {
            if( frame instanceof FrmMiniMode frmMiniMode )
            {
                miniWindow = frmMiniMode;
                break;
            }
        }

        return miniWindow;
    }
    
    /** Localizes and gets the current Smallville Clock main window instance
     *
     * @return The actual {@code FrmSmallvilleClockMain} current window instance
     * @see views.FrmSmallvilleClockMain
     */
    private static FrmSmallvilleClockMain mainFrameLocator()
    {
        FrmSmallvilleClockMain mainWindow = null;

        for( Frame frame : FrmSmallvilleClockMain.getFrames() )
        {
            if( frame instanceof FrmSmallvilleClockMain frmSmallvilleClockMain )
            {
                mainWindow = frmSmallvilleClockMain;
                break;
            }
        }

        return mainWindow;
    }
    
    /** Gets the current Smallville Clock main window instance
     *
     * @return The actual {@code FrmSmallvilleClockMain} current window instance
     * @see views.FrmSmallvilleClockMain
     */
    public static FrmSmallvilleClockMain getMainFrame()
    {
        return MAIN_FRAME;
    }

    /** Fills a given one column table with a provided list collection
     * @param table A {@code JTable} object to be filled
     * @param list  A {@code ArrayList<String>} collection to fill the provided 
     *              table with its items
     */
    public static void fillOneColumnTable( JTable table, ArrayList<String> list )
    {   // rowIndex: The actual row index in a JTable, it's used to specify where to put a new registry row on a table, the registry indicator number is rowIndex + 1.
        int rowIndex = 0;

        CommonUtils.clearTable( table );

        for ( String item : list )
        {
            CommonUtils.fillOneColumnStringRow( table, item, rowIndex );
            rowIndex++;
        }
    }
    
    /** Populates only one registry on a given {@code JTable} object with a given
     * {@code String}
     *
     * @param table    A given {@code JTable} to be populated
     * @param item     A given {@code String} to populate the table with it
     * @param rowIndex A given {@code int} number that represents the actual row
     *                 index in the {@code JTable} object
     */
    public static void fillOneColumnStringRow ( JTable table, String item, int rowIndex )
    {
        DefaultTableModel tableModel = ( DefaultTableModel ) table.getModel();
        tableModel.addRow( new Object[1] );

        table.setValueAt( item, rowIndex, 0 );
    }
    
    /** Clears the content of the specified {@code JTable}
     *
     * @param table The {@code JTable} to be cleared
     */
    public static void clearTable ( JTable table )
    {   // Metodo en el que eliminamos todos los elementos de la Tabla, seg�n se requiera, comunmente para volver a llenarla con los registros que coincidan con el criterio de busqueda
        DefaultTableModel tableModel = ( DefaultTableModel ) table.getModel();

        if ( tableModel.getRowCount() > 0 )
            tableModel.setRowCount( 0 );
    }
}