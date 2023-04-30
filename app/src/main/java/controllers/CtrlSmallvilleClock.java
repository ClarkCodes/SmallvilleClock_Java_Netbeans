package controllers;

/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

import data.FilesManager;
import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import models.Lap;
import models.PresetTimerTime;
import models.Stopwatch;
import models.Timer;
import models.WorldClock;
import views.IfrmShortClock;

/** Manages the behavior of the different modes and functionalities of the application
 * @author Clark - ClarkCodes
 * @since 1.0
 * */
public class CtrlSmallvilleClock
{
    private LinkedList<WorldClock> worldClocks;
    private LinkedList<Lap> stopwatchLaps;
    private LinkedList<PresetTimerTime> presetTimers;
    private ArrayList<String> zoneIds;
    private WorldClock systemWorldClock;
    private final Timer timer;
    private final Stopwatch stopwatch;
    private final ArrayList<JPanel> shortPanels;
    private final int MAX_WORLD_CLOCKS_CANT = 4;

    // Singleton Design Pattern
    private static CtrlSmallvilleClock controller = null;
    private CtrlSmallvilleClock() 
    {
        worldClocks = new LinkedList<>();
        presetTimers = new LinkedList<>();
        stopwatchLaps = new LinkedList<>();
        shortPanels = new ArrayList<>();
        timer = new Timer( Duration.ZERO, "" );
        stopwatch = new Stopwatch();
        zoneIdsListLoader();
    }

    /** Gets the unique instance of this class, following Singleton Design Pattern
     *
     * @return The Unique {@code CtrlSmallvilleClock} instance
     */
    public static CtrlSmallvilleClock getController()
    {
        if ( controller == null )
            controller = new CtrlSmallvilleClock();
        return controller;
    }
    
    //
    // Timer Section
    //
    /** Populates the given Timer Presets table {@code JTable} with the Timer 
     * Presets collection
     *
     * @param presetsTable The given {@code JTable} to be populated
     * 
     */
    public void fillTimerPresetsTable( JTable presetsTable )
    {   // rowIndex: The actual row index in a JTable, it's used to specify where to put a new registry row on a table, the registry indicator number is rowIndex + 1.
        int rowIndex = 0;
        String strPreset;

        CommonUtils.clearTable( presetsTable );

        for ( PresetTimerTime preset : presetTimers )
        {
            strPreset = CommonUtils.getFormattedDuration( preset.time(), CommonUtils.ChronoType.TIMER_TIME ) + " - " + preset.name();
            CommonUtils.fillOneColumnStringRow( presetsTable, strPreset, rowIndex );
            rowIndex++;
        }

        CommonUtils.centerTableColumns( presetsTable );
    }

    /** Verifies if a Timer preset is already in the collection with a specific 
     * time, it is not allowed for the time to be repeated
     * 
     * @param time A given {@code Duration} time to verify if it already exists
     * 
     * @return {@code true} if there is already a Timer preset with the same 
     *         time {@code Duration}, {@code false} otherwise
     * 
     * @see views.DlgSetTimer
     */
    public boolean timeExistsInPresets( Duration time )
    {
        boolean exists = false;
        
        for( PresetTimerTime preset : presetTimers )
        {
            if( preset.time().toSeconds() == time.toSeconds() )
            {
                exists = true;
                break;
            }    
        }
        
        return exists;
    }
    
    //
    // Stopwatch Section
    //
    /** Marks a new Stopwatch Lap with the current Stopwatch times
     */
    public void markLap()
    {
        if ( stopwatchLaps.isEmpty() )
        {
            stopwatchLaps.add( new Lap( CommonUtils.ChronoType.LAP, stopwatch.getStopwatchTime(), stopwatch.getStopwatchTime() ) );
        }
        else
            stopwatchLaps.add( new Lap( CommonUtils.ChronoType.LAP, stopwatch.getStopwatchTime(), stopwatch.getLapTime() ) );
        
        stopwatch.lap();
    }
    
