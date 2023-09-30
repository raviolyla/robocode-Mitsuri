package robot;
import robocode.*;
import java.awt.Color;
import java.util.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

public class Mitsurii extends AdvancedRobot
{
	protected boolean direcao;
    protected String nomeAlvo;
    protected double distanciaAlvo;
    protected double direcaoArma;
    protected double posicaoAlvo;
    protected double direcaoRadar;
	
	public void run() {
		
	//cores da Mitsu
	setColors(new Color(255, 255, 255), new Color(242,194,206), new Color(189,206,98),new Color(245,126,146),new Color(0, 117, 162));
		
	setAdjustGunForRobotTurn(true); 
    setAdjustRadarForGunTurn(false);
        
	turnRadarRight(360); 
    direcao = true;     
       
	//ação da Mitsu (scan, mira e movimento)
	 while(true){
	 
     	if(nomeAlvo.equals("")){
        	setTurnRadarLeft(10);

        	execute();
            }else {
                setTurnGunRight(direcaoArma);
                setTurnRight(posicaoAlvo);
                setAhead(150);
                
                execute();
            }
        }
    }
	
	 public void onScannedRobot(ScannedRobotEvent e)  
    {
        nomeAlvo = e.getName();  //identifica o alvo
        distanciaAlvo = e.getBearing();  //posiçao do alvo em relação a Mitsu
        posicaoAlvo = getHeading() + e.getBearing(); // posição exata do alvo
		direcaoArma = normalRelativeAngleDegrees(posicaoAlvo - getGunHeading());  //mira a arma 
        direcaoRadar = normalRelativeAngleDegrees(posicaoAlvo - getRadarHeading()); //mira o radar
		
		if (Math.abs(direcaoArma) <= 3) {    //atira se alvo estiver perto
			setTurnGunRight(direcaoArma);
            setTurnRadarRight(direcaoRadar);
			
			if (getGunHeat() == 0) {
				fire(Math.min(3 - Math.abs(direcaoArma), getEnergy() - .1));
			}
		}
		else {
			setTurnGunRight(direcaoArma);
		}
		if (direcaoArma == 0) {
			scan();
		}


    }
	
	public void onHitWall(HitWallEvent e) //ação se bater na parede
    {
        if(direcao)
        {

            setBack(50);
            execute();
            direcao = false;
 
        } else{

            setAhead(50);
            execute();
            direcao = true;

        }
    }
	
	public void onHitRobot(HitRobotEvent e) //ação se colidir com outro robô
    {
        if(e.isMyFault())
        {
            if(direcao)
        {
            stop();
            setBack(500);
            direcao = false;
            setResume();
        } else{
            stop();
            setAhead(500);
            direcao = true;
            setResume();
        }
        }
    }
	
	public void onHitByBullet(HitByBulletEvent e) //ação se for atingido por tiro
    {
        if(direcao)
        {

            setBack(50);
            execute();
            direcao = false;
 
        } else{

            setAhead(50);
            execute();
            direcao = true;

        }
    }
	}
