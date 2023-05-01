package controllers;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.*;
import data.FilesManager;
import java.awt.Color;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import views.FrmSmallvilleClockMain;

/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

/** Controller class for Settings management in the application
 * @author Clark - ClarkCodes
 * @since 1.0
 */
public class CtrlSettings
{   // Versioning fields - Application Version Constant 1.0
    private final static int VERSION_MAJOR = 1, VERSION_MINOR = 0;

    // Themes Main Lists - These lists will be useful to fulfill the combo boxes of Settings Window
    private final ArrayList<String> availableFlatLafThemes, availableIntelliJThemes;
    private ArrayList<String> timerSoundsList;
    private final String[] PREDEFINED_SOUNDS_NAMES = { "Spaceship Alarm.mp3", 
                "Bad Ringtone.mp3", 
                "Shooting Stars Ringtone.mp3" };
    private final LinkedList<String> PREDEFINED_SOUNDS_PATHS;

    // Properties - Fields
    private String currentSelectedGuiLafTheme, currentAppliedGuiLafTheme, startingFolder, timerSound;
    private boolean applyingThemePending, appOnTop, format24Hours, includeSeconds;
    private CommonUtils.FileType fileTypeToSave;
    private static Mode appMode = Mode.EXPANDED;

    // Internationalization (i18n) - App Locale
    private static SLL appLanguage;
    private static ResourceBundle languageBundle;

    // Icons
    private final int PX_X2, PX_SMALL; // Size in pixels that icons with double size will have, normal icons have 16x16 pixels, ergo x2 icons have 32x32 pixels
    private FlatSVGIcon settingsIcon, settingsIconForSettingsForm, addIcon, 
            addIconForGenerateForm, editIcon, editIconForSearchForm, deleteIcon,
            sourceOpenIcon, sourceOpenIconForOpenDialog, saveIcon, 
            saveIconForSaveDialog, infoIcon, infoIconForAboutDialog, 
            powerExitIcon, groupsAttributionIcon, groupsAttributionIconForAttributionForm,
            timerIcon, timerMiniIcon, addTimerIcon, checkLapsIcon, searchIcon, checkLapsMiniIcon,
            searchIconForSearchForm, fullModeIcon, miniModeIcon, nextIcon, previousIcon, 
            playStartResumeIcon, pauseIcon, stopCancelIcon, markLapIcon;

    /** Enum Helper to set the SVG Icon Color Filters */
    public static enum ColorSetType
    {   /** *  Color Set Type {@code enum} constant for STARTUP, used to colorize icons with a laf friendly color when the application stars */
        STARTUP,
        /** *  Color Set Type {@code enum} constant for RUNTIME, used to colorize icons with a laf friendly color when the application is already running */
        RUNTIME
    }

    /** Enum Helper to manage languages and locales for the application
     *
     * @see visual.DlgSettings
     */
    public static enum SLL // SLL: Supported Languages and Locales
    {   /** Language {@code enum} constant for Spanish */
        SPANISH ( "es", "EC", "Español" ),
        /** Language {@code enum} constant for English */
        ENGLISH ( "en", "US", "English" );

        final String language, country, tagName;

        SLL( String language, String country, String tagName )
        {
            this.language = language;
            this.country = country;
            this.tagName = tagName;
        }

        /** Gets the locale that corresponds to this {@code enum} constant
         * @return The corresponding {@code String} that contains the respective
         * locale using a determined format composed by the
         * ISO 639 alpha-2 language code and the ISO 3166 alpha-2 country code,
         * separated by an underline '_'. For example: "es_EC" or "en_US"
         * @see Locale
         */
        public String getLocale()
        {
            return this.language + "_" + this.country;
        }

        /** Gets the language code that corresponds to this {@code enum}
         * constant
         * @return The corresponding {@code String} that contains the respective
         * ISO 639 alpha-2 language code
         * @see Locale
         */
        public String getLanguage()
        {
            return this.language;
        }

        /** Gets the country code that corresponds to this {@code enum} constant
         * @return The corresponding {@code String} that contains the respective
         * ISO 3166 alpha-2 country code
         * @see Locale
         */
        public String getCountry()
        {
            return this.country;
        }
        
        /** Gets the tag name that corresponds to this {@code enum} constant and
         * aims to show the language name in it own language to the user on when
         * needed
         * @return The corresponding {@code String} that contains the respective
         * language name in its same language
         */
        public String getTagName()
        {
            return this.tagName;
        }
    }
    /** Enum Helper to manage the Application Mode, either expanded or mini mode
     *
     * @see visual.DlgSettings
     */
    public enum Mode
    {
        EXPANDED,
        MINI_MODE
    }

