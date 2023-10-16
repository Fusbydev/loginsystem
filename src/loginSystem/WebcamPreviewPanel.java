package loginSystem;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.github.sarxos.webcam.*;

class WebcamPreviewPanel extends JPanel {
    private WebcamPanel webcamPanel;

    public WebcamPreviewPanel() {
        webcamPanel = new WebcamPanel(Webcam.getDefault());
        webcamPanel.setFPSDisplayed(false);
        webcamPanel.setDisplayDebugInfo(false);
        webcamPanel.setImageSizeDisplayed(false);
        webcamPanel.setMirrored(true);
        webcamPanel.setPreferredSize(WebcamResolution.VGA.getSize());
        webcamPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setFont(new Font("Arial", Font.BOLD, 12));
        webcamPanel.setDisplayDebugInfo(true);
        this.setBackground(Color.lightGray);
        this.add(webcamPanel);
    }

    public BufferedImage getImage() {
        return webcamPanel.getImage();
    }
}
