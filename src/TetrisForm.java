import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class TetrisForm {
    
    
    Color formColor;
    byte size = 20;
    boolean isAlive=true;
    
    
    
    JParPaint[] jPar = new JParPaint[4];
    
    
//    public TetrisForm(Color formColor, byte size) {
//        this.formColor = formColor;
//        if (size != 0) {this.size = size;} else {this.size = 20;}
//        
//        for(int i=0; i<=3;i++){
//            jPar[i] = new JPanel();
//            jPar[i].setSize(size, size);
//            jPar[i].setBackground(formColor);
//        }
//    }
    public TetrisForm(int formColor, byte size) {
        //this.formColor = formColor;
        if (size != 0) {this.size = size;} else {this.size = 20;}
        
        for(int i=0; i<=3;i++){
            jPar[i] = new JParPaint(formColor);
            jPar[i].setSize(size, size);
        }
    }    
    
    
    public static Point setPlayGroundCoord(int x, int y){
        Point pn = new Point(0, 0);
        pn.setLocation(20*x, 20*y);
        return pn;
    }
    
    public static Point getPlayGroundCoord(JPanel jP){
        Point pn = new Point(0, 0);
        if(jP != null){
            pn.setLocation((jP.getLocation().x)/20, (jP.getLocation().y)/20);
        }
        return pn;
    }    
    
    private boolean checkLeft(JPanel Pan){
        boolean isBlockLeft = false;
        int locX = getPlayGroundCoord(Pan).x;
        int locY = getPlayGroundCoord(Pan).y;

        if(locX<1 || locY<0){
            isBlockLeft = true;
        }else{
            if (Gui.playGround[locY][locX-1]==1){isBlockLeft=true;}
        }
        return isBlockLeft;        
    }
    public boolean toLeft(){
        
        boolean tol = true;
        if(getPlayGroundCoord(jPar[0]).x >= 0){
            for(int i=0; i<=3;i++){
                if(checkLeft(jPar[i])){
                    tol=false;
                }
            }
            if(tol){
                for(int i=0; i<=3;i++){
                    jPar[i].setLocation(jPar[i].getLocation().x - size, jPar[i].getLocation().y);
                }
            }
        }else{
            tol=false;
        }
            
        return tol;           
    }

    private boolean checkRight(JPanel Pan){
        boolean isBlockRight = false;
        int locX = getPlayGroundCoord(Pan).x;
        int locY = getPlayGroundCoord(Pan).y;

        if(locX>7 || locY<0){
            isBlockRight = true;
        }else{
            if (Gui.playGround[locY][locX+1]==1){isBlockRight=true;}
        }
        //System.out.println(isBlockRight);
        return isBlockRight;        
    }
    public boolean toRight(){
        boolean tor = true;
        if(getPlayGroundCoord(jPar[0]).x <= 7){
            for(int i=0; i<=3;i++){
                if(checkRight(jPar[i])){
                    tor=false;
                }
            }
            if(tor){
                for(int i=0; i<=3;i++){
                    jPar[i].setLocation(jPar[i].getLocation().x + size, jPar[i].getLocation().y);
                }
            }
        }else{
            tor=false;
        }
            
        return tor;        
    }
    
    private static boolean checkUnder(JPanel Pan){
        boolean isBlockunder = false;
//        if (Pan!= null){
            
            int locX = getPlayGroundCoord(Pan).x;
            int locY = getPlayGroundCoord(Pan).y;
            //System.out.println(locY+1 + " y-x " + locX);
            if(locY>=0){if (Gui.playGround[locY+1][locX]==1){isBlockunder=true;}}
//        }
        
        return isBlockunder;
    }
    
    private static boolean sameUnder(TetrisForm tetF, JPanel jPan){
        boolean same = false;
        JPanel[] jPf = tetF.getJPar();
        
        int xCoor = getPlayGroundCoord(jPan).x;
        int yCoor = 1 + getPlayGroundCoord(jPan).y;        

        for(int i=0; i<=3; i++){
            if(jPf[i] != null){
                if(getPlayGroundCoord(jPf[i]).x == xCoor){
                    if(getPlayGroundCoord(jPf[i]).y == yCoor){
                        same = true;
                    }
                }
            }
        }                  
        
        return same;
    }

    private static boolean toBottom23(TetrisForm tetF, JPanel Jpa, JPanel Jpb){
        boolean toB = true;
        JPanel[] Jp = new JPanel[2];
        
        Jp[0] = Jpa;
        Jp[1] = Jpb;
        //if (Jpa != null) {Jp[0].add(Jpa);}
        //if (Jpb != null) {Jp[1].add(Jpb);}

            
        for(int i=0; i<=1;i++){
            if (Jp[i] != null){ // && toB==true    
                if(checkUnder(Jp[i])){
                    if(!sameUnder(tetF, Jp[i])){
                        toB=false;
                    }
                }
            }
        }
        if(toB){
            for(int i=0; i<=1;i++){
                if (Jp[i] != null) {
                    Jp[i].setLocation(setPlayGroundCoord(getPlayGroundCoord(Jp[i]).x, getPlayGroundCoord(Jp[i]).y + 1));
                }
            }
        } 
        
        return toB;
    }
    
    public static boolean toBottom(TetrisForm tetF){
        boolean toB = true;
        JPanel[] jPf = tetF.getJPar();
        
        
        if (isWhole(tetF)){
            //is whole 
            //check if cand go down
            for(int i=0; i<=3;i++){
                if(checkUnder(jPf[i])){
                    if(!sameUnder(tetF, jPf[i])){
                        toB=false;
                        break;
                    }
                }
            }
            //if cand go under .. true
            if(toB){for(int i=0; i<=3;i++){
                jPf[i].setLocation(setPlayGroundCoord(getPlayGroundCoord(jPf[i]).x, getPlayGroundCoord(jPf[i]).y + 1));
            }}
        }else{
            //some special exceptions
            if(jPf[1] == null && jPf[2] == null){
                if(jPf[0] != null){toB = toBottom(jPf[0]);}
                if(jPf[3] != null){toB = toBottom(jPf[3]);}
                return toB;
            }
            if(jPf[1] == null ){//&& jPf[2] != null
                if(jPf[0] != null){toB = toBottom(jPf[0]);}
                if(jPf[2] != null && jPf[3] != null){
                    toB = toBottom23(tetF, jPf[2], jPf[3]);
                }else{
                    if(jPf[2] != null){toB = toBottom(jPf[2]);}
                    if(jPf[3] != null){toB = toBottom(jPf[3]);}
                }
                return toB;
            }
            if(jPf[2] == null ){//&& jPf[1] != null
                if(jPf[3] != null){toB = toBottom(jPf[3]);}
                if(jPf[0] != null && jPf[1] != null){
                    toB = toBottom23(tetF, jPf[0], jPf[1]);
                }else{
                    if(jPf[0] != null){toB = toBottom(jPf[0]);}
                    if(jPf[1] != null){toB = toBottom(jPf[1]);}
                }                
                return toB;
            }
            
            //general 
            toB = toBottomOldFormula(tetF);
            
        }

        return toB;
    }
    
    public static boolean toBottomOldFormula(TetrisForm tetF){
        boolean toB = true;
        //boolean sameTF = false;
        
        JPanel[] jPf = tetF.getJPar();

        for(int i=0; i<=3;i++){
            if (jPf[i] != null){ // && toB==true    
                if(checkUnder(jPf[i])){
                    if(!sameUnder(tetF, jPf[i])){
                        toB=false;
                    }
                }else{
                    
                }
            }
        }
        
        if(toB){
            for(int i=0; i<=3;i++){
                if (jPf[i] != null) {
                    jPf[i].setLocation(setPlayGroundCoord(getPlayGroundCoord(jPf[i]).x, getPlayGroundCoord(jPf[i]).y + 1));
                }
            }
        }

        return toB;
    }    
    //Overloaded method toBottom
    public static boolean toBottom(JPanel tetF){
        boolean tob = true;
        
        
            if(checkUnder(tetF)){tob=false;}
            
            if(tob){
                    tetF.setLocation(setPlayGroundCoord(getPlayGroundCoord(tetF).x, getPlayGroundCoord(tetF).y + 1));
            }
        return tob;
    }    
    
    public void setCubeNull(){
        for(int i=0; i<=3;i++){
            jPar[i] = null;
        }
    }

    public static boolean isNull(TetrisForm tetF){
        boolean isNull=true;
        JPanel[] Pan = tetF.getJPar();
        
        for(int i=0; i<=3; i++){
            if (Pan[i] != null){isNull=false;}
        }
        
        return isNull;
    }

    public static boolean isWhole(TetrisForm tetF){
        boolean isWhole=true;
        JPanel[] Pan = tetF.getJPar();
        
        for(int i=0; i<=3; i++){
            if (Pan[i] == null){isWhole=false;break;}
        }
        
        return isWhole;        
    }
    
    //Getters for JPannels
    public boolean isAlive(){
        return isAlive;
    }
    
    public void isAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    
    //Setters for panel locations;
    public void setJPar0Location(Point p){
        jPar[0].setLocation(p);
    }      
    public void setJPar1Location(Point p){
        jPar[1].setLocation(p);
    }    
    public void setJPar2Location(Point p){
        jPar[2].setLocation(p);
    }
    public void setJPar3Location(Point p){
        jPar[3].setLocation(p);
    }
    
        //Getters for JPannels instances;
    public JPanel[] getJPar(){
        return jPar;
    }
    public JPanel getJPar0(){
        return jPar[0];
    }
    public JPanel getJPar1(){
        return jPar[1];
    }    
    public JPanel getJPar2(){
        return jPar[2];
    }
    public JPanel getJPar3(){
        return jPar[3];
    }    
    //Getters for JPannel locations 
    public Point getJPar0Location(){
        return jPar[0].getLocation();
    }      
    public Point getJPar1Location(){
        return jPar[1].getLocation();
    }    
    public Point getJPar2Location(){
        return jPar[2].getLocation();
    }   
    public Point getJPar3Location(){
        return jPar[3].getLocation();
    }    
    
    
    public class JParPaint extends JPanel{
        int paintColor;
        Image img;
        public JParPaint(int paintColor) {
            if((paintColor<0) || (paintColor>7)){paintColor=1;}
            this.paintColor = paintColor;
            
        }
        
        
        
        @Override
        public void paintComponent(Graphics g){
            switch(paintColor){
                case 0:
                    img = new ImageIcon("tetrdata\\tetrblock\\cube.png").getImage();
                    g.drawImage(img, 0 , 0, this);  
                    break;
                case 1:
                    img = new ImageIcon("tetrdata\\tetrblock\\zet.png").getImage();
                    g.drawImage(img, 0 , 0, this);  
                    break;
                case 2:
                    img = new ImageIcon("tetrdata\\tetrblock\\T.png").getImage();
                    g.drawImage(img, 0 , 0, this);  
                    break;
                case 3:
                    
                    img = new ImageIcon("tetrdata\\tetrblock\\line.png").getImage();
                    g.drawImage(img, 0 , 0, this);  
                    break;                    
                case 4:
                    
                    img = new ImageIcon("tetrdata\\tetrblock\\L.png").getImage();
                    g.drawImage(img, 0 , 0, this);  
                    break;                    
                case 5:
                    
                    img = new ImageIcon("tetrdata\\tetrblock\\zetmirror.png").getImage();
                    g.drawImage(img, 0 , 0, this);  
                    break;
                case 6:
                    img = new ImageIcon("tetrdata\\tetrblock\\LMirror.png").getImage();
                    g.drawImage(img, 0 , 0, this);  
                    break;                    
            }
            
        }
        
    }

    
    
}
