package models;

import controllers.CtrlSettings;
import data.FilesManager;
import java.io.File;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/** Helper class for playing the sound when the Timer's time ends, it is
 * necessary that it extends from Thread because when the sound plays it sleeps 
 * the thread where it executes on, so we need to do it in a separate Thread to
 * keep the main thread interactive
 * @author Clark - ClarkCodes
 * @see views.DlgTimeUp
 * @since 1.0
 */
public class TimerSound extends Thread
{   
    private final CtrlSettings ctrlSettings;
    private final String soundFilePath;
    private AdvancedPlayer mp3Player;
    private Clip wavClip;
    private FileFormat fileFormat;
    private boolean terminated = false;
    
    /** Constructor - Creates a new {@code TimerSound} object
     * @param soundFilePath A given sound file path to play it
     */
    public TimerSound( String soundFilePath ) 
    {
        super( "TimerSoundThread" );
        ctrlSettings = CtrlSettings.getController();
        this.soundFilePath = soundFilePath;
    }

    private enum FileFormat
    {
        MP3,
        WAV
    }

    @Override
    public void run ()
    {
        File soundFile = new File( soundFilePath );
            
        if ( !soundFile.exists() && soundFileNameIsNotPredefined( soundFilePath ) )
        {
            ctrlSettings.getTimerSoundsList().remove( soundFilePath );
            FilesManager.getController().setUserTimerSoundsListChanged( true );
            soundFile = new File( ctrlSettings.getPREDEFINED_SOUNDS_PATHS().get( 0 ) );
        }
        
        fileFormat = soundFile.getAbsolutePath().endsWith( "mp3" ) ? FileFormat.MP3 : FileFormat.WAV;
        
        playSound( soundFile );
    }

    private boolean soundFileNameIsNotPredefined( String soundPath )
    {
        boolean notPredefined = true;
        
        for ( String predefinedSoundPath : ctrlSettings.getPREDEFINED_SOUNDS_PATHS() )
        {
            if ( predefinedSoundPath.equalsIgnoreCase( soundPath ) )
            {
                notPredefined = false;
                break;
            }
        }
                
        return notPredefined;
    }
    
    /** Plays the specified sound or song
     * @param soundFile A specified sound or song {@code File} to play, it 
     * admits wav or mp3 format, it uses {@code Clip} for wav and {@code JLayer}
     * for mp3
     * @see javax.sound.sampled.Clip
     * @see javazoom.jl.player.advanced.AdvancedPlayer
     */
    private void playSound( File soundFile )
    {
        switch ( fileFormat )
        {
            case MP3 -> 
            {
                try ( final FileInputStream sound = new FileInputStream( soundFile ) )
                {
                    mp3Player = new AdvancedPlayer( sound );
                    mp3Player.play();
                    
                    if( !isTerminated() )
                        playSound( soundFile );
                }
                catch ( JavaLayerException jlex )
                {
                    JOptionPane.showMessageDialog( null, "TimerSound (Java Layer):\n" + jlex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_sound" ), JOptionPane.ERROR_MESSAGE );
                }
                catch ( Exception ex )
                {
                    JOptionPane.showMessageDialog( null, "TimerSound:\n" + ex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_sound" ), JOptionPane.ERROR_MESSAGE );
                }
            }
            case WAV ->
            {
                try ( final AudioInputStream sound = AudioSystem.getAudioInputStream( soundFile ) )
                {
                    wavClip = AudioSystem.getClip();
                    wavClip.open( sound );
                    wavClip.loop( Clip.LOOP_CONTINUOUSLY );
                    wavClip.setFramePosition( 0 );
                    wavClip.start();
                }
                catch ( Exception ex )
                {
                    JOptionPane.showMessageDialog( null, "TimerSound:\n" + ex.getMessage(), CtrlSettings.getLanguageBundle().getString( "key_exception_sound" ), JOptionPane.ERROR_MESSAGE );
                }
            }
        }
    }
    /** Terminates this object closing the playing sound, releasing resources
     * and flagging this object to be terminated, then the current thread reaches 
     * its end and finishes
     */
    public void terminate()
    {
        terminated = true;
        
        if ( fileFormat == FileFormat.WAV )
        {
            wavClip.stop();
            wavClip.close();
        }
        else if ( fileFormat == FileFormat.MP3 && mp3Player != null )
        {
            mp3Player.close();
        }
    }
    
    /** Gets the terminated state
     * @return {@code true} if this object is terminated or flagged to be it, 
     * {@code false} otherwise
     */
    public boolean isTerminated ()
    {
        return terminated;
    }
}