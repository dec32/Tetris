package package1;

import java.util.Random;

public class Tetromino {
	int x,y,height,width,r,g,b,posx,posy;//posx��posy��ʾΪ��ʹtetromino�����Ͻǵķ��������ʾ��Ҫ������ƫ����
	char type;
	char tp[]= {'I','O','T','L','J','Z','S'};
	boolean[][] shape=new boolean[4][4];
	Tetromino(){
		//�������tetromino������
		int t=new Random().nextInt(7);
		type=tp[t];
		x=4;
		y=0;
		//��tetromino����״��ʱ��Ϊ��
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				shape[i][j]=false;
			}
		}
		//���ݳ�ȡ����tetromino�����ͳ�ʼ��һЩ���ԣ�����״�ʹ�С��
		if(this.type=='I') {
			shape[0][0]=true;
			shape[1][0]=true;
			shape[2][0]=true;
			shape[3][0]=true;
			height=4;
			width=1;
			r=222;
			g=48;
			b=48;
			posx=45;
			posy=0;		
			
		}
		if(this.type=='O') {
			shape[0][0]=true;
			shape[0][1]=true;
			shape[1][0]=true;
			shape[1][1]=true;
			height=2;
			width=2;
			r=0;
			g=128;
			b=64;
			posx=30;
			posy=30;	
		}
		if(this.type=='T') {
			shape[0][1]=true;
			shape[1][0]=true;
			shape[1][1]=true;
			shape[1][2]=true;
			height=2;
			width=3;
			r=184;
			g=61;
			b=186;
			posx=15;
			posy=30;	
		}
		if(this.type=='L') {
			shape[0][0]=true;
			shape[1][0]=true;
			shape[2][0]=true;
			shape[2][1]=true;
			height=3;
			width=2;
			r=230;
			g=0;
			b=98;
			posx=30;
			posy=15;	
		}
		if(this.type=='J') {
			shape[0][1]=true;
			shape[1][1]=true;
			shape[2][1]=true;
			shape[2][0]=true;
			height=3;
			width=2;
			r=33;
			g=69;
			b=171;
			posx=30;
			posy=15;	
		}
		if(this.type=='Z') {
			shape[0][0]=true;
			shape[0][1]=true;
			shape[1][1]=true;
			shape[1][2]=true;
			height=2;
			width=3;
			r=255;
			g=201;
			b=14;
			posx=15;
			posy=30;	
		}
		if(this.type=='S') {
			shape[0][1]=true;
			shape[0][2]=true;
			shape[1][0]=true;
			shape[1][1]=true;
			height=2;
			width=3;
			r=0;
			g=190;
			b=190;
			posx=15;
			posy=30;	
		}
		
	}
	
	

}
