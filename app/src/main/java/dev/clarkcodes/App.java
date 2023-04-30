package dev.clarkcodes;

/* LICENSE
 * Creative CommonUtils Zero v1.0 Universal
 * CC0 1.0 Universal
 * Please check out the license file in this project's root folder.
 */

import javax.swing.UIManager;
import views.FrmSplash;

/**
 * Smallville Clock - Simple Useful Clock
 * @author Clark - ClarkCodes
 */
public class App 
{
    public static void main(String[] args) 
    {   // FlatLaf Customizations
        UIManager.put( "Component.arc", 5 );
        UIManager.put( "TextComponent.arc", 5 );
        UIManager.put( "Button.arc", 10 );

        /* Create and display the form */
        java.awt.EventQueue.invokeLater( () ->
        {
            new Thread( new FrmSplash() ).start();
        });
    }
}