    // Singleton Pattern
    /** Sole Constructor which loads the {@code CtrlSettings} unique instance
     * following Singleton Design Pattern */
    private CtrlSettings()
    {
        availableFlatLafThemes = new ArrayList<>();
        availableIntelliJThemes = new ArrayList<>();
        timerSoundsList = new ArrayList<>();
        PREDEFINED_SOUNDS_PATHS = predefinedPathsDeterminer();
        this.PX_X2 = 32; // Defining the icons pixels size constant as x2 (x1 = 18, x2 = 36)
        this.PX_SMALL = 18;
        initSettings();
    }

    // Singleton Pattern
    private static CtrlSettings controller = null;

    /** Gets the unique instance of this class, following Singleton Design Pattern
     *
     * @return The Unique {@code CtrlSettings} instance
     */
    public static CtrlSettings getController()
    {
        if ( controller == null )
            controller = new CtrlSettings();
        return controller;
    }

    /** Initializes important Settings like a list of predefined GUI Laf themes
     * from FlatLaf and FlatLaf-IntelliJ themes to use them after, and also the
     * application icons
     */
    private void initSettings ()
    {   // Adding items to the List
        availableFlatLafThemes.add( "Flat Dark" );
        availableFlatLafThemes.add( "Flat Light" );

        availableIntelliJThemes.addAll(
                Arrays.asList(
                        "Flat IntelliJ Light",
                        "Flat IntelliJ Dark",
                        "Flat Darcula",
                        "Dracula",
                        "Arc Dark (Material)",
                        "Arc Dark Orange",
                        "Arc Orange",
                        "One Dark",
                        "Dark Purple",
                        "Carbon",
                        "Cobalt 2",
                        "Gradianto Dark Fuchsia",
                        "Gradianto Deep Ocean",
                        "Gradianto Midnight Blue",
                        "Gradianto Nature Green",
                        "Gruvbox Dark Hard",
                        "Gruvbox Dark Medium",
                        "Hiberbee Dark",
                        "Material Design Dark",
                        "Monocai",
                        "Solarized Dark",
                        "Solarized Light",
                        "Solarized Dark (Material)",
                        "Solarized Light (Material)",
                        "Atom One Dark (Material)",
                        "Atom One Light (Material)",
                        "GitHub (Material)",
                        "GitHub Dark (Material)",
                        "Material Darker (Material)",
                        "Material Deep Ocean (Material)",
                        "Material Oceanic (Material)",
                        "Material Palenight (Material)",
                        "Monokai Pro (Material)",
                        "Moonlight (Material)",
                        "Night Owl (Material)"
                ) );

        // Sorting themes
        Collections.sort( availableIntelliJThemes );

        // Icons
        settingsIcon = new FlatSVGIcon( "settings_white_18dp.svg" );
        settingsIconForSettingsForm = new FlatSVGIcon( "settings_white_18dp.svg", PX_X2, PX_X2 );
        addIcon =  new FlatSVGIcon( "add_white_18dp.svg" );
        addIconForGenerateForm = new FlatSVGIcon( "add_white_18dp.svg", PX_X2, PX_X2 );
        editIcon = new FlatSVGIcon( "edit_white_18dp.svg" );
        editIconForSearchForm = new FlatSVGIcon( "edit_white_18dp.svg", PX_X2, PX_X2 );
        deleteIcon = new FlatSVGIcon( "delete_white_18dp.svg" );
        sourceOpenIcon = new FlatSVGIcon( "source_white_18dp.svg" );
        sourceOpenIconForOpenDialog = new FlatSVGIcon( "source_white_18dp.svg", PX_X2, PX_X2 );
        saveIcon = new FlatSVGIcon( "save_white_18dp.svg" );
        saveIconForSaveDialog = new FlatSVGIcon( "save_white_18dp.svg", PX_X2, PX_X2 );
        infoIcon = new FlatSVGIcon( "info_white_18dp.svg" );
        infoIconForAboutDialog = new FlatSVGIcon( "info_white_18dp.svg", PX_X2, PX_X2 );
        powerExitIcon = new FlatSVGIcon( "settings_power_white_18dp.svg" );
        groupsAttributionIcon = new FlatSVGIcon( "groups_white_18dp.svg" );
        groupsAttributionIconForAttributionForm = new FlatSVGIcon( "groups_white_18dp.svg", PX_X2, PX_X2 );
        timerIcon = new FlatSVGIcon( "timer_FILL0_wght500_GRAD0_opsz24.svg", PX_X2, PX_X2 );
        timerMiniIcon = new FlatSVGIcon( "timer_FILL0_wght500_GRAD0_opsz24.svg", PX_SMALL, PX_SMALL );
        addTimerIcon = new FlatSVGIcon( "more_time_FILL0_wght500_GRAD0_opsz24.svg", PX_X2, PX_X2 );
        checkLapsIcon = new FlatSVGIcon( "overview_FILL0_wght500_GRAD0_opsz24.svg", PX_X2, PX_X2 );
        checkLapsMiniIcon = new FlatSVGIcon( "overview_FILL0_wght500_GRAD0_opsz24.svg", PX_SMALL, PX_SMALL );
        searchIcon = new FlatSVGIcon( "search_white_18dp.svg" );
        searchIconForSearchForm = new FlatSVGIcon( "search_white_18dp.svg", PX_X2, PX_X2 );
        fullModeIcon = new FlatSVGIcon( "open_in_full_FILL0_wght500_GRAD-25_opsz24.svg", PX_SMALL, PX_SMALL );
        miniModeIcon = new FlatSVGIcon( "close_fullscreen_FILL0_wght500_GRAD-25_opsz20.svg" );
        previousIcon = new FlatSVGIcon( "arrow_back_ios_FILL0_wght500_GRAD-25_opsz24.svg", PX_SMALL, PX_SMALL );
        nextIcon = new FlatSVGIcon( "arrow_forward_ios_FILL0_wght500_GRAD-25_opsz24.svg", PX_SMALL, PX_SMALL );
        pauseIcon = new FlatSVGIcon( "pause_FILL0_wght500_GRAD-25_opsz24.svg", PX_SMALL, PX_SMALL );
        playStartResumeIcon = new FlatSVGIcon( "play_arrow_FILL0_wght500_GRAD-25_opsz24.svg", PX_SMALL, PX_SMALL );
        stopCancelIcon = new FlatSVGIcon( "stop_FILL0_wght500_GRAD-25_opsz24.svg", PX_SMALL, PX_SMALL );
        markLapIcon = new FlatSVGIcon( "shutter_speed_FILL0_wght500_GRAD-25_opsz24.svg", PX_SMALL, PX_SMALL );
    }
    /** Gets a list with the absolute paths in the storage of the predefined 
     * sounds for the Timer
     * @return A {@code LinkedList<String>} collection with the absolute paths
     * in the local storage of the predefined Timer sounds
     * @see views.DlgTimerSoundSelecter
     */
    private LinkedList<String> predefinedPathsDeterminer()
    {
        LinkedList<String> predefinedSoundsFiles = new LinkedList<>();
        
        try
        {
            for( int i = 0; i < PREDEFINED_SOUNDS_NAMES.length; i++ )
                predefinedSoundsFiles.add( ( new File( getClass().getClassLoader().getResource( PREDEFINED_SOUNDS_NAMES[ i ] ).toURI() ) ).getAbsolutePath() );
        }
        catch ( URISyntaxException ex )
        {
            Logger.getLogger( CtrlSettings.class.getName() ).log( Level.SEVERE, null, ex );
        }
        
        return predefinedSoundsFiles;
    }
    
