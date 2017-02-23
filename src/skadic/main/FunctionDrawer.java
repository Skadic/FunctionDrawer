package skadic.main;

import skadic.display.Frame;
import skadic.math.Evaluation;

import java.text.MessageFormat;

/**
 * Created by eti22 on 14.02.2017.
 */
public class FunctionDrawer {

    private Frame display;

    public FunctionDrawer() {
        display = new Frame("Function Drawer", 1920, 1080, 25);
        display.getFunctionDisplay().setFunction(x -> Evaluation.eval(MessageFormat.format("exp({0})", x.toString())));
    }
}