    /** Populates the given Stopwatch Laps table {@code JTable} with the 
     * Stopwatch Laps collection
     *
     * @param lapsTable The given {@code JTable} to be populated
     * 
     */
    public void fillStopwatchLapsTable( JTable lapsTable )
    {   // rowIndex: The actual row index in a JTable, it's used to specify where to put a new registry row on a table, the registry indicator number is rowIndex + 1.
        int rowIndex = 0;

        CommonUtils.clearTable( lapsTable );

        for ( int i = stopwatchLaps.size() - 1; i >= 0; i-- )
        {
            fillLapsTableRow( lapsTable, stopwatchLaps.get( i ), rowIndex, i + 1 );
            rowIndex++;
        }

        CommonUtils.centerTableColumns( lapsTable );
        
        DefaultTableColumnModel columnModelLT = ( DefaultTableColumnModel ) lapsTable.getColumnModel();
        columnModelLT.getColumn( 0 ).setPreferredWidth( 52 );
        columnModelLT.getColumn( 1 ).setPreferredWidth( 230 );
        columnModelLT.getColumn( 2 ).setPreferredWidth( 230 );
    }
    /** Populates only one registry on the Stopwatch Laps {@code JTable} object
     *
     * @param lapsTable The given Stopwatch Laps {@code JTable} to be populated
     * @param lap       A given {@code Lap} to populate the table with its data
     * @param rowIndex  A given {@code int} number that represents the actual row
     *                  index in the {@code JTable} object to populate
     */
    private void fillLapsTableRow( JTable lapsTable, Lap lap, int rowIndex, int numIndicator )
    {
        DefaultTableModel tableModel = ( DefaultTableModel ) lapsTable.getModel();
        tableModel.addRow( new Object[1] );

        lapsTable.setValueAt( CommonUtils.twoDigitsFormat( numIndicator ), rowIndex, 0 );
        lapsTable.setValueAt( CommonUtils.getFormattedDuration( lap.globalTime(), CommonUtils.ChronoType.STOPWATCH ), rowIndex, 1 );
        lapsTable.setValueAt( CommonUtils.getFormattedDuration( lap.lapTime(), CommonUtils.ChronoType.STOPWATCH ), rowIndex, 2 );
    }
    /** Saves a Stopwatch Laps Smallville Clock file with the current Laps in the collection
     * @see data.FilesManager
     */
    public void saveStopwatchLaps()
    {
        FilesManager.getController().saveLapsFile( stopwatchLaps );
    }
    /** Opens a Stopwatch Laps Smallville Clock file
     * @see data.FilesManager
     */
    public void openStopwatchLaps()
    {
        LinkedList<Lap> laps = FilesManager.getController().openLapsFile();
        
        if ( laps != null && FilesManager.getResult() == FilesManager.OperationResult.SUCCESSFUL )
        {
            if ( !stopwatchLaps.isEmpty() )
                stopwatchLaps.clear();
            
            stopwatchLaps = laps;
        }
    }
    
    
    // World Clock Section
    
