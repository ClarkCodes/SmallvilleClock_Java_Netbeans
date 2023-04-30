package models;
/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

/** Defines necessary methods for time operations that must be implemented by 
 * Timers and Stopwatches
 * @author Clark - ClarkCodes
 * @since 1.0
 */
public interface ITimeControlable
{
    public void start();
    public void stop();
    public void reset();
}