    /** Adds the predefined sounds paths by default to the Timer sounds list to be 
     * available
     * @param soundsList An {@code ArrayList<String>} collection that represents
     *                   the application Timer's sounds list without the 
     *                   predefined sounds names, so these can be added
     * @return An {@code ArrayList<String>} with the predefined sounds by default
     *         already added
     */
    public ArrayList<String> addPredefinedSounds( ArrayList<String> soundsList )
    {
        soundsList.addAll( PREDEFINED_SOUNDS_PATHS );        
        return soundsList;
    }
    
    /** Removes the predefined sounds paths by default from the Timer sounds list, so
     * this can be saved properly as user timer's custom sounds list, if the list
     * has more sounds than the predefined of course
     */
    public void removePredefinedSounds()
    {
        timerSoundsList.removeAll( PREDEFINED_SOUNDS_PATHS );
    }

    /** Sets the respective filtering color on svg icons to match properly with
     * every theme set by user either in app startup matching with saved theme
     * or in runtime when changing themes
     *
     * @param colorSetType the type either startup or runtime from the respective
     *                     ColorSetType Enum
     */
    public void colorOnIconsManager ( ColorSetType colorSetType )
    {
        if ( colorSetType.equals( ColorSetType.RUNTIME ) )
            FlatSVGIcon.ColorFilter.getInstance().removeAll();

        FlatSVGIcon.ColorFilter.getInstance().add( Color.WHITE, UIManager.getColor( "MenuItem.foreground" ) ); // Taking the Colors from UI MenuItem foreground for Icons and Setting the color for all FlatSVGIcons in use
        FlatSVGIcon.ColorFilter.getInstance().add( Color.BLACK, UIManager.getColor( "Button.foreground" ) ); // Taking the Colors from UI MenuItem foreground for Icons and Setting the color for all FlatSVGIcons in use
        miniModeIcon.setColorFilter( ( new FlatSVGIcon.ColorFilter() ).add( Color.BLACK, UIManager.getColor( "MenuItem.foreground" ) ) );
//        timerIcon.setColorFilter( ( new FlatSVGIcon.ColorFilter() ).add( Color.BLACK, UIManager.getColor( "Button.foreground" ) ) ); // This is necessary because for some reason Google icons now gives svg icons with a black color
//        addTimerIcon.setColorFilter( ( new FlatSVGIcon.ColorFilter() ).add( Color.BLACK, UIManager.getColor( "Button.foreground" ) ) );
//        checkLapsIcon.setColorFilter( ( new FlatSVGIcon.ColorFilter() ).add( Color.BLACK, UIManager.getColor( "Button.foreground" ) ) );
//        fullModeIcon.setColorFilter( ( new FlatSVGIcon.ColorFilter() ).add( Color.BLACK, UIManager.getColor( "Button.foreground" ) ) );
    }

