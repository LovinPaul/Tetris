
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;



import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



public class Gui{// extends JFrame  
    
    private JFrame frame;
    GameGroundPaint  gameGround  = new GameGroundPaint();
    String[] lvlItems = {"Easy", "Medium", "Hard", "Impossible"}; 
    private JComboBox level = new JComboBox(lvlItems);
    String[] strMods = {"Sand Mode", "Clasic Mode"}; // (Easy as fu*k)
    String[] strDescMods = {"<html>If you're under 5 years old then this is for you.</html>", 
                            "<html>Challenge your TETRIS skills and be the best. Good luck.</html>"}; 
    private JLabel modsDescLbl = new JLabel(strDescMods[1],  SwingConstants.RIGHT);//
    private JComboBox modsCombo = new JComboBox(strMods);
    private JButton backToModeBut = new JButton("Mode");
    private JButton startBut = new JButton("Start");
    private JButton pauseBut = new JButton("Pause");
    private JButton leftBut = new JButton("<");
    private JButton rightBut = new JButton(">");
    private JButton downBut = new JButton("V"); 
    private JButton rotateClockWBut = new JButton("R"); 
    private JButton selectModeBut = new JButton("Select");
    private JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
    private JLabel gameScoreLabel = new JLabel("Score : ");
    private JLabel gamePlusLabel = new JLabel();
    private StartPanelPaint startPanel = new StartPanelPaint();
    private InGamePanelPaint inGamePanel = new InGamePanelPaint();
    private Thread scoreIncrementT;
    private KeyListener keyLis = new FrameKeyListener();
    //private Font fontGameOver = new Font("Arial", 1, 20);
    
    private int gameScore;
    private int gamePlusScore;
    private int timeRate=500;
    private boolean gameRunning;
    private boolean gamePaused;
    static boolean gameOver;
    
//    int rndFormDuplicateCheck;
    
   // ArrayList<CubeForm> cubeList = new ArrayList();
    ArrayList<TetrisForm> tetrisList = new ArrayList();
    static int[][] playGround = new int[20][10]; //private encapsulate!

    //Getters
    public static int[][] getPLayGround(){
        return playGround;
    }
    
    //Setters
    public void setPLayGround(int[][] pg){
        playGround = pg;
    }
    
    public static void main(String args[]) throws InterruptedException{
        new Gui().makeGui();
    }

