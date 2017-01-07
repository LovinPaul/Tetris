
import java.awt.Color;
import java.awt.Point;

public class CubeForm extends TetrisForm {

    public CubeForm(byte size) {
        //super(Color.yellow, size);
        super(0, size);
    }

    //Setters For Cube;
    public void setCubeLocation(int x, int y){
        //  x x
        //  X x
        jPar[0].setLocation(new Point(TetrisForm.setPlayGroundCoord(x, y)));
        setJPar1Location(new Point(TetrisForm.setPlayGroundCoord(x+1, y)));
        setJPar2Location(new Point(TetrisForm.setPlayGroundCoord(x,y-1)));
        setJPar3Location(new Point(TetrisForm.setPlayGroundCoord(x+1,y-1)));
    }
}
