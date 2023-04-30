package views;

/* LICENSE
 * Creative Commons Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */
// Imports
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import controllers.CommonUtils;
import controllers.CtrlSettings;
import controllers.CtrlSmallvilleClock;
import data.FilesManager;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.swing.JFileChooser;

/** Settings Window
 *
 * @author Clark - ClarkCodes
 * @see control.AdmSettings
 * @since 1.4
 */
public class DlgSettings extends javax.swing.JDialog
{
    // Adms
    private final FilesManager filesManager;
    private final CtrlSettings ctrlSettings;

    public DlgSettings ( java.awt.Frame parent, boolean modal )
    {
        super( parent, modal );
        initComponents();
        this.setLocationRelativeTo( null );
        filesManager = FilesManager.getController();
        ctrlSettings = CtrlSettings.getController();
        //this.setIconImage( admSettings.getSettingsIconForSettingsForm().getImage() );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        btgTemaLaf = new javax.swing.ButtonGroup();
        btgFileType = new javax.swing.ButtonGroup();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        tpSettings = new javax.swing.JTabbedPane();
        tabPanelGenerals = new javax.swing.JPanel();
        panelGroupLanguage = new javax.swing.JPanel();
        lblLanguage = new javax.swing.JLabel();
        cmbAppLanguage = new javax.swing.JComboBox<>();
        panelGroupFileType = new javax.swing.JPanel();
        rdbFileTypeText = new javax.swing.JRadioButton();
        rdbFileTypeBinary = new javax.swing.JRadioButton();
        panelGroupStartingFolder = new javax.swing.JPanel();
        lblStartingFolderPath = new javax.swing.JLabel();
        txtStartingFolder = new javax.swing.JTextField();
        btnSelectFolder = new javax.swing.JButton();
        btnValueByDefault = new javax.swing.JButton();
        panelGroupTimersSound = new javax.swing.JPanel();
        lblSound = new javax.swing.JLabel();
        btnSelectSound = new javax.swing.JButton();
        lblSoundName = new javax.swing.JLabel();
        chkAppOnTop = new javax.swing.JCheckBox();
        tabPanelWorldClock = new javax.swing.JPanel();
        panelGroupWorldClock = new javax.swing.JPanel();
        chkFormat24Hours = new javax.swing.JCheckBox();
        chkIncludeSeconds = new javax.swing.JCheckBox();
        tabPanelLaf = new javax.swing.JPanel();
        lblLafThemesTitle = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        rbtFlatLaf = new javax.swing.JRadioButton();
        cmbFlatLaf = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        rbtIntelliJ = new javax.swing.JRadioButton();
        cmbIntelliJ = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        labelLafPreview = new javax.swing.JLabel();
        buttonApply = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/i18nBundle_es"); // NOI18N
        setTitle(bundle.getString("key_settings")); // NOI18N
        setIconImage(null);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                formKeyReleased(evt);
            }
        });

        btnOk.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnOk.setText(bundle.getString("key_ok")); // NOI18N
        btnOk.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnCancel.setText(bundle.getString("key_cancel")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCancelActionPerformed(evt);
            }
        });

        tpSettings.setFocusable(false);

        tabPanelGenerals.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabPanelGenerals.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                tabPanelGeneralsComponentShown(evt);
            }
        });

        panelGroupLanguage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("key_app_language"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        lblLanguage.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblLanguage.setText(bundle.getString("key_language_tag")); // NOI18N

        cmbAppLanguage.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        javax.swing.GroupLayout panelGroupLanguageLayout = new javax.swing.GroupLayout(panelGroupLanguage);
        panelGroupLanguage.setLayout(panelGroupLanguageLayout);
        panelGroupLanguageLayout.setHorizontalGroup(
            panelGroupLanguageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupLanguageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLanguage)
                .addGap(18, 18, 18)
                .addComponent(cmbAppLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(245, 245, 245))
        );
        panelGroupLanguageLayout.setVerticalGroup(
            panelGroupLanguageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupLanguageLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelGroupLanguageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLanguage)
                    .addComponent(cmbAppLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        panelGroupFileType.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("key_saving_file_type"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        btgFileType.add(rdbFileTypeText);
        rdbFileTypeText.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        rdbFileTypeText.setText(bundle.getString("key_file_type_text")); // NOI18N

        btgFileType.add(rdbFileTypeBinary);
        rdbFileTypeBinary.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        rdbFileTypeBinary.setText(bundle.getString("key_file_type_binary")); // NOI18N

        javax.swing.GroupLayout panelGroupFileTypeLayout = new javax.swing.GroupLayout(panelGroupFileType);
        panelGroupFileType.setLayout(panelGroupFileTypeLayout);
        panelGroupFileTypeLayout.setHorizontalGroup(
            panelGroupFileTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupFileTypeLayout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(rdbFileTypeText)
                .addGap(18, 18, 18)
                .addComponent(rdbFileTypeBinary)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGroupFileTypeLayout.setVerticalGroup(
            panelGroupFileTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupFileTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGroupFileTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbFileTypeText)
                    .addComponent(rdbFileTypeBinary))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        panelGroupStartingFolder.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Carpeta Inicial", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        lblStartingFolderPath.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblStartingFolderPath.setText(bundle.getString("key_path_tag")); // NOI18N

        txtStartingFolder.setEditable(false);
        txtStartingFolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnSelectFolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnSelectFolder.setText(bundle.getString("key_select")); // NOI18N
        btnSelectFolder.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSelectFolderActionPerformed(evt);
            }
        });

        btnValueByDefault.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnValueByDefault.setText(bundle.getString("key_value_by_default")); // NOI18N
        btnValueByDefault.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnValueByDefaultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGroupStartingFolderLayout = new javax.swing.GroupLayout(panelGroupStartingFolder);
        panelGroupStartingFolder.setLayout(panelGroupStartingFolderLayout);
        panelGroupStartingFolderLayout.setHorizontalGroup(
            panelGroupStartingFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGroupStartingFolderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblStartingFolderPath)
                .addGap(18, 18, 18)
                .addComponent(txtStartingFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelGroupStartingFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSelectFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnValueByDefault, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        panelGroupStartingFolderLayout.setVerticalGroup(
            panelGroupStartingFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupStartingFolderLayout.createSequentialGroup()
                .addGroup(panelGroupStartingFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGroupStartingFolderLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panelGroupStartingFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtStartingFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStartingFolderPath)))
                    .addGroup(panelGroupStartingFolderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSelectFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnValueByDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        panelGroupTimersSound.setBorder(javax.swing.BorderFactory.createTitledBorder(null, CtrlSettings.getLanguageBundle().getString( "key_timers_sound" )
            , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

    lblSound.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    lblSound.setText(bundle.getString("key_sound_tag")); // NOI18N

    btnSelectSound.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    btnSelectSound.setText(bundle.getString("key_select")); // NOI18N
    btnSelectSound.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            btnSelectSoundActionPerformed(evt);
        }
    });

    lblSoundName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    lblSoundName.setText("Nombre del Sonido");
    lblSoundName.setFocusable(false);
    lblSoundName.addPropertyChangeListener(new java.beans.PropertyChangeListener()
    {
        public void propertyChange(java.beans.PropertyChangeEvent evt)
        {
            lblSoundNamePropertyChange(evt);
        }
    });

    javax.swing.GroupLayout panelGroupTimersSoundLayout = new javax.swing.GroupLayout(panelGroupTimersSound);
    panelGroupTimersSound.setLayout(panelGroupTimersSoundLayout);
    panelGroupTimersSoundLayout.setHorizontalGroup(
        panelGroupTimersSoundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGroupTimersSoundLayout.createSequentialGroup()
            .addGap(162, 162, 162)
            .addComponent(lblSound)
            .addGap(18, 18, 18)
            .addComponent(lblSoundName, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(btnSelectSound, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18))
    );
    panelGroupTimersSoundLayout.setVerticalGroup(
        panelGroupTimersSoundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelGroupTimersSoundLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelGroupTimersSoundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSelectSound, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblSoundName)
                .addComponent(lblSound))
            .addContainerGap(18, Short.MAX_VALUE))
    );

    chkAppOnTop.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    chkAppOnTop.setText(bundle.getString("key_app_on_top")); // NOI18N
    chkAppOnTop.addChangeListener(new javax.swing.event.ChangeListener()
    {
        public void stateChanged(javax.swing.event.ChangeEvent evt)
        {
            chkAppOnTopStateChanged(evt);
        }
    });

    javax.swing.GroupLayout tabPanelGeneralsLayout = new javax.swing.GroupLayout(tabPanelGenerals);
    tabPanelGenerals.setLayout(tabPanelGeneralsLayout);
    tabPanelGeneralsLayout.setHorizontalGroup(
        tabPanelGeneralsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(panelGroupFileType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(panelGroupLanguage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(panelGroupStartingFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(panelGroupTimersSound, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(tabPanelGeneralsLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(chkAppOnTop)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    tabPanelGeneralsLayout.setVerticalGroup(
        tabPanelGeneralsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(tabPanelGeneralsLayout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addComponent(panelGroupFileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(panelGroupStartingFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(panelGroupTimersSound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(panelGroupLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(chkAppOnTop)
            .addContainerGap(76, Short.MAX_VALUE))
    );

    tpSettings.addTab(bundle.getString("key_general_settings"), tabPanelGenerals); // NOI18N

    panelGroupWorldClock.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("key_clock"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

    chkFormat24Hours.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    chkFormat24Hours.setText(bundle.getString("key_24_hours_format")); // NOI18N
    chkFormat24Hours.addChangeListener(new javax.swing.event.ChangeListener()
    {
        public void stateChanged(javax.swing.event.ChangeEvent evt)
        {
            chkFormat24HoursStateChanged(evt);
        }
    });

    chkIncludeSeconds.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    chkIncludeSeconds.setText(bundle.getString("key_include_seconds")); // NOI18N
    chkIncludeSeconds.addChangeListener(new javax.swing.event.ChangeListener()
    {
        public void stateChanged(javax.swing.event.ChangeEvent evt)
        {
            chkIncludeSecondsStateChanged(evt);
        }
    });

    javax.swing.GroupLayout panelGroupWorldClockLayout = new javax.swing.GroupLayout(panelGroupWorldClock);
    panelGroupWorldClock.setLayout(panelGroupWorldClockLayout);
    panelGroupWorldClockLayout.setHorizontalGroup(
        panelGroupWorldClockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelGroupWorldClockLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(chkFormat24Hours)
            .addGap(55, 55, 55)
            .addComponent(chkIncludeSeconds)
            .addGap(66, 66, 66))
    );
    panelGroupWorldClockLayout.setVerticalGroup(
        panelGroupWorldClockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelGroupWorldClockLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelGroupWorldClockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(chkFormat24Hours)
                .addComponent(chkIncludeSeconds))
            .addContainerGap(22, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout tabPanelWorldClockLayout = new javax.swing.GroupLayout(tabPanelWorldClock);
    tabPanelWorldClock.setLayout(tabPanelWorldClockLayout);
    tabPanelWorldClockLayout.setHorizontalGroup(
        tabPanelWorldClockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(panelGroupWorldClock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    tabPanelWorldClockLayout.setVerticalGroup(
        tabPanelWorldClockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(tabPanelWorldClockLayout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addComponent(panelGroupWorldClock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(458, Short.MAX_VALUE))
    );

    tpSettings.addTab("Reloj Mundial", tabPanelWorldClock);

    tabPanelLaf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    tabPanelLaf.addComponentListener(new java.awt.event.ComponentAdapter()
    {
        public void componentShown(java.awt.event.ComponentEvent evt)
        {
            tabPanelLafComponentShown(evt);
        }
    });

    lblLafThemesTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
    lblLafThemesTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblLafThemesTitle.setText(bundle.getString("key_look_and_feel_title")); // NOI18N

    jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("key_flatlaf_core_themes"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
    jPanel4.setPreferredSize(new java.awt.Dimension(718, 150));

    btgTemaLaf.add(rbtFlatLaf);
    rbtFlatLaf.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    rbtFlatLaf.setText(bundle.getString("key_flat_look_and_feel")); // NOI18N
    rbtFlatLaf.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            rbtFlatLafActionPerformed(evt);
        }
    });

    cmbFlatLaf.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    cmbFlatLaf.addItemListener(new java.awt.event.ItemListener()
    {
        public void itemStateChanged(java.awt.event.ItemEvent evt)
        {
            cmbFlatLafItemStateChanged(evt);
        }
    });

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(rbtFlatLaf)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
            .addComponent(cmbFlatLaf, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addGap(35, 35, 35)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rbtFlatLaf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbFlatLaf, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(52, 52, 52))
    );

    jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("key_flatlaf_intellij_platform_themes"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
    jPanel5.setPreferredSize(new java.awt.Dimension(718, 150));

    btgTemaLaf.add(rbtIntelliJ);
    rbtIntelliJ.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    rbtIntelliJ.setText(bundle.getString("key_intellij_look_and_feel")); // NOI18N
    rbtIntelliJ.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            rbtIntelliJActionPerformed(evt);
        }
    });

    cmbIntelliJ.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    cmbIntelliJ.addItemListener(new java.awt.event.ItemListener()
    {
        public void itemStateChanged(java.awt.event.ItemEvent evt)
        {
            cmbIntelliJItemStateChanged(evt);
        }
    });

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(rbtIntelliJ)
            .addGap(18, 18, 18)
            .addComponent(cmbIntelliJ, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addGap(35, 35, 35)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rbtIntelliJ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbIntelliJ, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(52, 52, 52))
    );

    jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("key_preview"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(labelLafPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(labelLafPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
            .addContainerGap())
    );

    buttonApply.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
    buttonApply.setText(bundle.getString("key_apply_theme")); // NOI18N
    buttonApply.setEnabled(false);
    buttonApply.addActionListener(new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            buttonApplyActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout tabPanelLafLayout = new javax.swing.GroupLayout(tabPanelLaf);
    tabPanelLaf.setLayout(tabPanelLafLayout);
    tabPanelLafLayout.setHorizontalGroup(
        tabPanelLafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(tabPanelLafLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(lblLafThemesTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
            .addContainerGap())
        .addGroup(tabPanelLafLayout.createSequentialGroup()
            .addGroup(tabPanelLafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addComponent(buttonApply, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    tabPanelLafLayout.setVerticalGroup(
        tabPanelLafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(tabPanelLafLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(lblLafThemesTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(tabPanelLafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(tabPanelLafLayout.createSequentialGroup()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
            .addComponent(buttonApply, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    tpSettings.addTab(bundle.getString("key_look_and_feel"), tabPanelLaf); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(tpSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap(18, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addComponent(tpSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18))
    );

    getRootPane().setDefaultButton(btnOk);
    tpSettings.getAccessibleContext().setAccessibleName(bundle.getString("key_look_and_feel")); // NOI18N

    getAccessibleContext().setAccessibleName("");

    pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Loads and sets user settings on this settings window to represent the
     * saved settings and make them available to posible change 
     */
    private void settingsSetterOnOpen ()
    {
        if ( ctrlSettings.getFileTypeToSave() == CommonUtils.FileType.TEXT )
        {
            rdbFileTypeText.setSelected( true );
        }
        else
        {
            rdbFileTypeBinary.setSelected( true );
        }

        if ( ctrlSettings.getCurrentSelectedGuiLafTheme().compareToIgnoreCase( "Flat Dark" ) == 0 || ctrlSettings.getCurrentSelectedGuiLafTheme().compareToIgnoreCase( "Flat Light" ) == 0 )
        {
            rbtFlatLaf.setSelected( true );
            cmbFlatLaf.setSelectedItem( ctrlSettings.getCurrentSelectedGuiLafTheme() );
        }
        else
        {
            rbtIntelliJ.setSelected( true );
            cmbIntelliJ.setSelectedItem( ctrlSettings.getCurrentSelectedGuiLafTheme() );
        }
        
        txtStartingFolder.setText( ctrlSettings.getStartingFolder() );
        
        
        lblSoundName.setText( ctrlSettings.soundNamePreparer( ctrlSettings.getTimerSound() ) );
        chkAppOnTop.setSelected( ctrlSettings.isAppOnTop() );
        chkFormat24Hours.setSelected( ctrlSettings.isFormat24Hours() );
        chkIncludeSeconds.setSelected( ctrlSettings.isIncludeSeconds() );

        cmbAppLanguage.setSelectedItem( CtrlSettings.getAppLanguage().getTagName() );

        previewSetter();
        enabledComponentsManager(); // Set the enabled and disabled controls-components following saved User Settings
        tabGeneralsInitialFocus();
    }

    /** Sets and Saves the changed user settings only if a change had place, 
     * this occurs on close when clicking on Ok button /
     *
     * @see control.AdmSettings
     */
    private void settingsSetterOnClose ()
    {
        applyTheme(); // Aplicando el Tema si es que no se ha aplicado y esta pendiente de ello.
        
        // Verification and set on Settings in AdmSettings
        // File Type to Save
        if( rdbFileTypeBinary.isSelected() )
        {
            if( ctrlSettings.getFileTypeToSave() != CommonUtils.FileType.valueOfLocalizedString( rdbFileTypeBinary.getText() ) )
                ctrlSettings.setFileTypeToSave( CommonUtils.FileType.BINARY.toString() );
        }
        else
        {
            if ( ctrlSettings.getFileTypeToSave() != CommonUtils.FileType.valueOfLocalizedString( rdbFileTypeText.getText() ) )
                ctrlSettings.setFileTypeToSave( CommonUtils.FileType.TEXT.toString() );
        }
        
        // Language
        if ( !CtrlSettings.getAppLanguage().getTagName().equalsIgnoreCase( cmbAppLanguage.getSelectedItem().toString() ) )
            CtrlSettings.setAppLanguage( cmbAppLanguage.getSelectedItem().toString() );

        // Verification and Saving on the user Properties object
        filesManager.settingsChangeVerifier();

        if ( !filesManager.getUserProperties().getProperty( "currentGuiLafTheme" ).equalsIgnoreCase( ctrlSettings.getCurrentSelectedGuiLafTheme() ) ) //NOI18N
            filesManager.getUserProperties().setProperty( "currentGuiLafTheme", ctrlSettings.getCurrentSelectedGuiLafTheme() ); //NOI18N

        if ( !filesManager.getUserProperties().getProperty( "fileTypeToSave" ).equalsIgnoreCase( ctrlSettings.getFileTypeToSave().toString().toLowerCase() ) )
            filesManager.getUserProperties().setProperty( "fileTypeToSave", ctrlSettings.getFileTypeToSave().toString().toLowerCase() );

        if ( !filesManager.getUserProperties().getProperty( "soundName" ).equalsIgnoreCase( ctrlSettings.getTimerSound() ) )
            filesManager.getUserProperties().setProperty( "soundName", ctrlSettings.getTimerSound() );

        if ( !filesManager.getUserProperties().getProperty( "appOnTop" ).equalsIgnoreCase( ctrlSettings.isAppOnTop() ? "true" : "false" ) )
        {
            filesManager.getUserProperties().setProperty( "appOnTop", ctrlSettings.isAppOnTop() ? "true" : "false" );
            ( CommonUtils.getMainFrame() ).setAlwaysOnTop( ctrlSettings.isAppOnTop() );
        }
        
        if ( !filesManager.getUserProperties().getProperty( "format24Hours" ).equalsIgnoreCase( ctrlSettings.isFormat24Hours() ? "true" : "false" ) )
            filesManager.getUserProperties().setProperty( "format24Hours", ctrlSettings.isFormat24Hours() ? "true" : "false" );
        
        if ( !filesManager.getUserProperties().getProperty( "includeSeconds" ).equalsIgnoreCase( ctrlSettings.isIncludeSeconds() ? "true" : "false" ) )
            filesManager.getUserProperties().setProperty( "includeSeconds", ctrlSettings.isIncludeSeconds() ? "true" : "false" );
        
        if ( !filesManager.getUserProperties().getProperty( "startingFolder" ).equalsIgnoreCase( ctrlSettings.getStartingFolder() ) )
        {
            filesManager.getUserProperties().setProperty( "startingFolder", ctrlSettings.getStartingFolder() );
            filesManager.dialogDirectorySetter();
        }

        if ( !filesManager.getUserProperties().getProperty( "appLocale" ).equalsIgnoreCase( CtrlSettings.getAppLanguage().getLocale() ) ) //NOI18N
        {
            filesManager.getUserProperties().setProperty( "appLocale", CtrlSettings.getAppLanguage().getLocale() ); //NOI18N
            ctrlSettings.applyLanguageInApp();
        }

        CtrlSmallvilleClock.getController().worldClocksFormatSetter(); // Update the DateTimeFormat for all World Clocks in case settings related have been changed
    }
    
    /** Sets the preview Look and Feel image on its component to check how the 
     * selected theme looks on
     * @see control.AdmSettings
     */
    private void previewSetter ()
    {
        //labelLafPreview.setIcon( new ImageIcon( this.getClass().getResource( "/themes_previews/preview_" + ctrlSettings.getCurrentSelectedGuiLafTheme() + ".png" ) ) ); //NOI18N
    }

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        settingsSetterOnClose();
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if ( !filesManager.getUserProperties().getProperty( "currentGuiLafTheme" ).equalsIgnoreCase( ctrlSettings.getCurrentAppliedGuiLafTheme() ) ) //NOI18N
            ctrlSettings.gettingBackToSavedTheme( filesManager.getUserProperties().getProperty( "currentGuiLafTheme" ) ); //NOI18N
        
        if ( !filesManager.getUserProperties().getProperty( "appLocale" ).equalsIgnoreCase( CtrlSettings.getAppLanguage().getLocale() ) ) //NOI18N
            CtrlSettings.setAppLanguage( filesManager.getUserProperties().getProperty( "appLocale" ) );
        
        if ( !filesManager.getUserProperties().getProperty( "fileTypeToSave" ).equalsIgnoreCase( ctrlSettings.getFileTypeToSave().toString().toLowerCase() ) )
            ctrlSettings.setFileTypeToSave( filesManager.getUserProperties().getProperty( "fileTypeToSave" ) );
        
        if ( !filesManager.getUserProperties().getProperty( "startingFolder" ).equalsIgnoreCase( ctrlSettings.getStartingFolder() ) )
            ctrlSettings.setStartingFolder( filesManager.getUserProperties().getProperty( "startingFolder" ) );
        
        if ( !filesManager.getUserProperties().getProperty( "soundName" ).equalsIgnoreCase( ctrlSettings.getTimerSound() ) )
            ctrlSettings.setTimerSound( filesManager.getUserProperties().getProperty( "soundName" ) );
        
        if ( !filesManager.getUserProperties().getProperty( "appOnTop" ).equalsIgnoreCase( ctrlSettings.isAppOnTop() ? "true" : "false" ) )
            ctrlSettings.setAppOnTop( Boolean.parseBoolean( filesManager.getUserProperties().getProperty( "appOnTop" ) ) );
        
        if ( !filesManager.getUserProperties().getProperty( "format24Hours" ).equalsIgnoreCase( ctrlSettings.isFormat24Hours() ? "true" : "false" ) )
            ctrlSettings.setFormat24Hours( Boolean.parseBoolean( filesManager.getUserProperties().getProperty( "format24Hours" ) ) );
        
        if ( !filesManager.getUserProperties().getProperty( "includeSeconds" ).equalsIgnoreCase( ctrlSettings.isIncludeSeconds() ? "true" : "false" ) )
            ctrlSettings.setIncludeSeconds( Boolean.parseBoolean( filesManager.getUserProperties().getProperty( "includeSeconds" ) ) );
        
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * Closes this dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        dispose();
    }//GEN-LAST:event_closeDialog

    private void rbtFlatLafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtFlatLafActionPerformed
        enabledComponentsManager();
        themeChooser();
    }//GEN-LAST:event_rbtFlatLafActionPerformed

    private void rbtIntelliJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtIntelliJActionPerformed
        enabledComponentsManager();
        themeChooser();
    }//GEN-LAST:event_rbtIntelliJActionPerformed
    /** Manages themes related components that must be enabled and disabled
     *  following {@code rbtFlatLaf} component selected state, this indicates 
     * what group of themes are chosen by user
     */
    private void enabledComponentsManager ()
    {
        if ( rbtFlatLaf.isSelected() )
        {
            rbtFlatLaf.requestFocus();
            cmbFlatLaf.setEnabled( true );
            cmbIntelliJ.setEnabled( false );
        }
        else
        {
            rbtIntelliJ.requestFocus();
            cmbIntelliJ.setEnabled( true );
            cmbFlatLaf.setEnabled( false );
        }
    }

    /** Selects a theme from the respective {@code JComboBox} themes list,
     * either FlatLaf or IntelliJ, let a user see a preview of that theme and 
     * make it ready to appy for the user to decide 
     * @see control.AdmSettings
     */
    private void themeChooser ()
    {
        ctrlSettings.setCurrentSelectedGuiLafTheme( rbtFlatLaf.isSelected() ? cmbFlatLaf.getSelectedItem().toString() : cmbIntelliJ.getSelectedItem().toString() );
        applyPendingVerifier();
        previewSetter();
    }

    /** Verifies if there is a theme ready to apply but it has not been applied
     * yet 
     */
    private void applyPendingVerifier ()
    {
        if ( !ctrlSettings.getCurrentAppliedGuiLafTheme().equalsIgnoreCase( ctrlSettings.getCurrentSelectedGuiLafTheme() ) )
        {
            ctrlSettings.setApplyingThemePending( true );
            buttonApply.setEnabled( true );
        }
        else
        {
            ctrlSettings.setApplyingThemePending( false );
            buttonApply.setEnabled( false );
        }
    }

    /** Applies a selected theme if it is not the current applied theme
     *
     * @see control.AdmSettings
     */
    private void applyTheme ()
    {
        if ( ctrlSettings.isApplyingThemePending() )
        {
            FlatAnimatedLafChange.showSnapshot(); // Preparing to Make a Soft Transition
            ctrlSettings.applyThemeManager( ctrlSettings.getCurrentSelectedGuiLafTheme() ); // Making the Call to apply the theme
            ctrlSettings.colorOnIconsManager(CtrlSettings.ColorSetType.RUNTIME );
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
            ctrlSettings.setCurrentAppliedGuiLafTheme( ctrlSettings.getCurrentSelectedGuiLafTheme() );
            applyPendingVerifier();
        }
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        ctrlSettings.fillLanguagesCombo( cmbAppLanguage );
        ctrlSettings.fillFlatLafCombo( cmbFlatLaf );
        ctrlSettings.fillIntelliJCombo( cmbIntelliJ );
        settingsSetterOnOpen();
        updateComponentsLanguage();
    }//GEN-LAST:event_formWindowOpened

    private void cmbFlatLafItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_cmbFlatLafItemStateChanged
    {//GEN-HEADEREND:event_cmbFlatLafItemStateChanged
        if ( this.isVisible() & rbtFlatLaf.isSelected() & cmbFlatLaf.getSelectedItem() != null & ctrlSettings.getCurrentSelectedGuiLafTheme() != null & !ctrlSettings.getCurrentSelectedGuiLafTheme().equalsIgnoreCase( cmbFlatLaf.getSelectedItem().toString() ) )
            themeChooser();
    }//GEN-LAST:event_cmbFlatLafItemStateChanged

    private void cmbIntelliJItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_cmbIntelliJItemStateChanged
    {//GEN-HEADEREND:event_cmbIntelliJItemStateChanged
        if ( this.isVisible() & rbtIntelliJ.isSelected() & cmbIntelliJ.getSelectedItem() != null & ctrlSettings.getCurrentSelectedGuiLafTheme() != null & !ctrlSettings.getCurrentSelectedGuiLafTheme().equalsIgnoreCase( cmbIntelliJ.getSelectedItem().toString() ) )
            themeChooser();
    }//GEN-LAST:event_cmbIntelliJItemStateChanged

    private void buttonApplyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonApplyActionPerformed
    {//GEN-HEADEREND:event_buttonApplyActionPerformed
        applyTheme();
    }//GEN-LAST:event_buttonApplyActionPerformed

    private void btnSelectFolderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSelectFolderActionPerformed
    {//GEN-HEADEREND:event_btnSelectFolderActionPerformed
        JFileChooser startingFolderChooser;
        
        if ( ctrlSettings.getStartingFolder().equalsIgnoreCase( "default" ) )
        {
            startingFolderChooser = new JFileChooser();
        }
        else
        {
            startingFolderChooser = new JFileChooser( ctrlSettings.getStartingFolder() );
        }
        
        startingFolderChooser.setPreferredSize( new Dimension( 900, 600 ) );
        startingFolderChooser.setLocale( new Locale ( CtrlSettings.getAppLanguage().getLanguage() ) );
        startingFolderChooser.setApproveButtonText( CtrlSettings.getLanguageBundle().getString( "key_select_button" ) );
        startingFolderChooser.setDialogTitle( CtrlSettings.getLanguageBundle().getString( "key_select" ) );
        startingFolderChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        startingFolderChooser.setDialogType( JFileChooser.CUSTOM_DIALOG );
        FilesManager.getOpenSaveFrame().setIconImage( ctrlSettings.getSearchIconForSearchForm().getImage() );
        
        if ( startingFolderChooser.showDialog( FilesManager.getOpenSaveFrame(), CtrlSettings.getLanguageBundle().getString( "key_select" ) ) == JFileChooser.APPROVE_OPTION )
        {
            ctrlSettings.setStartingFolder( startingFolderChooser.getSelectedFile().getAbsolutePath() );
            txtStartingFolder.setText( ctrlSettings.getStartingFolder() );
        }
    }//GEN-LAST:event_btnSelectFolderActionPerformed

    private void btnValueByDefaultActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnValueByDefaultActionPerformed
    {//GEN-HEADEREND:event_btnValueByDefaultActionPerformed
        if ( !ctrlSettings.getStartingFolder().equalsIgnoreCase( "default" ) )
            ctrlSettings.setStartingFolder( "default" );
        
        txtStartingFolder.setText( ctrlSettings.getStartingFolder() );
    }//GEN-LAST:event_btnValueByDefaultActionPerformed

    private void tabPanelGeneralsComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_tabPanelGeneralsComponentShown
    {//GEN-HEADEREND:event_tabPanelGeneralsComponentShown
        tabGeneralsInitialFocus();
    }//GEN-LAST:event_tabPanelGeneralsComponentShown

    private void tabPanelLafComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_tabPanelLafComponentShown
    {//GEN-HEADEREND:event_tabPanelLafComponentShown
        tabLafInitialFocus();
    }//GEN-LAST:event_tabPanelLafComponentShown

    private void btnSelectSoundActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSelectSoundActionPerformed
    {//GEN-HEADEREND:event_btnSelectSoundActionPerformed
        DlgTimerSoundSelector soundPicker = new DlgTimerSoundSelector( ( javax.swing.JFrame )this.getOwner(), true, lblSoundName );
        soundPicker.setVisible( true );
    }//GEN-LAST:event_btnSelectSoundActionPerformed

    private void chkAppOnTopStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_chkAppOnTopStateChanged
    {//GEN-HEADEREND:event_chkAppOnTopStateChanged
        if ( this.isVisible() )
            ctrlSettings.setAppOnTop( chkAppOnTop.isSelected() );
    }//GEN-LAST:event_chkAppOnTopStateChanged

    private void lblSoundNamePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_lblSoundNamePropertyChange
    {//GEN-HEADEREND:event_lblSoundNamePropertyChange
        lblSoundName.setToolTipText( lblSoundName.getText().length() >= 30 ? lblSoundName.getText() : "" );
    }//GEN-LAST:event_lblSoundNamePropertyChange

    private void formKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_formKeyReleased
    {//GEN-HEADEREND:event_formKeyReleased
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER )
        {
            btnOk.doClick();
        }
        else if ( evt.getKeyCode() == KeyEvent.VK_ESCAPE )
        {
            btnCancel.doClick();
        }
    }//GEN-LAST:event_formKeyReleased

    private void chkFormat24HoursStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_chkFormat24HoursStateChanged
    {//GEN-HEADEREND:event_chkFormat24HoursStateChanged
        if ( this.isVisible() )
            ctrlSettings.setFormat24Hours( chkFormat24Hours.isSelected() );
    }//GEN-LAST:event_chkFormat24HoursStateChanged

    private void chkIncludeSecondsStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_chkIncludeSecondsStateChanged
    {//GEN-HEADEREND:event_chkIncludeSecondsStateChanged
        if ( this.isVisible() )
            ctrlSettings.setIncludeSeconds( chkIncludeSeconds.isSelected() );
    }//GEN-LAST:event_chkIncludeSecondsStateChanged

    private void tabGeneralsInitialFocus()
    {
        if ( rdbFileTypeText.isSelected() )
        {
            rdbFileTypeText.requestFocus();
        }
        else
        {
            rdbFileTypeBinary.requestFocus();
        }
    }
    
    private void tabLafInitialFocus()
    {
        if ( rbtFlatLaf.isSelected() )
        {
            rbtFlatLaf.requestFocus();
        }
        else
        {
            rbtIntelliJ.requestFocus();
        }
    }
    
    /** Updates this window components language to make them match with the
     * current language set by the user
     */
    public void updateComponentsLanguage()
    {
        this.setTitle( CtrlSettings.getLanguageBundle().getString( "key_settings" ) );
        
        lblStartingFolderPath.setText( CtrlSettings.getLanguageBundle().getString( "key_path_tag" ) );
        lblLanguage.setText( CtrlSettings.getLanguageBundle().getString( "key_language_tag" ) );
        btnSelectFolder.setText( CtrlSettings.getLanguageBundle().getString( "key_select" ) );
        btnValueByDefault.setText( CtrlSettings.getLanguageBundle().getString( "key_value_by_default" ) );
        buttonApply.setText( CtrlSettings.getLanguageBundle().getString( "key_apply_theme" ) );
        btnCancel.setText( CtrlSettings.getLanguageBundle().getString( "key_cancel" ) );
        lblLafThemesTitle.setText( CtrlSettings.getLanguageBundle().getString( "key_look_and_feel_title" ) );
        labelLafPreview.setText( CtrlSettings.getLanguageBundle().getString( "key_preview" ) );
        btnOk.setText( CtrlSettings.getLanguageBundle().getString( "key_ok" ) );
        rbtFlatLaf.setText( CtrlSettings.getLanguageBundle().getString( "key_flat_look_and_feel" ) );
        rbtIntelliJ.setText( CtrlSettings.getLanguageBundle().getString( "key_intellij_look_and_feel" ) );
        rdbFileTypeBinary.setText( CtrlSettings.getLanguageBundle().getString( "key_file_type_binary" ) );
        rdbFileTypeText.setText( CtrlSettings.getLanguageBundle().getString( "key_file_type_text" ) );
        tpSettings.setTitleAt( 0, CtrlSettings.getLanguageBundle().getString( "key_general_settings" ) );
        tpSettings.setTitleAt( 1, CtrlSettings.getLanguageBundle().getString( "key_world_clock" ) );
        tpSettings.setTitleAt( 2, CtrlSettings.getLanguageBundle().getString( "key_look_and_feel" ) );
        lblSound.setText( CtrlSettings.getLanguageBundle().getString( "key_sound_tag" ) );
        btnSelectSound.setText( CtrlSettings.getLanguageBundle().getString( "key_select" ) );
        chkAppOnTop.setText( CtrlSettings.getLanguageBundle().getString( "key_app_on_top" ) );
        chkFormat24Hours.setText( CtrlSettings.getLanguageBundle().getString( "key_24_hours_format" ) );
        chkIncludeSeconds.setText( CtrlSettings.getLanguageBundle().getString( "key_include_seconds" ) );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgFileType;
    private javax.swing.ButtonGroup btgTemaLaf;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnSelectFolder;
    private javax.swing.JButton btnSelectSound;
    private javax.swing.JButton btnValueByDefault;
    private javax.swing.JButton buttonApply;
    private javax.swing.JCheckBox chkAppOnTop;
    private javax.swing.JCheckBox chkFormat24Hours;
    private javax.swing.JCheckBox chkIncludeSeconds;
    private javax.swing.JComboBox<String> cmbAppLanguage;
    private javax.swing.JComboBox<String> cmbFlatLaf;
    private javax.swing.JComboBox<String> cmbIntelliJ;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel labelLafPreview;
    private javax.swing.JLabel lblLafThemesTitle;
    private javax.swing.JLabel lblLanguage;
    private javax.swing.JLabel lblSound;
    private javax.swing.JLabel lblSoundName;
    private javax.swing.JLabel lblStartingFolderPath;
    private javax.swing.JPanel panelGroupFileType;
    private javax.swing.JPanel panelGroupLanguage;
    private javax.swing.JPanel panelGroupStartingFolder;
    private javax.swing.JPanel panelGroupTimersSound;
    private javax.swing.JPanel panelGroupWorldClock;
    private javax.swing.JRadioButton rbtFlatLaf;
    private javax.swing.JRadioButton rbtIntelliJ;
    private javax.swing.JRadioButton rdbFileTypeBinary;
    private javax.swing.JRadioButton rdbFileTypeText;
    private javax.swing.JPanel tabPanelGenerals;
    private javax.swing.JPanel tabPanelLaf;
    private javax.swing.JPanel tabPanelWorldClock;
    private javax.swing.JTabbedPane tpSettings;
    private javax.swing.JTextField txtStartingFolder;
    // End of variables declaration//GEN-END:variables

}
