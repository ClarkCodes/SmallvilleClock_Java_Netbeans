package models;

import controllers.CommonUtils;
import java.io.Serializable;
import java.time.Duration;

/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

/** Represents a Timer Preset that saves a specific time and name in the Timer mode
 * @author Clark - ClarkCodes
 * @see views.DlgSetTimer
 * @see controllers.CtrlSmallvilleClock
 * @since 1.0 */
public record PresetTimerTime ( CommonUtils.ChronoType type, Duration time, String name ) implements Serializable {}