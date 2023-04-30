package models;
/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

import controllers.CommonUtils;
import controllers.CommonUtils.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import javax.swing.JLabel;
import views.DlgTimeUp;

/** Defines the Timer clock, allowing to set a period of time and a name, when 
 * started it ticks backward until it reaches 0 and then it shows an alarm
 * @author Clark - ClarkCodes
 * @see controllers.CtrlSmallvilleClock
 * @see views.DlgSetTimer
 * @see data.FilesManager
 * @since 1.0
 * */
public class Timer extends TimeElement implements ITimeControlable
{
    private Duration timeSet = Duration.ZERO, tickingTime = Duration.ZERO;
    private String name;
    private State state;
    private final javax.swing.Timer timerTicker;
    private JLabel timerLabel;
    
    /** Constructor - Creates a new Timer object
     * @param time A given {@code Duration} object to set as the Timer time 
     * @param name A given {@code String} with the Timer's name
     * @see controllers.CtrlSmallvilleClock
     */
    public Timer( Duration time, String name )
    {
        super( CommonUtils.ChronoType.TIMER_TIME );
        this.timeSet = time;
        this.name = name;
        this.state = State.STOPPED;

        ActionListener timeUpdater = new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent evt) 
            {
                if( !tickingTime.isZero() )
                {
                    tickingTime = tickingTime.minusSeconds( Long.valueOf( 1 ) ); // Adding a second value
                    timerLabel.setText( CommonUtils.getFormattedDuration( tickingTime, CommonUtils.ChronoType.TIMER_TIME ) );
                }
                else
                {
                    timerTicker.stop();
                    DlgTimeUp timeUpWindow = new DlgTimeUp( null, true, getName() );
                    timeUpWindow.setVisible( true );
                }
            }
        };
        
        this.timerTicker = new javax.swing.Timer( CommonUtils.getMILLIS_IN_SECOND(), timeUpdater );
    }

    /** Sets the Timer label which updates and shows the Timer time on it
     * @param timerLabel A {@code JLabel} object to show the time on it
     * @see views.FrmSmallvilleClockMain
     */
    public void setTimerLabel( JLabel timerLabel )
    {
        this.timerLabel = timerLabel;
    }
    /** Gets the Timer time set
     * @return A {@code Duration} object with the Timer time set
     */
    public Duration getTimeSet()
    {
        return timeSet;
    }
    /** Sets the Timer time
     * @param timeSet A given {@code Duration} object to set the Timer time
     */
    public void setTimeSet( Duration timeSet )
    {
        this.timeSet = timeSet;
        this.tickingTime = timeSet;
    }
    /** Gets the Timer ticking time
     * @return A {@code Duration} object with the Timer ticking time
     */
    public Duration getTickingTime ()
    {
        return tickingTime;
    }
    /** Gets the Timer name
     * @return A {@code String} object with the Timer name
     */
    public String getName()
    {
        return name;
    }
    /** Sets the Timer name
     * @param name A {@code String} object with the Timer name
     */
    public void setName( String name )
    {
        this.name = name;
    }
    /** Gets the Timer state
     * @return A {@code CommonUtils.State} enum constant indicator describing
     * the current Timer state
     */
    public State getState ()
    {
        return state;
    }
    /** Sets the Timer state
     * @param state A {@code CommonUtils.State} enum constant indicator describing
     * the current Timer state
     */
    private void setState ( State state )
    {
        this.state = state;
    }
    /** Starts the Timer and updates its state
     * @see models.ITimeControlable
     */
    @Override
    public void start ()
    {
        this.timerTicker.start();
        setState( State.RUNNING );
    }
    /** Stops the Timer and updates its state
     * @see models.ITimeControlable
     */
    @Override
    public void stop ()
    {
        this.timerTicker.stop();
        setState( State.PAUSED );
    }
    /** Resets the Timer, stopping it and returning its ticking time to its 
     * time set, it also updates its state and the Timer's {@code JLabel}
     * @see models.ITimeControlable
     */
    @Override
    public void reset ()
    {
        this.timerTicker.stop();
        this.tickingTime = this.timeSet;
        timerLabel.setText( CommonUtils.getFormattedDuration( tickingTime, CommonUtils.ChronoType.TIMER_TIME ) );
        setState( State.STOPPED );
    }

}