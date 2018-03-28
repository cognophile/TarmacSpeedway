package main.java.client;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class BaseWindow extends JFrame
{
    private JFrame window;
    private List<Component> components = new ArrayList<Component>();
    private Container contentPane;

    public BaseWindow()
    {
        this.composeWindow();
    }

    public BaseWindow(String windowTitle)
    {
        this.composeWindow(windowTitle);
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

    public void close()
    {
        this.window.setVisible(false);
        this.window.dispose();
    }

    private void composeWindow()
    {
        this.window = new JFrame();
        this.contentPane = this.window.getContentPane();
    }

    private void composeWindow(String title)
    {
        this.window = new JFrame(title);
        this.contentPane = this.window.getContentPane();
    }

    @Override
    public int getWidth()
    {
        return this.window.getWidth();
    }

    @Override
    public int getHeight()
    {
        return this.window.getHeight();
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
