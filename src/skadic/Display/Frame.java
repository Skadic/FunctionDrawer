package skadic.Display;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    FunctionDisplay canvas;
    Dimension dim;

    public Frame(String title, int width, int height, int drawQuality) throws HeadlessException {
        super(title);

        dim = new Dimension(width, height);
        this.setPreferredSize(dim);
        this.setSize(dim);

        canvas = new FunctionDisplay(dim, drawQuality);
        this.getContentPane().add(canvas);

        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

    public int getWidth(){
        return (int) dim.getWidth();
    }

    public int getHeight(){
        return (int) dim.getHeight();
    }

    public FunctionDisplay getFunctionDisplay() {
        return canvas;
    }
}
