
import javax.microedition.lcdui.*;

import java.io.*;
import java.util.*;
import javax.microedition.media.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

//------------------------------------------------------
class KaperCanvas extends Canvas implements Runnable
//class KaperCanvas extends com.nokia.mid.ui.FullCanvas implements Runnable
//------------------------------------------------------
{

	// Public:

	public Kaper m_oKaperApp;

	public int    m_iWidth;
	public int    m_iHeight;

//	public long   m_iMemoryDif;
//	public String m_iMemoryMsg;
	
	public Random m_oRand;

	public Image[] m_oImageArray;

	public StateMenu    m_oStateMenu;
	public StateGame    m_oStateGame;
	
	public Font       m_oFontSmallP;	
	public Font       m_oFontSmallB;
	public Font       m_oFontMediumB;

	public boolean    m_bSpeedOptimized;
	public long          m_lSpeed;
	public boolean      m_bFontBitmap;
	public boolean		m_bWantToQuit;
	
	// Protected:
	// Private:

	public char         m_cGameMode;
	private Player      m_oPlayer;
	private long		m_lIntroWait;
	private boolean     m_bIntroWaitb;
	private Graphics    m_oFrontBuffer;
	private StringBuffer m_stringBuffer;
	private char[]       m_stringChars; 

	
	//------------------------------------------------------
	public KaperCanvas()
	//------------------------------------------------------
	{
		//super(true); // true = supressKeyEvents

		m_bWantToQuit        = false;
		
		m_stringBuffer       = new StringBuffer("0000");
		m_stringChars        = new char[m_stringBuffer.length()];
		m_lIntroWait = 1999999999;
		m_cGameMode  = 0;
//		m_iMemoryMsg = "";
		
		m_iWidth        = getWidth();
		m_iHeight       = getHeight();

		m_oRand			= new Random(System.currentTimeMillis());

		m_oFontSmallP   = Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_PLAIN,Font.SIZE_SMALL);		
		m_oFontSmallB   = Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_BOLD,Font.SIZE_SMALL);
		m_oFontMediumB  = Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
		
		m_oPlayer    = null;
		m_oStateMenu = null;
		m_oStateGame = null;
		
		m_bFontBitmap = true;
		m_bIntroWaitb = false;
			}

	//------------------------------------------------------
	public void paint(Graphics oFrontBuffer)
	//------------------------------------------------------
	{
		//m_iWidth        = getWidth();   // Only MIDP 2.0 - Fullscreen support
		//m_iHeight       = getHeight();  // Only MIDP 2.0 - Fullscreen support

		m_oFrontBuffer = oFrontBuffer; // To use with Font
if(!m_bWantToQuit)
{
		switch(m_cGameMode)
		{
			case 0:
			case 6:
			{
				
				oFrontBuffer.setColor(42,102,139);
				oFrontBuffer.fillRect(0,0,m_iWidth,m_iHeight);
				oFrontBuffer.setClip((m_iWidth/2)-50,(m_iHeight/2)-50,89,71);
				oFrontBuffer.drawImage(m_oImageArray[1],(m_iWidth/2)-50,(m_iHeight/2)-50,Graphics.TOP | Graphics.LEFT);
				oFrontBuffer.setClip(0,0,m_iWidth,m_iHeight);
				if(0 == m_cGameMode)
				{
					int iXCenter = (m_iWidth/2)-67;
					int iYCenter = (m_iHeight/2)-20;
					int iMove = (int)(m_lIntroWait - System.currentTimeMillis()); // 2000-0

					oFrontBuffer.setColor(42,102,139);
					if(iMove >= 3000)break;			
					else			 oFrontBuffer.fillRect(0,0,m_iWidth,m_iHeight);

					oFrontBuffer.setColor(255,200,200);
					if(iMove <=   0)iMove = 1;
					if(iMove > 2000)iMove = 2000;
					
					// M
					oFrontBuffer.drawLine(iXCenter+20,2+iYCenter,iXCenter+20,12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+20,2+iYCenter,iXCenter+23-(iMove/600),7-(iMove/400)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+23,7-(iMove/800)+iYCenter,iXCenter+26-(iMove/600),2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+26,2+iYCenter,iXCenter+26,12-(iMove/200)+iYCenter);
					// O
					oFrontBuffer.drawLine(iXCenter+28,2+iYCenter,iXCenter+28,12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+34,2+iYCenter,iXCenter+34,12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+28,2+iYCenter,iXCenter+34-(iMove/200),2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+28,12+iYCenter,iXCenter+34-(iMove/200),12+iYCenter);
					// B
					oFrontBuffer.drawLine(iXCenter+36,2+iYCenter,iXCenter+36,12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+42,2+iYCenter,iXCenter+42,12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+36,2+iYCenter,iXCenter+42-(iMove/200),2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+36,12+iYCenter,iXCenter+42-(iMove/200),12+iYCenter);
					oFrontBuffer.drawLine(iXCenter+36,6+iYCenter,iXCenter+42-(iMove/200),6+iYCenter);
					// I
					oFrontBuffer.drawLine(iXCenter+44,2+iYCenter,iXCenter+50-(iMove/300), 2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+44,12+iYCenter,iXCenter+50-(iMove/300),12+iYCenter);
					oFrontBuffer.drawLine(iXCenter+47,2+iYCenter,iXCenter+47,12-(iMove/200)+iYCenter);
					// L
					oFrontBuffer.drawLine(iXCenter+52,2+iYCenter,iXCenter+52, 12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+52,12+iYCenter,iXCenter+58-(iMove/300),12+iYCenter);
					// K
					oFrontBuffer.drawLine(iXCenter+60,2+iYCenter,iXCenter+60, 12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+60,7-(iMove/400)+iYCenter,iXCenter+66-(iMove/400),  2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+60,7+iYCenter,iXCenter+66-(iMove/400), 12-(iMove/400)+iYCenter);
					// A
					oFrontBuffer.drawLine(iXCenter+68,12-(iMove/200)+iYCenter,iXCenter+71-(iMove/600), 2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+71,2+iYCenter,iXCenter+74-(iMove/600), 12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+69,10+iYCenter,iXCenter+73-(iMove/500), 10+iYCenter);
					// P
					oFrontBuffer.drawLine(iXCenter+76,12-(iMove/200)+iYCenter,iXCenter+76, 2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+76,2+iYCenter,iXCenter+82-(iMove/400),  2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+82,2+iYCenter,iXCenter+82, 7-(iMove/400)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+76,7+iYCenter,iXCenter+82-(iMove/400),  7+iYCenter);
					// E
					oFrontBuffer.drawLine(iXCenter+84,12-(iMove/200)+iYCenter,iXCenter+84, 2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+84,2+iYCenter,iXCenter+90-(iMove/300),2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+84,7+iYCenter,iXCenter+90-(iMove/300),7+iYCenter);
					oFrontBuffer.drawLine(iXCenter+84,12+iYCenter,iXCenter+90-(iMove/300),12+iYCenter);
					// R
					oFrontBuffer.drawLine(iXCenter+92,12-(iMove/200)+iYCenter,iXCenter+92, 2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+92,2+iYCenter,iXCenter+98-(iMove/300),2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+98,2+iYCenter,iXCenter+98,7-(iMove/500)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+92,7+iYCenter,iXCenter+98-(iMove/300),7+iYCenter);
					oFrontBuffer.drawLine(iXCenter+92,7+iYCenter,iXCenter+98-(iMove/300),12-(iMove/500)+iYCenter);
					// .
					oFrontBuffer.drawLine(iXCenter+100,10+iYCenter,iXCenter+102-(iMove/800), 10+iYCenter);
					oFrontBuffer.drawLine(iXCenter+100,11+iYCenter,iXCenter+102-(iMove/800), 11+iYCenter);
					oFrontBuffer.drawLine(iXCenter+100,12+iYCenter,iXCenter+102-(iMove/800),12+iYCenter);
					// D
					oFrontBuffer.drawLine(iXCenter+105,2+iYCenter,iXCenter+105,12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+110,2+iYCenter,iXCenter+110,12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+104,2+iYCenter,iXCenter+110-(iMove/200), 2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+104,12+iYCenter,iXCenter+110-(iMove/200),12+iYCenter);
					// K
					oFrontBuffer.drawLine(iXCenter+112,2+iYCenter,iXCenter+112, 12-(iMove/200)+iYCenter);
					oFrontBuffer.drawLine(iXCenter+112,7-(iMove/300)+iYCenter,iXCenter+118-(iMove/300),  2+iYCenter);
					oFrontBuffer.drawLine(iXCenter+112,7+iYCenter,iXCenter+118-(iMove/300), 12-(iMove/300)+iYCenter);

				
					oFrontBuffer.setColor(220,102,139);

					// o
					oFrontBuffer.drawLine(iXCenter+15,iYCenter-3,iXCenter+123, iYCenter-3);
					oFrontBuffer.drawLine(iXCenter+15,iYCenter+18,iXCenter+123, iYCenter+18);
					oFrontBuffer.drawLine(iXCenter+15,iYCenter+18,iXCenter+15, iYCenter-3);
					oFrontBuffer.drawLine(iXCenter+123,iYCenter-3,iXCenter+123, iYCenter+18);
					
					oFrontBuffer.drawLine(iXCenter+13,iYCenter-5,iXCenter+125, iYCenter-5);
					oFrontBuffer.drawLine(iXCenter+13,iYCenter+20,iXCenter+125, iYCenter+20);
					oFrontBuffer.drawLine(iXCenter+13,iYCenter+20,iXCenter+13, iYCenter-5);
					oFrontBuffer.drawLine(iXCenter+125,iYCenter-5,iXCenter+125, iYCenter+20);
				}
				
				if(6 == m_cGameMode)
				{
					oFrontBuffer.setColor(255,0,0);
					oFrontBuffer.drawString("Not enough mem... [0]",3,m_iHeight-20,Graphics.TOP | Graphics.LEFT);
					oFrontBuffer.drawLine(1,m_iHeight- 4,110,m_iHeight- 4);
					oFrontBuffer.drawLine(1,m_iHeight-20,110,m_iHeight-20);
					oFrontBuffer.drawLine(1,m_iHeight-20,  1,m_iHeight- 4);
					oFrontBuffer.drawLine(110,m_iHeight-20,  110,m_iHeight- 4);
				}
			}
			break;
			
			case 1:
			{
				m_oStateMenu.Draw(oFrontBuffer);
			}
			break;
			
			case 2:
			{
				m_oStateGame.Draw(oFrontBuffer);
			}
			break;
		}		

		// Draw Memory used
/*
		oFrontBuffer.setColor(0,0,255);
		oFrontBuffer.drawRect(3,3,103,4);
		oFrontBuffer.drawRect(4,4,101,4);
		oFrontBuffer.setColor(255,0,0);
		oFrontBuffer.drawRect(5,5,(int)(((1000000/Runtime.getRuntime().totalMemory())*(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())) / 10000),1);
		oFrontBuffer.drawString(m_iMemoryMsg,135,0,Graphics.TOP | Graphics.LEFT);

		if(m_iMemoryDif != (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()))
		{		
			m_iMemoryMsg = Integer.toString((int)(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
			m_iMemoryDif = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
		}
*/
}
		if(m_bWantToQuit)
		{
			oFrontBuffer.setColor(42,102,139);
			oFrontBuffer.fillRect(0,0,m_iWidth,m_iHeight);
			oFrontBuffer.setClip((m_iWidth/2)-77,2,149,59);
			oFrontBuffer.drawImage(m_oImageArray[0],(m_iWidth/2)-77,2,0);
			oFrontBuffer.setClip(0,0,m_iWidth,m_iHeight);
			DrawString("[Game paused]",(m_iWidth/2)-42      ,(m_iHeight/2)-5,false,2);
			DrawString("Press again to quit",(m_iWidth/2)-52,(m_iHeight/2)-30,false,2);
		}
		
	}

	//------------------------------------------------------
	public void Init(int cGameMode)
	//------------------------------------------------------
	{
		switch(cGameMode)
		{
			case 0:
			{
				try
				{
					m_oImageArray    = new Image[25];
					m_oImageArray[1] = Image.createImage("/2.png");
					m_oImageArray[24] = Image.createImage("/8.png");
				
					//setFullScreenMode(true);  // Only MIDP 2.0
				}
				catch(IOException e){}		
			}
			break;
			
			case 1:
			{
				if(m_oStateMenu == null)m_oStateMenu    = new StateMenu(this);
				
				try
				{
					// Check for Continue Game
					RecordStore nameRecord = RecordStore.openRecordStore("KaperGame",true);
					if(nameRecord.getNumRecords() < 10)
					{
						m_oStateMenu.m_bAnyContinueGame = false;
					}
					else
					{
						m_oStateMenu.m_bAnyContinueGame = true;
					}
					nameRecord.closeRecordStore();
				}
				catch(RecordStoreException e)
				{
				}
				
				if(m_oStateMenu.m_bAnyContinueGame)m_oStateMenu.m_iMenuSelect = 1;
				else                               m_oStateMenu.m_iMenuSelect = 0;
			}
			break;
			
			case 2:
			{
				try
				{
					if(m_oStateGame == null)m_oStateGame    = new StateGame(this);
					m_oStateGame.Init();
				}
				catch(OutOfMemoryError e){m_cGameMode = 6;}
			}
			break;
		}	
	}

	//------------------------------------------------------
	public void run()
	//------------------------------------------------------
	{
		while(true)
		{
			//Thread.yield();
			try{Thread.sleep(1);} catch(Exception e) {}
			
			if((System.currentTimeMillis() - m_lSpeed) >= 60)
			{
				m_lSpeed = System.currentTimeMillis();
				repaint();
				
				switch(m_cGameMode)
				{
					case 0:
					{
						try
						{
							if(m_bIntroWaitb == false)
							{
								m_lIntroWait = 3500 + System.currentTimeMillis();
								m_oImageArray[0]  = Image.createImage("/1.png");
								Init(1);
								m_bIntroWaitb = true;
							}
							else if((m_lIntroWait+1000) < System.currentTimeMillis())
							{
								m_cGameMode = 1;
							}
						}
						catch(IOException e){m_cGameMode = 6;}
						catch(OutOfMemoryError e){m_cGameMode = 6;}
			
					}
					break;
					
					case 1:
					{
						if(m_oKaperApp.m_bPaused)break;
		
						int iStatus = m_oStateMenu.Update();
		
						if(iStatus == 0) // Start new fresh game
						{
							m_cGameMode = 5;
							Init(2);
							m_oStateGame.CleanGame(true);
							m_cGameMode = 2;
						}
		
						if(iStatus == 1) // Continue game
						{
							m_cGameMode = 5;
							Init(2);
							m_cGameMode = 2;
							m_oStateGame.LoadGame();
						}
		
						if(iStatus == 3) // Start new fresh game with +500g
						{
							m_cGameMode = 5;
							Init(2);
							m_oStateGame.CleanGame(true);
							m_cGameMode = 2;
		
							m_oStateGame.m_iResourceGold += 500;
						}
		
					}
					break;
		
					case 2:
					{
						if(m_oKaperApp.m_bPaused)break;
						if(m_bWantToQuit)break;
						
						if(m_oStateGame.Update() == true)
						{ // End game
							Init(1);
							m_cGameMode = 1;
						}
		
					}
					break;
				}	

			}

		}
	}
	//------------------------------------------------------
	public void SoftButtonSND(boolean bState)
	//------------------------------------------------------
	{
		try
		{
			if(!bState)
			{
				if(m_oPlayer != null)StopSound();
			}
			else
			{
				if(m_oPlayer != null)
				{
					m_oPlayer.start();
				}
				else if(m_cGameMode == 1)
				{
					PlaySound("a.mid",false);
				}
				else if(m_cGameMode == 2)
				{

					if(m_oStateGame.m_iGameState == 1)
					{
						PlaySound("d.mid",true);
					}

				}
			}
		}
		catch(MediaException e){}
	}

	//------------------------------------------------------
	public void PlaySound(String sName,boolean bRepeat)
	//------------------------------------------------------
	{
		try
		{
			if(!m_oKaperApp.m_bSnd)return;

			StopSound();

			InputStream is = getClass().getResourceAsStream(sName);
			           m_oPlayer = Manager.createPlayer(is,"audio/midi");
			if(bRepeat)m_oPlayer.setLoopCount(-1);
			else       m_oPlayer.setLoopCount( 1);
			is = null;

			m_oPlayer.realize();
			m_oPlayer.prefetch();

			m_oPlayer.start();
		}
		catch(IOException ioe) {}
		catch(MediaException e){}
	}

	//------------------------------------------------------
	public void StopSound()
	//------------------------------------------------------
	{
		if(m_oPlayer != null)
		{
			try
			{
				if(m_oPlayer.getState() == Player.STARTED)m_oPlayer.stop();
				m_oPlayer.deallocate();
				m_oPlayer.close();
				m_oPlayer = null;
			}
			catch(MediaException e){}
		}
	}

	//------------------------------------------------------
	public void ShutDown()
	//------------------------------------------------------
	{
		m_bWantToQuit = false;
		
		if(m_cGameMode == 2)
		{
			if(m_oStateGame.m_iGameState == 1)
			{
				m_oStateGame.m_oStateCity.DeInit();
				m_oStateGame.m_iGameState = 0;
			}
			else if(m_oStateGame.m_iGameState == 4)
			{
				m_oStateGame.m_oStateAttack.m_bRetreat = true;
				m_oStateGame.m_oStateAttack.m_iKeyPause = 0;
				m_oStateGame.m_oStateAttack.SoftKey(Canvas.KEY_NUM0);
			}
			else if(m_oStateGame.m_iGameState == 3)
			{
				m_oStateGame.m_oStateBoard.m_bRetreat = true;
				m_oStateGame.m_oStateBoard.m_iKeyPause = 0;
				m_oStateGame.m_oStateBoard.SoftKey(Canvas.KEY_NUM0);
			}
//			else
//			{

				m_oStateGame.SaveGame();
				Init(1);
				m_cGameMode = 1;
//			}
		}
		else
		{
			m_oKaperApp.destroyApp(false);
			m_oKaperApp.notifyDestroyed();
			m_oFontSmallP  = null;
			m_oFontSmallB  = null;
			m_oFontMediumB = null;
		}
	}

	//------------------------------------------------------
	protected void keyPressed(int keyCode)
	//------------------------------------------------------
	{
		if(0   == m_cGameMode)
		{
			m_lIntroWait -= 3000;
		}
		
		if(2   == m_cGameMode)
		{
			if(m_oStateGame != null)
			{
				if(m_oStateGame.m_bLocked == false)m_oStateGame.NormalButton(keyCode);
			}
		}
		if(1   == m_cGameMode)
		{
			if(m_oStateMenu != null)
			{
				if(m_oStateMenu.m_bLocked == false)m_oStateMenu.NormalButton(keyCode);
			}	
		}		
		
		if(Canvas.KEY_STAR == keyCode || Canvas.KEY_POUND == keyCode)
		{
			if(m_bWantToQuit == true)
			{
				if(m_oKaperApp.m_oKaperCanvas != null)ShutDown();
				else
				{
					m_oKaperApp.destroyApp(false);
					m_oKaperApp.notifyDestroyed();
				}

			}
			else
			{
				if(2 == m_cGameMode)
				{
					if(m_oStateGame.m_bShipMoving != true && m_oStateGame.m_bMapMoving != true && m_oStateGame.m_bKeyUp == false && m_oStateGame.m_bKeyDown == false && m_oStateGame.m_bKeyRight == false && m_oStateGame.m_bKeyLeft == false)
					{
						m_bWantToQuit = true;
					}
				}
				else
				{
					m_bWantToQuit = true;
				}
			}
		}
		else 
		{
			m_bWantToQuit = false;
		}
	}

	//------------------------------------------------------
	public void keyReleased (int keyCode)
	//------------------------------------------------------
	{
		if(6 == m_cGameMode && keyCode == Canvas.KEY_NUM0)ShutDown();
		
		if(2 == m_cGameMode)
		{
			if(m_oStateGame != null)
			{
				if(m_oStateGame.m_bLocked == false)
				{
					if(m_oStateGame.m_iGameState == 4)m_oStateGame.m_oStateAttack.SoftKeyRelease(keyCode);
				else if(m_oStateGame.m_iGameState == 3)m_oStateGame.m_oStateBoard.SoftKeyRelease(keyCode);
				else                                   m_oStateGame.NormalButton(128);
				}
			}
		}
		if(1   == m_cGameMode)
		{
			if(m_oStateMenu != null)
			{
				if(m_oStateMenu.m_bLocked == false)m_oStateMenu.NormalButton(128);
			}	
		}
	}
	///////////////////////////////////////////////////////////////////////////
	//                                                                       //
	//                              Tool funtions                            //
	//                                                                       //
	///////////////////////////////////////////////////////////////////////////
	//------------------------------------------------------
	public void DrawString(String sText, int x, int y, boolean bBold,int iColor)
	//------------------------------------------------------
	{
	//try
	//{
		sText = sText.toUpperCase();
		
		if(m_bFontBitmap == false || m_oImageArray[24] == null)
		{
			if(bBold)m_oFrontBuffer.setFont(m_oFontMediumB);
			else     m_oFrontBuffer.setFont(m_oFontSmallP);
			     if(iColor == 1)m_oFrontBuffer.setColor(255,255,255);
			else if(iColor == 0)m_oFrontBuffer.setColor(0,0,0);
			else if(iColor == 2)m_oFrontBuffer.setColor(255,0,0);
			else if(iColor >= 3)m_oFrontBuffer.setColor(200,200,0);
			     
			y += 2;
			     
			m_oFrontBuffer.drawString(sText,x,y,Graphics.TOP | Graphics.LEFT);			
		}
		else
		{
			int iXoffset = 0;
			int iYoffset = 0;
			boolean bDraw = false;
			
			y += 5;
			
			for(int i=0;i < sText.length();i++)
			{
				iYoffset = 0;
				
				     if(sText.charAt(i) >= '0' && sText.charAt(i) <= ':')iXoffset  = (sText.charAt(i) - '0')*5;
				else if(sText.charAt(i) >= 'A' && sText.charAt(i) <= 'H')iXoffset  = (sText.charAt(i) - 'A')*5+55;
				else if(sText.charAt(i) >= 'I' && sText.charAt(i) <= 'Z'){iXoffset = (sText.charAt(i) - 'I')*5;iYoffset = 7;}
				else if(sText.charAt(i) == '.')bDraw = true;
				else if(sText.charAt(i) == '[')bDraw = true;
				else if(sText.charAt(i) == ']')bDraw = true;
				else if(sText.charAt(i) == '<')bDraw = true;
				else if(sText.charAt(i) == '>')bDraw = true;
				else continue;
	
				if(iColor == 1)iYoffset += 14;
				if(iColor == 2)iYoffset += 28;
				if(iColor >= 3)iYoffset += 42;
				
				m_oFrontBuffer.setClip(x+(i*5),y,4,7);
	
				if(bDraw)
				{
				   if(iColor == 1)m_oFrontBuffer.setColor(255,255,255);
				   else if(iColor == 0)m_oFrontBuffer.setColor(0,0,0);
				   else if(iColor >= 2)m_oFrontBuffer.setColor(255,0,0);
					m_oFrontBuffer.setClip(0,0,m_iWidth,m_iHeight);

					if(sText.charAt(i) == '.')
					{
						m_oFrontBuffer.drawLine(x+1+(i*5),y+5,x+(i*5)+2,y+5);
						m_oFrontBuffer.drawLine(x+1+(i*5),y+6,x+(i*5)+2,y+6);
					}
					else if(sText.charAt(i) == '>')
					{
						m_oFrontBuffer.drawLine(x+(i*5),y  ,x+(i*5)+4,y+3);
						m_oFrontBuffer.drawLine(x+(i*5),y+6,x+(i*5)+4,y+3);
					}
					else if(sText.charAt(i) == '<')
					{
						m_oFrontBuffer.drawLine(x+(i*5),y+3,x+(i*5)+4,y);
						m_oFrontBuffer.drawLine(x+(i*5),y+3,x+(i*5)+4,y+6);
					}
					else if(sText.charAt(i) == '[')
					{
						m_oFrontBuffer.drawLine(x+1+(i*5),y  ,1+x+(i*5),y+6);
						m_oFrontBuffer.drawLine(x+1+(i*5),y  ,x+(i*5)+3,y);
						m_oFrontBuffer.drawLine(x+1+(i*5),y+6,x+(i*5)+3,y+6);
					}
					else if(sText.charAt(i) == ']')
					{
						m_oFrontBuffer.drawLine(x+3+(i*5),y  ,x+3+(i*5),y+6);
						m_oFrontBuffer.drawLine(x+3+(i*5),y  ,x+1+(i*5),y);
						m_oFrontBuffer.drawLine(x+3+(i*5),y+6,x+1+(i*5),y+6);
					}
					
					bDraw = false;
					continue;
				}
				else	m_oFrontBuffer.drawImage(m_oImageArray[24],x+(i*5)-iXoffset,y-iYoffset,Graphics.TOP | Graphics.LEFT);
				
				if(bBold)
				{
					m_oFrontBuffer.setClip(1+x+(i*5),y,4,7);
					m_oFrontBuffer.drawImage(m_oImageArray[24],1+x+(i*5)-iXoffset,y-iYoffset,Graphics.TOP | Graphics.LEFT);
				}
				
				
			}
			
			m_oFrontBuffer.setClip(0,0,m_iWidth,m_iHeight);
		}
	//}
	//catch(Exception e)
	//{
	//	m_bFontBitmap = false;
	//}
	}
	
	//------------------------------------------------------
	public void DrawString(int iNumbers, int x, int y, boolean bBold,int iColor)
	//------------------------------------------------------
	{
	//try
	//{
		m_stringBuffer.delete(0,m_stringBuffer.length());
		m_stringBuffer.append((int)iNumbers);
		m_stringBuffer.getChars( 0, m_stringBuffer.length(), m_stringChars, 0 );

		if(m_bFontBitmap == false || m_oImageArray[24] == null)
		{
			if(bBold)m_oFrontBuffer.setFont(m_oFontMediumB);
			else     m_oFrontBuffer.setFont(m_oFontSmallP);
			     if(iColor == 1)m_oFrontBuffer.setColor(255,255,255);
			else if(iColor == 0)m_oFrontBuffer.setColor(0,0,0);
			else if(iColor == 2)m_oFrontBuffer.setColor(255,0,0);
			else if(iColor >= 3)m_oFrontBuffer.setColor(200,200,0);
			     
			y += 2;
					
			m_oFrontBuffer.drawChars(m_stringChars,0,m_stringBuffer.length(),x,y,Graphics.TOP | Graphics.LEFT);			
		}
		else
		{
			int iXoffset = 0;
			int iYoffset = 0;
	
			y += 5;	
			x += 1;
			
			for(int i=0;i < 4;i++)
			{
				iYoffset = 0;

			     	 if(m_stringBuffer.length() <= i)break;
			
				     if(m_stringChars[i] >= '0' && m_stringChars[i] <= '9')iXoffset  = (m_stringChars[i] - '0')*5;
				     else continue;
	
				if(iColor == 1)iYoffset += 14;
				if(iColor == 2)iYoffset += 28;
				if(iColor >= 3)iYoffset += 42;
				
				m_oFrontBuffer.setClip(x+(i*5),y,4,7);
				m_oFrontBuffer.drawImage(m_oImageArray[24],x+(i*5)-iXoffset,y-iYoffset,Graphics.TOP | Graphics.LEFT);
/*
				if(bBold)
				{
					m_oFrontBuffer.setClip(1+x+(i*5),y,4,7);
					m_oFrontBuffer.drawImage(m_oImageArray[24],1+x+(i*5)-iXoffset,y-iYoffset,Graphics.TOP | Graphics.LEFT);
				}
*/
			}
		}		
		m_oFrontBuffer.setClip(0,0,m_iWidth,m_iHeight);
	//}
	//catch(Exception e)
	//{
	//	m_bFontBitmap = false;
	//}
	}
}