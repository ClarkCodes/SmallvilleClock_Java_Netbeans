package models;

import controllers.CommonUtils;
import controllers.CtrlSettings;
import controllers.CtrlSmallvilleClock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;

/** Defines a WorldClock class with the posibility to adjust its {@code ZoneId} to
 * show a properly clock time based on its zone time
 * @author Clark - ClarkCodes
 * @since 1.0
 */
public class WorldClock extends TimeElement implements Serializable
{
    private final CtrlSettings ctrlSettings;
    private final javax.swing.Timer clockTicker;
    private final String regionZone;
    private Clock clock;
    private LocalDate date;
    private DateTimeFormatter timeFormatter, dateFormatter;
    private JLabel clockLabel, dateLabel, zoneLabel;
    private boolean started = false, miniModeOnFront = false;
    
    /** Creates a new instance of {@code WorldClock}
     * @param zone       A {@code ZoneId} object to show the clock properly adjusting to that zone time
     * @param clockLabel A {@code JLabel} where the clock information is going to be shown
     * @param dateLabel  A {@code JLabel} where the date information is going to be shown
     * @param zoneLabel  A {@code JLabel} where the zone id information is going to be shown
     */
    public WorldClock ( ZoneId zone )
    {
        super( CommonUtils.ChronoType.WORLD_CLOCK );
        this.regionZone = zone.getId();
        this.clock = Clock.system( zone );
        this.date = LocalDate.now( clock );
        ctrlSettings = CtrlSettings.getController();
        formatterDeterminer();
        
        ActionListener timeUpdater = new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent evt) 
            {
                if ( CtrlSettings.getAppMode() == CtrlSettings.Mode.EXPANDED || miniModeOnFront )
                {
                    clockLabel.setText( LocalTime.now( clock ).format( timeFormatter ) );

                    if ( LocalDate.now( clock ) != date )
                    {
                        setDate();
                        date = LocalDate.now( clock );
                    }
                }
            }
        };
        
        this.clockTicker = new javax.swing.Timer( CommonUtils.getMILLIS_IN_SECOND(), timeUpdater );
    }
    /** Determinates and sets the proper time format to show it as set in Settings
     */
    public final void formatterDeterminer()
    {   
        if ( CtrlSettings.getAppMode().equals( CtrlSettings.Mode.MINI_MODE ) )
        {
            timeFormatter = CommonUtils.getTimeFormat( CommonUtils.TypeTimeFormat.HOURS_MINUTES_24 );
        }
        else if ( ctrlSettings.isFormat24Hours() )
        {
            timeFormatter = ctrlSettings.isIncludeSeconds() ? 
                    CommonUtils.getTimeFormat( CommonUtils.TypeTimeFormat.HOURS_MINUTES_SECONDS_24 ) :
                    CommonUtils.getTimeFormat( CommonUtils.TypeTimeFormat.HOURS_MINUTES_24 );
        }
        else
            timeFormatter = ctrlSettings.isIncludeSeconds() ? 
                    CommonUtils.getTimeFormat( CommonUtils.TypeTimeFormat.HOURS_MINUTES_SECONDS ) :
                    CommonUtils.getTimeFormat( CommonUtils.TypeTimeFormat.HOURS_MINUTES );
        
        if ( CtrlSettings.getAppMode().equals( CtrlSettings.Mode.MINI_MODE ) )
        {
            dateFormatter = CommonUtils.getDateFormat( CommonUtils.TypeDateFormat.SHORT_DATE );
        }
        else if ( CtrlSmallvilleClock.getController().getWorldClocks().isEmpty() )
        {
            dateFormatter = CommonUtils.getDateFormat( CommonUtils.TypeDateFormat.LONG_DATE );
        }
        else
            dateFormatter = CommonUtils.getDateFormat( CommonUtils.TypeDateFormat.SHORT_DATE );
    }
    /** Gets the {@code ZoneId} this Clock was set with
     * @return A {@code String} with the Zone Id in text format that belongs to
     *         this Clock
     */
    public String getRegionZone ()
    {
        return regionZone;
    }
    /** Gets the Mini Mode On Front state when in Mini Mode
     * @return {@code true} if this World Clock is currently showing on front, 
     *         this is on the World Clock labels of the Mini Mode Window, 
     *         {@code false} otherwise
     */
    public boolean isMiniModeOnFront ()
    {
        return miniModeOnFront;
    }
    /** Sets the Mini Mode On Front state when in Mini Mode
     * @param miniModeOnFront Should be {@code true} if this World Clock is currently 
     *                        showing on front, this is on the World Clock 
     *                        labels of the Mini Mode Window, {@code false} 
     *                        otherwise
     */
    public void setMiniModeOnFront ( boolean miniModeOnFront )
    {
        this.miniModeOnFront = miniModeOnFront;
    }
    /** Gets the started state
     * @return {@code true} if this World Clock has been started, {@code false} otherwise
     */
    public boolean isStarted ()
    {
        return started;
    }
    /** Sets the date, useful to update it when the date changes due to turning day
     */
    private void setDate()
    {
        String date = LocalDate.now( clock ).format( dateFormatter );
        date =  Character.toUpperCase( date.charAt( 0 ) ) + date.substring( 1 );
        dateLabel.setText( date );
    }
    /** Starts this World Clock to tick, syncronizing it on the system clock 
     * second pulse
     */
    public void start()
    {
        int secondReference = LocalTime.now().getSecond();
        while ( LocalTime.now().getSecond() == secondReference ) {} // Syncronizing with the system clock on the second value
        this.clockTicker.start();
        this.started = true;
    }
    /** Stops this World Clock
     */
    public void stop()
    {
        this.clockTicker.stop();
    }
    
    // Getters and Setters
    /** Sets the Clock label
     * @param clockLabel A {@code JLabel} object where the clock information is
     *                   going to be shown
     */
    public void setClockLabel ( JLabel clockLabel )
    {
        this.clockLabel = clockLabel;
    }
    /** Sets the Date label
     * @param dateLabel A {@code JLabel} object where the date information is
     *                  going to be shown
     */
    public void setDateLabel ( JLabel dateLabel )
    {
        this.dateLabel = dateLabel;
    }
    /** Sets the Clock label
     * @param zoneLabel A {@code JLabel} object where the zone id information is
     *                  going to be shown
     */
    public void setZoneLabel ( JLabel zoneLabel )
    {
        this.zoneLabel = zoneLabel;
        this.zoneLabel.setText( CtrlSmallvilleClock.getController().zoneIdNamePreparer( regionZone ) );
    }
    /** Gets this current World Clock Time, useful when changing clocks in Mini Mode
     * @return A {@code String} with this current World Clock Time already formatted
     */
    public String getCurrentClockTime()
    {
        return LocalTime.now( clock ).format( timeFormatter );
    }
}
