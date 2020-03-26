package package1;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements KeyListener,ActionListener{

	Tetromino tm=new Tetromino();
	Tetromino nexttm=new Tetromino();
	Timer timer=new Timer(500,this);
	boolean [][] stage=new boolean[20][10];
	int score=0;
	int level=1;
	int line=0;
	int status=0;//游戏状态，0为未开始，1为开始，2为暂停，3为失败；
	
	
	
	Panel(){
		//timer.start();
		initstage();
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	
	//把游戏舞台置空
	public void initstage() {
		for(int i=0;i<20;i++) {
			for(int j=0;j<10;j++) {
				stage[i][j]=false;
			}
		}
		
	}
	
	
	//给定tetromino形状和位置，判定该方块能否存在于该位置
	public boolean judge (boolean[][] shape,int x,int y) {
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(shape[i][j]==true) {
					if(y+i>19||x+j>9||x+j<0||y+i<0||stage[y+i][x+j]==true) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	//旋转tetromino
	public void spin() {
		boolean flag=false;
		boolean[][] t=new boolean[4][4];
		//先把Tetromino转过来,保存到数组t里面
		
		//旋转形状.保存进数组t
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				t[3-j][i]=tm.shape[i][j];
			}
		}
	
		//把形状移回数组t的左上角
		while(flag==false){
			for(int i=0;i<4;i++) {
				if(t[i][0]==true) {
					flag=true;
				}			
			}
			if(flag==false) {
				for(int j=0;j<3;j++) {
					for(int i=0;i<4;i++) {
						t[i][j]=t[i][j+1];
					}
				}
				for(int i=0;i<4;i++) {
					t[i][3]=false;
				}
				
			}
			
			
		}
		
		
		if(judge(t,tm.x,tm.y)==true) {//如果旋转之后的tetromino可以放置在舞台中，则把数组t的内容迁移到数组shape里面
			for(int i=0;i<4;i++) {
			    for(int j=0;j<4;j++) {
				    tm.shape[i][j]=t[i][j];
				    repaint();
			    }
		    }
		}
		else {
			return;//不可以的话，直接禁止转动
		}
	
		
	}
	
	
	//用来检查是否有某一行可以被消灭,计算分值和等级
	public void clean() {				
		int row=0;//用来记录总共清除了几行
		boolean flag=true;
		for(int o=0;o<20;o++) {
			for(int p=0;p<10;p++) {
				if(stage[o][p]==false) {
					flag=false;
					break;
				}
			}
			if(flag==true) {
				row++;
				for(int q=o;q>0;q--) {
					for(int r=0;r<10;r++) {
						stage[q][r]=stage[q-1][r];
					}
				}
				for(int r=0;r<10;r++) {
					stage[0][r]=false;
				}
			}
			flag=true;
		}
		line+=row;
		score+=row*row*100;
		level=score/500+1;
	}
	
	//判断游戏是否失败
	public boolean lose() {
		for(int i=0;i<9;i++) {
			if(stage[0][i]==true) {
				status=3;
				return true;
			}
		}
		return false;
	}

	//重新开始游戏
	public void restart() {
		status=0;
		initstage();
		tm=new Tetromino();
		nexttm=new Tetromino();
		score=0;
		level=1;
		line=0;
		repaint();
		return;
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);		
		g.drawRect(29,29,301,601);
		g.drawRect(359,29,121,121);
		
		g.setFont(new Font("arial",Font.PLAIN,15));
		g.drawString("-DATA-", 395,190);	
		g.drawString("-INSTRUCTION-", 365,330);
		g.setFont(new Font("arial",Font.PLAIN,10));
		g.drawString("LINE : "+line, 393,220);
		g.drawString("SCORE : "+score, 380,250);
		g.drawString("LEVEL : "+level, 383,280);		
		g.drawString("ENTER : PAUSE", 380,360);
		g.drawString("← : LEFT", 405,390);
		g.drawString("→ : RIGHT", 405,420);
		g.drawString("↓ : SPEED UP", 410,450);
		g.drawString("SPACE : SPIN", 380,480);
		
		
		
		
		
		//输出状态信息
		g.setColor(new Color(0,0,0));
		
		
		
		//用来画当前的tetromino
		g.setColor(new Color(tm.r,tm.g,tm.b));		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(tm.shape[i][j]==true) {
					g.fillRect(30+tm.x*30+j*30, 30+tm.y*30+i*30, 30, 30);
				}
			}
		}
		
		//绘制下一个tetromino
		g.setColor(new Color(nexttm.r,nexttm.g,nexttm.b));		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(nexttm.shape[i][j]==true) {
					g.fillRect(360+j*30+nexttm.posx, 30+i*30+nexttm.posy, 30, 30);
				}
			}
		}
		
			
		
		//画出当前tetromino的目标位置
		
		
		
		int dy=tm.y;
		while(judge(tm.shape,tm.x,dy+1)==true) {
			dy++;
		}
	
		

		
		g.setColor(new Color(tm.r,tm.g,tm.b));		
		//最左边的轮廓线
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(tm.shape[i][j]==true) {
					g.drawLine(30+30*(tm.x+j),30+30*(dy+i), 30+30*(tm.x+j), 30+30*(dy+i)+29);
					if(i+1<4&&tm.shape[i+1][j]==true) {
						g.drawLine(30+30*(tm.x+j),30+30*(dy+i), 30+30*(tm.x+j), 30+30*(dy+i)+30);
					}
					if(i-1>=0&&tm.shape[i-1][j]==true) {
						g.drawLine(30+30*(tm.x+j),30+30*(dy+i)-1, 30+30*(tm.x+j), 30+30*(dy+i)+29);
					}
					break;
				}
			}
		}		
		//最右边的轮廓线
		for(int i=3;i>=0;i--) {
			for(int j=3;j>=0;j--) {
				if(tm.shape[i][j]==true) {
					g.drawLine(30+30*(tm.x+j+1)-1,30+30*(dy+i), 30+30*(tm.x+j+1)-1, 30+30*(dy+i)+29);
					if(i+1<4&&tm.shape[i+1][j]==true) {
						g.drawLine(30+30*(tm.x+j+1)-1,30+30*(dy+i), 30+30*(tm.x+j+1)-1, 30+30*(dy+i)+30);
					}
					if(i-1>=0&&tm.shape[i-1][j]==true) {
						g.drawLine(30+30*(tm.x+j+1)-1,30+30*(dy+i)-1, 30+30*(tm.x+j+1)-1, 30+30*(dy+i)+29);
					}
					break;
				}
			}
		}		
		//最上面的轮廓线
		for(int j=0;j<4;j++) {
			for(int i=0;i<4;i++) {
				if(tm.shape[i][j]==true) {
					g.drawLine(30+30*(tm.x+j),30+30*(dy+i), 30+30*(tm.x+j)+29, 30+30*(dy+i));
					break;
				}
			}
		}		
		//最下面的轮廓线
		for(int j=3;j>=0;j--) {
			for(int i=3;i>=0;i--) {
				if(tm.shape[i][j]==true) {
					g.drawLine(30+30*(tm.x+j),30+30*(dy+i+1)-1, 30+30*(tm.x+j)+29, 30+30*(dy+i+1)-1);
					break;
				}
			}
		}
		
			
		//用黑色填充已经被占据的格子
		g.setColor(new Color(0,0,0));
		
		for(int i=0;i<20;i++) {
			for(int j=0;j<10;j++) {
				if(stage[i][j]==true) {					
					g.fillRect(30+j*30, 30+i*30, 30, 30);
				}
				
			}
		}
		
		
		
		
		//游戏暂停以及未开始时的提示信息
		if(status==0) {
			g.drawRect(89,299,182,62);
			g.setColor(Color.WHITE);
			g.fillRect(90,300,180,60);
			g.setColor(Color.BLACK);
			g.drawString("PRESS \"ENTER\" TO START", 110,332);
		}
		
		
		if(status==2) {
			g.drawRect(89,299,182,62);
			g.setColor(Color.WHITE);
			g.fillRect(90,300,180,60);
			g.setColor(Color.BLACK);
			g.drawString("PRESS \"ENTER\" TO CONTINUNE", 100,332);
		}
		//游戏失败的提示信息
		if(status==3) {
			g.drawRect(89,299,182,62);
			g.setColor(Color.WHITE);
			g.fillRect(90,300,180,60);
			g.setColor(Color.BLACK);
			g.drawString("PRESS \"ENTER\" TO RESTART", 105,332);
		}
					
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {

	
		if(judge(tm.shape,tm.x,tm.y+1)==true) {//如果可以往下移动，则往下移动
			tm.y++;
			repaint();
			timer.restart();
			return;
		}
		else {//否则让tetromino占据舞台的空间
			timer.stop();
			timer=new Timer(500,this);
			timer.restart();
			for(int i=0;i<4;i++) {//
				for(int j=0;j<4;j++) {
					if(tm.shape[i][j]==true) {
						stage[tm.y+i][tm.x+j]=true;
					}
				}
			}
			clean();//然后清空一下可以清空的行
			if(lose()==true) {//判断游戏是否失败，如果失败，不再重启计时器，直接结束函数
				timer.stop();
				repaint();
				return;
			}
			tm=nexttm;
			nexttm=new Tetromino();//生成一个新的tetromino
			repaint();
			timer.restart();
			return;
		}
		
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode=e.getKeyCode();
		
		
		//在未开始或者暂停状态下，按回车键可以开始游戏
		if(keyCode==KeyEvent.VK_ENTER&&(status==0||status==2)) {
			status=1;
			timer.start();
			return;
		}
		
		//在进行状态下，按回车键可以暂停游戏
		if(keyCode==KeyEvent.VK_ENTER&&status==1) {
			status=2;
			timer.stop();
			repaint();
			return;
		}
		
		//在游戏失败的状态下，按回车键可以重新开始游戏
		if(keyCode==KeyEvent.VK_ENTER&&status==3) {
			restart();
			return;
		}	
		if(keyCode==37&&status==1) {
			if(judge(tm.shape,tm.x-1,tm.y)==true) {
				tm.x--;
				repaint();
				//sd.move.play();
				return;
			}
		}
		if(keyCode==39&&status==1) {
			if(judge(tm.shape,tm.x+1,tm.y)==true) {
				tm.x++;
				repaint();
				return;
			}
		}
		if(keyCode==32&&status==1) {
			spin();
			repaint();
			return;
		}
		if(keyCode==40&&status==1) {
			timer.stop();
			timer=new Timer(100,this);
			timer.restart();		
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
