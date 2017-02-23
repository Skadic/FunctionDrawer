package skadic.display;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.function.Function;

/**
 * Created by eti22 on 14.02.2017.
 */
public class FunctionDisplay extends JComponent{

    private double scale, scaleX, scaleY;
    private int xOffset, yOffset;
    private Function<Double, Double> func;

    public FunctionDisplay(Dimension d, int scale) {
        this.setSize(d);
        this.scale = Math.max(scale, 1);
        this.xOffset = 0;
        this.yOffset = 0;
        this.scaleX = 1;
        this.scaleY = 1;
        this.func = x -> x;
        initKeyActions();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0x111111));
        g.fillRect(0,0, getWidth(), getHeight());

        g.setColor(Color.white);

        drawHUD(g);

        g.setColor(Color.RED);
        for (double x = -getWidth() / 2; x < getWidth() / 2; x++) {
            double x1 = x, x2 = x + 1;
            g.drawLine(
                    convertPosX(x1 * scaleX),
                    convertPosY(computeFunction(x1 / scale) * scaleY * scale - yOffset),
                    convertPosX(x2 * scaleX),
                    convertPosY(computeFunction(x2 / scale) * scaleY * scale - yOffset)
            );
        }
    }

    private void initKeyActions(){
        InputMap inMap = getInputMap();
        ActionMap acMap = getActionMap();

        inMap.put(KeyStroke.getKeyStroke("UP"), "incrYOffset");
        inMap.put(KeyStroke.getKeyStroke("DOWN"), "decrYOffset");
        inMap.put(KeyStroke.getKeyStroke("LEFT"), "decrXOffset");
        inMap.put(KeyStroke.getKeyStroke("RIGHT"), "incrXOffset");
        inMap.put(KeyStroke.getKeyStroke("W"), "scaleUp");
        inMap.put(KeyStroke.getKeyStroke("S"), "scaleDown");
        inMap.put(KeyStroke.getKeyStroke("E"), "scaleXUp");
        inMap.put(KeyStroke.getKeyStroke("D"), "scaleXDown");
        inMap.put(KeyStroke.getKeyStroke("R"), "scaleYUp");
        inMap.put(KeyStroke.getKeyStroke("F"), "scaleYDown");
        inMap.put(KeyStroke.getKeyStroke("X"), "resetScaleX");
        inMap.put(KeyStroke.getKeyStroke("Y"), "resetScaleY");

        final double SCALE_FACTOR = 2;

        acMap.put("decrYOffset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yOffset -= 20;
                paint(getGraphics());
            }
        });
        acMap.put("incrYOffset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yOffset += 20;
                paint(getGraphics());
            }
        });
        acMap.put("incrXOffset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xOffset -= 20;
                paint(getGraphics());
            }
        });
        acMap.put("decrXOffset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xOffset += 20;
                paint(getGraphics());
            }
        });
        acMap.put("scaleUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scale = scale * SCALE_FACTOR;
                paint(getGraphics());
            }
        });
        acMap.put("scaleDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scale = scale / SCALE_FACTOR;
                paint(getGraphics());
            }
        });
        acMap.put("scaleXUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleX = scaleX * SCALE_FACTOR;
                paint(getGraphics());
            }
        });
        acMap.put("scaleXDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleX = scaleX / SCALE_FACTOR;
                paint(getGraphics());
            }
        });
        acMap.put("scaleYUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleY = scaleY * SCALE_FACTOR;
                paint(getGraphics());
            }
        });
        acMap.put("scaleYDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleY = scaleY / SCALE_FACTOR;
                paint(getGraphics());
            }
        });
        acMap.put("resetScaleY", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleY = 1;
                paint(getGraphics());
            }
        });
        acMap.put("resetScaleX", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleX = 1;
                paint(getGraphics());
            }
        });

    }

    private void drawHUD(Graphics g){
        g.setFont(Font.getFont("Arial"));
        g.drawString("Scaling: " + scale, 30, 20);
        g.drawString("X Scaling: " + scaleX, 30, 30);
        g.drawString("Y Scaling: " + scaleY, 30, 40);
        g.drawString("X Offset: " + xOffset, 30, 50);
        g.drawString("Y Offset: " + yOffset, 30, 60);

        //X-Axis
        g.drawLine(
                0,
                getHeight() / 2 + yOffset,
                getWidth(),
                getHeight() / 2 + yOffset);

        for (int i = (-getWidth() - xOffset) / 2; i < (getWidth() - xOffset) / 2; i++) {
            if(i % 25 == 0) {
                g.drawLine(
                        convertPosX(i),
                        getHeight() / 2 + yOffset - 3,
                        convertPosX(i),
                        getHeight() / 2 + yOffset
                );
                String num = Double.toString(Math.round(i / scaleX / scale * 100) / 100D);
                g.drawString(
                        num.endsWith(".0") ? num.replace(".0", "") : num,
                        convertPosX(i),
                        getHeight() / 2 + 12 + yOffset);
            }
        }

        //Y-Axis
        g.drawLine(
                getWidth() / 2 + xOffset,
                0,
                getWidth() / 2 + xOffset,
                getHeight());

        for (int i = (-getHeight()  + yOffset) / 2; i < (getHeight() + yOffset) / 2; i++) {
            if(i % 25 == 0){
                g.drawLine(
                        getWidth() / 2 - 3 + xOffset,
                        convertPosY(i - yOffset),
                        getWidth() / 2 + xOffset,
                        convertPosY(i - yOffset)
                );
                String num = Double.toString(Math.round(i / scaleY / scale * 100) / 100D);
                g.drawString(
                        num.endsWith(".0") ? num.replace(".0", "") : num,
                        getWidth() / 2 + 5 + xOffset,
                        convertPosY(i - yOffset) + 5);
            }
        }
    }

    private int convertPosX(double x){
        return (int) (getWidth() / 2 + xOffset + x * 2);
    }

    private int convertPosY(double y){
        return (int) (getHeight() / 2 - yOffset + y * 2 * -1);
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public void addXOffset(int x){
        xOffset += x;
    }

    public void addYOffset(int y){
        yOffset += y;
    }

    private double computeFunction(double x){
        return func.apply(x);
    }

    public void setFunction(Function<Double, Double> func) {
        this.func = func;
    }
}
