
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.microedition.io.*;
import java.io.*;

//------------------------------------------------------
class StateMenu
//------------------------------------------------------
{
	// Public:

	public  int           m_iSubmitCodeStatus;

	public String         m_sWebCode;

	// Protected:
	// Private:

	private int           m_iMenuY;

	private int           m_iWebCodePos;

	private int           m_iWebRankShow;

	private String        m_sHighPlayers[];
	private String        m_sHighScore[];

	private KaperCanvas   m_oCanvas;
	public  int           m_iMenuSelect;
	private int           m_iKeyOldState;

	private char          m_cPageInstructions;
	private boolean       m_bShowInstructions;
	private boolean       m_bShowHighscore;
	private boolean       m_bShowWebHighscore;
	private boolean       m_bShowCredits;
	private boolean       m_bShowWebCode;

	private int           m_iScrollX;

	public boolean       m_bLocked;	

	//------------------------------------------------------
	public StateMenu(KaperCanvas oKaperCanvas)
	//------------------------------------------------------
	{
		m_oCanvas      = oKaperCanvas;

		m_bLocked      = true;
	
		m_iMenuY       = (m_oCanvas.m_iHeight/2)-27;

		m_iWebRankShow   = 1;

		m_sWebCode       = "1";

		m_bShowHighscore = false;
		m_bShowCredits   = false;
		m_bShowWebHighscore = false;
		m_bShowWebCode      = false;

		m_sHighPlayers = new String[5];
		m_sHighScore   = new String[5];

		m_oCanvas.PlaySound("a.mid",false);		

		m_bLocked      = false;
	}

	//------------------------------------------------------
	public void Draw(Graphics g)
	//------------------------------------------------------
	{

//			Image oImgWater = Image.createImage(25,25);
//			oImgWater.getGraphics().drawImage(m_oCanvas.m_oImageArray[1],-165,-20,Graphics.TOP | Graphics.LEFT);

			for(int x=0;x <= (m_oCanvas.m_iWidth/25)+1;x++)
			{
				for(int y=0;y <= (m_oCanvas.m_iHeight/25)+1;y++)
				{
//					g.drawImage(oImgWater,(x*25)+m_iScrollX,y*25,Graphics.TOP | Graphics.LEFT);
					g.setClip((x*25)+m_iScrollX,y*25,25,25);
					g.drawImage(m_oCanvas.m_oImageArray[1],(x*25)-165+m_iScrollX,(y*25)-20,Graphics.TOP | Graphics.LEFT);
				}
			}


		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
		g.setColor(255,255,255);
		g.setFont(m_oCanvas.m_oFontMediumB);
	
		if(m_bShowInstructions)
		{
			g.drawString("Instructions"  ,(m_oCanvas.m_iWidth/2)-42, 0,Graphics.TOP | Graphics.LEFT);
			
			if(m_cPageInstructions == 0)
			{
				g.setFont(m_oCanvas.m_oFontSmallB);
	
				g.drawString("Check hiscores at www.mobilkaper.dk",1, 20,Graphics.TOP | Graphics.LEFT);
				g.drawString("Or directly on your internet "      ,1, 30,Graphics.TOP | Graphics.LEFT);
				g.drawString("enabled phone."                     ,1, 40,Graphics.TOP | Graphics.LEFT);
	
				g.setFont(m_oCanvas.m_oFontMediumB);
				g.drawString("Goal:"                              ,1, 53,Graphics.TOP | Graphics.LEFT);
				g.setFont(m_oCanvas.m_oFontSmallB);
				g.drawString("You must board and capture"         ,1, 65,Graphics.TOP | Graphics.LEFT);
				g.drawString("as many enemy ships as you can."    ,1, 75,Graphics.TOP | Graphics.LEFT);
				g.drawString("You get the highest reward for"     ,1, 85,Graphics.TOP | Graphics.LEFT);
				g.drawString("bringing them to a harbor."         ,1, 95,Graphics.TOP | Graphics.LEFT);
	
				g.setFont(m_oCanvas.m_oFontMediumB);
				g.drawString("Page 1/3"                           ,(m_oCanvas.m_iWidth/2)-30,118,Graphics.TOP | Graphics.LEFT);
				g.setFont(m_oCanvas.m_oFontSmallB);
			}
			else if (m_cPageInstructions == 1)
			{
				g.setFont(m_oCanvas.m_oFontMediumB);
				g.drawString("Ship duel:"                         ,1, 18,Graphics.TOP | Graphics.LEFT);
				g.setFont(m_oCanvas.m_oFontSmallB);
				g.drawString("Hold and release 0 or 5 to shoot."  ,1, 30,Graphics.TOP | Graphics.LEFT);
				g.drawString("Move to the bottom of the"          ,1, 40,Graphics.TOP | Graphics.LEFT);
				g.drawString("screen to escape combat."           ,1, 50,Graphics.TOP | Graphics.LEFT);
				g.drawString("Tip: Shoot the enemy ship with"     ,1, 60,Graphics.TOP | Graphics.LEFT);
				g.drawString("your guns to lessen their numbers." ,1, 70,Graphics.TOP | Graphics.LEFT);
				g.drawString("But don't sink it."                 ,1, 80,Graphics.TOP | Graphics.LEFT);
				g.drawString("Sail into the enemy ship to"        ,1, 90,Graphics.TOP | Graphics.LEFT);
				g.drawString("board it and battle on the deck."   ,1,100,Graphics.TOP | Graphics.LEFT);
	 
				g.setFont(m_oCanvas.m_oFontMediumB);
				g.drawString("Page 2/3"                           ,(m_oCanvas.m_iWidth/2)-30,118,Graphics.TOP | Graphics.LEFT);
				g.setFont(m_oCanvas.m_oFontSmallB);
			}
			
			else if (m_cPageInstructions == 2)
			{
				g.setFont(m_oCanvas.m_oFontMediumB);
				g.drawString("Upgrading:"                         ,1, 18,Graphics.TOP | Graphics.LEFT);
				g.setFont(m_oCanvas.m_oFontSmallB);
				g.drawString("To carry more grain, guns and"         ,1, 30,Graphics.TOP | Graphics.LEFT);
				g.drawString("crew. You must upgrade your ship"     ,1, 40,Graphics.TOP | Graphics.LEFT);
				g.drawString("in a harbor."                ,1, 50,Graphics.TOP | Graphics.LEFT);

				g.setFont(m_oCanvas.m_oFontMediumB);
				g.drawString("Music:"                             ,1, 63,Graphics.TOP | Graphics.LEFT);
				g.setFont(m_oCanvas.m_oFontSmallB);
				g.drawString("Music can be turned on with your"   ,1, 75,Graphics.TOP | Graphics.LEFT);
				g.drawString("mobile soft-buttons. Default is off.",1, 85,Graphics.TOP | Graphics.LEFT);

				
				g.setFont(m_oCanvas.m_oFontMediumB);
				g.drawString("Page 3/3"                           ,(m_oCanvas.m_iWidth/2)-30,118,Graphics.TOP | Graphics.LEFT);
				g.setFont(m_oCanvas.m_oFontSmallB);
			}
			
		}

		else if(m_bShowHighscore)
		{
			for(int i=0;i < 5;i++)
			{
				g.drawString(m_sHighPlayers[i],(m_oCanvas.m_iWidth/2)-63,2+(i*20),Graphics.TOP | Graphics.LEFT);
				g.drawString(m_sHighScore[i],(m_oCanvas.m_iWidth/2)+27,2+(i*20),Graphics.TOP | Graphics.LEFT);
			}
		}

		else if(m_bShowWebCode)
		{
			if(m_iSubmitCodeStatus == 0)
			{
				g.drawString("Web code",(m_oCanvas.m_iWidth/2)-25,25,Graphics.TOP | Graphics.LEFT);

				g.drawString("Code:",18,40,Graphics.TOP | Graphics.LEFT);
				g.drawString(m_sWebCode,60,40,Graphics.TOP | Graphics.LEFT);			
				g.drawLine(60+(m_iWebCodePos*7),55,66+(m_iWebCodePos*7),55);//;


				g.setFont(m_oCanvas.m_oFontSmallB);
				g.drawString("[1]Cancel",(m_oCanvas.m_iWidth/2)-70,70,Graphics.TOP | Graphics.LEFT);
				g.drawString("[3]Submit",(m_oCanvas.m_iWidth/2)+30,70,Graphics.TOP | Graphics.LEFT);
			}
			if(m_iSubmitCodeStatus == 1)
			{
				g.drawString("Please wait...",(m_oCanvas.m_iWidth/2)-35,45,Graphics.TOP | Graphics.LEFT);
				g.drawString("[1]Cancel",(m_oCanvas.m_iWidth/2)-70,70,Graphics.TOP | Graphics.LEFT);
			}

			if(m_iSubmitCodeStatus == 2)
			{
				g.drawString("Respond: " + m_sWebCode,18,40,Graphics.TOP | Graphics.LEFT);
				g.drawString("[1]Back",(m_oCanvas.m_iWidth/2)-70,70,Graphics.TOP | Graphics.LEFT);
			}

		}


		else if(m_bShowCredits)
		{
			// Draw Text
			g.setFont(m_oCanvas.m_oFontSmallB);
			g.setColor(255,255,255);
	
			g.drawString("Art & Design:",(m_oCanvas.m_iWidth/2)+2-68,00,Graphics.TOP | Graphics.LEFT);
			g.drawString("Jonas Raagaard",(m_oCanvas.m_iWidth/2)+20-68,10,Graphics.TOP | Graphics.LEFT);
			
			g.drawString("Code:",(m_oCanvas.m_iWidth/2)+2-68,20,Graphics.TOP | Graphics.LEFT);
			g.drawString("Tommy Kobberø",(m_oCanvas.m_iWidth/2)+20-68,30,Graphics.TOP | Graphics.LEFT);
	
			g.drawString("Thanks to the",(m_oCanvas.m_iWidth/2)+2-68,50,Graphics.TOP | Graphics.LEFT);
	
			g.drawString("original author:",(m_oCanvas.m_iWidth/2)+2-68,60,Graphics.TOP | Graphics.LEFT);
			g.drawString("Peter Ole Frederiksen",(m_oCanvas.m_iWidth/2)+20-68,70,Graphics.TOP | Graphics.LEFT);
	
			g.drawString("(C)2005 Logicwork.net",(m_oCanvas.m_iWidth/2)+2-68,90,Graphics.TOP | Graphics.LEFT);
			g.drawString("& Jonaz.dk",(m_oCanvas.m_iWidth/2)+70-68,100,Graphics.TOP | Graphics.LEFT);
		}

		else // Menu
		{
			int iScreenMove = 0;
			int iTempY = m_iMenuY;
			int iYOffSet = 0;

			// Draw Title
			g.setClip((m_oCanvas.m_iWidth/2)-77,2,149,59);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)-77,2,0);

			if(((m_oCanvas.m_iHeight/2)-30) <= 58)
			{
				iYOffSet = 58 - ((m_oCanvas.m_iHeight/2)-30);
			}

			if((iTempY+iYOffSet) >= m_oCanvas.m_iHeight-15)
			{
				iScreenMove = (iTempY+iYOffSet) - m_oCanvas.m_iHeight + 15;
			}

			iScreenMove -= iYOffSet;

			// Menu	//
			g.setClip(0,58,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);

			g.drawString("Start new game",(m_oCanvas.m_iWidth/2)-50,(m_oCanvas.m_iHeight/2)-30-iScreenMove,Graphics.TOP | Graphics.LEFT);
		g.setColor(155,155,155);
			g.drawString("Continue game" ,(m_oCanvas.m_iWidth/2)-50,(m_oCanvas.m_iHeight/2)-15-iScreenMove,Graphics.TOP | Graphics.LEFT);
		g.setColor(255,255,255);
			g.drawString("instructions"  ,(m_oCanvas.m_iWidth/2)-50,(m_oCanvas.m_iHeight/2)   -iScreenMove,Graphics.TOP | Graphics.LEFT);
			g.drawString("Local score"   ,(m_oCanvas.m_iWidth/2)-50,(m_oCanvas.m_iHeight/2)+15-iScreenMove,Graphics.TOP | Graphics.LEFT);
		g.setColor(155,155,155);
			g.drawString("Web score"     ,(m_oCanvas.m_iWidth/2)-50,(m_oCanvas.m_iHeight/2)+30-iScreenMove,Graphics.TOP | Graphics.LEFT);
			g.drawString("Web code"      ,(m_oCanvas.m_iWidth/2)-50,(m_oCanvas.m_iHeight/2)+45-iScreenMove,Graphics.TOP | Graphics.LEFT);
		g.setColor(255,255,255);
			g.drawString("Credits"       ,(m_oCanvas.m_iWidth/2)-50,(m_oCanvas.m_iHeight/2)+60-iScreenMove,Graphics.TOP | Graphics.LEFT);
			g.drawString("Quit"          ,(m_oCanvas.m_iWidth/2)-50,(m_oCanvas.m_iHeight/2)+75-iScreenMove,Graphics.TOP | Graphics.LEFT);

			// Arrows

			if((m_iMenuY+iYOffSet) >= m_oCanvas.m_iHeight-15){iTempY = m_oCanvas.m_iHeight-15;iYOffSet = 0;}

			g.setClip((m_oCanvas.m_iWidth/2)+55,iTempY+iYOffSet-1,10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)+55-131,iTempY+iYOffSet-63,Graphics.TOP | Graphics.LEFT);
			g.setClip((m_oCanvas.m_iWidth/2)-66,iTempY+iYOffSet-1,10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)-66-120,iTempY+iYOffSet-63,Graphics.TOP | Graphics.LEFT);			
		}
		
		g.setFont(m_oCanvas.m_oFontSmallB);
	}	

	//------------------------------------------------------
	public int Update()
	//------------------------------------------------------
	{
		if(!m_bShowHighscore && !m_bShowCredits && !m_bShowWebCode && !m_bShowInstructions)
		{
			int iSpeed = 1;
			switch(m_iMenuSelect)
			{
				case 0:
				{
					if(m_iMenuY > (m_oCanvas.m_iHeight/2)-27)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)-22)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}
				break;
				
				case 1:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)-12)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)-17)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)-12)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)-7)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}
				break;
	
				case 2:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+ 3)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)- 2)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)+ 3)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)+ 8)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}
				break;
	
				case 3:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+18)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)+13)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)+ 18)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)+23)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}			
				break;
	
				case 4:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+33)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)+28)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)+ 33)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)+38)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}			
				break;
	
				case 5:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+48)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)+43)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)+ 48)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)+53)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}			
				break;

				case 6:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+63)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)+58)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)+ 63)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)+68)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}			
				break;				
				
				case 7:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+78)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)+73)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
				}			
				break;
			}

			// Key check
			if((m_iMenuSelect & 0x08) == 0x08)return (m_iMenuSelect - 0x08);

			// SerialCode accepted
			if(m_sWebCode.compareTo("Accepted") == 0)return 3;
		}

		if(m_iScrollX <= -25)m_iScrollX = 0;

		m_iScrollX--;

		return -1;
	}	

	//------------------------------------------------------
	public void NormalButton(int iKey)
	//------------------------------------------------------
	{
		int keyState = 0;
		if(iKey != 128)keyState  = m_oCanvas.getGameAction(iKey);

		if((m_bShowHighscore || m_bShowCredits || m_bShowInstructions || m_bShowWebHighscore) && iKey != 128)
		{
			if(m_bShowInstructions)
			{
				if(keyState == Canvas.DOWN)
				{
					if(m_cPageInstructions != 2)m_cPageInstructions++;
					return;
				}
				if(keyState == Canvas.UP)
				{
					if(m_cPageInstructions != 0)m_cPageInstructions--;
					return;
				}
			}
			
			
			if(m_bShowWebHighscore)
			{
				if(keyState == Canvas.LEFT)
				{
					m_iWebRankShow--;
					if(m_iWebRankShow <= 0)m_iWebRankShow = 9;
					return;
				}
				if(keyState == Canvas.RIGHT)
				{
					m_iWebRankShow++;
					if(m_iWebRankShow >= 10)m_iWebRankShow = 1;
					return;
				}
			}

			m_bShowInstructions = false;
			m_bShowCredits      = false;
			m_bShowHighscore    = false;
			m_bShowWebHighscore = false;

			for(int i=0;i < 5;i++)
			{
				m_sHighPlayers[i] = null;
			}

			System.gc();

			return;
		}


		// Op
		if((m_iKeyOldState & 4) == 0 && keyState == Canvas.UP)
		{
			     if(m_iMenuSelect == 0){m_iMenuSelect = 7;m_iMenuY = (m_oCanvas.m_iHeight/2)+78;}
			else if(m_iMenuSelect >  0)m_iMenuSelect--;
			m_iKeyOldState |= 4;
		}

		// Ned
		if((m_iKeyOldState & 8) == 0 && keyState == Canvas.DOWN)
		{
			     if(m_iMenuSelect == 7){m_iMenuSelect = 0;m_iMenuY = (m_oCanvas.m_iHeight/2)-27;}
			else if(m_iMenuSelect  < 7)m_iMenuSelect++;
			m_iKeyOldState |= 8;
		}

		// Klik
		if(keyState == Canvas.FIRE)
		{
			if(m_iMenuSelect == 0)
			{
				m_iMenuSelect |= 0x08;
			}
			if(m_iMenuSelect == 1)
			{
				//m_iMenuSelect |= 0x08;
			}
			if(m_iMenuSelect == 2)
			{
				m_bShowInstructions = true;
			}
			if(m_iMenuSelect == 3 && m_bShowHighscore == false)
			{
				LoadScore();
				m_bShowHighscore = true;
			}
/*
			if(m_iMenuSelect == 4 && m_bShowWebHighscore == false)
			{
				if(m_oHighscore == null)
				{
					Thread commThread;
					m_oHighscore = new RegisterHighscore("http://www.mobilkaper.dk/AdLucem.php?GetHigh=yeah");
					commThread = new Thread(m_oHighscore);
					commThread.start();
					
					
				}
				m_bShowWebHighscore = true;
			}
			if(m_iMenuSelect == 5)
			{
				m_sWebCode       = "1";
				m_iWebCodePos    = 0;
				m_bShowWebCode = true;
			}
*/
			if(m_iMenuSelect == 6)
			{
				m_bShowCredits = true;
			}
			if(m_iMenuSelect == 7)
			{
				m_oCanvas.ShutDown();
			}
		}

		if((keyState != Canvas.DOWN))m_iKeyOldState &= (0xff - 8);
		if((keyState != Canvas.UP)  )m_iKeyOldState &= (0xff - 4);

	}

	//------------------------------------------------------
	private void LoadScore()
	//------------------------------------------------------
	{
		byte []recordData;
		int    iLength;

		// Open highscore list
		try
		{
			//----------------------------
			//---Open record
			//----------------------------

			RecordStore nameRecord = RecordStore.openRecordStore("KaperHigh",true);

			//----------------------------
			//---Check if Highscore list is empty
			//----------------------------
			if(nameRecord.getNumRecords() < 3)
			{
				// Add dummy data
				for(int i=0;i < 5;i++)
				{
					if(i == 4)nameRecord.addRecord("Kizza".getBytes(),0,"Kizza".length());
					if(i == 3)nameRecord.addRecord("Kampfisk".getBytes(),0,"Kampfisk".length());
					if(i == 2)nameRecord.addRecord("Malaika".getBytes(),0,"Malaika".length());
					if(i == 1)nameRecord.addRecord("Jonas".getBytes(),0,"Jonas".length());
					if(i == 0)nameRecord.addRecord("Tommy".getBytes(),0,"Tommy".length());
					nameRecord.addRecord(Integer.toString(50-(i*10)).getBytes(),0,Integer.toString(50-(i*10)).length());
				}
			}

			//----------------------------
			//---Get the list
			//----------------------------

			for(int i=0;i < 5;i++)
			{
				recordData = new byte[nameRecord.getRecordSize((i*2)+1)];
				iLength = nameRecord.getRecord((i*2)+1,recordData,0);
				m_sHighPlayers[i]  = new String(recordData, 0, iLength);

				m_sHighPlayers[i] = "[" + (i+1) + "] " + m_sHighPlayers[i];

				recordData = new byte[nameRecord.getRecordSize((i*2)+2)];
				iLength = nameRecord.getRecord((i*2)+2,recordData,0);
				m_sHighScore[i] = new String(recordData, 0, iLength);
			}

			//----------------------------
			//---Close record
			//----------------------------
			recordData = null;
			nameRecord.closeRecordStore();
			nameRecord = null;
		}
		catch(RecordStoreException e)
		{
		}
	}

	//------------------------------------------------------
	private char SwitchChar(char cChar,boolean bUp)
	//------------------------------------------------------
	{
		if(bUp)
		{
			     if('0' == cChar)cChar = '1';
			else if('1' == cChar)cChar = '2';
			else if('2' == cChar)cChar = '3';
			else if('3' == cChar)cChar = '4';
			else if('4' == cChar)cChar = '5';
			else if('5' == cChar)cChar = '6';
			else if('6' == cChar)cChar = '7';
			else if('7' == cChar)cChar = '8';
			else if('8' == cChar)cChar = '9';
			else if('9' == cChar)cChar = 'a';
			else if('a' == cChar)cChar = 'b';
			else if('b' == cChar)cChar = 'c';
			else if('c' == cChar)cChar = 'd';
			else if('d' == cChar)cChar = 'e';
			else if('e' == cChar)cChar = 'f';
			else if('f' == cChar)cChar = ' ';
			else if(' ' == cChar)cChar = '0';
		}
		else
		{
			     if('0' == cChar)cChar = ' ';
			else if('1' == cChar)cChar = '0';
			else if('2' == cChar)cChar = '1';
			else if('3' == cChar)cChar = '2';
			else if('4' == cChar)cChar = '3';
			else if('5' == cChar)cChar = '4';
			else if('6' == cChar)cChar = '5';
			else if('7' == cChar)cChar = '6';
			else if('8' == cChar)cChar = '7';
			else if('9' == cChar)cChar = '8';
			else if('a' == cChar)cChar = '9';
			else if('b' == cChar)cChar = 'a';
			else if('c' == cChar)cChar = 'b';
			else if('d' == cChar)cChar = 'c';
			else if('e' == cChar)cChar = 'd';
			else if('f' == cChar)cChar = 'e';
			else if(' ' == cChar)cChar = 'f';
		}

		return cChar;
	}
}

