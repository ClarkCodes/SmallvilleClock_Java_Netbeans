package views;

import controllers.CommonUtils;
import controllers.CtrlSettings;
import controllers.CtrlSmallvilleClock;
import java.time.Duration;
import javax.swing.JButton;

/** Stopwatch {@code JPanel} for the Mini Mode Window
 * @author Clark - ClarkCodes
 * @since 1.0
 */
public class IfrmStopwatchMiniMode extends javax.swing.JPanel
{
    private final CtrlSettings ctrlSettings;
    private final CtrlSmallvilleClock clocksManager;
    private DlgStopwatchLaps stopwatchLapsWindow = null;
    
    /** Creates new form IfrmStopwatchMiniMode */
    public IfrmStopwatchMiniMode ()
    {
        initComponents();
        ctrlSettings = CtrlSettings.getController();
        clocksManager = CtrlSmallvilleClock.getController();
        clocksManager.getStopwatch().setStopwatchLapLabel( lblMiniStopwatch, lblMiniLapTime );
        
        if ( clocksManager.getStopwatch().getState() != CommonUtils.State.RUNNING )
        {
            lblMiniStopwatch.setText( CommonUtils.getFormattedDuration( clocksManager.getStopwatch().getStopwatchTime(), CommonUtils.ChronoType.STOPWATCH ) );

            if ( clocksManager.getStopwatch().getLapTime() != Duration.ZERO )
                lblMiniLapTime.setText( CommonUtils.getFormattedDuration( clocksManager.getStopwatch().getLapTime(), CommonUtils.ChronoType.STOPWATCH ) );
        }
        
        btnMiniCheckLaps.setToolTipText( CtrlSettings.getLanguageBundle().getString( "key_check_laps" ) );
        btnMiniCheckLaps.setIcon( ctrlSettings.getCheckLapsMiniIcon() );
        stopwatchButtonsIconsNamesSetter();
        stopwatchEnablingVerifier();
    }

    public JButton getBtnMiniCheckLaps ()
    {
        return btnMiniCheckLaps;
    }

