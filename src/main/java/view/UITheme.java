package view;

import java.awt.*;

/**
 * Simple theme configuration for consistent UI styling
 */
public class UITheme {

    // Color Palette - Professional blue theme
    public static final Color PRIMARY = new Color(41, 128, 185);      // Blue
    public static final Color PRIMARY_DARK = new Color(31, 97, 141);  // Darker blue
    public static final Color ACCENT = new Color(46, 204, 113);       // Green
    public static final Color BACKGROUND = new Color(236, 240, 241);  // Light gray
    public static final Color PANEL_BG = Color.WHITE;
    public static final Color TEXT_AREA_BG = new Color(250, 250, 250);
    public static final Color BORDER_COLOR = new Color(189, 195, 199);
    public static final Color TEXT_COLOR = new Color(44, 62, 80);

    // Fonts
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font TEXT_AREA_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    // Spacing
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 12;
    public static final int PADDING_LARGE = 16;

    /**
     * Create a styled button with primary color
     */
    public static void styleButton(javax.swing.JButton button, boolean isPrimary) {
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        if (isPrimary) {
            button.setBackground(PRIMARY);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(BACKGROUND);
            button.setForeground(TEXT_COLOR);
        }

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isPrimary) {
                    button.setBackground(PRIMARY_DARK);
                } else {
                    button.setBackground(new Color(220, 220, 220));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (isPrimary) {
                    button.setBackground(PRIMARY);
                } else {
                    button.setBackground(BACKGROUND);
                }
            }
        });
    }

    /**
     * Create a styled titled border
     */
    public static javax.swing.border.Border createTitledBorder(String title) {
        return javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER_COLOR, 1),
            title,
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            TITLE_FONT,
            TEXT_COLOR
        );
    }
}
