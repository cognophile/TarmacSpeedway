package main.java;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel
{
    private String headerText;

    public HeaderPanel(String headerText)
    {
        // super(new BorderLayout(2,2));

        this.headerText = headerText;
        this.add(new JLabel(headerText, JLabel.CENTER));
    }
}