    public DlgStopwatchLaps getStopwatchLapsWindow ()
    {
        return stopwatchLapsWindow;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        lblMiniStopwatch = new javax.swing.JLabel();
        btnMiniStartResumeStopwatch = new javax.swing.JButton();
        btnMiniLapCancelStopwatch = new javax.swing.JButton();
        btnMiniCheckLaps = new javax.swing.JButton();
        lblMiniLapTime = new javax.swing.JLabel();

        lblMiniStopwatch.setFont(new java.awt.Font("Exo", 1, 16)); // NOI18N
        lblMiniStopwatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMiniStopwatch.setText("00:00.00");

        btnMiniStartResumeStopwatch.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnMiniStartResumeStopwatchActionPerformed(evt);
            }
        });

        btnMiniLapCancelStopwatch.setEnabled(false);
        btnMiniLapCancelStopwatch.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnMiniLapCancelStopwatchActionPerformed(evt);
            }
        });

        btnMiniCheckLaps.setEnabled(false);
        btnMiniCheckLaps.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnMiniCheckLapsActionPerformed(evt);
            }
        });

        lblMiniLapTime.setFont(new java.awt.Font("Exo", 0, 12)); // NOI18N
        lblMiniLapTime.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(lblMiniStopwatch, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMiniLapTime, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMiniStartResumeStopwatch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMiniLapCancelStopwatch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMiniCheckLaps, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnMiniCheckLaps, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btnMiniStartResumeStopwatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMiniLapCancelStopwatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblMiniLapTime))
            .addComponent(lblMiniStopwatch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnMiniStartResumeStopwatchActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnMiniStartResumeStopwatchActionPerformed
    {//GEN-HEADEREND:event_btnMiniStartResumeStopwatchActionPerformed
        CommonUtils.getMainFrame().getBtnStopwatchStartStopResume().doClick();
        
        if ( !btnMiniLapCancelStopwatch.isEnabled() )
            btnMiniLapCancelStopwatch.setEnabled( true );
        
        stopwatchButtonsIconsNamesSetter();
        stopwatchEnablingVerifier();
    }//GEN-LAST:event_btnMiniStartResumeStopwatchActionPerformed

    private void btnMiniLapCancelStopwatchActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnMiniLapCancelStopwatchActionPerformed
    {//GEN-HEADEREND:event_btnMiniLapCancelStopwatchActionPerformed
        CommonUtils.getMainFrame().getBtnLapReset().doClick();
        
        if ( clocksManager.getStopwatch().getState() == CommonUtils.State.RUNNING )
        {
            if ( !btnMiniCheckLaps.isEnabled() )
                btnMiniCheckLaps.setEnabled( true );
            
            openUpdateLapsWindow();
        }
        
        stopwatchButtonsIconsNamesSetter();
        stopwatchEnablingVerifier();
    }//GEN-LAST:event_btnMiniLapCancelStopwatchActionPerformed

    private void btnMiniCheckLapsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnMiniCheckLapsActionPerformed
    {//GEN-HEADEREND:event_btnMiniCheckLapsActionPerformed
        if ( stopwatchLapsWindow != null && stopwatchLapsWindow.isVisible() )
        {
            stopwatchLapsWindow.dispose();
        }
        else
        {
            stopwatchLapsWindow = new DlgStopwatchLaps( CommonUtils.getMiniWindow(), false );
            stopwatchLapsWindow.setVisible( true );
        }
    }//GEN-LAST:event_btnMiniCheckLapsActionPerformed

    public final void stopwatchButtonsIconsNamesSetter()
    {
        btnMiniStartResumeStopwatch.setToolTipText( switch ( clocksManager.getStopwatch().getState() )
        {
            case STOPPED -> CtrlSettings.getLanguageBundle().getString( "key_start" );
            case RUNNING -> CtrlSettings.getLanguageBundle().getString( "key_pause" );
            case PAUSED -> CtrlSettings.getLanguageBundle().getString( "key_resume" );
        } );
        
        btnMiniLapCancelStopwatch.setToolTipText( 
                clocksManager.getStopwatch().getState() == CommonUtils.State.PAUSED ? 
                        CtrlSettings.getLanguageBundle().getString( "key_reset" ) : 
                        CtrlSettings.getLanguageBundle().getString( "key_lap" ) );
        
        btnMiniStartResumeStopwatch.setIcon( 
                clocksManager.getStopwatch().getState() == CommonUtils.State.RUNNING ?
                        ctrlSettings.getPauseIcon() :
                        ctrlSettings.getPlayStartResumeIcon() );
        
        btnMiniLapCancelStopwatch.setIcon( 
                clocksManager.getStopwatch().getState() == CommonUtils.State.RUNNING ?
                        ctrlSettings.getMarkLapIcon() :
                        ctrlSettings.getStopCancelIcon() );
    }

    public final void stopwatchEnablingVerifier()
    {
        if ( clocksManager.getStopwatch().getState() == CommonUtils.State.STOPPED )
        {
            if ( !btnMiniStartResumeStopwatch.isEnabled() )
                btnMiniStartResumeStopwatch.setEnabled( true );
            
            if ( btnMiniLapCancelStopwatch.isEnabled() )
                btnMiniLapCancelStopwatch.setEnabled( false );
            
            if ( btnMiniCheckLaps.isEnabled() )
                btnMiniCheckLaps.setEnabled( false );
        }
        else
        {
            if ( !btnMiniLapCancelStopwatch.isEnabled() )
                btnMiniLapCancelStopwatch.setEnabled( true );
            
            if ( clocksManager.getStopwatch().isLimitReached() )
                btnMiniStartResumeStopwatch.setEnabled( false );
        }
    }
    
    private void openUpdateLapsWindow()
    {
        if ( stopwatchLapsWindow == null )
        {
            stopwatchLapsWindow = new DlgStopwatchLaps( CommonUtils.getMiniWindow(), false );
            stopwatchLapsWindow.setVisible( true );
        }
        else if ( !stopwatchLapsWindow.isVisible() )
        {
            stopwatchLapsWindow.setVisible( true );
            stopwatchLapsWindow.updateLapsTable();
        }
        else
        {
            stopwatchLapsWindow.updateLapsTable();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMiniCheckLaps;
    private javax.swing.JButton btnMiniLapCancelStopwatch;
    private javax.swing.JButton btnMiniStartResumeStopwatch;
    private javax.swing.JLabel lblMiniLapTime;
    private javax.swing.JLabel lblMiniStopwatch;
    // End of variables declaration//GEN-END:variables
}
