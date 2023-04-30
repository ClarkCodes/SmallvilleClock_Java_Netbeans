package data;

/* LICENSE
 * Creative Commons Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

import controllers.CommonUtils;
import controllers.CtrlSettings;
import controllers.CtrlSmallvilleClock;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import models.Lap;
import models.PresetTimerTime;
import models.WorldClock;
import views.FrmOpenSave;

/** Manages Smallville Clock Laps Files, for open and save operations,
 * and also for loading and saving user settings.
 *
 * @author Clark - ClarkCodes
 * @since 1.0
 */
public class FilesManager
{
    private final CtrlSettings ctrlSettings;
    //Propiedades y Campos
    private String appDataDirectory = "", dirSeparator = ""; 
    private static final String SETTINGS_EXT = ".svKnSettings", LAPS_FILES_EXT = "svlps";
    private SystemType systemType = null;
    private File ruta = null;
    private boolean userSettingsChanged = false, 
            userTimerSoundsListChanged = false, 
            userTimerPresetsListChanged = false,
            userWorldClocksListChanged = false;
    private CommonUtils.FileType fileType = null; // Helper to determine what kind of file it has to work with on open and save operations.
    private Properties userProperties = null; // Properties Object for User Settings
    private static final JFileChooser openSaveAsDialog = new JFileChooser(); // JFileChooser for opening and saving files, only one in a generalized and static way for more comfortableness and a JFrame for the JFileChooser and mostly this way it is able to set the right icon
    private static final FrmOpenSave openSaveFrame = new FrmOpenSave(); // Frame that only exists to pass it as parameter to the Open and Save Dialog
    private static OperationResult result = OperationResult.FAILED;

    // Enums
    /** Enum constants to specify the criteria used to determine the file type
     *  that is being used in an open/save operation to call the proper
     *  method and do it in the right way
     */
    private enum FileTypeDetermination
    {
        FROM_PROPERTIES,
        INSPECTOR
    }
    
    /** Enum constants to determine the system type used by the user this is 
     * either a Windows or a Linux, onto point correctly a path for open/save
     * operations
     */
    private enum SystemType
    {
        LINUX,
        WINDOWS
    }

    /** Enum constants to specify the result of an open/save operation */
    public static enum OperationResult
    {
        SUCCESSFUL,
        FAILED,
        CANCELED_BY_USER
    }

    // Singleton Design Pattern
    private static FilesManager controller = null;
    private FilesManager () // Constructor
    {
        ctrlSettings = CtrlSettings.getController();
        systemAnalizer();
        defaultSettingsVerifier();
        userSettingsVerifier();
        userSettingsLoader();
        openSaveAsDialog.setPreferredSize( new Dimension( 900, 600 ) );
    }

    /** Gets the unique instance of this class, following Singleton Desing
     * Pattern in Development
     *
     * @return The Unique {@code DatosTickets} instance
     */
    public static FilesManager getController()
    {
        if ( controller == null )
            controller = new FilesManager();
        return controller;
    }

