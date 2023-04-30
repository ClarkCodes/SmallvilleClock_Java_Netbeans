package models;
/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */
import controllers.CommonUtils;
import java.io.Serializable;
import java.time.Duration;


/** Represents a lap mark with a specific global time and lap time in the stopwatch mode
 * @author Clark - ClarkCodes
 * @since 1.0 */
public record Lap ( CommonUtils.ChronoType type, Duration globalTime, Duration lapTime ) implements Serializable {}