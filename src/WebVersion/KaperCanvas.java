
import javax.microedition.lcdui.*;

import java.io.*;
import java.util.*;
import javax.microedition.media.*;

//------------------------------------------------------
class KaperCanvas extends Canvas implements Runnable
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
	
	public long          m_lSpeed;

	public Font       m_oFontSmallP;	
	public Font       m_oFontSmallB;
	public Font       m_oFontMediumB;

	// Protected:
	// Private:

	public char         m_cGameMode;
	private Player       m_oPlayer;

	//private Image Doublebuf;
	//private Graphics       gc;

	//------------------------------------------------------
	public KaperCanvas(boolean supressKeyEvents)
	//------------------------------------------------------
	{
		//super(true); // true = supressKeyEvents

		m_cGameMode  = 0;
//		m_iMemoryMsg = "";
		
		m_iWidth        = getWidth();
		m_iHeight       = getHeight();

		//Doublebuf       = Image.createImage(m_iWidth,m_iHeight);
		//gc		= Doublebuf.getGraphics();

		m_oRand			= new Random(System.currentTimeMillis());

		m_oFontSmallP   = Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_PLAIN,Font.SIZE_SMALL);		
		m_oFontSmallB   = Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_BOLD,Font.SIZE_SMALL);
		m_oFontMediumB  = Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
		
		m_oPlayer    = null;
		m_oStateMenu = null;
		m_oStateGame = null;
	}

	//------------------------------------------------------
	public void paint(Graphics oFrontBuffer)
	//------------------------------------------------------
	{
		switch(m_cGameMode)
		{
			case 0:
			case 6:
			{
				oFrontBuffer.setFont(m_oFontSmallB);
				oFrontBuffer.setColor(42,102,139);
				oFrontBuffer.fillRect(0,0,m_iWidth,m_iHeight);
				oFrontBuffer.setClip((m_iWidth/2)-50,(m_iHeight/2)-50,89,71);
				oFrontBuffer.drawImage(m_oImageArray[1],(m_iWidth/2)-50,(m_iHeight/2)-50,Graphics.TOP | Graphics.LEFT);

				if(6 == m_cGameMode)
				{
					oFrontBuffer.setClip(0,0,m_iWidth,m_iHeight);
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

		//oFrontBuffer.drawImage(Doublebuf,0,0,0);

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
					m_oImageArray    = new Image[6];
					m_oImageArray[1] = Image.createImage("/2.png");
				}
				catch(IOException e){}		
			}
			break;
			
			case 1:
			{
				if(m_oStateMenu == null)m_oStateMenu    = new StateMenu(this);
				m_oStateMenu.m_iMenuSelect = 0;
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
							m_oImageArray[0] = Image.createImage("/1.png");
							Init(1);
							m_cGameMode = 1;
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
							m_cGameMode = 2;
		
							m_oStateGame.m_iResourceGold += 500;
						}
		
					}
					break;
		
					case 2:
					{
						if(m_oKaperApp.m_bPaused)break;
		
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
		if(m_cGameMode == 2)
		{
			if(m_oStateGame.m_iGameState == 1)
			{
				m_oStateGame.m_oStateCity.DeInit();
				m_oStateGame.m_iGameState = 0;
			}
			else
			{
				m_oStateGame.SaveGame();
				Init(1);
				m_cGameMode = 1;
			}
		}

		else
		{
			m_oKaperApp.destroyApp(false);
			m_oKaperApp.notifyDestroyed();
			m_oFontSmallP  = null;
			m_oFontSmallB  = null;
			m_oFontMediumB = null;

			return;
		}

		return;
	}

	//------------------------------------------------------
	protected void keyPressed(int keyCode)
	//------------------------------------------------------
	{
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
}