    private void makeGui() throws InterruptedException {
        //Frame
        frame = new JFrame("JTetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(300, 400);
        frame.setMinimumSize(new Dimension(275, 410));
        //frame.setType(Type.UTILITY);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setLocationByPlatform(true);
        frame.setLayout(null);
        frame.addKeyListener(keyLis);
        frame.setVisible(true);
        
        makeGuiComponents();
        inGameGui(false);
        StartGui(true);
        
        startPanel.add(modsCombo);
        startPanel.add(modsDescLbl);
        startPanel.add(selectModeBut);

        

        inGamePanel.add(backToModeBut);
        inGamePanel.add(level);
        inGamePanel.add(gameScoreLabel);
        inGamePanel.add(gamePlusLabel);
        inGamePanel.add(startBut);
        inGamePanel.add(pauseBut);
        
//        gamePanel.add(rightBut);
//        gamePanel.add(leftBut);
//        gamePanel.add(downBut);
//        gamePanel.add(rotateClockWBut);
        //
        gameGround.add(gameOverLabel);
        //
        frame.getContentPane().add(gameGround);
        frame.getContentPane().add(inGamePanel);
        frame.getContentPane().add(startPanel);
        //frame.getContentPane().add(gameScoreLabel);
        //frame.getContentPane().add(gamePlusLabel);
        //frame.getContentPane().add(startBut);
        //frame.getContentPane().add(pauseBut);
        //frame.getContentPane().add(rightBut);
        //frame.getContentPane().add(leftBut);
        //frame.getContentPane().add(downBut);
        //frame.getContentPane().add(rotateClockWBut);
        //frame.getContentPane().add(gameOverLabel);
        //frame.getContentPane().add(level);
        
        
        frame.pack();
        frame.repaint();        

        //inGameGui();
        makeRndForm();
        
    }
    
    public void makeGuiComponents() {

        //gamePlayPanel.set
        //in game components
        //Game play Panel
//        gamePlayPanel.setLocation(0, 0);
//        gamePlayPanel.setSize(180, 400);
        /////////////////////////////////
        inGamePanel.setLocation(180, 0);
        inGamePanel.setSize(90, 400);
        //
        gameGround.setLocation(0, 0);
        gameGround.setSize(180, 400);
        gameGround.setLayout(null);                

        ///////////////////////////////
        inGamePanel.setBackground(Color.LIGHT_GRAY);        
        level.addKeyListener(keyLis);
        level.setSelectedIndex(1);  
        backToModeBut.addActionListener(new ModeButtonListener());
        startBut.addActionListener(new StartButtonListener());
        startBut.addKeyListener(keyLis);
        pauseBut.addActionListener(new PauseButtonListener());
        pauseBut.addKeyListener(keyLis);
        //leftBut.addActionListener(new LeftButtonListener());
        leftBut.addKeyListener(keyLis);
        leftBut.setVisible(false);
        //rightBut.addActionListener(new RightButtonListener());
        rightBut.addKeyListener(keyLis);
        rightBut.setVisible(false);        
        //downBut.addActionListener(new DownButtonListener());
        downBut.addKeyListener(keyLis);
        downBut.setVisible(false);         
        //rotateClockWBut.addActionListener(new RotateCWButtonListener());
        rotateClockWBut.addKeyListener(keyLis);
        rotateClockWBut.setVisible(false);        
        gameOverLabel.setFont(new Font("Arial", 1, 20));
        gameOverLabel.setVisible(false);
        //in game components size & location
        gameScoreLabel.setLocation( 190, 20);
        gameScoreLabel.setSize(200, 20);
        //
        gamePlusLabel.setLocation( 190, 30);
        gamePlusLabel.setSize(200, 20);
        //
        //backToModeBut.setLocation( 190, 110);   
        backToModeBut.setSize(70, 20);
        //
        level.setLocation( 190, 80);
        level.setSize(70, 20); 
        //
        startBut.setLocation( 190, 110);   
        startBut.setSize(70, 20);
        //
        pauseBut.setLocation( 190, 130);
        pauseBut.setSize(70, 20);
        //
        leftBut.setLocation( 190, 160);
        leftBut.setSize(45, 20);
        //
        rightBut.setLocation( 220, 160);
        rightBut.setSize(45, 20);
        //
        downBut.setLocation( 200, 180);
        downBut.setSize(45, 20);
        //
        gameOverLabel.setLocation( 0, 80);
        gameOverLabel.setSize(180, 25);
        
        
        //start gui components
        startPanel.setLayout(null);
        modsCombo.addActionListener(new ComboModeListener());
        modsCombo.setSelectedIndex(1);
        //start gui components size & location
        startPanel.setSize(275, 410);
        startPanel.setLocation(0, 0);
        //startPanel.setBackground(Color.red);
        modsCombo.setSize(100,20);
        modsCombo.setLocation(40, 70); 
        modsDescLbl.setLocation(40, 80);
        modsDescLbl.setSize(200, 100);
        selectModeBut.setSize(70, 20);
        selectModeBut.setLocation( 160 , 70);
        selectModeBut.addActionListener(new SelectButtonListener());
    }
    
    private void inGameRunningGui(boolean show){
            //gameScoreLabel.setVisible(show);
            pauseBut.setVisible(show);
            leftBut.setVisible(show);
            rightBut.setVisible(show);
            downBut.setVisible(show);
            rotateClockWBut.setVisible(show);
    }
    
    private void inGameGui(boolean show){

        
        level.setVisible(show);
        startBut.setVisible(show);
        
        inGamePanel.setVisible(show);
        gameGround.setVisible(show);
        if(!show){
            gameScoreLabel.setVisible(show);
            gamePlusLabel.setVisible(show); 
            pauseBut.setVisible(show);
            leftBut.setVisible(show);
            rightBut.setVisible(show);
            downBut.setVisible(show);
            rotateClockWBut.setVisible(show);
        }
    }
    
    private void StartGui(boolean show){
        if (show){
            startPanel.setVisible(true);
        }else{
            startPanel.setVisible(false);
        }
    }
    
    private void makeRndForm() throws InterruptedException{
        //System.out.println("->makeRndForm");
        int thrSleep = 500;
        while(true){
            if(gameRunning){
                int rndForm = (int) (Math.random() * 7);
                switch(rndForm){
                    case 0 :
                        CubeForm cube = new CubeForm((byte) 20);
                        tetrisList.add(cube);
                        gameGround.add(cube.getJPar0());
                        gameGround.add(cube.getJPar1());
                        gameGround.add(cube.getJPar2());
                        gameGround.add(cube.getJPar3());               
                        cube.setCubeLocation(4,-1);//(int)(Math.random()*8)
                        System.out.println("new Cube");
                        break;                    
                    
                    case 1 :
                        ZetForm zet = new ZetForm((byte) 20);
                        tetrisList.add(zet);
                        gameGround.add(zet.getJPar0());
                        gameGround.add(zet.getJPar1());
                        gameGround.add(zet.getJPar2());
                        gameGround.add(zet.getJPar3());                  
                        zet.setZetLocation((int)(Math.random()*2),4,-1);//  (int)(Math.random()*8)    
                        System.out.println("new Zet");
                        break;    
                    
                    case 2 :
                        TForm t = new TForm((byte) 20);
                        tetrisList.add(t);
                        gameGround.add(t.getJPar0());
                        gameGround.add(t.getJPar1());
                        gameGround.add(t.getJPar2());
                        gameGround.add(t.getJPar3());                
                        t.setTLocation((int)(Math.random()*4),4,-1);// (int)(Math.random()*8) 
                        System.out.println("new T");
                        break;     
                        
                    case 3 :
                        LineForm line = new LineForm((byte) 20);
                        tetrisList.add(line);
                        gameGround.add(line.getJPar0());
                        gameGround.add(line.getJPar1());
                        gameGround.add(line.getJPar2());
                        gameGround.add(line.getJPar3());                                           
                        line.setLineLocation((int)(Math.random()*2),4,-1);// (int)(Math.random()*8)
                        System.out.println("new Line");
                        break; 
                    case 4 :
                        LForm l = new LForm((byte) 20);
                        tetrisList.add(l);
                        gameGround.add(l.getJPar0());
                        gameGround.add(l.getJPar1());
                        gameGround.add(l.getJPar2());
                        gameGround.add(l.getJPar3());                                           
                        l.setLLocation((int)(Math.random()*2),4,-1);//  (int)(Math.random()*8) 
                        System.out.println("new L");
                        break;
                    case 5 :
                        ZetMirrorForm zetM = new ZetMirrorForm((byte) 20);
                        tetrisList.add(zetM);
                        gameGround.add(zetM.getJPar0());
                        gameGround.add(zetM.getJPar1());
                        gameGround.add(zetM.getJPar2());
                        gameGround.add(zetM.getJPar3());                                          
                        zetM.setZetMirrorLocation((int)(Math.random()*2),4,-1);//  (int)(Math.random()*8)    
                        System.out.println("new ZetMirror");
                        break;
                    case 6 :
                        LMirrorForm lM = new LMirrorForm((byte) 20);
                        tetrisList.add(lM);
                        gameGround.add(lM.getJPar0());
                        gameGround.add(lM.getJPar1());
                        gameGround.add(lM.getJPar2());
                        gameGround.add(lM.getJPar3());                                        
                        lM.setLMirrorLocation((int)(Math.random()*4),4,-1);//  (int)(Math.random()*8) 
                        System.out.println("new LMirror");
                        break;
                }

                downWithTheForm(tetrisList.get(tetrisList.size()-1));
                
                if(scoreIncrementT == null){
                    gamePlusLabel.setVisible(false);
                    gamePlusScore=0;                    
                }

                
                updatePlayGround();
                checkForLines();
                updatePlayGround();


                if(gameOver==true){
                    gameOver();
                }
                
                thrSleep = 100;
            }else{
                thrSleep = 500;
                if (gameOver){
                    gameOverLabel.setText("Game Over");
                    gameOverLabel.setVisible(true);
                    gameOverLabel.setForeground(new Color((float)(Math.random()), (float)(Math.random()), (float)(Math.random())));
                }else{
                    gameOverLabel.setText("Game Paused");
                    gameOverLabel.setForeground(Color.MAGENTA);
                    //gameOverLabel.setForeground(new Color((float)(Math.random()), (float)(Math.random()), (float)(Math.random())));
                }
            }
            Thread.sleep(thrSleep);
        }
    }

    private void downWithTheForm(TetrisForm tetrisForm) throws InterruptedException{
        //System.out.println("->downWithTheForm");
        if(gameRunning){
            for(int i=0; i<=18;i++){
                //System.out.println("->downWithTheForm i = " + i);
                
                if(!gamePaused && gameRunning){ //running normal
                    if(gameOver){gameOver();break;}
                    if(!TetrisForm.toBottom(tetrisForm)){break;}              
                }else if(gamePaused && gameRunning){ //paused
                    i--;
                }else if(!gameRunning){ //reset
                    resetRunningGameGui();
                    break;
                }
                
                Thread.sleep(timeRate);  
            }
        }
    }


    
    public void resetRunningGameGui(){
        removeAllTetrisBlocks();
        gameOverLabel.setVisible(false);
        gameOver=false;
        level.setEnabled(true);
        startBut.setEnabled(true);
        startBut.setText("Start");
        //resetPlayGround();
    }
    
    public void resetPlayGround(){
        for(int i=0; i<=19; i++){
            for(int j=0; j<=8; j++){
                playGround[i][j]=0;
                if(i==19){
                    playGround[i][j]=1;
                }
            }
        }
        
    }
    
    public void removeAllTetrisBlocks(){
        for(int i=0; i<=tetrisList.size()-1;i++){
            for(int j=0; j <=3; j++){
                if(tetrisList.get(i).getJPar()[j] != null) {gameGround.remove(tetrisList.get(i).getJPar()[j]);}  
            }
                         
        }   
        tetrisList.clear();
        resetPlayGround();
        frame.repaint(); 
    }
    
    public void gameOver(){
        gameRunning=false;
        inGameRunningGui(false);
        level.setEnabled(true);
        startBut.setEnabled(true);
        startBut.setText("Start");
    }

    public void updatePlayGround(){
        //clear PlayGround
        resetPlayGround();
        //Update PLayGround
        int xCord;
        int yCord;
        for(int i=0; i<=tetrisList.size()-1; i++){
            for(int j=0; j<=3; j++){
                if(tetrisList.get(i).getJPar()[j] != null){
                    xCord = TetrisForm.getPlayGroundCoord(tetrisList.get(i).getJPar()[j]).x;
                    yCord = TetrisForm.getPlayGroundCoord(tetrisList.get(i).getJPar()[j]).y;
                    if(yCord<0){
                        gameOver = true;
                    }else{
                        playGround[yCord][xCord] = 1;
                    }                    
                }

            }
        }
    }

    public void removeTetrisListNulls(){
        
        for(int a=0; a <= tetrisList.size()-1; a++){        
            if (TetrisForm.isNull(tetrisList.get(a))){
                tetrisList.remove(a);
                a--;
            }
        }
    }
    
    public boolean checkForLines(){
        
        boolean x;
        boolean ret=false;
        
        for(int i=0; i<=18; i++){
            for(int j=0; j<=8; j++){
                //check every line for reduction
                if(playGround[i][j] == 1){
                    x = true;
                }else{
                    break;
                }

                //if a line can be reduced
                if(j==8 && x==true){
                    ret = true; //a least one line has beed reduced 
                    System.out.println("Line : " + i);
                    for(int a=0; a <= tetrisList.size()-1; a++){
                        for(int f=0; f<=3; f++){
                            if(TetrisForm.getPlayGroundCoord(tetrisList.get(a).jPar[f]).y == i){
                                gamePlusScore += 3;
                                gameGround.remove(tetrisList.get(a).jPar[f]);
                                tetrisList.get(a).jPar[f] = null;
                            }                                 
                        }
                    }
                }
            }
        }
        
        removeTetrisListNulls();
                
        //<editor-fold defaultstate="collapsed" desc="de optimizat">
        if(gamePlusScore != 0 &&  scoreIncrementT == null){
            updatePlayGround();
            gamePlusLabel.setVisible(true);
            Runnable job = new ScoreIncrement();
            scoreIncrementT = new Thread(job);
            scoreIncrementT.start();
        }
        frame.repaint();
        if (ret){rearangeBlocks();}
        //</editor-fold>
       return ret;
    }

    public void rearangeBlocks(){
        boolean blocks=true;
        
        frame.removeKeyListener(keyLis);
        pauseBut.removeKeyListener(keyLis);
        startBut.removeKeyListener(keyLis);
        level.removeKeyListener(keyLis);

        while(blocks){
            blocks=false;

            for(int i=0; i<=tetrisList.size()-1; i++){
                
                if(modsCombo.getSelectedIndex()==1){
                    //Classic Tetris Mode
                    updatePlayGround();
                    if(TetrisForm.toBottom(tetrisList.get(i))){
                        frame.repaint();
                        blocks = true;
                    }  
                    //updatePlayGround();                    
                }else{
                    //Sand Tetris Mode
                    for(int j=0; j<=3; j++){
                        if(tetrisList.get(i).getJPar()[j] != null){
                            updatePlayGround();
                            if(TetrisForm.toBottom(tetrisList.get(i).getJPar()[j])){
                                frame.repaint();
                                blocks = true;
                            }
                        }
                    }                    
                }
            }
            
            try {
                Thread.sleep(400);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            
        }
        
        startBut.addKeyListener(keyLis);
        pauseBut.addKeyListener(keyLis);
        level.addKeyListener(keyLis);
        frame.addKeyListener(keyLis);
        
        checkForLines();
    }
    
    public class StartButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("actionperformed");
            if(gameRunning){
                
                gameRunning = false;
                gamePaused = false;
                gameOver = false;
                startBut.setEnabled(false);
                inGameRunningGui(false);

            }else{
                
                
                
                removeAllTetrisBlocks(); 
                gameScoreLabel.setVisible(true);
                gameScoreLabel.setText("Score :");
                gameScore = 0;
                gameOverLabel.setVisible(false);
                gameOver = false;    
                gamePaused=false;
                level.setEnabled(false);
                inGameRunningGui(true);
                startBut.setText("Reset");
                switch(level.getSelectedIndex()){
                    case 0:
                        timeRate = 750;
                        break;
                    case 1:
                        timeRate = 250;
                        break;                    
                    case 2:
                        timeRate = 150;
                        break;                        
                    case 3:
                        timeRate = 100;
                        break;                        
                }
                gameRunning = true;      
            }
        }
    }

