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

    /**
     * Create a BaseWindow object with a specified window title
     * @param windowTitle
     */
    public BaseWindow(String windowTitle)
    {
        this.composeWindow(windowTitle);
    }

    /**
     * Render the BaseWindow
     */
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

    /**
     * Add a Component or derived object of Component to this BaseWindow
     * @param component
     */
    public void addComponents(Component component)
    {
        if (component != null) {
            this.components.add(component);
        }
    }

    /**
     * Dispose of the BaseWindow
     */
    public void close()
    {
        this.window.setVisible(false);
        this.window.dispose();
    }

    /**
     * Set the size of the BaseWindow
     * @param width
     * @param height
     */
    public void setWindowSize(int width, int height)
    {
        this.window.setSize(width, height);
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

    private void loadComponents()
    {
        for (final Component obj : this.components)
        {
            this.contentPane.add(obj);
        }
    }
}