    /** Loads the available Zone Ids list
     * @see views.DlgZoneIdSelector
     */
    private void zoneIdsListLoader()
    {
        zoneIds = new ArrayList<>();
        zoneIds.addAll( Arrays.asList( "America/El_Salvador", "Africa/Cairo", "America/Guatemala",
                "Europe/London", "America/Panama", "Europe/Brussels", "America/Chicago", 
                "Chile/Continental", "Europe/Istanbul", "Europe/Luxembourg", "Canada/Atlantic",
                "America/Argentina/Cordoba", "US/Alaska", "Asia/Dubai", "Europe/Isle_of_Man",
                "Cuba", "America/Argentina/Salta", "Israel", "US/Arizona", "America/Montevideo",
                "Brazil/East", "Pacific/Easter", "America/Argentina/Mendoza", "Europe/Rome",
                "America/Sao_Paulo", "America/Guayaquil", "Portugal", "Europe/Berlin", "Europe/Moscow",
                "America/Puerto_Rico", "Europe/Stockholm", "Europe/Budapest", "Pacific/Galapagos", 
                "Europe/Paris", "Indian/Maldives", "America/Buenos_Aires", "America/Dominica",
                "America/Costa_Rica", "Europe/Amsterdam", "Europe/Dublin", "America/Monterrey",
                "US/Hawaii", "Brazil/West", "America/La_Paz", "America/Mexico_City", "Asia/Jerusalem",
                "Europe/Andorra", "America/Bogota", "America/Edmonton", "America/Santo_Domingo",
                "US/Eastern", "America/Cancun", "Australia/Sydney", "America/Lima", "Europe/Madrid",
                "America/Vancouver", "Asia/Hong_Kong", "Etc/Greenwich", "America/Montreal",
                "America/New_York", "Europe/Vatican", "Asia/Tokyo", "America/Toronto", "America/Los_Angeles",
                "America/Argentina/Buenos_Aires", "US/Pacific") );
        
        Collections.sort( zoneIds );
    }
    /** Sets properly the Date and Time format for the existing World Clocks
     */
    public void worldClocksFormatSetter()
    {
        systemWorldClock.formatterDeterminer();
        worldClocks.forEach( wc -> wc.formatterDeterminer() );
    }
    /** Adds a new {@code WorldClock} object to the collection with a provided
     * {@code String} Zone Id
     * @param zoneId A given {@code String} Zone Id to create the {@code WorldClock}
     *               object with
     */
    public void addWorldClock( String zoneId )
    {
        if ( worldClocks.size() < MAX_WORLD_CLOCKS_CANT )
        {
            worldClocks.add( new WorldClock( ZoneId.of( zoneId ) ) );
            
            if( !FilesManager.getController().isUserWorldClocksListChanged() )
                FilesManager.getController().setUserWorldClocksListChanged( true );
            
            if ( worldClocks.size() == 1 ) // If now there is only 1 world clock it means it is the first custom wc so the short panels grid must be set, so I call to the World Clock Set Manager to do it
            {
                CommonUtils.getMainFrame().worldClockSetManager();
            }
            else if ( worldClocks.size() > 1 ) // Otherwise if there are more than 1 element it means the short clocks grid is already set and I have to add only the last world clock to it properly
            {
                WorldClock wc = worldClocks.getLast();
                IfrmShortClock shortClock = new IfrmShortClock();
                wc.setClockLabel( shortClock.getLblWorldClock() );
                wc.setDateLabel( shortClock.getLblDate() );
                wc.setZoneLabel( shortClock.getLblZone() );
                shortPanels.get( worldClocks.size() - 1 ).add( shortClock ); // Adding the last World Clock in the collection to the correspondent Short Panel using the last World Clocks collection size -1 to get the right index and get the proper Short Panel

                if ( !wc.isStarted() )
                    wc.start();
                
                CommonUtils.getMainFrame().getShortClocksPanelGrid().worldClockEnablingVerifier();
            }
        }
    }
    /** Deletes a {@code WorldClock} object from the collection with a specified 
     * index
     * @param worldClockIndex A {@code int} numeric index to remove a 
     *                        {@code WorldClock} from the collection with
     * @see views.IfrmShortClocksGrid
     */
    public void deleteWorldClock( int worldClockIndex )
    {
        if ( !worldClocks.isEmpty() )
        {
            worldClocks.remove( worldClockIndex );
            
            if( !FilesManager.getController().isUserWorldClocksListChanged() )
                FilesManager.getController().setUserWorldClocksListChanged( true );
            
            if ( worldClocks.isEmpty() )
                CommonUtils.getMainFrame().worldClockSetManager();
        }
    }
    /** Gets the indicator about if a Zone Id is already in use, this is that 
     * already exist a {@code WorldClock} that has the specified Zone Id
     * @param selectedZoneId A {@code String} with a specified Zone Id
     * @return {@code true} if a {@code WorldClock} that uses the specified 
     *                      Zone Id has been found in the World Clocks collection,
     *                      {@code false} otherwise
     */
    public boolean isZoneIdAlreadyUsed( String selectedZoneId )
    {
        boolean alreadyUsed = false;
        
        if ( systemWorldClock.getRegionZone().equalsIgnoreCase( selectedZoneId ) )
            return true;
        
        for ( WorldClock wc : worldClocks )
        {
            if ( wc.getRegionZone().equalsIgnoreCase( selectedZoneId ) )
            {
                alreadyUsed = true;
                break;
            }
        }
        
        return alreadyUsed;
    }
    
