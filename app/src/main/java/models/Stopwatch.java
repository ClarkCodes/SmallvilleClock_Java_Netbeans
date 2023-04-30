package models;

import controllers.CommonUtils;
import controllers.CommonUtils.State;
import controllers.CtrlSettings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

public class Stopwatch extends TimeElement implements ITimeControlable
{
    private Duration stopwatchTime, lapTime;
    private State state, lapState;
    private final long STOPWATCH_TIME_LIMIT = 3599000L;
    private final javax.swing.Timer stopwatchTicker, lapTicker;
    private JLabel stopwatchLabel, lapLabel;
    private boolean limitReached = false;
    
    /** Constructor - Creates a new Stopwatch object
     * @see controllers.CtrlSmallvilleClock
     */
    public Stopwatch()
    {
        super( CommonUtils.ChronoType.STOPWATCH );
        this.state = State.STOPPED;
        this.lapState = State.STOPPED;
        this.stopwatchTime = Duration.ZERO;
        this.lapTime = Duration.ZERO;
        
        // Stopwatch Time ActionListener and Ticket Assignment
        ActionListener stopwatchTimeUpdater = new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent evt) 
            {
                if( stopwatchTime.toMillis() < STOPWATCH_TIME_LIMIT )
                {
                    stopwatchTime = stopwatchTime.plusMillis( Long.valueOf( CommonUtils.getHUNDRETHS_IN_SECOND() ) ); // This is 100 because I need a thenth of a second and that is going to be the unit if the stopwatch
                    stopwatchLabel.setText( CommonUtils.getFormattedDuration( stopwatchTime, CommonUtils.ChronoType.STOPWATCH ) );
                }
                else
                {
                    stop();
                    limitReached = true;
                    JOptionPane.showMessageDialog( null, 
                            CtrlSettings.getLanguageBundle().getString( "key_stopwatch_limit_reached_msj" ), 
                            CtrlSettings.getLanguageBundle().getString( "key_stopwatch_limit_reached_title" ), 
                            JOptionPane.WARNING_MESSAGE );
                    CommonUtils.getMainFrame().stopwatchEnablingVerifier();
                }
            }
        };
        
        this.stopwatchTicker = new javax.swing.Timer( CommonUtils.getHUNDRETHS_IN_SECOND(), stopwatchTimeUpdater );
        
        // Lap Time ActionListener and Ticket Assignment
        ActionListener lapTimeUpdater = new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent evt) 
            {
                if( lapTime.toMillis() < STOPWATCH_TIME_LIMIT )
                {
                    lapTime = lapTime.plusMillis( Long.valueOf( CommonUtils.getHUNDRETHS_IN_SECOND() ) ); // This is 100 because I need a thenth of a second and that is going to be the unit if the stopwatch
                    lapLabel.setText( CommonUtils.getFormattedDuration( lapTime, CommonUtils.ChronoType.STOPWATCH ) );
                }
                else
                {
                    lapTicker.stop();
                }
            }
        };
        
        this.lapTicker = new javax.swing.Timer( CommonUtils.getHUNDRETHS_IN_SECOND(), lapTimeUpdater );
    }

    /** Executes necesary steps when a lap is marked, which are return the 
     * lap time to zero and restart the lap ticker
     */
    public void lap()
    {
        lapTime = Duration.ZERO;
        lapTicker.restart();
    }
    /** Starts the Lap Ticker for the first time, it should be invoked when the
     * first lap is marked
     */
    public void startLapTickerFirstTime()
    {
        if ( lapState == State.STOPPED )
        {
            this.lapTicker.start();
            lapState = State.RUNNING;
        }
    }
    
    /** Gets the limit reached state
     * @return {@code true} only if the Stopwatch time limit specified by its 
     *         constant has been reached, {@code false} otherwise
     */
    public boolean isLimitReached ()
    {
        return limitReached;
    }
    /** Gets the Stopwatch state
     * @return A {@code CommonUtils.State} enum constant indicator describing
     * the current Stopwatch state
     */
    public State getState ()
    {
        return state;
    }
    /** Sets the Stopwatch state
     * @param state A {@code CommonUtils.State} enum constant indicator describing
     * the current Timer state
     */
    public void setState ( State state )
    {
        this.state = state;
    }
    /** Gets the Lap Time
     * @return A {@code Duration} object that represents the Lap Time
     */
    public Duration getLapTime ()
    {
        return lapTime;
    }
    /** Gets the Stopwatch Time
     * @return A {@code Duration} object that represents the Stopwatch Time
     */
    public Duration getStopwatchTime ()
    {
        return stopwatchTime;
    }
    /** Sets the Stopwatch label and the lap label which updates and shows the 
     * Timer time and lap time on them
     * @param stopwatchLabel A {@code JLabel} object to show the Stopwatch time on it
     * @param lapLabel       A {@code JLabel} object to show the Lap time on it
     * @see views.FrmSmallvilleClockMain
     */
    public void setStopwatchLapLabel ( JLabel stopwatchLabel, JLabel lapLabel )
    {
        this.stopwatchLabel = stopwatchLabel;
        this.lapLabel = lapLabel;
    }
    
    /** Starts the Stopwatch and updates its state
     * @see models.ITimeControlable
     */
    @Override
    public void start ()
    {
        this.stopwatchTicker.start();
        setState( State.RUNNING );
        
        if ( lapState != State.STOPPED )
        {
            this.lapTicker.start();
            lapState = State.RUNNING;
        }
    }
    /** Stops the Stopwatch and updates its state
     * @see models.ITimeControlable
     */
    @Override
    public void stop ()
    {
        this.stopwatchTicker.stop();
        setState( State.PAUSED );
        
        if ( lapState == State.RUNNING )
        {
            this.lapTicker.stop();
            lapState = State.PAUSED;
        }
    }
    /** Resets the Stopwatch, stopping it and returning its ticking time to zero, 
     * it also updates its state and the Stopwatch's {@code JLabel}
     * @see models.ITimeControlable
     */
    @Override
    public void reset ()
    {
        this.stopwatchTicker.stop();
        this.stopwatchTime = Duration.ZERO;
        stopwatchLabel.setText( CommonUtils.getFormattedDuration( Duration.ZERO, CommonUtils.ChronoType.STOPWATCH ) );
        setState( State.STOPPED );

        if ( lapState != State.STOPPED )
        {
            this.lapTicker.stop();
            this.lapTime = Duration.ZERO;
            lapLabel.setText( "" );
            lapState = State.STOPPED;
        }
        
        if ( limitReached )
            limitReached = false;
    }
}