    public class FrameKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
            //System.out.println(Character.toUpperCase(e.getKeyChar()));
            if(Character.toUpperCase(e.getKeyChar())=='P' && pauseBut.isVisible()){pauseBut.doClick();}//pauseBut.doClick()
            if(Character.toUpperCase(e.getKeyChar())=='A' && !gamePaused){tetrisList.get(tetrisList.size()-1).toLeft();}
            if(Character.toUpperCase(e.getKeyChar())=='D' && !gamePaused){tetrisList.get(tetrisList.size()-1).toRight();}
            if(Character.toUpperCase(e.getKeyChar())=='S' && !gamePaused){TetrisForm.toBottom(tetrisList.get(tetrisList.size()-1));}
            //Rotate every object;
            if(Character.toUpperCase(e.getKeyChar())=='K' && !gamePaused){
                    if(tetrisList.get(tetrisList.size()-1) instanceof TForm){
                        TForm tfm = (TForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setTLocation(tfm.rotateClockwise(false), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof LineForm){
                        LineForm tfm = (LineForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setLineLocation(tfm.rotateClockwise(false), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof ZetForm){
                        ZetForm tfm = (ZetForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setZetLocation(tfm.rotateClockwise(false), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof LForm){
                        LForm tfm = (LForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setLLocation(tfm.rotateClockwise(false), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof ZetMirrorForm){
                        ZetMirrorForm tfm = (ZetMirrorForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setZetMirrorLocation(tfm.rotateClockwise(false), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof LMirrorForm){
                        LMirrorForm tfm = (LMirrorForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setLMirrorLocation(tfm.rotateClockwise(false), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
            }
            //Rotate every object;
            if(Character.toUpperCase(e.getKeyChar())=='M' && !gamePaused){
                    if(tetrisList.get(tetrisList.size()-1) instanceof TForm){
                        TForm tfm = (TForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setTLocation(tfm.rotateClockwise(true), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof LineForm){
                        LineForm tfm = (LineForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setLineLocation(tfm.rotateClockwise(true), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof ZetForm){
                        ZetForm tfm = (ZetForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setZetLocation(tfm.rotateClockwise(true), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }  
                    if(tetrisList.get(tetrisList.size()-1) instanceof LForm){
                        LForm tfm = (LForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setLLocation(tfm.rotateClockwise(true), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof ZetMirrorForm){
                        ZetMirrorForm tfm = (ZetMirrorForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setZetMirrorLocation(tfm.rotateClockwise(true), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
                    if(tetrisList.get(tetrisList.size()-1) instanceof LMirrorForm){
                        LMirrorForm tfm = (LMirrorForm) tetrisList.get(tetrisList.size()-1);
                        tfm.setLMirrorLocation(tfm.rotateClockwise(true), TetrisForm.getPlayGroundCoord(tfm.getJPar0()).x, TetrisForm.getPlayGroundCoord(tfm.getJPar0()).y);
                    }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
        
    }

    public class PauseButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
               if(gamePaused){
                   gamePaused=false;
                   gameOverLabel.setVisible(false);
               }else{
                   gameOverLabel.setText("Game Paused");
                   gamePaused=true;
                   gameOverLabel.setVisible(true);
               }
        }
    }

    public class SelectButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

                inGameGui(true);
                StartGui(false);
                resetRunningGameGui();
            
            frame.setTitle("JTetris     *" + strMods[modsCombo.getSelectedIndex()] + "*");
            
        }
    }
    
    public class ModeButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameRunning){
                startBut.doClick();
                inGameGui(false);
                StartGui(true);
                frame.setTitle("JTetris");
            }else{
                inGameGui(false);
                StartGui(true);
                frame.setTitle("JTetris");
            }
        }
        
    }

    public class ComboModeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            modsDescLbl.setText(strDescMods[modsCombo.getSelectedIndex()]);
        }
        
    }
    
    public class Tmp implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            for(int i=0; i<=18; i++){
                System.out.println("");
                for(int j=0; j<=8; j++){
                    System.out.print(playGround[i][j]); 
                }
            }
                
            //checkForLines();
        }
    }

    
    public class GameGroundPaint extends JPanel {
       
        @Override
        public void paintComponent(Graphics g){
            Image img = new ImageIcon("tetrdata\\java2.png").getImage();
            g.drawImage(img, 0 , 0, this);
        } 
    }
    public class InGamePanelPaint extends JPanel {
       
        @Override
        public void paintComponent(Graphics g){
            g.drawLine(5, 0, 5, 400);
            Graphics2D g2d = (Graphics2D) g; //70 70 150 150
            GradientPaint grad = new GradientPaint(70, 70, Color.white, 200, 200, Color.lightGray);
            g2d.setPaint(grad);
            g2d.fillRect(0, 0, 90, 400);
            
        } 
    }
 
    public class StartPanelPaint extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            Image img = new ImageIcon("tetrdata\\link.jpg").getImage();
            g.drawImage(img, 0 , 0, this);
        }         
    }
    
    public class ScoreIncrement implements Runnable {
        @Override
        public void run() {
            //int gPSTemp = gameScore;
            for(int i=0; i<= gamePlusScore; i++){
                
                gameScoreLabel.setText("Score : " + gameScore++);
                gamePlusLabel.setText("               + " + i);
                
                if(gamePlusLabel.getForeground()== Color.black){
                    gamePlusLabel.setForeground(Color.red);
                }else{
                    gamePlusLabel.setForeground(Color.black);
                }
                if (i == gamePlusScore){gamePlusLabel.setForeground(Color.black);}
                
                int sleepTime = 100;
                if( (gamePlusScore - i) > 0 ){
                    sleepTime=100;
                    if( (gamePlusScore - i) > 3 ){
                        sleepTime=50;
                        if( (gamePlusScore - i) > 10 ){
                            sleepTime=25;
                            if( (gamePlusScore - i) > 50 ){
                                sleepTime=10;
                            }                            
                        }
                    }
                }
                

                
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            scoreIncrementT = null;
        }
    }

    
}
