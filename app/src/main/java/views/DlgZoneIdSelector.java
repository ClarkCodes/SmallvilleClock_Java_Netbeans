package views;

import controllers.CommonUtils;
import controllers.CtrlSettings;
import controllers.CtrlSmallvilleClock;

/** Zone Id Selector Window
 * @author Clark - ClarkCodes
 */
public class DlgZoneIdSelector extends javax.swing.JDialog
{
    CtrlSmallvilleClock clocksManager;
    /** Creates new form DlgZoneIdSelecter */
    public DlgZoneIdSelector ( java.awt.Frame parent, boolean modal )
    {
        super( parent, modal );
        initComponents();
        setLocationRelativeTo( null );
        tableInitSetter();
        updateComponentsLanguage();
        clocksManager = CtrlSmallvilleClock.getController();
        CommonUtils.fillOneColumnTable( tblZoneIds, clocksManager.getZoneIds() );
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblZoneIds = new javax.swing.JTable();
        lblInstruction = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        tblZoneIds.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblZoneIds.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblZoneIds.setRowHeight(50);
        tblZoneIds.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tblZoneIdsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblZoneIds);

        lblInstruction.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/i18nBundle_es"); // NOI18N
        lblInstruction.setText(bundle.getString("key_select_zone_instruction")); // NOI18N

        btnOk.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnOk.setText(bundle.getString("key_ok")); // NOI18N
        btnOk.setEnabled(false);
        btnOk.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCancel.setText(bundle.getString("key_cancel")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInstruction)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblInstruction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /** Initializes the Zone Id table structure {@code DefaultTableModel}
     */
    private void tableInitSetter()
    {   // Table Sounds List
        tblZoneIds.setModel( new javax.swing.table.DefaultTableModel (
            new Object [][]
            {

            },
            new String []
            {
                CtrlSettings.getLanguageBundle().getString("key_zone")
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false
            };

            public Class getColumnClass( int columnIndex )
            {
                return types [columnIndex];
            }

            public boolean isCellEditable( int rowIndex, int columnIndex )
            {
                return canEdit [columnIndex];
            }
        });
    }
    
    private void btnOkActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOkActionPerformed
    {//GEN-HEADEREND:event_btnOkActionPerformed
        String zoneId = clocksManager.getZoneIds().get( tblZoneIds.getSelectedRow() );
            
        if ( !clocksManager.isZoneIdAlreadyUsed( zoneId ) )
            clocksManager.addWorldClock( zoneId );

        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
    {//GEN-HEADEREND:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tblZoneIdsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tblZoneIdsMouseClicked
    {//GEN-HEADEREND:event_tblZoneIdsMouseClicked
        if ( this != null && this.isVisible() )
        {
            if ( tblZoneIds.getSelectedRow() != -1 )
            {
                if ( !btnOk.isEnabled() )
                    btnOk.setEnabled( true );
            }
            else
                if ( btnOk.isEnabled() )
                    btnOk.setEnabled( false );
        }
    }//GEN-LAST:event_tblZoneIdsMouseClicked

    private void updateComponentsLanguage()
    {
        setTitle( CtrlSettings.getLanguageBundle().getString( "key_zone_selector" ) );
        lblInstruction.setText( CtrlSettings.getLanguageBundle().getString( "key_select_zone_instruction" ) );
        btnOk.setText( CtrlSettings.getLanguageBundle().getString( "key_ok" ) );
        btnCancel.setText( CtrlSettings.getLanguageBundle().getString( "key_cancel" ) );
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInstruction;
    private javax.swing.JTable tblZoneIds;
    // End of variables declaration//GEN-END:variables
}
