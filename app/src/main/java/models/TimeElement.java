package models;

/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */
import controllers.CommonUtils.ChronoType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Defines basic attributes of a time element, such as a clock, timer or stopwatch
 * @author Clark - ClarkCodes
 * @since 1.0
 * */
public abstract class TimeElement implements Serializable
{   // id structure: current dateTime( 2 digits each ) + ChronoType < yearMonthDayHourMinuteSecondMillisecondType >
    // where Type is the name of a ChronoType World Clock, Timer Time or Lap
    protected String id;
    protected ChronoType type;

    /** Abstract Constructor
     * @param type A {@code CommonUtils.ChronoType} enum constant that indicates
     * the element type
     */
    public TimeElement( ChronoType type )
    {
        this.type = type;
        idSetter();
    }
    
    /** Sets the id in a Time Element class object
     */
    private void idSetter()
    {
        String nowDateTime = LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyMMdd_HH:mm:ss.SSS_" ) );
        this.id = switch ( type )
        {
            case WORLD_CLOCK -> nowDateTime + ChronoType.WORLD_CLOCK.name();
            case TIMER_TIME -> nowDateTime + ChronoType.TIMER_TIME.name();
            case STOPWATCH -> nowDateTime + ChronoType.STOPWATCH.name();
            case LAP -> nowDateTime + ChronoType.LAP.name();
            case TIMER_OVER_TIME -> nowDateTime + ChronoType.TIMER_OVER_TIME.name();
        };
    }
}