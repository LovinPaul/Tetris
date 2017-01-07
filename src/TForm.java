
import java.awt.Color;
import java.awt.Point;


public class TForm extends TetrisForm {
    private int position=0;
    
    public TForm( byte size) {
        //super(Color.MAGENTA, size);
        super(2, size);
    }
    public void setTLocation(int rotate, int x, int y){
        //int plm = ;
        switch(rotate){//(int)(Math.random() * 4)
            case 0:
                rotate0(x, y);
                position=0;
                break;
            case 1:
                rotate1(x, y);
                position=1;
                break;
            case 2:
                rotate2(x, y);
                position=2;
                break;
            case 3:
                rotate3(x, y);
                position=3;
                break;
        }
        //System.out.println(position);
    }
    
    public int getPosition(){
        return position;
    }
    
    public int rotateClockwise(boolean forward){
        if(forward){
            if(position==3){
                position=0;
            }else{
                position++;
            }
        }else{
            if(position==0){
                position=3;
            }else{
                position--;
            }            
        }
        return position;
    }

    public void rotate0(int x, int y){
        //    x
        //  X x x
        if(x>6){x=6;}
        
        boolean canRotate=true;
        if(y>0){
            int[][] playG = Gui.getPLayGround();

            if(playG[y][x+1] == 1){canRotate=false;}
            if(playG[y][x+2] == 1){canRotate=false;}
            if(playG[y-1][x+1] == 1){canRotate=false;}
        
        }else{
            canRotate=true;
        }
        
        if(canRotate){              
            jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
            setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x+1, y)));
            setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x+2,y)));
            setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x+1,y-1))); 
        }
    }

    public void rotate1(int x, int y){
        //  x
        //  x x    JPar1 JPar2
        //  X
        if(x>7){x=7;}

        boolean canRotate=true;
        if(y>1){
            int[][] playG = Gui.getPLayGround();

            if(playG[y-1][x] == 1){canRotate=false;}
            if(playG[y-1][x+1] == 1){canRotate=false;}
            if(playG[y-2][x] == 1){canRotate=false;}
        
        }else{
            canRotate=true;
        }
        
        if(canRotate){              
            jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
            setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x, y-1)));
            setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x+1,y-1)));
            setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x,y-2))); 
        }
    }
    
    public void rotate2(int x, int y){
        //  x x x
        //    X
        if(x>7){x=7;}
        if(x<1){x=1;}
        
        boolean canRotate=true;
        if(y>0){
            int[][] playG = Gui.getPLayGround();

            if(playG[y-1][x] == 1){canRotate=false;}
            if(playG[y-1][x+1] == 1){canRotate=false;}
            if(playG[y-1][x-1] == 1){canRotate=false;}
        
        }else{
            canRotate=true;
        }
        
        if(canRotate){              
            jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
            setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x, y-1)));
            setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x+1,y-1)));
            setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x-1,y-1)));
        }
    }
    public void rotate3(int x, int y){
        //    x 
        //  x x   JPar1 JPar2
        //    X 
        if(x<1){x=1;}
        
        boolean canRotate=true;
        if(y>1){
            int[][] playG = Gui.getPLayGround();

            if(playG[y-1][x] == 1){canRotate=false;}
            if(playG[y-1][x-1] == 1){canRotate=false;}
            if(playG[y-2][x] == 1){canRotate=false;}
        
        }else{
            canRotate=true;
        }
        
        if(canRotate){              
            jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
            setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x, y-1)));
            setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x-1,y-1)));
            setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x,y-2))); 
        }
    }
}
