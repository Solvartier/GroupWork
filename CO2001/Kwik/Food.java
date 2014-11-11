import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Food implements Runnable{

private int foodx, foody, fooddx, fooddy, foodspeed;
private int[] dx, dy;
public boolean eaten = false;
public int scorevalue = 100;
public static Random randomGenerator = new Random();
public Board ref;
private static Image sprite;
public Rectangle fRectangle;

	public Food(Board r){
		loadImages();
		ref = r;
		foodspeed = 2;
		
		
		int pos = randomGenerator.nextInt(ref.level.spawnx.length);
		
		foodx=ref.level.spawnx[pos]*ref.level.blocksize;
		foody=ref.level.spawny[pos]*ref.level.blocksize;
		fRectangle = new Rectangle(foodx, foody, 30, 30);
	}
	public void moveFoodRectangle(){
		fRectangle.setLocation(foodx+7, foody+7);
	}

    private void loadImages() {
	      try{
            String current = new java.io.File( "." ).getCanonicalPath();
            current = current.replace('\\', '/');
			current = current + "/";
			sprite = new ImageIcon(current + "cake.png").getImage();
		  }
		  catch(Exception e){ 
			System.out.println("Graphics did not load.");
			System.exit(0);
		  }
    }
	
    public void draw(Graphics2D g2d) {
   		g2d.drawImage(sprite, foodx, foody, ref);
    }
    
    public void move() {
        int pos;
        int count;
            if (foodx % ref.level.blocksize == 0 && foody % ref.level.blocksize == 0) {
                pos = foodx / ref.level.blocksize + ref.level.nrofblocks * (int) (foody / ref.level.blocksize);
                count = 0;
        		dx = new int[4];
        		dy = new int[4];
                if ((ref.level.screendata[pos] & 1) == 0 && fooddx != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((ref.level.screendata[pos] & 2) == 0 && fooddy != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((ref.level.screendata[pos] & 4) == 0 && fooddx != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((ref.level.screendata[pos] & 8) == 0 && fooddy != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                
                if (count == 0) {

                    if ((ref.level.screendata[pos] & 15) == 15) {
                        fooddx = 0;
                        fooddy = 0;
                    } else {
                        fooddx = -fooddx;
                        fooddy = -fooddy;
                    }

                } else {

  
                	count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    fooddx = dx[count];
                    fooddy = dy[count];
                }

            }

            foodx = foodx + (fooddx * foodspeed);
            foody = foody + (fooddy * foodspeed);
		moveFoodRectangle();
    }
	

	public void run(){
		while(true){
			move();
			if(ref.diz.pRectangle.intersects(fRectangle)){
			//if(foodx == ref.diz.eggx && foody == ref.diz.eggy){
				eaten = true;
			} else {
				try{
					Thread.sleep(10);
				}catch(InterruptedException e){
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
	}
}
//lololol
