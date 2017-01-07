
import java.awt.Color;
import java.awt.Point;


public class LineForm extends TetrisForm {
    private int position=0;
    
    
    public LineForm(byte size) {
        //super(Color.cyan, size);
        super(3, size);
    }
    
    public void setLineLocation(int rotate, int x, int y){
        switch(rotate){
            case 0:
                rotate0(x, y);
                break;
            case 1:
                rotate1(x, y);
                break;
//            case 2:
//                rotate2(x, y);
//                break;
//            case 3:
//                rotate3(x, y);
//                break;
        }
    }
    
        
    public int getPosition(){
        return position;
    }
    
    public int rotateClockwise(boolean forward){
        if(forward){
            if(position==1){
                position=0;
            }else{
                position++;
            }
        }else{
            if(position==0){
                position=1;
            }else{
                position--;
            }            
        }
        return position;
    }
    
    private void rotate0(int x, int y){
        //  x  3
        //  x  JPar2
        //  x  JPar1
        //  X   0
        boolean canRotate=true;
        if(y>2){
            int[][] playG = Gui.getPLayGround();

            if(playG[y-1][x] == 1){canRotate=false;}
            if(playG[y-2][x] == 1){canRotate=false;}
            if(playG[y-3][x] == 1){canRotate=false;}
        
        }else{
            canRotate=true;
        }
        
        if(canRotate){      
            jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
            setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x, y-1)));
            setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x,y-2)));
            setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x,y-3)));  
        }
    }
    private void rotate1(int x, int y){
        //  X x x x
        if(x>5){x=5;}
        
        boolean canRotate=true;
        if(y>-1){
            int[][] playG = Gui.getPLayGround();

            if(playG[y][x+1] == 1){canRotate=false;}
            if(playG[y][x+2] == 1){canRotate=false;}
            if(playG[y][x+3] == 1){canRotate=false;}
        
        }else{
            canRotate=true;
        }
        
        if(canRotate){              
            jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
            setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x+1, y)));
            setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x+2,y)));
            setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x+3,y)));
        }        
    }
    
//    private void rotate2(int x, int y){
//        //  x 
//        //  x
//        //  X
//        jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
//        setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x, y-1)));
//        setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x,y-2)));
//        setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x,y-3)));        
//    }
//    private void rotate3(int x, int y){
//        //  X x x
//        jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
//        setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x+1, y)));
//        setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x+2,y)));
//        setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x+3,y)));        
//    }
}
