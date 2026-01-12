package com.mots.pendule.app;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Draws the hangman scaffold and body based on the current error count.
 */
public final class HangmanPanel extends JPanel {
    private static final int MAX_STAGE = 6;

    private int stage = 0;

    public HangmanPanel() {
        setPreferredSize(new Dimension(240, 220));
    }

    public void setProgress(int errorsMade, int maxErrors) {
        int normalized = 0;
        if (maxErrors > 0) {
            double ratio = Math.min(1.0, errorsMade / (double) maxErrors);
            normalized = (int) Math.round(ratio * MAX_STAGE);
        }
        normalized = Math.max(0, Math.min(MAX_STAGE, normalized));
        if (normalized != stage) {
            stage = normalized;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(44, 62, 80));
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int w = getWidth();
        int h = getHeight();

        int baseY = h - 20;
        int poleX = 40;
        int topY = 20;
        int beamX = 140;
        int ropeY = 40;

        // scaffold
        g2.drawLine(poleX, baseY, beamX, baseY);
        g2.drawLine(poleX, baseY, poleX, topY);
        g2.drawLine(poleX, topY, beamX, topY);
        g2.drawLine(beamX, topY, beamX, ropeY);

        int headCenterX = beamX;
        int headCenterY = ropeY + 18;
        int headRadius = 16;
        int bodyTopY = headCenterY + headRadius;
        int bodyBottomY = bodyTopY + 50;

        if (stage >= 1) {
            g2.drawOval(headCenterX - headRadius, headCenterY - headRadius, headRadius * 2, headRadius * 2);
        }
        if (stage >= 2) {
            g2.drawLine(headCenterX, bodyTopY, headCenterX, bodyBottomY);
        }
        if (stage >= 3) {
            g2.drawLine(headCenterX, bodyTopY + 10, headCenterX - 22, bodyTopY + 30);
        }
        if (stage >= 4) {
            g2.drawLine(headCenterX, bodyTopY + 10, headCenterX + 22, bodyTopY + 30);
        }
        if (stage >= 5) {
            g2.drawLine(headCenterX, bodyBottomY, headCenterX - 20, bodyBottomY + 30);
        }
        if (stage >= 6) {
            g2.drawLine(headCenterX, bodyBottomY, headCenterX + 20, bodyBottomY + 30);
        }

        g2.dispose();
    }
}
