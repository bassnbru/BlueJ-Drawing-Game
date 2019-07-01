import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class AnimPanel extends JPanel
{
    private int WIDTH  = 1360, HEIGHT = 767;    
    private int mousex = 0, mousey = 0;
    private int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    private boolean redo = false;
    private Color background = new Color(10,10,10);
    private Color currentColor = Color.black;
    private boolean fillStyle = false, chainmode = false, rainbow = false,safety = false;
    private int gstyle = 3; //see below  
    private int wid = 1; //width boi
    private int rPoint = 0;
    //button time yay. every box is 50x50
    private int resetX = 0,   rectX  = 50,  circX  = 100,  lineX  = 150, fillX  = 200, chaiX  = 250, 
                plusX  = 300, minuX  = 325, rainX  = 350, customX = 1300;
    public AnimPanel()
    {    
        addMouseListener(new DotsListener());       
        setBackground(background);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
    public void paint(Graphics g)
    {       
        //super.paintComponent(g);
        if(((x1 >= resetX && x1 <= 50) && (y1 >= resetX && y1 <=50)))//reset bounds
        {
            //resets the layer by drawing rectangle that clears to background level (eraser)
            g.setColor(background);
            g.clearRect(0,0,WIDTH,HEIGHT);
            g.setColor(Color.black);  
            safety = true;
        }
        else if(((x1 >= rectX && x1 <= rectX+50) && (y1 >= 0 && y1 <=50)))
            gstyle = 1;// drawing rectangles
        else if(((x1 >= circX && x1 <= circX+50) && (y1 >= 0 && y1 <=50)))
            gstyle = 2;// drawing circs
        else if(((x1 >= lineX && x1 <= lineX+50) && (y1 >= 0 && y1 <=50)))        
            gstyle = 3;// drawing lines   
        else if(((x1 >= fillX && x1 <= fillX+50) && (y1 >= 0 && y1 <=50)))
            {fillStyle = !fillStyle; safety = true;}//fill style button
        else if(((x1 >= chaiX && x1 <= chaiX+50) && (y1 >= 0 && y1 <=50)))
            {chainmode = !chainmode; safety = true;}//chain mode button
        else if(((x1 >= customX && x1 <= customX+60) && (y1 >= 0 && y1 <=511)))    
            {currentColor = rgb(y1); safety = true;}//makeshift slider for color choice
        else if(((x1 >= customX && x1 <= customX+60) && (y1 > 512 && y1 <767)))    
            {currentColor = new Color(767-y1,767-y1,767-y1); safety = true;}//^^
        else if(((x1 >= plusX && x1 <= plusX+25) && (y1 >= 0 && y1 <=50)))//plus and minus buttons        
        {
             if(wid >= 1 && wid < 5)
                wid++;               
        }
        else if(((x1 >= minuX && x1 <= minuX+25) && (y1 >= 0 && y1 <=50)))        
        {  
             if(wid > 1 && wid <= 5)
                wid--; 
        }
        else if(((x1 >= rainX && x1 <= rainX+50) && (y1 >= 0 && y1 <=50)))
            {rainbow = !rainbow; safety = true;}
        else//must not be a button. they tryna draw
        if(!fillStyle)
        {
            if(rainbow)//get next rgb for drawing rainbow
            {
                g.setColor(rgb(rPoint));
                for(int i = 0; i <= 5; i++)//more = less rainbow legnth
                    rPoint++;
                if(rPoint >= 510)
                    rPoint = 0;
            }    
            else
                g.setColor(currentColor);
            if(gstyle ==1)//draw rect
            {
                if(x2 > x1 && y2 > y1)//down right slope 
                {
                    int distX = Math.abs(x2-x1), distY = Math.abs(y2-y1);
                    g.drawRect(x1,y1,distX,distY);
                }
                else if(x2 < x1 && y2 > y1)//down left slope 
                {
                    int distX = Math.abs(x2-x1), distY = Math.abs(y2-y1);
                    g.drawRect(x2,y1,distX,distY);
                }
                else if(x2 > x1 && y2 < y1)//up right slope 
                {
                    int distX = Math.abs(x2-x1), distY = Math.abs(y2-y1);
                    g.drawRect(x1,y2,distX,distY);
                }
                else if(x2 < x1 && y2 < y1)//up left slope 
                {
                    int distX = Math.abs(x2-x1), distY = Math.abs(y2-y1);
                    g.drawRect(x2,y2,distX,distY);
                }
            }
            else if(gstyle ==2)//draw circ
            {
                int dist = Math.abs((int)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)) * 2);//get the radius for the circle using distance form
                g.drawOval(x1-(int)(dist/2),y1-(int)(dist/2),dist,dist);
            }
            else if(gstyle == 3)//drawline w/ width
            {
                for(int i = 0; i <= wid; i++)
                {
                    g.drawLine(x1+i, y1+i, x2+i, y2+i);
                    g.drawLine(x1-i, y1-i, x2-i, y2-i);  
                }
            }
        }
        else
        {
            if(rainbow)
            {
                g.setColor(rgb(rPoint));
                rPoint++; rPoint++; rPoint++;
                if(rPoint == 510)
                    rPoint = 0;
            }    
            else
                g.setColor(currentColor);
            if(gstyle ==1)//fill rect
            {
                if(x2 > x1 && y2 > y1)//down right slope 
                {
                    int distX = Math.abs(x2-x1), distY = Math.abs(y2-y1);
                    g.fillRect(x1,y1,distX,distY);
                }
                else if(x2 < x1 && y2 > y1)//down left slope 
                {
                    int distX = Math.abs(x2-x1), distY = Math.abs(y2-y1);
                    g.fillRect(x2,y1,distX,distY);
                }
                else if(x2 > x1 && y2 < y1)//up right slope 
                {
                    int distX = Math.abs(x2-x1), distY = Math.abs(y2-y1);
                    g.fillRect(x1,y2,distX,distY);
                }
                else if(x2 < x1 && y2 < y1)//up left slope 
                {
                    int distX = Math.abs(x2-x1), distY = Math.abs(y2-y1);
                    g.fillRect(x2,y2,distX,distY);
                }
            }
            else if(gstyle ==2)//fill circ
            {
                int dist = (int)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)) * 2;
                dist = Math.abs(dist);
                g.fillOval(x1-(int)(dist/2),y1-(int)(dist/2),dist,dist);
            }
            else if(gstyle == 3)//line 
                for(int i = 0; i <= wid; i++)
                {
                    g.drawLine(x1+i, y1+i, x2+i, y2+i);
                    g.drawLine(x1-i, y1-i, x2-i, y2-i);  
                }
        }
        //boundry
        g.setColor(Color.black);
        g.fillRect(0,0,1360,60);
        g.fillRect(1293,0,67,767);
        //draw info
        g.setColor(currentColor);
        g.fillRect(1250,0,50,50);
        g.setColor(Color.white);
        g.drawString(""+wid,1240,30);
        //tool bar items
        g.setColor(Color.red);
        g.fillRect(0,0,50,50);
        //if toggled turn green, else white
        if(gstyle == 1)g.setColor(Color.green); else g.setColor(Color.white);
        g.fillRect(rectX,0,50,50); 
        if(gstyle == 2)g.setColor(Color.green); else g.setColor(Color.white);
        g.fillRect(circX,0,50,50);
        if(gstyle == 3)g.setColor(Color.green); else g.setColor(Color.white);
        g.fillRect(lineX,0,50,50);
        if(fillStyle)g.setColor(Color.green);   else g.setColor(Color.white);
        g.fillRect(fillX,0,50,50);
        if(chainmode)g.setColor(Color.green);   else g.setColor(Color.white);
        g.fillRect(chaiX,0,50,50);
        if(rainbow)g.setColor(Color.green);     else g.setColor(Color.white);
        g.fillRect(rainX,0,50,50);
        //tool bar text
        g.setColor(Color.black);
        g.drawString("Reset",10,30);
        g.drawString("Rect",rectX+10,30);
        g.drawString("Circ",circX+10,30);
        g.drawString("Line",lineX+10,30);
        g.drawString("Fill",fillX+10,30);
        g.drawString("Chain",chaiX+10,30);
        g.setColor(Color.white);
        g.drawString("+",plusX+2,30);
        g.drawString("-",minuX+2,30);
        g.setColor(Color.black);
        g.drawString("Rain",rainX+10,30);
        for(int i = 0; i <= 511; i++)//color fade
        {
            g.setColor(rgb(i));
            g.drawLine(1310,i,1360,i);
        }
        for(int i = 255; i >= 0; i--)//dark fade
        {
            g.setColor(new Color(i,i,i));
            g.drawLine(1310,767-i,1360,767-i);
        }
    }
    public Color rgb(int point)//very brute force rainbow value. math = gay
    {
        Color clr = new Color(255,0,0);
        int slide = -1, rr = 255, gg = 0, bb = 0;  
        boolean ry, yg, gc, cb, bp, pr, over = false; ry = yg = gc = cb = bp = pr = false;
        while(!over)
        {   
            slide++; 
            if(ry && yg && gc && cb && bp && pr) over = true;
                 if(!ry){gg++; gg++; gg++; if(gg >= 255)ry=true;}
            else if(!yg){rr--; rr--; rr--; if(rr <= 0)  yg=true;}
            else if(!gc){bb++; bb++; bb++; if(bb >= 255)gc=true;}
            else if(!cb){gg--; gg--; gg--; if(gg <= 0)  cb=true;}
            else if(!bp){rr++; rr++; rr++; if(rr >= 255)bp=true;}
            else if(!pr){bb--; bb--; bb--; if(bb <= 0)  pr=true;}
            clr = new Color(rr,gg,bb);
            if(slide == point)
                over = true;
        }       
        return clr;
    }
    private class DotsListener implements MouseListener
    {
        public void mousePressed(MouseEvent event)
        {   
            if(!redo)
            {
                Point mousePos = event.getPoint(); //x1 and y1 are cords of where mouse was pressed
                x1 = mousePos.x;
                y1 = mousePos.y;
                if(!safety)
                {
                    if(chainmode)
                        repaint();
                }
                else
                    safety = !safety;
            }
        }
        public void mouseReleased (MouseEvent event)
        {
            if(!redo)
            {
                Point mousePos = event.getPoint();//x2,y2 are where released, to simulate a graphics dragged movement
                x2 = mousePos.x;
                y2 = mousePos.y;
                if(!safety)
                    repaint();//only draw when released, so the drawing is what is dragged
            }
            else
            redo = true;
        }
        public void mouseClicked  (MouseEvent event){}
        public void mouseEntered  (MouseEvent event){}
        public void mouseExited   (MouseEvent event){}
    }
    //listener from mouse
}