    /** Fills a {@code JComboBox} object with available languages
     *
     * @param languagesCombo The {@code JComboBox} object to be populated
     */
    public void fillLanguagesCombo ( JComboBox<String> languagesCombo )
    {
        for ( SLL l : SLL.values() )
            languagesCombo.addItem( l.getTagName() );
    }

    /** Fills a given {@code JComboBox} with the available pre-set FlatLaf Themes list
     *
     * @param flatLafCombo The {@code JComboBox} to be filled with FlatLaf Themes
     */
    public void fillFlatLafCombo ( JComboBox<String> flatLafCombo )
    {
        this.availableFlatLafThemes.forEach ( flatLafCombo::addItem );
    }

    /** Fills a given {@code JComboBox} with the available pre-set FlatLaf
     * IntelliJ Themes list
     *
     * @param intelliJCombo The {@code JComboBox} to be filled with IntelliJ Themes
     */
    public void fillIntelliJCombo ( JComboBox<String> intelliJCombo )
    {
        this.availableIntelliJThemes.forEach ( intelliJCombo::addItem );
    }

    /** Sets a Look and Feel Theme on the Application
     * @param themeToSet A given {@code String} representation that contains the
     *                   theme name to set up the respective proper theme
     */
    public void themeSetter ( String themeToSet )
    {
        var theme = switch ( themeToSet )
                {
                    case "Flat Dark":
                        yield new com.formdev.flatlaf.FlatDarkLaf();
                    case "Flat Light":
                        yield new com.formdev.flatlaf.FlatLightLaf();
                    case "Flat Darcula":
                        yield new com.formdev.flatlaf.FlatDarculaLaf();
                    case "Dracula":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatDraculaIJTheme();
                    case "Flat IntelliJ Light":
                        yield new FlatLightFlatIJTheme();
                    case "Flat IntelliJ Dark":
                        yield new FlatDarkFlatIJTheme();
                    case "Arc Dark (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme();
                    case "Arc Dark Orange":
                        yield new FlatArcDarkOrangeIJTheme();
                    case "Arc Orange":
                        yield new FlatArcOrangeIJTheme();
                    case "One Dark":
                        yield new FlatOneDarkIJTheme();
                    case "Dark Purple":
                        yield new FlatDarkPurpleIJTheme();
                    case "Monocai":
                        yield new FlatMonocaiIJTheme();
                    case "Solarized Dark":
                        yield new FlatSolarizedDarkIJTheme();
                    case "Solarized Light":
                        yield new FlatSolarizedLightIJTheme();
                    case "Solarized Dark (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatSolarizedDarkIJTheme();
                    case "Solarized Light (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatSolarizedLightIJTheme();
                    case "Carbon":
                        yield new FlatCarbonIJTheme();
                    case "Cobalt 2":
                        yield new FlatCobalt2IJTheme();
                    case "Gradianto Dark Fuchsia":
                        yield new FlatGradiantoDarkFuchsiaIJTheme();
                    case "Gradianto Deep Ocean":
                        yield new FlatGradiantoDeepOceanIJTheme();
                    case "Gradianto Midnight Blue":
                        yield new FlatGradiantoMidnightBlueIJTheme();
                    case "Gradianto Nature Green":
                        yield new FlatGradiantoNatureGreenIJTheme();
                    case "Gruvbox Dark Hard":
                        yield new FlatGruvboxDarkHardIJTheme();
                    case "Gruvbox Dark Medium":
                        yield new FlatGruvboxDarkMediumIJTheme();
                    case "Hiberbee Dark":
                        yield new FlatHiberbeeDarkIJTheme();
                    case "Atom One Dark (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme();
                    case "Atom One Light (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme();
                    case "GitHub (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme();
                    case "GitHub Dark (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme();
                    case "Material Darker (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme();
                    case "Material Design Dark":
                        yield new FlatMaterialDesignDarkIJTheme();
                    case "Material Deep Ocean (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme();
                    case "Material Oceanic (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialOceanicIJTheme();
                    case "Material Palenight (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialPalenightIJTheme();
                    case "Monokai Pro (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMonokaiProIJTheme();
                    case "Moonlight (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightIJTheme();
                    case "Night Owl (Material)":
                        yield new com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlIJTheme();
                    default:
                        yield null;
                };

        try
        {
            UIManager.setLookAndFeel( theme ); // Applying the selected theme itself
        }
        catch ( UnsupportedLookAndFeelException ex )
        {
            Logger.getLogger( CtrlSettings.class.getName() ).log( Level.SEVERE, null, ex );
            JOptionPane.showMessageDialog( null, "Error on theme setting: \n" + ex.getMessage(), "Laf UI Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    /** Manages the set of a Look and Feel Theme on the Application with a given
     * Look and Feel {@code String} that contains its name, following 3 needed
     * steps.
     * <p>
     * The first step is to call the method {@code themeSetter}, which is the
     * one in charge to apply the theme sending the Laf name to it as parameter.
     * <p>
     * The second step is to call the {@code FlatLaf.updateUI} method to update
     * all the current Application UI tree.
     * <p>
     * And the third step is to update properly the {@code JFileChooser} used to
     * open and save Smallville Clock files by calling the method in charge, this
     * needs to be done independently due to the {@code JFIleChooser} nature.
     * <p>
     * So once these 3 steps are done with no error, only then and not before,
     * it is possible to conclude that the laf theme has been successfully
     * applied.
     *
     * @param currentGuiLafTheme The {@code String} representation that contains
     *                           a Look and Feel theme name
     */
    public void applyThemeManager ( String currentGuiLafTheme )
    {
        themeSetter( currentGuiLafTheme ); // Applying Theme
        FlatLaf.updateUI(); // Visual Components Update-Refreshing
        FilesManager.updateSaveOpenDlg();
    }

    /** Gets the application back to the theme saved in user settings, this occurs
     * if user applies a certain laf theme different to the actual saved theme
     * in user settings, but decides to click on cancel button instead ok
     * button, so this method reverses that operation
     *
     * @param themeInProperties The actual theme saved in user settings {@code Properties}
     *                          object
     */
    public void gettingBackToSavedTheme ( String themeInProperties ) // Method when the user cancel, so he doesn't actually to apply the theme, the operation is reverted and the saved theme in user properties y applied back.
    {
        applyThemeManager( themeInProperties ); // Making the Call to apply the theme
        colorOnIconsManager(ColorSetType.RUNTIME );
    }

    /** Loads the language saved in user settings properties file and sets it as
     * the current application language with that given specified saved locale,
     * this method is mostly used when the application starts to automatically
     * apply the right language, either the one by default or the one specified
     * by the user
     *
     * @param strSavedLocale A locale saved in the properties file to set and
     *                       apply the application language
     */
    public void loadLanguageFromPreferences ( String strSavedLocale )
    {
        setAppLanguage( strSavedLocale.startsWith( "en" ) ? "English" : "Español" );
        applyLanguageInApp();
    }

    /** Applies and makes effective the already set language as the
     * application language to this current Java Virtual Machine as the default
     * locale, making every window to open with the corresponding language, also
     * sets the corresponding proper {@code ResourceBundle} object to make all
     * app windows, texts and strings manually coded match with the new language
     * and finally, it calls the proper method in the main window to update
     * its G.U.I. text components to the current language.
     * <p>
     * This is because when a language is set, it is set to the respective
     * attribute, but it is not made effective, this means the application
     * does not show the new language yet, but this method makes the language
     * effective and actually applies it to the application
     */
    public void applyLanguageInApp ()
    {
        FrmSmallvilleClockMain mainWindow = CommonUtils.getMainFrame();
        mainWindow.setLocaleInApp();

        languageBundle = switch ( appLanguage )
                {
                    case SPANISH -> ResourceBundle.getBundle( "i18n/i18nBundle_es" );
                    case ENGLISH -> ResourceBundle.getBundle( "i18n/i18nBundle_en" );
                };

        if( mainWindow.isVisible() )
            mainWindow.updateComponentsLanguage();
    }
    
    /** Gets the Timer's sound name with a given sound file name or sound path, 
     * if a path, it removes the extra path part and the extension, if it is a 
     * sound file name then it only removes the extension, so it always returns
     * only the pure sound name with no path part and with no extension part
     * 
     * @param soundNamePath A given {@code String} that contains either a timer's
     * sound name available by default or a user's custom defined sound name's 
     * absolute path
     * 
     * @return A {@code String} that represents no more than the Timer's sound 
     * name itself
     */
    public String soundNamePreparer( String soundNamePath )
    {
        int indexToStartFrom = 0, indexToEnd = soundNamePath.length();
        
        if ( soundNamePath.contains( FilesManager.getController().getDirSeparator() ) )
            indexToStartFrom = soundNamePath.lastIndexOf( FilesManager.getController().getDirSeparator() ) + 1;

        if ( soundNamePath.contains( "." ) )
            indexToEnd = soundNamePath.lastIndexOf( '.' );
        
        return soundNamePath.substring( indexToStartFrom, indexToEnd );
    }
    /** Gets a list of the Timer sounds ready to show to the user by only have
     * the sounds name and not the entire location path
     * @param listToShow An {@code ArrayList<String>} Timer sounds list with 
     *                   their absolute location paths
     * @return An {@code ArrayList<String>} collection with the Timer sounds list 
     *         only with their names
     */
    public ArrayList<String> listToShowPreparer( ArrayList<String> listToShow )
    {
        LinkedList<String> readyList = new LinkedList<>();
        
        listToShow.forEach( sound ->
        {
            readyList.add( soundNamePreparer( sound ) );
        } );
        
        Collections.sort( readyList );
        listToShow.clear();
        listToShow.addAll( readyList );
        
        return listToShow;
    }
    /** Compares a given sound name with all those in the collection, when
     * it finds it, sets it as the current Timer sound
     * @param selectedSoundName A given sound name to search for
     */
    public void timerSoundFullNameSetter( String selectedSoundName )
    {
        for( String sound : timerSoundsList )
        {
            if( soundNamePreparer( sound ).equalsIgnoreCase( selectedSoundName ) )
            {
                setTimerSound( sound );
                break;
            }
        }
    }

    // Getters and Setters
    /** Gets a {@code String} with the application version using constants
     * @return This Application "Smallville Clock" actual version
     */
    public static String getAPP_VERSION ()
    {
        return VERSION_MAJOR + "." + VERSION_MINOR;
    }

    /** Gets the Java platform version this application is currently working with
     *
     * @return A {@code String} representation of the Java platform version this
     *         application is currently working with
     */
    public static String getPlatform_VERSION ()
    {
        return "Java " + System.getProperty( "java.version" ) + "; OpenJDK 64-Bit Server VM 17.0.2+8-86";
    }

    /** Gets this application language
     * @return This application {@code SLL enum} constant that represents the current
     * language value applied in the application
     */
    public static SLL getAppLanguage ()
    {
        return appLanguage;
    }

    /** Sets the application language with a given language tag name
     *
     * @param language The {@code String} language tag name representation to 
     * set the application language up
     * 
     * @see SLL
     */
    public static void setAppLanguage ( String language )
    {        
        for ( SLL ln : SLL.values() )
        {
            if( ln.getTagName().equalsIgnoreCase( language ) )
            {
                CtrlSettings.appLanguage = ln;
                break;
            }
        }
    }

    /** Gets the application language bundle
     * @return This Application proper Language {@code ResourceBundle} object
     * depending on the current locale and language set
     */
    public static ResourceBundle getLanguageBundle ()
    {
        return languageBundle;
    }

    /** Gets the current applied graphical user interface look and feel theme
     * @return A {@code String} that contains the Current Applied Gui Laf Theme
     * representation
     */
    public String getCurrentAppliedGuiLafTheme ()
    {
        return currentAppliedGuiLafTheme;
    }

    /** Sets the current applied graphical user interface look and feel theme
     * @param currentAppliedGuiLafTheme A {@code String} representation of the
     *                                  Current Applied Gui Laf Theme
     */
    public void setCurrentAppliedGuiLafTheme ( String currentAppliedGuiLafTheme )
    {
        this.currentAppliedGuiLafTheme = currentAppliedGuiLafTheme;
    }

    /** Gets the current selected graphical user interface look and feel theme
     * @return A {@code String} that contains the current selected graphical
     * user interface look and feel theme representation
     */
    public String getCurrentSelectedGuiLafTheme ()
    {
        return currentSelectedGuiLafTheme;
    }

    /** Sets the current selected graphical user interface look and feel theme
     * @param currentSelectedGuiLafTheme A {@code String} representation of the
     *                                   Current Selected Gui Laf Theme
     */
    public void setCurrentSelectedGuiLafTheme ( String currentSelectedGuiLafTheme )
    {
        this.currentSelectedGuiLafTheme = currentSelectedGuiLafTheme;
    }

    /** Gets the corresponding state that indicates if there is a theme that is pending
     * to apply and do something in consequence
     * @return {@code true} if there is a theme pending to apply, {@code false} otherwise
     */
    public boolean isApplyingThemePending ()
    {
        return applyingThemePending;
    }

    /** Sets the applying theme pending indicator
     * @param applyingThemePending  A {@code boolean} representation that must indicate
     *                              {@code true} if a theme is pending to apply,
     *                              {@code false} otherwise
     */
    public void setApplyingThemePending ( boolean applyingThemePending )
    {
        this.applyingThemePending = applyingThemePending;
    }

    /** Gets the application starting folder value
     * @return A {@code String} representation that contains the application
     * starting folder set in settings or the value by default
     */
    public String getStartingFolder ()
    {
        return startingFolder;
    }

    /** Sets the application starting folder value
     * @param startingFolder A {@code String} representation that contains the
     *                       application starting folder value
     */
    public void setStartingFolder ( String startingFolder )
    {
        this.startingFolder = startingFolder;
    }

    /** Gets the type to save Smallville Clock Stopwatch Laps files
     * @return The {@code FileType} enum constant that indicates the file type
     * format specified in settings that shall be used to save Lap Stopwatch
     * files
     */
    public CommonUtils.FileType getFileTypeToSave ()
    {
        return fileTypeToSave;
    }

    /** Sets the file type setting property the application has to use to save
     * files, this is made in a custom way accepting a {@code String} as file
     * type parameter and converting it to the respective enum constant that is
     * actually the one used as file type attribute in the application
     *
     * @param fileType A {@code String} representation that contains the file
     *                 type to set
     *
     * @see data.FilesManager
     */
    public void setFileTypeToSave ( String fileType )
    {
        this.fileTypeToSave = CommonUtils.FileType.valueOf( fileType.toUpperCase() );
    }
    /** Gets the application mode, either expanded or mini mode
     * @return The {@code Mode} enum constant that indicates the current application mode
     */
    public static Mode getAppMode ()
    {
        return appMode;
    }
    /** Sets the application mode, either expanded or mini mode
     * @param appMode A {@code Mode} constant that indicates the mode to set
     */
    public static void setAppMode ( Mode appMode )
    {
        CtrlSettings.appMode = appMode;
    }
    /** Gets the application Timer's sound
     * @return A {@code String} with the application Timer's sound name or location 
     * absolute path
     */
    public String getTimerSound ()
    {
        return timerSound;
    }
    /** Sets the application Timer's sound
     * @param timerSound A {@code String} with the application Timer's sound name or location 
     * absolute path
     */
    public void setTimerSound ( String timerSound )
    {
        this.timerSound = timerSound;
    }
    /** Gets the application app on top setting
     * @return {@code true} if this feature has been activated by the user, 
     * {@code false} otherwise
     */
    public boolean isAppOnTop ()
    {
        return appOnTop;
    }
    /** Sets the application app on top setting
     * @param appOnTop {@code true} if this feature has been activated by the 
     * user, false otherwise
     */
    public void setAppOnTop ( boolean appOnTop )
    {
        this.appOnTop = appOnTop;
    }
    /** Gets the application user Timer's sounds list
     * @return A {@code ArrayList<String>} collection with the user Timer's 
     * sounds list
     */
    public ArrayList<String> getTimerSoundsList ()
    {
        return timerSoundsList;
    }
    /** Sets the application user Timer's sounds list
     * @param timerSoundsList A {@code ArrayList<String>} collections that 
     * contains the application user Timer's sounds list
     */
    public void setTimerSoundsList ( ArrayList<String> timerSoundsList )
    {
        this.timerSoundsList = timerSoundsList;
    }

    /** Gets the application predefined Timer's sounds paths list
     * @return A {@code LinkedList<String>} constants collection with the application predefined
     * Timer's sounds paths list
     */
    public LinkedList<String> getPREDEFINED_SOUNDS_PATHS ()
    {
        return PREDEFINED_SOUNDS_PATHS;
    }
    /** Gets the World Clock 24 hours format setting
     * @return {@code true} if the 24 hours format has been selected for the 
     *         World Clock, {@code false} otherwise
     */
    public boolean isFormat24Hours ()
    {
        return format24Hours;
    }
    /** Sets the World Clock 24 hours format setting
     * @param format24Hours It must be {@code true} if the 24 hours format has 
     *                      been selected for the World Clock, {@code false} 
     *                      otherwise
     */
    public void setFormat24Hours ( boolean format24Hours )
    {
        this.format24Hours = format24Hours;
    }
    /** Gets the include seconds indicator for World Clock
     * @return {@code true} if seconds are included in World Clocks, 
     *         {@code false} otherwise
     */
    public boolean isIncludeSeconds ()
    {
        return includeSeconds;
    }
    /** Sets the include seconds indicator for World Clock
     * @param includeSeconds {@code true} if seconds must be included in World 
     *                       Clocks, {@code false} otherwise
     */
    public void setIncludeSeconds ( boolean includeSeconds )
    {
        this.includeSeconds = includeSeconds;
    }


    // FlatSVGIcon Icons Getters
    
    /** Gets the icon choosed to represent the action "settings" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getSettingsIcon()
    {
        return settingsIcon;
    }

    /** Gets the icon choosed to represent the action "settings" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon and doubled
     * size
     */
    public FlatSVGIcon getSettingsIconForSettingsForm()
    {
        return settingsIconForSettingsForm;
    }

    /** Gets the icon choosed to represent the action "save" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getSaveIcon ()
    {
        return saveIcon;
    }
    
    /** Gets the icon choosed to represent the action "add" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getAddIcon ()
    {
        return addIcon;
    }
    /** Gets the icon choosed to represent the action "delete" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getDeleteIcon ()
    {
        return deleteIcon;
    }
    /** Gets the icon choosed to represent the action "edit" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getEditIcon ()
    {
        return editIcon;
    }
    /** Gets the icon choosed to represent the action "edit" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon and doubled
     * size
     */
    public FlatSVGIcon getEditIconForSearchForm ()
    {
        return editIconForSearchForm;
    }
    /** Gets the icon choosed to represent the action "attribution" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getGroupsAttributionIcon ()
    {
        return groupsAttributionIcon;
    }
    /** Gets the icon choosed to represent the action "about" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getInfoIcon ()
    {
        return infoIcon;
    }
    
    /** Gets the icon choosed to represent the action "open" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getSourceOpenIcon ()
    {
        return sourceOpenIcon;
    }
    /** Gets the icon choosed to represent the action "add" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon and doubled
     * size
     */
    public FlatSVGIcon getAddIconForGenerateForm ()
    {
        return addIconForGenerateForm;
    }
    /** Gets the icon choosed to represent the action "open" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon and doubled
     * size
     */
    public FlatSVGIcon getSourceOpenIconForOpenDialog ()
    {
        return sourceOpenIconForOpenDialog;
    }
    /** Gets the icon choosed to represent the action "save" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon and doubled
     * size
     */
    public FlatSVGIcon getSaveIconForSaveDialog ()
    {
        return saveIconForSaveDialog;
    }
    
    /** Gets the icon choosed to represent the action "attribution" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon and doubled
     * size
     */
    public FlatSVGIcon getGroupsAttributionIconForAttributionForm ()
    {
        return groupsAttributionIconForAttributionForm;
    }
    /** Gets the icon choosed to represent the action "about" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon and doubled
     * size
     */
    public FlatSVGIcon getInfoIconForAboutDialog ()
    {
        return infoIconForAboutDialog;
    }
    
    /** Gets the icon choosed to represent the action "close" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getPowerExitIcon ()
    {
        return powerExitIcon;
    }
    
    /** Gets the icon choosed to represent the action "timer" in {@code PX_2X}, 
     * double of normal size for set timer button
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getTimerIcon ()
    {
        return timerIcon;
    }
    
    /** Gets the icon choosed to represent the action "addTimerIcon" in {@code PX_2X}, 
     * double of normal size for add timer button
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getAddTimerIcon ()
    {
        return addTimerIcon;
    }
    
    /** Gets the icon choosed to represent the action "checkLapsIcon" in {@code PX_2X}, 
     * double of normal size for add timer button
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getCheckLapsIcon ()
    {
        return checkLapsIcon;
    }
    
    /** Gets the icon choosed to represent the action "search" in normal size used
     * commonly in menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getSearchIcon ()
    {
        return searchIcon;
    }
    /** Gets the icon choosed to represent the action "search" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getSearchIconForSearchForm ()
    {
        return searchIconForSearchForm;
    }
    /** Gets the icon choosed to represent the action "full mode" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getFullModeIcon ()
    {
        return fullModeIcon;
    }
    /** Gets the icon choosed to represent the action "mini mode" in 
     * normal size for use on menus
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getMiniModeIcon ()
    {
        return miniModeIcon;
    }
    /** Gets the icon choosed to represent the action "previous" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getPreviousIcon ()
    {
        return previousIcon;
    }
    /** Gets the icon choosed to represent the action "next" in double of 
     * normal size for use on windows title bars and buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getNextIcon ()
    {
        return nextIcon;
    }
    /** Gets the icon choosed to represent the action "next" in 
     * normal size for use on Mini Mode buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getPauseIcon ()
    {
        return pauseIcon;
    }
    /** Gets the icon choosed to represent the action "next" in in 
     * normal size for use on Mini Mode buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getPlayStartResumeIcon ()
    {
        return playStartResumeIcon;
    }
    /** Gets the icon choosed to represent the action "next" in 
     * normal size for use on Mini Mode buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getStopCancelIcon ()
    {
        return stopCancelIcon;
    }
    /** Gets the icon choosed to represent the action "check laps" in 
     * small size for use on Mini Mode buttons
     * @return A {@code FlatSVGIcon} icon object with the proper icon
     */
    public FlatSVGIcon getCheckLapsMiniIcon ()
    {
        return checkLapsMiniIcon;
    }
    
    public FlatSVGIcon getTimerMiniIcon ()
    {
        return timerMiniIcon;
    }
    
    public FlatSVGIcon getMarkLapIcon ()
    {
        return markLapIcon;
    }

}