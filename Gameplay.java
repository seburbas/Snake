import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{
	//tablica wspólrzednych smoka w planszy
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	//kierunki poruszania smoka
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	//obrazki
	private ImageIcon rightmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	private ImageIcon leftmouth;
	//ruchy smoka
	private int moves = 0;
	//obrazek
	private ImageIcon titleImage;
	//d³ugoœæ smoka
	private int lengthofsnake = 3;
	//tworze Timer,aby porusza³ siê smok
	private Timer timer;
	//prêdkoœæ smoka
	private int delay = 100;
	//pozycje pileczki w planszy o pozycjach X i Y
	private int[] enemyxpos = {25,50,75,100,125,150,175,200,225,250,275,300,
			325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,
			700,725,750,775,800,825,850};
	
	private int[] enemyypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,
			425,450,475,500,525,550,575,600,625};
	//obrazek
	private ImageIcon enemyimage;
	//losowe pojawianie sie pileczki na planszy
	private Random random = new Random();
	//dodanie losowania pileczki na planszy cyfry oznaczaja ilosc losowanych pozycji
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);
	//obrazek
	private ImageIcon snakeImage;
	//pocz¹tkowa iloœæ punktów
	private int score = 0;
	
	public Gameplay()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}
	//metoda do rysowania i dodawania obrazków
	public void paint(Graphics g)
	{
		
		if(moves == 0)
		{
			//pierwotne po³o¿enie smoka
			snakexlength[2] = 50;
			snakexlength[1] = 75;
			snakexlength[0] = 100;
			
			snakeylength[2] = 100;
			snakeylength[1] = 100;
			snakeylength[0] = 100;
		}
		
		//draw title image border(kolor obwodu ramki)
		g.setColor(Color.MAGENTA);
		g.drawRect(24,10,851,55);
		
		//draw the title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this,g,25,11);
		
		//draw border for gameplay
		g.setColor(Color.CYAN);
		g.drawRect(24,74,851,577);
		
		//draw background for the gameplay
		g.setColor(Color.black);
		g.fillRect(25,75,850,575);
		
		//draw scores
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.PLAIN,14));
		g.drawString("Scores: "+ score,780,30);
		
		//draw length
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.PLAIN,14));
		g.drawString("Length: "+ lengthofsnake,780,50);
		
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g,snakexlength[0] , snakeylength[0]);
		
		for(int i = 0; i<lengthofsnake; i++)
		{
			if(i==0 && right)
			{
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, g,snakexlength[i] , snakeylength[i]);
			}
			if(i==0 && left)
			{
				leftmouth = new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this, g,snakexlength[i] , snakeylength[i]);
			}
			if(i==0 && down)
			{
				downmouth = new ImageIcon("downmouth.png");
				downmouth.paintIcon(this, g,snakexlength[i] , snakeylength[i]);
			}
			if(i==0 && up)
			{
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, g,snakexlength[i] , snakeylength[i]);
			}
			if(i != 0)
			{
				snakeImage = new ImageIcon("snakeImage.png");
				snakeImage.paintIcon(this, g,snakexlength[i] , snakeylength[i]);
			}
		}
		
		enemyimage = new ImageIcon("enemy.png");
		
		if((enemyxpos[xpos]) == snakexlength[0] && enemyypos[ypos] == snakeylength[0])
		{
			score++;
			lengthofsnake++;
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
		}
		
		enemyimage.paintIcon(this, g,enemyxpos[xpos],enemyypos[ypos]);
		
		for(int b = 1; b<lengthofsnake; b++)
		{
			if(snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0])
			{
				right = false;
				left = false;
				up = false;
				down = false;
				
				g.setColor(Color.white);
				g.setFont(new Font("arial",Font.BOLD,50));
				g.drawString("Game Over!",300,300);
				
				g.setFont(new Font("arial",Font.BOLD,20));
				g.drawString("Space to RESTART",350,340);
			}
		}
		
		g.dispose();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(right)
		{
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				snakeylength[r+1] = snakeylength[r];
			}
			for(int r = lengthofsnake; r>=0; r--)
			{
				if(r==0)
				{
					snakexlength[r] = snakexlength[r] + 25;
				}
				else
				{
					snakexlength[r] = snakexlength[r-1];
				}
				if(snakexlength[r] > 850)
				{
					snakexlength[r] = 25;
				}
			}
			repaint();
		}
		if(left)
		{
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				snakeylength[r+1] = snakeylength[r];
			}
			for(int r = lengthofsnake; r>=0; r--)
			{
				if(r==0)
				{
					snakexlength[r] = snakexlength[r] - 25;
				}
				else
				{
					snakexlength[r] = snakexlength[r-1];
				}
				if(snakexlength[r] < 25)
				{
					snakexlength[r] = 850;
				}
			}
			repaint();
		}
		if(up)
		{
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				snakexlength[r+1] = snakexlength[r];
			}
			for(int r = lengthofsnake; r>=0; r--)
			{
				if(r==0)
				{
					snakeylength[r] = snakeylength[r] + 25;
				}
				else
				{
					snakeylength[r] = snakeylength[r-1];
				}
				if(snakeylength[r] < 75)
				{
					snakeylength[r] = 625;
				}
			}
			repaint();
		}
		if(down)
		{
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				snakexlength[r+1] = snakexlength[r];
			}
			for(int r = lengthofsnake; r>=0; r--)
			{
				if(r==0)
				{
					snakeylength[r] = snakeylength[r] + 25;
				}
				else
				{
					snakeylength[r] = snakeylength[r-1];
				}
				if(snakeylength[r] > 625 )
				{
					snakeylength[r] = 75;
				}
			}
			repaint();	
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			moves = 0;
			score = 0;
			lengthofsnake = 3;
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moves++;
			right = true;
			if(!left)
			{
				right = true;
			}
			else
			{
				right = false;
				left = true;
			}
			
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moves++;
			left = true;
			if(!right)
			{
				left = true;
			}
			else
			{
				left = false;
				right = true;
			}
			
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			moves++;
			up = true;
			if(!down)
			{
				up = true;
			}
			else
			{
				up = false;
				down = true;
			}
			
			left = false;
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			moves++;
			down = true;
			if(!up)
			{
				down = true;
			}
			else
			{
				up = true;
				down = false;
			}
			
			left = false;
			right = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
}
