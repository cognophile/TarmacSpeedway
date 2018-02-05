package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class CoreWindow extends JFrame
{
    private JFrame window;
    private List<Component> components = new ArrayList<Component>();
    private Container contentPane;

    public CoreWindow(String windowTitle)
    {
        this.window = new JFrame(windowTitle);
        this.contentPane = this.window.getContentPane();
    }

    public void render()
    {
        this.loadComponents();
        this.window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        this.window.setVisible(true);
    }

    public void setWindowSize(int width, int height)
    {
        this.window.setSize(width, height);
    }

    public void addComponents(Component component)
    {
        if (component != null) {
            this.components.add(component);
        }
    }

    private void loadComponents()
    {
        for (final Component obj : this.components)
        {
            this.contentPane.add(obj);
        }
    }
}