    /** Gets the Zone Id name with a given {@code String} that contains a full 
     * Zone Id, it removes the extra name part related with continent and country
     * 
     * @param zoneId A given {@code String} that contains a {@code ZoneId} text
     * 
     * @return A {@code String} that represents no more than the city name of a 
     *         given full Zone Id, removing the first part until the last ocurrence
     *         of the '/' character
     */
    public String zoneIdNamePreparer( String zoneId )
    {
        int indexToStartFrom = 0;
        
        if ( zoneId.contains( "/" ) )
            indexToStartFrom = zoneId.lastIndexOf( '/' ) + 1;

        return ( zoneId.substring( indexToStartFrom ) ).replace( '_', ' ');
    }
    
    
    // MiniMode Section
    
    public void fillFunctionCombo( JComboBox functionCombo )
    {
        functionCombo.addItem( CtrlSettings.getLanguageBundle().getString( "key_world_clock" ) );
        functionCombo.addItem( CtrlSettings.getLanguageBundle().getString( "key_timer" ) );
        functionCombo.addItem( CtrlSettings.getLanguageBundle().getString( "key_stopwatch" ) );
    }
    
    // Getters and Setters
    
    /** Gets the laps count 
     * @return The laps count as {@code int}
     */
    public int getLapsCount()
    {
        return stopwatchLaps.size();
    }
    /** Gets the Preset Timers collection
     * @return The {@code LinkedList<PresetTimerTime>} main collection where 
     *         Timer Presets are stored while the application is running
     */
    public LinkedList<PresetTimerTime> getPresetTimers ()
    {
        return presetTimers;
    }
    /** Sets the Preset Timers collection
     * @param presetTimers The {@code LinkedList<PresetTimerTime>} main 
     *                     collection where Timer Presets are stored while the 
     *                     application is running
     */
    public void setPresetTimers ( LinkedList<PresetTimerTime> presetTimers )
    {
        this.presetTimers = presetTimers;
    }
    /** Sets the World Clocks collection
     * @param worldClocks The {@code LinkedList<WorldClock>} main 
     *                     collection where World Clocks are stored while the 
     *                     application is running
     */
    public void setWorldClocks ( LinkedList<WorldClock> worldClocks )
    {
        this.worldClocks = worldClocks;
    }
    /** Gets the application Stopwatch
     * @return The application unique {@code Stopwatch} instance object
     */
    public Stopwatch getStopwatch ()
    {
        return stopwatch;
    }
    /** Gets the Stopwatch collection where Laps are stored while the 
     * application is running
     * @return The {@code LinkedList<Lap>} main Stopwatch collection
     */
    public LinkedList<Lap> getStopwatchLaps ()
    {
        return stopwatchLaps;
    }
    /** Gets the application {@code Timer}
     *
     * @return The application unique {@code Timer} instance object
     */
    public Timer getTimer ()
    {
        return timer;
    }
    /** Gets the World Clocks collection where user customized World Clocks are 
     * stored while the application is running
     * @return The {@code LinkedList<WorldClock>} user's collection
     */
    public LinkedList<WorldClock> getWorldClocks ()
    {
        return worldClocks;
    }
    /** Gets the maximum {@code WorldClock} objects quantity in the collection, 
     * and in consequence, in the application, this is a constant
     * @return A {@code int} constant number that describes the maximum quantity of
     *         {@code WorldClock} objects available to have
     */
    public int getMAX_WORLD_CLOCKS_CANT ()
    {
        return MAX_WORLD_CLOCKS_CANT;
    }
    /** Gets the Zone Ids collection
     * @return An {@code ArrayList<String>} collection with the Zone Ids available
     */
    public ArrayList<String> getZoneIds ()
    {
        return zoneIds;
    }
    /** Gets the Short World Clock Panels collection
     * @return An {@code ArrayList<JPanel>} collection with the Short World Clock Panels
     */
    public ArrayList<JPanel> getShortPanels ()
    {
        return shortPanels;
    }
    /** Gets the application main System World Clock
     * @return A {@code WorldClock} object that corresponds to the system world
     *         clock by default
     */
    public WorldClock getSystemWorldClock ()
    {
        return systemWorldClock;
    }
    /** Sets the application main System World Clock
     * @param systemWorldClock A {@code WorldClock} object that corresponds to 
     *                         the system world clock by default
     */
    public void setSystemWorldClock ( WorldClock systemWorldClock )
    {
        this.systemWorldClock = systemWorldClock;
    }


}