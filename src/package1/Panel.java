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
	int status=0;//��Ϸ״̬��0Ϊδ��ʼ��1Ϊ��ʼ��2Ϊ��ͣ��3Ϊʧ�ܣ�
	
	
	
	Panel(){
		//timer.start();
		initstage();
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	
	//����Ϸ��̨�ÿ�
	public void initstage() {
		for(int i=0;i<20;i++) {
			for(int j=0;j<10;j++) {
				stage[i][j]=false;
			}
		}
		
	}
	
	
	//����tetromino��״��λ�ã��ж��÷����ܷ�����ڸ�λ��
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
	
	//��תtetromino
	public void spin() {
		boolean flag=false;
		boolean[][] t=new boolean[4][4];
		//�Ȱ�Tetrominoת����,���浽����t����
		
		//��ת��״.���������t
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				t[3-j][i]=tm.shape[i][j];
			}
		}
	
		//����״�ƻ�����t�����Ͻ�
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
		
		
		if(judge(t,tm.x,tm.y)==true) {//�����ת֮���tetromino���Է�������̨�У��������t������Ǩ�Ƶ�����shape����
			for(int i=0;i<4;i++) {
			    for(int j=0;j<4;j++) {
				    tm.shape[i][j]=t[i][j];
				    repaint();
			    }
		    }
		}
		else {
			return;//�����ԵĻ���ֱ�ӽ�ֹת��
		}
	
		
	}
	
	
	//��������Ƿ���ĳһ�п��Ա�����,�����ֵ�͵ȼ�
	public void clean() {				
		int row=0;//������¼�ܹ�����˼���
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
	
	//�ж���Ϸ�Ƿ�ʧ��
	public boolean lose() {
		for(int i=0;i<9;i++) {
			if(stage[0][i]==true) {
				status=3;
				return true;
			}
		}
		return false;
	}

	//���¿�ʼ��Ϸ
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
		g.drawString("�� : LEFT", 405,390);
		g.drawString("�� : RIGHT", 405,420);
		g.drawString("�� : SPEED UP", 410,450);
		g.drawString("SPACE : SPIN", 380,480);
		
		
		
		
		
		//���״̬��Ϣ
		g.setColor(new Color(0,0,0));
		
		
		
		//��������ǰ��tetromino
		g.setColor(new Color(tm.r,tm.g,tm.b));		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(tm.shape[i][j]==true) {
					g.fillRect(30+tm.x*30+j*30, 30+tm.y*30+i*30, 30, 30);
				}
			}
		}
		
		//������һ��tetromino
		g.setColor(new Color(nexttm.r,nexttm.g,nexttm.b));		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(nexttm.shape[i][j]==true) {
					g.fillRect(360+j*30+nexttm.posx, 30+i*30+nexttm.posy, 30, 30);
				}
			}
		}
		
			
		
		//������ǰtetromino��Ŀ��λ��
		
		
		
		int dy=tm.y;
		while(judge(tm.shape,tm.x,dy+1)==true) {
			dy++;
		}
	
		

		
		g.setColor(new Color(tm.r,tm.g,tm.b));		
		//����ߵ�������
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
		//���ұߵ�������
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
		//�������������
		for(int j=0;j<4;j++) {
			for(int i=0;i<4;i++) {
				if(tm.shape[i][j]==true) {
					g.drawLine(30+30*(tm.x+j),30+30*(dy+i), 30+30*(tm.x+j)+29, 30+30*(dy+i));
					break;
				}
			}
		}		
		//�������������
		for(int j=3;j>=0;j--) {
			for(int i=3;i>=0;i--) {
				if(tm.shape[i][j]==true) {
					g.drawLine(30+30*(tm.x+j),30+30*(dy+i+1)-1, 30+30*(tm.x+j)+29, 30+30*(dy+i+1)-1);
					break;
				}
			}
		}
		
			
		//�ú�ɫ����Ѿ���ռ�ݵĸ���
		g.setColor(new Color(0,0,0));
		
		for(int i=0;i<20;i++) {
			for(int j=0;j<10;j++) {
				if(stage[i][j]==true) {					
					g.fillRect(30+j*30, 30+i*30, 30, 30);
				}
				
			}
		}
		
		
		
		
		//��Ϸ��ͣ�Լ�δ��ʼʱ����ʾ��Ϣ
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
		//��Ϸʧ�ܵ���ʾ��Ϣ
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

	
		if(judge(tm.shape,tm.x,tm.y+1)==true) {//������������ƶ����������ƶ�
			tm.y++;
			repaint();
			timer.restart();
			return;
		}
		else {//������tetrominoռ����̨�Ŀռ�
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
			clean();//Ȼ�����һ�¿�����յ���
			if(lose()==true) {//�ж���Ϸ�Ƿ�ʧ�ܣ����ʧ�ܣ�����������ʱ����ֱ�ӽ�������
				timer.stop();
				repaint();
				return;
			}
			tm=nexttm;
			nexttm=new Tetromino();//����һ���µ�tetromino
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
		
		
		//��δ��ʼ������ͣ״̬�£����س������Կ�ʼ��Ϸ
		if(keyCode==KeyEvent.VK_ENTER&&(status==0||status==2)) {
			status=1;
			timer.start();
			return;
		}
		
		//�ڽ���״̬�£����س���������ͣ��Ϸ
		if(keyCode==KeyEvent.VK_ENTER&&status==1) {
			status=2;
			timer.stop();
			repaint();
			return;
		}
		
		//����Ϸʧ�ܵ�״̬�£����س����������¿�ʼ��Ϸ
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
