
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

	private char          m_cLineInstructions;
	private boolean       m_bShowInstructions;
	private boolean       m_bShowHighscore;
	private boolean       m_bShowWebHighscore;
	private boolean       m_bShowCredits;
	private boolean       m_bShowWebCode;

	public boolean       m_bAnyContinueGame;
	
	private RegisterHighscore  m_oHighscore;
	
	private int           m_iScrollX;

	public boolean       m_bLocked;	
	public boolean       m_bFirstTime;

	//------------------------------------------------------
	public StateMenu(KaperCanvas oKaperCanvas)
	//------------------------------------------------------
	{
		m_oCanvas      = oKaperCanvas;

		m_bLocked      = true;
	
		m_oHighscore = null;
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

		m_bFirstTime   = true;
		m_bLocked      = false;
	}

	//------------------------------------------------------
	public void Draw(Graphics g)
	//------------------------------------------------------
	{
		if(m_bFirstTime)
		{
			////////////////////////////////////////////////////
			//Check Speed, to determine SpeedOptimze
			////////////////////////////////////////////////////
			long lStart = System.currentTimeMillis();

			int x;
			for(x=0;x < 8;x++)
			{
				g.setClip(0,0,25,25);
				g.drawImage(m_oCanvas.m_oImageArray[1],-165,-20,Graphics.TOP | Graphics.LEFT);
			}
		
			for(x=0;x <= (m_oCanvas.m_iWidth/25)+1;x++)
			{
				for(int y=0;y <= (m_oCanvas.m_iHeight/25)+1;y++)
				{
					g.drawImage(m_oCanvas.m_oImageArray[1],-165,-20,Graphics.TOP | Graphics.LEFT);
				}
			}
		
			if(95 < (System.currentTimeMillis() - lStart)) m_oCanvas.m_bSpeedOptimized = true;
			else                                           m_oCanvas.m_bSpeedOptimized = false;
			////////////////////////////////////////////////////
			//Check Speed, to determine SpeedOptimze
			////////////////////////////////////////////////////
			m_bFirstTime = false;
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);

		if(m_oCanvas.m_bSpeedOptimized == true)
		{
			g.setColor(2,142,239);
			g.fillRect(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
		}
		else
		{
			Image oImgWater = Image.createImage(25,25);
			oImgWater.getGraphics().drawImage(m_oCanvas.m_oImageArray[1],-165,-20,Graphics.TOP | Graphics.LEFT);

			for(int x=0;x <= (m_oCanvas.m_iWidth/25)+1;x++)
			{
				for(int y=0;y <= (m_oCanvas.m_iHeight/25)+1;y++)
				{
					g.drawImage(oImgWater,(x*25)+m_iScrollX,y*25,Graphics.TOP | Graphics.LEFT);
				}
			}
		}

		if(m_bShowInstructions)
		{
			
			m_oCanvas.DrawString("INSTRUCTIONS"  ,(m_oCanvas.m_iWidth/2)-42,-m_cLineInstructions*10,true,1);
			
//			----------------------------'''''''''''''''''''''''---------------------
							m_oCanvas.DrawString("CHECK HISCORES AT"      ,1, 22 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("WWW.MOBILKAPER.DK OR"   ,1, 33 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("DIRECTLY ON YOUR"       ,1, 44 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("INTERNET ENABLED PHONE.",1, 55 - (m_cLineInstructions*11),false,1);

							m_oCanvas.DrawString("GOAL:"                  ,1, 70 - (m_cLineInstructions*11),true,1);
							m_oCanvas.DrawString("YOU MUST BOARD AND"     ,1, 83 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("CAPTURE AS MANY ENEMY"  ,1, 94 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("SHIPS AS YOU CAN. YOU"  ,1,105 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("GET THE HIGHEST REWARD" ,1,116 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("FOR BRINGING THEM TO"   ,1,127 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("A HARBOR."              ,1,138 - (m_cLineInstructions*11),false,1);

							m_oCanvas.DrawString("SHIP DUEL:"             ,1,153 - (m_cLineInstructions*11),true,1);
							m_oCanvas.DrawString("HOLD AND RELEASE 0"     ,1,166 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("OR 5 TO SHOOT. MOVE TO" ,1,177 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("THE BOTTOM OF THE"      ,1,188 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("SCREEN TO ESCAPE COMBAT.",1,199 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("TIP: SHOOT THE ENEMY"   ,1,210 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("SHIP WITH YOUR GUNS TO" ,1,221 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("LESSEN THEIR NUMBERS."  ,1,232 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("BUT DON'T SINK IT. SAIL",1,243 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("INTO THE ENEMY SHIP TO" ,1,254 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("BOARD IT AND BATTLE ON" ,1,265 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("THE DECK."              ,1,276 - (m_cLineInstructions*11),false,1);

							
							m_oCanvas.DrawString("UPGRADING:"             ,1,291 - (m_cLineInstructions*11),true,1);
							m_oCanvas.DrawString("TO CARRY MORE GRAIN,"   ,1,304 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("GUNS AND CREW. YOU"     ,1,315 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("MUST UPGRADE YOUR SHIP" ,1,326 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("IN A HARBOR."           ,1,337 - (m_cLineInstructions*11),false,1);

							m_oCanvas.DrawString("MUSIC:"                 ,1,352 - (m_cLineInstructions*11),true,1);
							m_oCanvas.DrawString("MUSIC CAN BE TURNED"    ,1,363 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("ON IN THE MENU."    ,1,374 - (m_cLineInstructions*11),false,1);
							m_oCanvas.DrawString("DEFAULT IS OFF."          ,1,385 - (m_cLineInstructions*11),false,1);		
		}

		else if(m_bShowHighscore)
		{
			for(int i=0;i < 5;i++)
			{
				m_oCanvas.DrawString(m_sHighPlayers[i],(m_oCanvas.m_iWidth/2)-63,2+(i*20),false,1);
				m_oCanvas.DrawString(m_sHighScore[i],(m_oCanvas.m_iWidth/2)+27,2+(i*20),false,1);
			}
		}

		else if(m_bShowWebCode)
		{
			if(m_iSubmitCodeStatus == 0)
			{
				m_oCanvas.DrawString("Web code",(m_oCanvas.m_iWidth/2)-25,25,false,1);

				m_oCanvas.DrawString("Code:",18,40,false,1);
				m_oCanvas.DrawString(m_sWebCode,60,40,false,1);			
				g.drawLine(60+(m_iWebCodePos*5),55,64+(m_iWebCodePos*5),55);//;


				m_oCanvas.DrawString("[1]Cancel",2,60,false,1);
				m_oCanvas.DrawString("[3]Submit",2,73,false,1);
			}
			if(m_iSubmitCodeStatus == 1)
			{
				m_oCanvas.DrawString("Please wait...",(m_oCanvas.m_iWidth/2)-35,45,false,1);
				m_oCanvas.DrawString("[1]Cancel",(m_oCanvas.m_iWidth/2)-70,70,false,1);
			}

			if(m_iSubmitCodeStatus == 2)
			{
				m_oCanvas.DrawString("Respond: " + m_sWebCode,18,40,false,1);
				m_oCanvas.DrawString("[1]Back",(m_oCanvas.m_iWidth/2)-70,70,false,1);
			}
			if(m_iSubmitCodeStatus == 3)
			{
				m_oCanvas.DrawString("Connection failed...",18,40,false,1);
				m_oCanvas.DrawString("[1]Back",(m_oCanvas.m_iWidth/2)-70,70,false,1);
			}
		}

		else if(m_bShowWebHighscore)
		{
			if(m_iWebRankShow == 1)m_oCanvas.DrawString("Highscore   <Month>",(m_oCanvas.m_iWidth/2)-60,5,false,1);
			if(m_iWebRankShow == 2)m_oCanvas.DrawString("Highscore   <Today>",(m_oCanvas.m_iWidth/2)-60,5,false,1);
			if(m_iWebRankShow == 3)m_oCanvas.DrawString("Highscore   < All >",(m_oCanvas.m_iWidth/2)-60,5,false,1);
			if(m_iWebRankShow == 4)m_oCanvas.DrawString("Highscore100<Month>",(m_oCanvas.m_iWidth/2)-60,5,false,1);
			if(m_iWebRankShow == 5)m_oCanvas.DrawString("Highscore100< Day >",(m_oCanvas.m_iWidth/2)-60,5,false,1);
			if(m_iWebRankShow == 6)m_oCanvas.DrawString("Highscore100< All >",(m_oCanvas.m_iWidth/2)-60,5,false,1);
			if(m_iWebRankShow == 7)m_oCanvas.DrawString("Highscore300<Month>",(m_oCanvas.m_iWidth/2)-60,5,false,1);
			if(m_iWebRankShow == 8)m_oCanvas.DrawString("Highscore300<Today>",(m_oCanvas.m_iWidth/2)-60,5,false,1);
			if(m_iWebRankShow == 9)m_oCanvas.DrawString("Highscore300< All >",(m_oCanvas.m_iWidth/2)-60,5,false,1);

			if(m_oHighscore.m_cWebDataReady == 2)
			{
				m_oCanvas.DrawString(m_oHighscore.sLink, 4,62,false,1);
				m_oCanvas.DrawString("Connection failed...",(m_oCanvas.m_iWidth/2)-60,82,false,1);
			}
			
			else if(m_oHighscore.m_cWebDataReady == 1)
			{
				for(int i=0;i < 5;i++)
				{
					if(m_iWebRankShow == 1)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHighMonPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHighMonScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
					if(m_iWebRankShow == 2)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHighDayPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHighDayScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
					if(m_iWebRankShow == 3)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHighAllPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHighAllScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
					if(m_iWebRankShow == 4)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh100MonPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh100MonScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
					if(m_iWebRankShow == 5)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh100DayPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh100DayScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
					if(m_iWebRankShow == 6)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh100AllPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh100AllScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
					if(m_iWebRankShow == 7)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh300MonPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh300MonScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
					if(m_iWebRankShow == 8)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh300DayPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh300DayScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
					if(m_iWebRankShow == 9)
					{
						m_oCanvas.DrawString(Integer.toString((i+1)) + ".",(m_oCanvas.m_iWidth/2)-87,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh300AllPlayers[i],(m_oCanvas.m_iWidth/2)-65,20+(i*15),false,1);
						m_oCanvas.DrawString(m_oHighscore.m_sWebHigh300AllScore[i],(m_oCanvas.m_iWidth/2)+15,20+(i*15),false,1);
					}
				}
			}
			else
			{
				m_oCanvas.DrawString("Loading...",(m_oCanvas.m_iWidth/2)-35,35,false,1);
			}
		}

		else if(m_bShowCredits)
		{
			// Draw Text

			m_oCanvas.DrawString("ART AND DESIGN:",(m_oCanvas.m_iWidth/2)+2-68,00,false,1);			
			m_oCanvas.DrawString("JONAS RAAGAARD",(m_oCanvas.m_iWidth/2)+20-68,10,false,1);			

			m_oCanvas.DrawString("CODE:",(m_oCanvas.m_iWidth/2)+2-68,20,false,1);			
			m_oCanvas.DrawString("TOMMY KOBBEROE",(m_oCanvas.m_iWidth/2)+20-68,30,false,1);	
			
			m_oCanvas.DrawString("THANKS TO THE",(m_oCanvas.m_iWidth/2)+2-68,50,false,1);	
			m_oCanvas.DrawString("ORIGINAL AUTHOR:",(m_oCanvas.m_iWidth/2)+2-68,60,false,1);	
			m_oCanvas.DrawString("PETER OLE FREDERIKSEN",(m_oCanvas.m_iWidth/2)+20-68,70,false,1);	

			m_oCanvas.DrawString("C 2005 LOGICWORK.NET",(m_oCanvas.m_iWidth/2)+2-68,90,false,1);	
			m_oCanvas.DrawString("AND JONAZ.DK",(m_oCanvas.m_iWidth/2)+70-68,100,false,1);	
		}

		else // Menu
		{
			int iScreenMove = 0;
			int iTempY = m_iMenuY;
			int iYOffSet = 0;

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

if(45 <= ((m_oCanvas.m_iHeight/2)-30-iScreenMove))
{
			m_oCanvas.DrawString("START NEW GAME",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)-30-iScreenMove,false,1);
}
if(45 <= ((m_oCanvas.m_iHeight/2)-15-iScreenMove))
{
			if(!m_bAnyContinueGame)m_oCanvas.DrawString("CONTINUE GAME" ,(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)-15-iScreenMove,false,2);
			else                   m_oCanvas.DrawString("CONTINUE GAME" ,(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)-15-iScreenMove,false,1);
}
if(45 <= ((m_oCanvas.m_iHeight/2)-iScreenMove))
{
			m_oCanvas.DrawString("INSTRUCTIONS",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)   -iScreenMove,false,1);
}
if(45 <= ((m_oCanvas.m_iHeight/2)+15-iScreenMove))
{
			m_oCanvas.DrawString("LOCAL SCORE",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)+15-iScreenMove,false,1);
}
if(45 <= ((m_oCanvas.m_iHeight/2)+30-iScreenMove))
{
			m_oCanvas.DrawString("WEB SCORE",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)+30-iScreenMove,false,1);
}
if(45 <= ((m_oCanvas.m_iHeight/2)+45-iScreenMove))
{
			m_oCanvas.DrawString("WEB CODE",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)+45-iScreenMove,false,1);
}
if(45 <= ((m_oCanvas.m_iHeight/2)+60-iScreenMove))
{
			m_oCanvas.DrawString("CREDITS",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)+60-iScreenMove,false,1);
}
if(45 <= ((m_oCanvas.m_iHeight/2)+75-iScreenMove))
{
			m_oCanvas.DrawString("FONT:"  ,(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)+75-iScreenMove,false,1);
			if(m_oCanvas.m_bFontBitmap && m_oCanvas.m_oImageArray[24] != null) m_oCanvas.DrawString("BITMAP",(m_oCanvas.m_iWidth/2)+10,(m_oCanvas.m_iHeight/2)+75-iScreenMove,false,1);
			else							m_oCanvas.DrawString("DEFAULT",(m_oCanvas.m_iWidth/2)+10,(m_oCanvas.m_iHeight/2)+75-iScreenMove,false,2);
}
if(45 <= ((m_oCanvas.m_iHeight/2)+90-iScreenMove))
{
			m_oCanvas.DrawString("SPEED:",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)+90-iScreenMove,false,1);

			if(m_oCanvas.m_bSpeedOptimized) m_oCanvas.DrawString("ON",(m_oCanvas.m_iWidth/2)+10,(m_oCanvas.m_iHeight/2)+90-iScreenMove,false,2);
			else							m_oCanvas.DrawString("OFF",(m_oCanvas.m_iWidth/2)+10,(m_oCanvas.m_iHeight/2)+90-iScreenMove,false,1);
}
			m_oCanvas.DrawString("MUSIC:",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)+105-iScreenMove,false,1);

			if(m_oCanvas.m_oKaperApp.m_bSnd)m_oCanvas.DrawString("ON",(m_oCanvas.m_iWidth/2)+10,(m_oCanvas.m_iHeight/2)+105-iScreenMove,false,1);
			else							m_oCanvas.DrawString("OFF",(m_oCanvas.m_iWidth/2)+10,(m_oCanvas.m_iHeight/2)+105-iScreenMove,false,2);

			m_oCanvas.DrawString("QUIT",(m_oCanvas.m_iWidth/2)-40,(m_oCanvas.m_iHeight/2)+120-iScreenMove,false,1);
			
			// Draw Title
			g.setClip((m_oCanvas.m_iWidth/2)-77,2,149,59);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)-77,2,0);
			
			// Arrows
			if((m_iMenuY+iYOffSet) >= m_oCanvas.m_iHeight-15){iTempY = m_oCanvas.m_iHeight-15;iYOffSet = 0;}

			g.setClip((m_oCanvas.m_iWidth/2)+55,iTempY+iYOffSet-1,10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)+55-131,iTempY+iYOffSet-63,Graphics.TOP | Graphics.LEFT);
			g.setClip((m_oCanvas.m_iWidth/2)-66,iTempY+iYOffSet-1,10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)-66-120,iTempY+iYOffSet-63,Graphics.TOP | Graphics.LEFT);			

			}
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
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)-27)
					{
						m_iMenuY = (m_oCanvas.m_iHeight/2)-27;
					}
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
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)+ 78)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)+83)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}			
				break;		

				case 8:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+93)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)+88)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)+ 93)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)+98)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}
				break;
				case 9:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+108)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)+103)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
					else if(m_iMenuY > (m_oCanvas.m_iHeight/2)+ 108)
					{
						iSpeed = 1 + ((  m_iMenuY - ((m_oCanvas.m_iHeight/2)+113)  ) / 5);
						m_iMenuY += -iSpeed;
					}
				}
				break;		
				
				case 10:
				{
					if(m_iMenuY < (m_oCanvas.m_iHeight/2)+123)
					{
						iSpeed = 1 + ((  ((m_oCanvas.m_iHeight/2)+118)  -  m_iMenuY) / 5);
						m_iMenuY += iSpeed;
					}
				}			
				break;
				
			}

			// Key check
			if((m_iMenuSelect & 0x80) == 0x80)return (m_iMenuSelect - 0x80);

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
					if(m_cLineInstructions != 36)m_cLineInstructions++;
					return;
				}
				if(keyState == Canvas.UP)
				{
					if(m_cLineInstructions != 0)m_cLineInstructions--;
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

		else if(m_bShowWebCode)
		{
			if(iKey == Canvas.KEY_NUM1)
			{
				m_iSubmitCodeStatus = 0;
				m_bShowWebCode = false;
				return;
			}

			if(iKey == Canvas.KEY_NUM3 && m_iSubmitCodeStatus == 0)
			{
				m_iSubmitCodeStatus = 1;

				Thread commThread;
				commThread = new Thread(new SubmitWebScore(this));
				commThread.start();

				return;
			}

			if(keyState == Canvas.UP)
			{
				m_sWebCode = m_sWebCode.substring(0,m_iWebCodePos) + SwitchChar(m_sWebCode.charAt(m_iWebCodePos),true) + m_sWebCode.substring(m_iWebCodePos+1);
			}

			if(keyState == Canvas.DOWN)
			{
				m_sWebCode = m_sWebCode.substring(0,m_iWebCodePos) + SwitchChar(m_sWebCode.charAt(m_iWebCodePos),false) + m_sWebCode.substring(m_iWebCodePos+1);
			}

			if(keyState == Canvas.LEFT)
			{
				if(m_iWebCodePos > 0)m_iWebCodePos--;

			}

			if(keyState == Canvas.RIGHT)
			{
				if(m_iWebCodePos > 10)return;

				if(m_sWebCode.length() <= (m_iWebCodePos+1))m_sWebCode += "1";
				m_iWebCodePos++;
			}
			return;
		}

		// Op
		if((m_iKeyOldState & 4) == 0 && keyState == Canvas.UP)
		{
			     if(m_iMenuSelect == 0){m_iMenuSelect = 10;m_iMenuY = (m_oCanvas.m_iHeight/2)+123;}
			else if(m_iMenuSelect >  0)m_iMenuSelect--;
			m_iKeyOldState |= 4;
		}

		// Ned
		if((m_iKeyOldState & 8) == 0 && keyState == Canvas.DOWN)
		{
			     if(m_iMenuSelect == 10){m_iMenuSelect = 0;m_iMenuY = (m_oCanvas.m_iHeight/2)-27;}
			else if(m_iMenuSelect  < 10)m_iMenuSelect++;
			m_iKeyOldState |= 8;
		}

		// Klik
		if(keyState == Canvas.FIRE)
		{
			if(m_iMenuSelect == 0)
			{
				m_iMenuSelect |= 0x80;
			}
			if(m_iMenuSelect == 1 && m_bAnyContinueGame)
			{
				m_iMenuSelect |= 0x80;
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
			if(m_iMenuSelect == 6)
			{
				m_bShowCredits = true;
			}
			if(m_iMenuSelect == 7)
			{
				if(m_oCanvas.m_bFontBitmap == true)m_oCanvas.m_bFontBitmap = false;
				else                                   m_oCanvas.m_bFontBitmap = true;
			}
			if(m_iMenuSelect == 8)
			{
				if(m_oCanvas.m_bSpeedOptimized == true)m_oCanvas.m_bSpeedOptimized = false;
				else                                   m_oCanvas.m_bSpeedOptimized = true;
			}
			if(m_iMenuSelect == 9)
			{
				if(m_oCanvas.m_oKaperApp.m_bSnd == true)
				{
					m_oCanvas.m_oKaperApp.m_bSnd = false;
				}
				else
				{
					m_oCanvas.m_oKaperApp.m_bSnd = true;
				}
				if(m_oCanvas != null)m_oCanvas.SoftButtonSND(m_oCanvas.m_oKaperApp.m_bSnd);
			}
			if(m_iMenuSelect == 10)
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

//------------------------------------------------------
class SubmitWebScore implements Runnable
//------------------------------------------------------
{
	StateMenu      m_oFather;
	InputStream    m_Stream = null;
	StringBuffer   m_sBuf   = new StringBuffer();
	HttpConnection m_Http   = null;

	//------------------------------------------------------
	SubmitWebScore(StateMenu oFather)
	//------------------------------------------------------
	{
		m_oFather = oFather;
	}

	//------------------------------------------------------
	public void run()
	//------------------------------------------------------
	{
		try
		{
			String sLink = "http://www.mobilkaper.dk/AdLucem.php?SubmitCode=" + m_oFather.m_sWebCode;

			m_Http       = (HttpConnection)Connector.open(sLink,Connector.READ,true);

			m_Http.setRequestMethod(HttpConnection.GET);
			m_Http.setRequestProperty("Content-Type","text/plain");
			m_Http.setRequestProperty("Connection","close");			

			// Send request and recive data

			m_Stream     = m_Http.openInputStream();

			// Read data to string
			int ch;
			while((ch = m_Stream.read()) != -1)
			{
				m_sBuf.append((char)ch);
			}

			// Close connection
			if(m_Stream != null)m_Stream.close();
			if(m_Http   != null)m_Http.close();
			m_Stream = null;
			m_Http   = null;

			// Convert Buf to String
			m_oFather.m_sWebCode = m_sBuf.toString();
			m_oFather.m_iSubmitCodeStatus = 2;
		}
		catch(Exception e)
		{
			m_oFather.m_iSubmitCodeStatus = 3;
		}
	}
}
