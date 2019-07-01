import javax.swing.*;
public class AnimFrame extends AnimPanel
{
    public AnimFrame(){}
    public void go()
    {
        JFrame dFrame = new JFrame("Drawing Frame");
        dFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dFrame.getContentPane().add(new AnimPanel());
        dFrame.pack();
        dFrame.setVisible(true);
    }
}