    /** Manages the saving of a Stopwatch Laps file, it shows a save file
     * dialog and calls the proper method based on the file type specified in
     * user settings/properties, either text or binary type to save the file
     * following that setting
     *
     * @param laps A {@code LinkedList<Lap>} collection which contains the 
     *             current stopwatch laps
     *
     * @see models.Lap
     */
    public final void saveLapsFile( LinkedList<Lap> laps )
    {
        ruta = null;
        boolean proceed = true;

        // Dialog Preparation
        openSaveFrame.setIconImage( ctrlSettings.getSaveIconForSaveDialog().getImage() );
        openSaveAsDialog.setApproveButtonText( CtrlSettings.getLanguageBundle().getString( "key_save_as" ) );
        openSaveAsDialog.setApproveButtonToolTipText( CtrlSettings.getLanguageBundle().getString( "key_data_save_tooltiptext" ) );
        openSaveAsDialog.setDialogTitle( CtrlSettings.getLanguageBundle().getString( "key_save_as" ) );
        openSaveAsDialog.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
        openSaveAsDialog.setFileFilter( new FileNameExtensionFilter( CtrlSettings.getLanguageBundle().getString( "key_laps_file" ) + "( *." + LAPS_FILES_EXT + " )", LAPS_FILES_EXT ) );

        if ( openSaveAsDialog.showSaveDialog( openSaveFrame ) == JFileChooser.APPROVE_OPTION )
        {
            ruta = openSaveAsDialog.getSelectedFile();

            if ( ruta != null )
            {
                if ( ruta.exists() )
                    if ( JOptionPane.showConfirmDialog( null, CtrlSettings.getLanguageBundle().getString( "key_file_already_exists_msj" ) + "\n" + CtrlSettings.getLanguageBundle().getString( "key_file_already_exists_question" ), CtrlSettings.getLanguageBundle().getString( "key_existing_file_title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE ) != JOptionPane.OK_OPTION )
                        proceed = false;

                if ( proceed )
                {
                    getFileType( FileTypeDetermination.FROM_PROPERTIES );

                    switch ( fileType )
                    {
                        case TEXT -> saveLapsTextFile( laps );
                        case BINARY -> saveLapsBinaryFile( laps );
                    }
                }
                else
                    result = OperationResult.CANCELED_BY_USER;
            }
            else
                result = OperationResult.CANCELED_BY_USER;
        }
        else
            result = OperationResult.CANCELED_BY_USER;
    }

    /** Saves a Smallville Clock Stopwatch Laps text file
     *
     * @param laps A {@code LinkedList<Lap>} collection which contains the 
     *             current stopwatch laps to be saved
     */
    private final void saveLapsTextFile( LinkedList<Lap> laps )
    {
        try ( FileWriter fileWriter = new FileWriter( ruta + ( !ruta.getAbsolutePath().endsWith( "." + LAPS_FILES_EXT ) ? "." + LAPS_FILES_EXT : "" ), false ) )
        {
            for ( Lap lap : laps )
            {   // Lap data
                fileWriter.write( lap.globalTime().toMillis() + "\n" );
                fileWriter.write( lap.lapTime().toMillis() + "\n" );
            }

            fileWriter.write( "---lpsfEnd---" );

            result = OperationResult.SUCCESSFUL;
            JOptionPane.showMessageDialog( null, CtrlSettings.getLanguageBundle().getString("key_save_successful_msj") + "\n" + CtrlSettings.getLanguageBundle().getString("key_saved_on") + ruta, CtrlSettings.getLanguageBundle().getString("key_save_successful_title"), JOptionPane.INFORMATION_MESSAGE );

        }
        catch ( IOException ioex )
        {
            result = OperationResult.FAILED;
            JOptionPane.showMessageDialog( null, "Laps Text File Saving, something went not as expected.\n" + result.toString() + ": " + ioex.getMessage() + "\nClass: " + ioex.getClass() + "\nStack Trace: " + Arrays.toString( ioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_save_error_title" ), JOptionPane.ERROR_MESSAGE );
        }
    }

    /** Saves a Smallville Clock Stopwatch Laps binary file
     *
     * @param laps A {@code LinkedList<Lap>} collection which contains the 
     *             current stopwatch laps to be saved
     */
    private final void saveLapsBinaryFile( LinkedList<Lap> laps )
    {
        try ( FileOutputStream objFileSetter = new FileOutputStream( ruta + ( !ruta.getAbsolutePath().endsWith( "." + LAPS_FILES_EXT ) ? "." + LAPS_FILES_EXT : "" ), false );
                ObjectOutputStream objWriter = new ObjectOutputStream( objFileSetter ) )
        {
            objWriter.writeInt( laps.size() ); // First it is written how many laps are going to be in this file for when it opens to read exactly those times

            for ( Lap lap : laps )
                objWriter.writeObject( lap ); // Lap data

            result = OperationResult.SUCCESSFUL;
            JOptionPane.showMessageDialog( null, CtrlSettings.getLanguageBundle().getString("key_save_successful_msj") + "\n" + CtrlSettings.getLanguageBundle().getString("key_saved_on") + ruta, CtrlSettings.getLanguageBundle().getString("key_save_successful_title"), JOptionPane.INFORMATION_MESSAGE );
        }
        catch ( IOException ioex )
        {
            result = OperationResult.FAILED;
            JOptionPane.showMessageDialog( null, "Laps Binary File Saving, something went not as expected.\n" + result.toString() + ": " + ioex.getMessage() + "\nClass: " + ioex.getClass() + "\nStack Trace: " + Arrays.toString( ioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_save_error_title" ), JOptionPane.ERROR_MESSAGE );
        }
    }

    /** Manages the opening of a Smallville Clock Stopwatch Laps file,
     * it shows an open file dialog and calls the proper method based on
     * the determined file type, either text or binary to read the file in
     * the right way and show its content
     *
     * @return A {@code LinkedList<Lap>} collection which contains laps from a 
     *         Smallville Clock Stopwatch Laps file
     */
    public final LinkedList<Lap> openLapsFile()
    {   // Dialog Preparation
        openSaveFrame.setIconImage( ctrlSettings.getSourceOpenIconForOpenDialog().getImage() );
        openSaveAsDialog.setMultiSelectionEnabled( false );
        openSaveAsDialog.setFileSelectionMode( JFileChooser.FILES_ONLY );
        openSaveAsDialog.setApproveButtonText( CtrlSettings.getLanguageBundle().getString( "key_open" ) );
        openSaveAsDialog.setApproveButtonToolTipText( CtrlSettings.getLanguageBundle().getString( "key_open_tooltiptext" ) );
        openSaveAsDialog.setDialogTitle( CtrlSettings.getLanguageBundle().getString( "key_open_title" ) );
        openSaveAsDialog.setFileFilter( new FileNameExtensionFilter( CtrlSettings.getLanguageBundle().getString( "key_laps_file" ) + "( *." + LAPS_FILES_EXT + " )", LAPS_FILES_EXT ) );

        ruta = null;

        if ( openSaveAsDialog.showOpenDialog( openSaveFrame ) == JFileChooser.APPROVE_OPTION )
        {
            ruta = openSaveAsDialog.getSelectedFile();

            if ( ruta != null )
            {
                getFileType( FileTypeDetermination.INSPECTOR );

                return switch( fileType )
                        {
                            case TEXT -> openLapsTextFile();
                            case BINARY -> openLapsBinaryFile();
                        };
            }
            else
                result = OperationResult.CANCELED_BY_USER;
        }
        else
            result = OperationResult.CANCELED_BY_USER;

        return null;
    }

    /** Opens a Smallville Clock Stopwatch Laps text file
     *
     * @return A {@code LinkedList<Lap>} collection which contains laps from a 
     *         Smallville Clock Stopwatch Laps text file
     */
    private final LinkedList<Lap> openLapsTextFile()
    {
        LinkedList<Lap> laps = null;
        Lap lap = null;

        String globalTime;

        try ( FileReader fileReader = new FileReader( ruta );
              BufferedReader bufferedReader = new BufferedReader( fileReader ) )
        {
            laps = new LinkedList<>();

            while ( !( globalTime = bufferedReader.readLine() ).equalsIgnoreCase( "---lpsfEnd---" ) )
            {
                lap = new Lap( CommonUtils.ChronoType.LAP, Duration.ofMillis( Long.parseLong( globalTime ) ), Duration.ofMillis( Long.parseLong( bufferedReader.readLine().trim() ) ) );
                laps.add( lap );
            }

            result = OperationResult.SUCCESSFUL;
        }
        catch ( IOException ioex )
        {
            result = OperationResult.FAILED;
            JOptionPane.showMessageDialog( null, "Laps Text File Opening, something went not as expected.\n" + result.toString() + ": " + ioex.getMessage() + "\nClass: " + ioex.getClass() + "\nStack Trace: " + Arrays.toString( ioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_open_error_title" ), JOptionPane.ERROR_MESSAGE );
        }
        
        return laps;
    }

    /** Opens a Smallville Clock Stopwatch Laps binary file
     *
     * @return A {@code LinkedList<Lap>} collection which contains laps from the
     *         specified Smallville Clock Stopwatch Laps binary file
     */
    private final LinkedList<Lap> openLapsBinaryFile()
    {
        LinkedList<Lap> laps = new LinkedList<>();
        Lap lap;
        int cantLaps = 0;

        try ( FileInputStream objFileSetter = new FileInputStream( ruta );
                ObjectInputStream objReader = new ObjectInputStream( objFileSetter ) )
        {
            cantLaps = objReader.readInt();

            for ( int i = 0; i < cantLaps; i++ )
            {
                lap = ( Lap ) objReader.readObject();
                laps.add( lap );
            }

            result = OperationResult.SUCCESSFUL;
        }
        catch ( EOFException eofex )
        { // This is an especial exception in a binary file, given that always that the end is reached, this exception is thrown, so in this catch block where we have to return the laps collection or in other words, that what it has been read from the file
            if( laps.size() == cantLaps )
            {
                result = OperationResult.SUCCESSFUL;
                return laps;
            }
            else
            {
                result = OperationResult.FAILED;
                JOptionPane.showMessageDialog( null, "Laps Binary File Opening, something went not as expected, laps size doesn't match with laps quantity.\n" + result.toString() + ": " + eofex.getMessage() + "\nClass: " + eofex.getClass() + "\nStack Trace: " + Arrays.toString( eofex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_open_error_title" ), JOptionPane.ERROR_MESSAGE );
            }
        }
        catch ( ClassNotFoundException | IOException cnfioex )
        {
            result = OperationResult.FAILED;
            JOptionPane.showMessageDialog( null, "Laps Binary File Opening, something went not as expected.\n" + result.toString() + ": " + cnfioex.getMessage() + "\nClass: " + cnfioex.getClass() + "\nStack Trace: " + Arrays.toString( cnfioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_open_error_title" ), JOptionPane.ERROR_MESSAGE );
        }
        
        return laps;
    }
    
    /** Saves the custom user Timer's sounds list as a binary file for it to be 
     * available the next time the application opens
     *
     * @see controllers.CtrlSettings
     * @see views.DlgTimerSoundSelecter
     */
    public final void saveTimerSoundsList()
    {
        try ( FileOutputStream objFileSetter = new FileOutputStream( appDataDirectory + dirSeparator + "usersTSL.svtl", false );
                ObjectOutputStream objWriter = new ObjectOutputStream( objFileSetter ) )
        {
            objWriter.writeObject( ctrlSettings.getTimerSoundsList() );
        }
        catch ( IOException ioex )
        {
            JOptionPane.showMessageDialog( null, "Timer Sounds List Saving, something went not as expected.\n" + result.toString() + ": " + ioex.getMessage() + "\nClass: " + ioex.getClass() + "\nStack Trace: " + Arrays.toString( ioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_save_error_title" ), JOptionPane.ERROR_MESSAGE );
        }
    }
    
    /** Saves the custom user Timer presets list as a binary file for it to be 
     * available the next time the application opens
     * 
     * @see views.DlgSetTimer
     * @see controllers.CtrlSmallvilleClock
     */
    public final void saveTimerPresetsList()
    {
        try ( FileOutputStream objFileSetter = new FileOutputStream( appDataDirectory + dirSeparator + "usersTPL.svtl", false );
                ObjectOutputStream objWriter = new ObjectOutputStream( objFileSetter ) )
        {
            objWriter.writeObject( CtrlSmallvilleClock.getController().getPresetTimers() );
        }
        catch ( IOException ioex )
        {
            JOptionPane.showMessageDialog( null, "Timer Presets List Saving, something went not as expected.\n" + result.toString() + ": " + ioex.getMessage() + "\nClass: " + ioex.getClass() + "\nStack Trace: " + Arrays.toString( ioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_save_error_title" ), JOptionPane.ERROR_MESSAGE );
        }
    }
    
    /** Saves the custom user World Clocks list, saving actually their zone ids 
     * as a binary file for it to restore clocks with these and they may be 
     * available the next time the application opens
     * 
     * @see views.FrmSmallvilleClockMain
     * @see controllers.CtrlSmallvilleClock
     */
    public final void saveWorldClocksList()
    {
        LinkedList<String> zoneIdsToSave = new LinkedList<>();
        CtrlSmallvilleClock.getController().getWorldClocks().forEach( wc -> zoneIdsToSave.add( wc.getRegionZone() ) );
        
        try ( FileOutputStream objFileSetter = new FileOutputStream( appDataDirectory + dirSeparator + "usersWCL.svtl", false );
                ObjectOutputStream objWriter = new ObjectOutputStream( objFileSetter ) )
        {
            objWriter.writeObject( zoneIdsToSave );
        }
        catch ( IOException ioex )
        {
            JOptionPane.showMessageDialog( null, "World Clocks List Saving, something went not as expected.\n" + result.toString() + ": " + ioex.getMessage() + "\nClass: " + ioex.getClass() + "\nStack Trace: " + Arrays.toString( ioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_save_error_title" ), JOptionPane.ERROR_MESSAGE );
        }
    }
    
    /** Opens the custom user Timer's sounds list from a binary file if this 
     * exists, this means, only if the user set this list previously, if so, 
     * this list is added to the Timer sounds list
     * 
     * @see controllers.CtrlSettings
     * @see views.DlgTimerSoundSelecter
     */
    public final void openTimerSoundsList()
    {
        ArrayList<String> soundsList = new ArrayList<>();
        File soundsListFile = new File( appDataDirectory + dirSeparator + "usersTSL.svtl" ); // TSL: Timer Sounds List

        if ( soundsListFile.exists() )
        {
            try ( FileInputStream objFileSetter = new FileInputStream( soundsListFile );
                    ObjectInputStream objReader = new ObjectInputStream( objFileSetter ) )
            {
                soundsList = ctrlSettings.addPredefinedSounds( ( ArrayList<String> ) objReader.readObject() );
            }
            catch ( EOFException eofex )
            { // This is an especial exception in a binary file, given that always that the end is reached, this exception is thrown, so in this catch block where we have to return the laps collection or in other words, that what it has been read from the file
                ctrlSettings.setTimerSoundsList( soundsList );
            }
            catch ( ClassNotFoundException | IOException cnfioex )
            {
                JOptionPane.showMessageDialog( null, "Timer Sounds List Opening, something went not as expected.\n" + result.toString() + ": " + cnfioex.getMessage() + "\nClass: " + cnfioex.getClass() + "\nStack Trace: " + Arrays.toString( cnfioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_open_error_title" ), JOptionPane.ERROR_MESSAGE );
            }
            
            ctrlSettings.setTimerSoundsList( soundsList );
        }
        else
        {
            ctrlSettings.setTimerSoundsList( ctrlSettings.addPredefinedSounds( soundsList ) );
        }
    }
    
    /** Opens the user Timer presets list from a binary file if this exists, 
     * only if the user set this list previously, if so, this list is added to 
     * the Timer presets collection
     * 
     * @see controllers.CtrlSmallvilleClock
     * @see views.DlgSetTimer
     */
    public final void openTimerPresets()
    {
        File timerPresetsFile = new File( appDataDirectory + dirSeparator + "usersTPL.svtl" ); // TPL: Timer Presets List

        if ( timerPresetsFile.exists() )
        {
            CtrlSmallvilleClock clockManager = CtrlSmallvilleClock.getController();
            LinkedList<PresetTimerTime> timerPresets = new LinkedList<>();
        
            try ( FileInputStream objFileSetter = new FileInputStream( timerPresetsFile );
                    ObjectInputStream objReader = new ObjectInputStream( objFileSetter ) )
            {
                timerPresets = ( LinkedList<PresetTimerTime> ) objReader.readObject();
            }
            catch ( EOFException eofex )
            { // This is an especial exception in a binary file, given that always that the end is reached, this exception is thrown, so in this catch block where we have to return the laps collection or in other words, that what it has been read from the file
                clockManager.setPresetTimers( timerPresets );
            }
            catch ( ClassNotFoundException | IOException cnfioex )
            {
                JOptionPane.showMessageDialog( null, "Timer Presets List Opening, something went not as expected.\n" + result.toString() + ": " + cnfioex.getMessage() + "\nClass: " + cnfioex.getClass() + "\nStack Trace: " + Arrays.toString( cnfioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_open_error_title" ), JOptionPane.ERROR_MESSAGE );
            }
            
            clockManager.setPresetTimers( timerPresets );
        }
    }

    /** Opens the user World Clocks list from a binary file if this exists, 
     * only if the user has modified this list previously, this is if added or 
     * deleted a World Clock, if so, this World Clocks list is added to the 
     * World Clocks collection
     * 
     * @see controllers.CtrlSmallvilleClock
     * @see views.FrmSmallvilleClockMain
     */
    public final void openWorldClocksList()
    {
        File worldClocksFile = new File( appDataDirectory + dirSeparator + "usersWCL.svtl" ); // WCL: World Clocks List

        if ( worldClocksFile.exists() )
        {
            CtrlSmallvilleClock clocksManager = CtrlSmallvilleClock.getController();
            LinkedList<String> savedZoneIds;
            LinkedList<WorldClock> restoredWorldClocks = new LinkedList<>();
        
            try ( FileInputStream objFileSetter = new FileInputStream( worldClocksFile );
                    ObjectInputStream objReader = new ObjectInputStream( objFileSetter ) )
            {
                savedZoneIds = ( LinkedList<String> ) objReader.readObject();
                savedZoneIds.forEach( zid -> restoredWorldClocks.add( new WorldClock( ZoneId.of( zid ) ) ) );
            }
            catch ( EOFException eofex )
            { // This is an especial exception in a binary file, given that always that the end is reached, this exception is thrown, so in this catch block where we have to return the laps collection or in other words, that what it has been read from the file
                clocksManager.setWorldClocks( restoredWorldClocks );
            }
            catch ( ClassNotFoundException | IOException cnfioex )
            {
                JOptionPane.showMessageDialog( null, "World Clocks List Opening, something went not as expected.\n" + result.toString() + ": " + cnfioex.getMessage() + "\nClass: " + cnfioex.getClass() + "\nStack Trace: " + Arrays.toString( cnfioex.getStackTrace() ), CtrlSettings.getLanguageBundle().getString( "key_open_error_title" ), JOptionPane.ERROR_MESSAGE );
            }
            
            clocksManager.setWorldClocks( restoredWorldClocks );
        }
    }
    
    /** Manages the determination of a Laps file type and sets it to the
     * respective attribute, following the specified determination criteria
     * in order to save or open a Smallville Clock Stopwatch Laps file with
     * the right file format
     *
     * @param determinationMethod A {@code FileTypeDetermination enum} constant
     *                            to indicate the way to perform the file type
     *                            determination, this could be the file type
     *                            used to save Smallville Clock files specified
     *                            user settings or by inspecting a specified
     *                            file by the user in order to open it in the
     *                            right way by calling the proper method, this
     *                            is if the specified file is a text or a binary
     *                            file
     */
    private void getFileType( FileTypeDetermination determinationMethod )
    {
        fileType = switch( determinationMethod )
                {
                    case FROM_PROPERTIES -> getUserProperties().getProperty( "fileTypeToSave" ).equalsIgnoreCase( "binary" ) ? CommonUtils.FileType.BINARY : CommonUtils.FileType.TEXT;
                    case INSPECTOR -> fileTypeInspector();
                };
    }

    /** Inspects a specified file to determine its type, using an own method to
     * know if it is a binary or a text Smallville Clock Stopwatch Laps file.
     *
     * @return A {@code Commons.FileType enum} constant with the corresponding
     *         value representation for either binary or text
     */
    private CommonUtils.FileType fileTypeInspector()
    {
        try ( FileInputStream objFileSetter = new FileInputStream( ruta );
                ObjectInputStream objReader = new ObjectInputStream( objFileSetter ) )
        {// It is about to read the first object of a Smallville Clock Stopwatch Laps binary file, if it is possible to read it and an exception it is not thrown, it means that in fact it is a binary file
            objReader.readInt();
        }
        catch ( IOException ioex )
        {// Otherwise if an exception is thrown, then it is deduced and it is determined that it is about a text file
            return CommonUtils.FileType.TEXT;
        }

        return CommonUtils.FileType.BINARY;
    }

    /** Sets the save or open dialog starting folder following the user settings
     * @see dev.clarkcodes.controllers.CtrlSettings
     */
    public final void dialogDirectorySetter()
    {
        if ( ctrlSettings.getStartingFolder().equalsIgnoreCase( "default" ) )
        {
            openSaveAsDialog.setCurrentDirectory( null );
        }
        else
        {
            openSaveAsDialog.setCurrentDirectory( new File( ctrlSettings.getStartingFolder() ) );
        }
    }
    
    private final void systemAnalizer()
    {
        systemType = System.getProperty( "os.name" ).toLowerCase().contains( "nux" ) ? SystemType.LINUX : SystemType.WINDOWS;
        
        if ( systemType == SystemType.WINDOWS )
        {
            appDataDirectory = System.getenv("APPDATA") + "\\SmallvilleClock";
            dirSeparator = "\\";
        }
        else
        {
            appDataDirectory = System.getenv("APPDATA") + "/SmallvilleClock";
            dirSeparator = "/";
        }
        
        File svClockDataDirectory = new File( appDataDirectory );
        
        if ( !svClockDataDirectory.exists() )
            svClockDataDirectory.mkdir();                
    }

    /** Verifies settings by default, if Default Settings file doesn't exist
     * yet, it is created with generic starting values
     */
    public final void defaultSettingsVerifier ()
    {      
        File defaultSettingsFile = new File( appDataDirectory + dirSeparator + "defaultSettings" + SETTINGS_EXT );

        if ( !defaultSettingsFile.exists() )
        {
            try ( FileOutputStream writer = new FileOutputStream( defaultSettingsFile, false ) )
            {   // Setting default Properties
                Properties defaultProperties = new Properties();
                defaultProperties.setProperty( "currentGuiLafTheme", "Night Owl (Material)" );
                defaultProperties.setProperty( "appLocale", "es_EC" );
                defaultProperties.setProperty( "fileTypeToSave", "binary" );
                defaultProperties.setProperty( "startingFolder", "default" );
                defaultProperties.setProperty( "soundName", "Spaceship Alarm.mp3" );
                defaultProperties.setProperty( "appOnTop", "false" );
                defaultProperties.setProperty( "format24Hours", "true" );
                defaultProperties.setProperty( "includeSeconds", "false" );

                // Saving the default Properties
                defaultProperties.store( writer, "-- Smallville Clock User Properties -- WARNING: THIS IS AN APPLICATION SYSTEM PROPERTIES FILE. DO NOT CHANGE OR MODIFY MANUALLY THIS FILE UNLESS YOU KNOW WHAT YOU ARE DOING, OTHERWISE IT COULD GENERATE ERRORS" );
            }
            catch ( FileNotFoundException fnfex )
            {
                JOptionPane.showMessageDialog( null, "DefaultSettingsVerifier:\n" + fnfex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_fnf" ), JOptionPane.ERROR_MESSAGE );
                Logger.getLogger( FilesManager.class.getName() ).log( Level.SEVERE, null, fnfex );
            }
            catch ( IOException ioex )
            {
                JOptionPane.showMessageDialog( null, "DefaultSettingsVerifier:\n" + ioex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_io" ), JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /** Verifies user settings, if User Settings file doesn't exist yet, it is
     * created just as a copy with same values as default settings
     */
    public final void userSettingsVerifier ()
    {
        File userSettingsFile = new File( appDataDirectory + dirSeparator + "userSettings" + SETTINGS_EXT );

        if ( !userSettingsFile.exists() )
        {
            try ( FileInputStream settingsReader = new FileInputStream( appDataDirectory + dirSeparator + "defaultSettings" + SETTINGS_EXT );
                  FileOutputStream settingsWriter = new FileOutputStream( userSettingsFile, false ) )
            {   // Setting the User Properties from scratch loading the default Properties into it
                userProperties = new Properties(); 
                userProperties.load( settingsReader );

                // Saving the User Properties now
                userProperties.store( settingsWriter, "-- Smallville Clock User Properties -- WARNING: THIS IS AN APPLICATION SYSTEM PROPERTIES FILE. DO NOT CHANGE OR MODIFY MANUALLY THIS FILE UNLESS YOU KNOW WHAT YOU ARE DOING, OTHERWISE IT COULD GENERATE ERRORS" );
                userProperties = null;
            }
            catch ( FileNotFoundException fnfex )
            {
                JOptionPane.showMessageDialog( null, "UserSettingsVerifier:\n" + fnfex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_fnf" ), JOptionPane.ERROR_MESSAGE );
                Logger.getLogger( FilesManager.class.getName() ).log( Level.SEVERE, null, fnfex );
            }
            catch ( IOException ioex )
            {
                JOptionPane.showMessageDialog( null, "UserSettingsVerifier:\n" + ioex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_io" ), JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /** Loads user settings only if User Settings file exists
     * @see dev.clarkcodes.controllers.CtrlSettings
     */
    public final void userSettingsLoader ()
    {
        File userSettingsFile = new File( appDataDirectory + dirSeparator + "userSettings" + SETTINGS_EXT );

        if ( userSettingsFile.exists() )
        {
            try ( FileInputStream settingsReader = new FileInputStream( userSettingsFile ) )
            {   // Setting the User Properties from the saved User Properties File
                userProperties = new Properties();
                userProperties.load( settingsReader );

                // Applying saved settings to app
                ctrlSettings.applyThemeManager( getUserProperties().getProperty( "currentGuiLafTheme" ) ); // Making the Call to apply the Laf theme saved in User Settings
                ctrlSettings.colorOnIconsManager( CtrlSettings.ColorSetType.STARTUP ); // Applying Colors on Icons
                // Setting App Language saved in User Settings
                ctrlSettings.loadLanguageFromPreferences( getUserProperties().getProperty( "appLocale" ) );
                ctrlSettings.setFileTypeToSave( getUserProperties().getProperty( "fileTypeToSave" ) );
                ctrlSettings.setStartingFolder( getUserProperties().getProperty( "startingFolder" ) );
                ctrlSettings.setTimerSound( getUserProperties().getProperty( "soundName" ) );
                ctrlSettings.setAppOnTop( getUserProperties().getProperty( "appOnTop" ).equalsIgnoreCase( "true" ) );
                ctrlSettings.setFormat24Hours( getUserProperties().getProperty( "format24Hours" ).equalsIgnoreCase( "true" ) );
                ctrlSettings.setIncludeSeconds( getUserProperties().getProperty( "includeSeconds" ).equalsIgnoreCase( "true" ) );
                ctrlSettings.setCurrentSelectedGuiLafTheme( getUserProperties().getProperty( "currentGuiLafTheme" ) );
                ctrlSettings.setCurrentAppliedGuiLafTheme( ctrlSettings.getCurrentSelectedGuiLafTheme() );
                ctrlSettings.setApplyingThemePending( false );
                openTimerSoundsList();
                openTimerPresets();
                openWorldClocksList();
                dialogDirectorySetter();
            }
            catch ( FileNotFoundException fnfex )
            {
                JOptionPane.showMessageDialog( null, "UserSettingsLoader:\n" + fnfex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_fnf" ), JOptionPane.ERROR_MESSAGE );
                Logger.getLogger( FilesManager.class.getName() ).log( Level.SEVERE, null, fnfex );
            }
            catch ( IOException ioex )
            {
                JOptionPane.showMessageDialog( null, "UserSettingsLoader:\n" + ioex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_io" ), JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /** Saves user settings only if User Settings have changed, using a boolean
     * helper attribute
     */
    public final void userSettingsSaver ()
    {
        soundsListSavingVerifier();
        timerPresetsListSavingVerifier();
        worldClocksListSavingVerifier();
        
        if ( isUserSettingsChanged() )
        {
            File userSettingsFile = new File( appDataDirectory + dirSeparator + "userSettings" + SETTINGS_EXT );
            
            try ( FileOutputStream settingsWriter = new FileOutputStream( userSettingsFile, false ) )
            {   // Saving the User Properties at the Application Closing
                userProperties.store( settingsWriter, "-- Smallville Clock User Properties -- WARNING: THIS IS AN APPLICATION SYSTEM PROPERTIES FILE. DO NOT CHANGE OR MODIFY MANUALLY THIS FILE UNLESS YOU KNOW WHAT YOU ARE DOING, OTHERWISE IT COULD GENERATE ERRORS" );
            }
            catch ( FileNotFoundException fnfex )
            {
                JOptionPane.showMessageDialog( null, "UserSettingsSaver:\n" + fnfex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_fnf" ), JOptionPane.ERROR_MESSAGE );
                Logger.getLogger( FilesManager.class.getName() ).log( Level.SEVERE, null, fnfex );
            }
            catch ( IOException ioex )
            {
                JOptionPane.showMessageDialog( null, "UserSettingsSaver:\n" + ioex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_io" ), JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /** Verifies if a user setting has been changed compared with the saved user
     * setting stored in the respective {@code Properties} object
     */
    public final void settingsChangeVerifier ()
    {   // Setting the Property to store the Properties object later on App Close
        if ( ( !getUserProperties().getProperty( "appLocale" ).equalsIgnoreCase( CtrlSettings.getAppLanguage().getLocale() )
                | !getUserProperties().getProperty( "fileTypeToSave" ).equalsIgnoreCase( ctrlSettings.getFileTypeToSave().toString().toLowerCase() )
                | !getUserProperties().getProperty( "startingFolder" ).equalsIgnoreCase( ctrlSettings.getStartingFolder() )
                | !getUserProperties().getProperty( "soundName" ).equalsIgnoreCase( ctrlSettings.getTimerSound() )
                | !getUserProperties().getProperty( "appOnTop" ).equalsIgnoreCase( ctrlSettings.isAppOnTop() ? "true" : "false" )
                | !getUserProperties().getProperty( "format24Hours" ).equalsIgnoreCase( ctrlSettings.isFormat24Hours() ? "true" : "false" )
                | !getUserProperties().getProperty( "includeSeconds" ).equalsIgnoreCase( ctrlSettings.isIncludeSeconds() ? "true" : "false" )
                | !getUserProperties().getProperty( "currentGuiLafTheme" ).equalsIgnoreCase( ctrlSettings.getCurrentSelectedGuiLafTheme() ) )
                && !isUserSettingsChanged() )
        {
            setUserSettingsChanged( true );
        }
    }
    
    /** Verifies if sounds list setting has changed and it is necessary to save
     * the user custom sounds list
     */
    public void soundsListSavingVerifier()
    {
        if ( isUserTimerSoundsListChanged() && ctrlSettings.getTimerSoundsList().size() > 3 )
        {
            ctrlSettings.removePredefinedSounds();
            saveTimerSoundsList();
        }
    }
    /** Verifies if timer presets list setting has changed and it is necessary 
     * to save the user timer presets list
     */
    public void timerPresetsListSavingVerifier()
    {
        if ( isUserTimerPresetsListChanged() )
            saveTimerPresetsList();
    }
    /** Verifies if timer presets list setting has changed and it is necessary 
     * to save the user timer presets list
     */
    public void worldClocksListSavingVerifier()
    {
        if ( isUserWorldClocksListChanged() )
            saveWorldClocksList();
    }
    /** Updates the Open and Save file dialog, this method is mostly used
     * when a Look and Feel is set, to properly update the {@code JFileChooser}
     * to match with the current theme
     */
    public static void updateSaveOpenDlg ()
    {
        openSaveAsDialog.updateUI();
    }

    // Getters and Setters
    /** Gets the result of a open or save operation
     * @return The {@code OperationResult enum} resulting constant
     */
    public static OperationResult getResult ()
    {
        return result;
    }

    /** Gets the user settings changed indicator
     * @return {@code true} if a user setting has changed, {@code false}
     * otherwise
     */
    public boolean isUserSettingsChanged ()
    {
        return userSettingsChanged;
    }

    /** Sets the user settings changed indicator
     * @param userSettingsChanged A {@code boolean} that indicates the change of
     *                            a user setting, it should be {@code true} if a
     *                            setting has changed and {@code false} if not
     */
    public void setUserSettingsChanged ( boolean userSettingsChanged )
    {
        this.userSettingsChanged = userSettingsChanged;
    }
    
    /** Gets the user Timer's sounds list changed indicator
     * @return {@code true} if a user Timer's sounds list has changed, 
     * {@code false} otherwise
     */
    public boolean isUserTimerSoundsListChanged ()
    {
        return userTimerSoundsListChanged;
    }
    
    /** Sets the user timer sounds list changed indicator
     * @param userTimerSoundsListChanged A {@code boolean} that indicates the 
     *                                   change of the Timer sounds list, it 
     *                                   should be {@code true} if this list 
     *                                   has changed, {@code false} otherwise
     */
    public void setUserTimerSoundsListChanged ( boolean userTimerSoundsListChanged )
    {
        this.userTimerSoundsListChanged = userTimerSoundsListChanged;
    }
    /** Gets the user Timer presets list changed indicator
     * @return {@code true} if a user Timer presets list has changed, 
     * {@code false} otherwise
     */
    public boolean isUserTimerPresetsListChanged ()
    {
        return userTimerPresetsListChanged;
    }
    /** Sets the user timer presets list changed indicator
     * @param userTimerPresetsListChanged A {@code boolean} that indicates the 
     *                                    change of the timer presets list, it 
     *                                    should be {@code true} if this collection 
     *                                    has changed, {@code false} otherwise
     */
    public void setUserTimerPresetsListChanged ( boolean userTimerPresetsListChanged )
    {
        this.userTimerPresetsListChanged = userTimerPresetsListChanged;
    }
    /** Gets the user World Clocks list changed indicator, this is, if a World 
     * Clock has been added or deleted, 
     * @return {@code true} if there has been a change on the user World Clocks
     * collection, {@code false} otherwise
     */
    public boolean isUserWorldClocksListChanged ()
    {
        return userWorldClocksListChanged;
    }
    /** Sets the user World Clock list changed indicator
     * @param userWorldClocksListChanged A {@code boolean} that indicates the 
     *                                   change of the user World Clocks list, it 
     *                                   should be {@code true} if this collection 
     *                                   has changed, {@code false} otherwise
     */
    public void setUserWorldClocksListChanged ( boolean userWorldClocksListChanged )
    {
        this.userWorldClocksListChanged = userWorldClocksListChanged;
    }

    /** Gets the user Properties
     * @return A {@code Properties} object that corresponds to user settings
     */
    public Properties getUserProperties ()
    {
        return userProperties;
    }

    /** Gets a {@code JFrame} used to show the open/save dialog
     * @return A {@code JFrame FrmOpenSave} object that is a window where
     * the open/save dialog inherits
     */
    public static FrmOpenSave getOpenSaveFrame ()
    {
        return openSaveFrame;
    }
    /** Gets the directories separator character as a {@code String}
     * @return A {@code String} representation that contains the directories 
     *         separator that corresponds the system type detected
     */
    public String getDirSeparator ()
    {
        return dirSeparator;
    }
}