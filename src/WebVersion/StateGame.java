
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
import javax.microedition.rms.*;

//------------------------------------------------------
class StateGame
//------------------------------------------------------
{
	////////////////////////////////////////////////////////////
	// KaperShip
	////////////////////////////////////////////////////////////
	public int         m_iShipX;
	public int         m_iShipY;
	public int         m_iShipGraphicWay;
	public boolean     m_bShipMoving;

	private int         m_iShipWaveA[];
	private int         m_iShipWaveX[];
	private int         m_iShipWaveY[];

	////////////////////////////////////////////////////////////
	// EnemyShips
	////////////////////////////////////////////////////////////
	public int         m_iEnemyShipX[];
	public int         m_iEnemyShipY[];
	public int         m_iEnemyShipShipType[];
	public int         m_iEnemyShipCannons[];
	public int         m_iEnemyShipSoldiers[];
	private char       m_iEnemyShipGraphicWay[];

	private int         m_iEnemyShipWaveA[][];
	private int         m_iEnemyShipWaveX[][];
	private int         m_iEnemyShipWaveY[][];	
	
	////////////////////////////////////////////////////////////
	// TheMap
	////////////////////////////////////////////////////////////
	public int          m_iMapScreenX;
	public int          m_iMapScreenY;
	public int          m_iMapOffScreenX;
	public int          m_iMapOffScreenY;
	public boolean      m_bMapMoving;
	public char         m_aoGameMap[][];

	private int         m_iMapWay;
	private boolean     m_bMapScrollN;
	private boolean     m_bMapScrollS;
	private boolean     m_bMapScrollE;
	private boolean     m_bMapScrollW;

	////////////////////////////////////////////////////////////
	// Skies
	////////////////////////////////////////////////////////////
	private int         m_iSkyX[];
	private int         m_iSkyY[];
	private int         m_iSkyType[];
	private int         m_iSkyCounterY;

	////////////////////////////////////////////////////////////
	// Event
	////////////////////////////////////////////////////////////
	public int         m_iAddFood;
	public int         m_iAddGold;
	public int         m_iAddPirates;
	public int         m_iAddCannons;
	public int         m_iEventOld;
	public String      m_sEventMsg1;
	public String      m_sEventMsg2;

	public int         m_iShipNr;
	public int         m_iEvent;
	public boolean     m_bWait;
	public int         m_iPiratesOnCapturedShip;

	private int        m_iFleePunishKapers;
	private int        m_iFleePunishCannons;	

	////////////////////////////////////////////////////////////
	// Interface
	////////////////////////////////////////////////////////////
	public int         m_iResourceGold;
	public int         m_iResourceCorn;
	public int         m_iResourceCannon;
	public int         m_iResourcePirate;
	public int         m_iTurn;
	public int         m_iScore;
	public int         m_iScoreTurn100;
	public int         m_iScoreTurn300;
	public int         m_iShipLvl;	
	
	////////////////////////////////////////////////////////////
	// StateGame
	////////////////////////////////////////////////////////////
	public int              m_iGameState;
	public CGameStateBoard  m_oStateBoard;
	public CGameStateAttack m_oStateAttack;
	public CGameStateCity	m_oStateCity;
	public int              m_iCapturedShip;

	public String         m_sPlayerName;

	// Protected:
	// Private:

	private KaperCanvas   m_oCanvas;
	private boolean       m_bWaitingOnMove;
	private boolean       m_bClose;
	private boolean       m_bToCity;

	private int           m_iWebRankShow;

	private CGameStateSailingCity m_oStateSailingToCity;

	private int           m_iPlayerNamePos;

	private int           m_iKeyCountDown;
	private boolean       m_bKeyLeft;
	private boolean       m_bKeyRight;
	private boolean       m_bKeyUp;
	private boolean       m_bKeyDown;

	public boolean       m_bLocked;

	// Events in water
	private int           m_iEventX[];
	private int           m_iEventY[];
	private int           m_iEventAnim;

	private StringBuffer stringBuffer;
	private char[] stringChars;

	//------------------------------------------------------
	public StateGame(KaperCanvas oKaperCanvas)
	//------------------------------------------------------
	{
		m_oCanvas      = oKaperCanvas;
		m_bLocked      = true;

		////////////////////////////////////////////////////////////
		// Skies
		////////////////////////////////////////////////////////////
		m_iSkyX               = new int[19];
		m_iSkyY               = new int[19];
		m_iSkyType            = new int[19];

		////////////////////////////////////////////////////////////
		// KaperShip
		////////////////////////////////////////////////////////////
		m_iShipWaveA = new int[3];
		m_iShipWaveX = new int[3];
		m_iShipWaveY = new int[3];

		////////////////////////////////////////////////////////////
		// StateGame
		////////////////////////////////////////////////////////////
		m_iEventX             = new int[15];
		m_iEventY             = new int[15];

		stringBuffer       = new StringBuffer("0000");
		stringChars        = new char[stringBuffer.length()];

		for(int i=0;i < 19;i++)
		{
			m_iSkyType[i] = Math.abs(m_oCanvas.m_oRand.nextInt()%3);

			m_iSkyX[i]    = -50 + Math.abs(m_oCanvas.m_oRand.nextInt()%(29*25));
			m_iSkyY[i]    = Math.abs(m_oCanvas.m_oRand.nextInt()%(14*25));
		}
		
		m_aoGameMap = new char[][]
		{
	{ 1,1,1,1,1,1,1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1 },
	{ 1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1 },
	{ 1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,0,1,1,1 },
	{ 1,1,1,0,1,1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1 },
	{ 1,1,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1 },
	{ 1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1 },
	{ 1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,5,0,1,1,1 },
	{ 1,1,1,0,0,1,0,0,0,0,0,0,1,1,0,0,0,6,1,1,1,1,1,1,0,0,1,1,1 },
	{ 1,1,0,0,0,1,0,0,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,1 },
	{ 0,1,0,0,0,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1 },
	{ 1,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1 },
	{ 1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,0,0,0,0 },
	{ 0,0,0,0,0,0,0,0,0,0,8,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0 },
	{ 0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0 }, 
		};
		
		////////////////////////////////////////////////////////////
		// EnemyShip
		////////////////////////////////////////////////////////////
		m_iEnemyShipX          = new int[10];
		m_iEnemyShipY          = new int[10];
		m_iEnemyShipShipType   = new int[10];
		m_iEnemyShipCannons    = new int[10];
		m_iEnemyShipSoldiers   = new int[10];
		m_iEnemyShipGraphicWay = new char[10];

		m_iEnemyShipWaveA = new int[10][3];
		m_iEnemyShipWaveX = new int[10][3];
		m_iEnemyShipWaveY = new int[10][3];

	//------------------------------------//
	//----------- MAP / States -----------//
	//------------------------------------//

		try
		{
			m_oStateSailingToCity = new CGameStateSailingCity(m_oCanvas);	
			m_oStateCity          = new CGameStateCity(m_oCanvas);

			m_oCanvas.m_oImageArray[4] = Image.createImage("/5.png");
			m_oCanvas.m_oImageArray[3] = Image.createImage("/4.png");
			
			m_oStateAttack        = new CGameStateAttack(m_oCanvas);
			m_oStateBoard         = new CGameStateBoard(m_oCanvas);
							
			m_oCanvas.m_oImageArray[2] = Image.createImage("/3.png");
			m_oCanvas.m_oImageArray[5] = Image.createImage("/6.png");

		}
		catch(Throwable th){}
	}
	
	//------------------------------------------------------
	public void Init()
	//------------------------------------------------------
	{
		m_bLocked          = true;

		m_bWaitingOnMove   = false;
		m_bClose           = false;
		m_bToCity          = false;		
		m_bWait            = false;
		
		m_iGameState       = 0;
		
		m_iWebRankShow   = 1;

		m_sPlayerName    = "a";		
		
		////////////////////////////////////////////////////////////
		// Map
		////////////////////////////////////////////////////////////
		m_iMapScreenX = 7;
		m_iMapScreenY = 3;
		
		////////////////////////////////////////////////////////////
		// Event
		////////////////////////////////////////////////////////////
		m_iEventOld   = -1;

		m_sEventMsg1   = "Empty...";
		m_sEventMsg2   = "";
		
		////////////////////////////////////////////////////////////
		// KaperShip
		////////////////////////////////////////////////////////////
		m_bShipMoving    = false;

		m_iShipX = 25;
		m_iShipY = 25;

		m_iShipGraphicWay = 1;
		
		////////////////////////////////////////////////////////////
		// Interface
		////////////////////////////////////////////////////////////
		m_iResourceGold    =  25;
		m_iResourceCorn    = 100;
		m_iResourceCannon  =   5;
		m_iResourcePirate  =  30;
		m_iShipLvl         =   1;
		m_iCapturedShip    =   0;
		
		m_iTurn            =   0;
		m_iScore           =   0;
		m_iScoreTurn100    =   0;
		m_iScoreTurn300    =   0;

		for(int i=0;i < 15;i++)
		{
			if(m_iEventX[i] != 0 && m_iEventY[i] != 0)continue;
			do
			{
				m_iEventX[i] = 25 * Math.abs(m_oCanvas.m_oRand.nextInt()%(29));
				m_iEventY[i] = 25 * Math.abs(m_oCanvas.m_oRand.nextInt()%(14));
			}
			while(MapCheckCollision((m_iEventX[i] / 25)+0,(m_iEventY[i] / 25)-0,false) == 0 || ((m_iEventX[i] / 25) == 8 && (m_iEventY[i] / 25) == 4));
		}

		////////////////////////////////////////////////////////////
		// EnemyShip
		////////////////////////////////////////////////////////////
		int iRandom;
		
		for(int i=0;i < 10;i++)
		{
			// Placing random place
			do
			{
				m_iEnemyShipX[i] = 25 * Math.abs(m_oCanvas.m_oRand.nextInt()%(29));
				m_iEnemyShipY[i] = 25 * Math.abs(m_oCanvas.m_oRand.nextInt()%(14));
			}
			while(m_oCanvas.m_oStateGame.MapCheckCollision((m_iEnemyShipX[i] / 25)+0,(m_iEnemyShipY[i] / 25)-0,false) == 0 || ((m_iEnemyShipX[i] / 25) == 8 && (m_iEnemyShipY[i] / 25) == 4));
	
			m_iEnemyShipGraphicWay[i] = 1;
	
			iRandom = Math.abs(m_oCanvas.m_oRand.nextInt()%(m_iShipLvl+1));
	
			m_iEnemyShipShipType[i] = 12 + iRandom;
	
			if(iRandom == 0)
			{
				m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  3)+  1 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
				m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()%  10)+  5 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
			}
	
			if(iRandom == 1)
			{
				m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  5)+  2 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
				m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 21)+  15 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
			}
			if(iRandom == 2)
			{
				m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  14)+  13 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
				m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 26)+ 35 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
			}
			if(iRandom == 3)
			{
				m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  26)+ 25 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
				m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 71)+ 40 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
			}
			if(iRandom == 4)
			{
				m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()% 51)+ 30 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
				m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 351)+ 100 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
			}
			if(iRandom == 5)
			{
				m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()% 101)+ 100 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
				m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 501)+ 500 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
			}
			if(iRandom == 6)
			{
				m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  101)+   50 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
				m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 501)+  50 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
			}		
		}

	//------------------------------------//
	//-------------- States --------------//
	//------------------------------------//
		
		m_oCanvas.StopSound();
		
		m_bLocked          = false;
	}	
/*
	//------------------------------------------------------
	public void DeInit()
	//------------------------------------------------------
	{
		m_bLocked          = true;

		m_iEnemyShipWaveA = null;
		m_iEnemyShipWaveX = null;
		m_iEnemyShipWaveY = null;

		m_iShipWaveA = null;
		m_iShipWaveX = null;
		m_iShipWaveY = null;

		m_aoGameMap = null;
		m_oCanvas.m_oImageArray[2] = null;

		m_sEventMsg1  = null;
		m_sEventMsg2  = null;		
		
		DeInit();

		m_iGameState   = 5+1;

		m_iEventX = null;
		m_iEventY = null;

		m_sPlayerName         = null;

		m_iSkyX    = null;
		m_iSkyY    = null;
		m_iSkyType = null;

			m_oCanvas.StopSound();

		System.gc();
	}	
*/

////////////////////////////////////////////////////////////
// TheMap
////////////////////////////////////////////////////////////
	//------------------------------------------------------
	public boolean MapIncSetXY(int iWay)
	//------------------------------------------------------
	{
		if(m_bMapMoving == true)return false;

		// Check if Moving map is allowed
		if(iWay == 1)
		{
			if(((m_oCanvas.m_iWidth-20)/25)+m_iMapScreenX > 27)return true;
		}
		if(iWay == 2)
		{
			if(m_iMapScreenX <= 0)return true;
		}
		if(iWay == 8)
		{
			if(((m_oCanvas.m_iHeight-20)/25)+m_iMapScreenY > 13)return true;
		}
		if(iWay == 4)
		{
			if(m_iMapScreenY <= 0)return true;
		}

		m_bMapScrollN = false;
		m_bMapScrollS = false;
		m_bMapScrollE = false;
		m_bMapScrollW = false;

		if(iWay == 0x20)
		{
			if(!(m_iMapScreenY <= 0))m_bMapScrollN = true;
			if(!(m_iMapScreenX <= 0))m_bMapScrollW = true;

			if(m_iShipY > 20)m_bMapScrollS = false;
			if(m_iShipX > 20)m_bMapScrollE = false;

			if(m_bMapScrollN && m_bMapScrollW)
			{
				m_bMapScrollN=false;
				m_bMapScrollW=false;
			}
			else
			{
				if(!m_bMapScrollN && m_bMapScrollW)ShipMove(4,true);
				if(!m_bMapScrollW && m_bMapScrollN)ShipMove(2 ,true);
				if(!m_bMapScrollN && !m_bMapScrollW){ShipMove(0x20 ,true);return true;}
				m_iShipGraphicWay = 0x20;
			}
		}

		if(iWay == 0x80)
		{
			if(!(m_iMapScreenY <= 0))                             m_bMapScrollN = true;
			if(!(((m_oCanvas.m_iWidth-20)/25)+m_iMapScreenX > 27))m_bMapScrollE = true;

			if(m_iShipY > 20)m_bMapScrollS = false;
			if(m_iShipX < 20)m_bMapScrollE = false;

			if(m_bMapScrollN && m_bMapScrollE)
			{
				m_bMapScrollN=false;
				m_bMapScrollE=false;
			}
			else
			{
				if(!m_bMapScrollN && m_bMapScrollE)ShipMove(4,true);
				if(!m_bMapScrollE && m_bMapScrollN)ShipMove(1 ,true);
				if(!m_bMapScrollN && !m_bMapScrollE){ShipMove(0x80 ,true);return true;}
				m_iShipGraphicWay = 0x80;
			}
		}

		if(iWay == 0x10)
		{
			if(!(((m_oCanvas.m_iHeight-20)/25)+m_iMapScreenY > 13))m_bMapScrollS = true;
			if(!(m_iMapScreenX <= 0))                              m_bMapScrollW = true;

			if(m_iShipY < 20)m_bMapScrollS = false;
			if(m_iShipX > 20)m_bMapScrollE = false;

			if(m_bMapScrollS && m_bMapScrollW)
			{
				m_bMapScrollS=false;
				m_bMapScrollW=false;
			}
			else
			{
				if(!m_bMapScrollS && m_bMapScrollW)ShipMove(8,true);
				if(!m_bMapScrollW && m_bMapScrollS)ShipMove(2 ,true);
				if(!m_bMapScrollS && !m_bMapScrollW){ShipMove(0x10 ,true);return true;}
				m_iShipGraphicWay = 0x10;
			}
		}

		if(iWay == 0x40)
		{
			if(!(((m_oCanvas.m_iHeight-20)/25)+m_iMapScreenY > 13))m_bMapScrollS = true;
			if(!(((m_oCanvas.m_iWidth-20)/25)+m_iMapScreenX > 27)) m_bMapScrollE = true;

			if(m_iShipY < 20)m_bMapScrollS = false;
			if(m_iShipX < 20)m_bMapScrollE = false;

			if(m_bMapScrollS && m_bMapScrollE)
			{
				m_bMapScrollS=false;
				m_bMapScrollE=false;
			}
			else
			{
				if(!m_bMapScrollS && m_bMapScrollE)ShipMove(8,true);
				if(!m_bMapScrollE && m_bMapScrollS)ShipMove(1 ,true);
				if(!m_bMapScrollS && !m_bMapScrollE){ShipMove(0x40 ,true);return true;}
				m_iShipGraphicWay = 0x40;
			}
		}

		// Move Screen
		m_iMapOffScreenX = 0;
		m_iMapOffScreenY = 0;
		m_iMapWay    = iWay;
		m_bMapMoving = true;
		return false;
	}

	//------------------------------------------------------
	public int MapCheckCollision(int iX,int iY,boolean bScreenOffset)
	//------------------------------------------------------
	{
		if(bScreenOffset == true)
		{
			iX += m_iMapScreenX;
			iY += m_iMapScreenY;
		}

		if(iX < 0  || iY < 0) return 0;
		if(iX > 28 || iY > 13)return 0;

		if(0 == m_aoGameMap[iY][iX])return 1;
		if(1  < m_aoGameMap[iY][iX])return 2;

		return 0;
	}

	//------------------------------------------------------
	public void MapDraw(Graphics g)
	//------------------------------------------------------
	{
		if(m_oCanvas.m_oImageArray[2] != null)
			g.drawImage(m_oCanvas.m_oImageArray[2],-((m_iMapScreenX*25)-m_iMapOffScreenX),-((m_iMapScreenY*25)-m_iMapOffScreenY),Graphics.TOP | Graphics.LEFT);

		else
		{
			g.setColor(0,0,200);
			g.fillRect(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
					
			for(int iY = -1;iY < (14-m_iMapScreenY)+1;iY++)
			{
				for(int iX = -1;iX < (29-m_iMapScreenX)+1;iX++)
				{
					if((iY+m_iMapScreenY) <  0) continue;
					if((iX+m_iMapScreenX) <  0) continue;
					if((iY+m_iMapScreenY) >= 14)continue;
					if((iX+m_iMapScreenX) >= 29)continue;

					if((m_oCanvas.m_iWidth/25)  < iX)continue;
					if((m_oCanvas.m_iHeight/25) < iY)continue;
					
					if(m_aoGameMap[iY+m_iMapScreenY][iX+m_iMapScreenX] == 0)continue;
					if(m_aoGameMap[iY+m_iMapScreenY][iX+m_iMapScreenX] == 1)g.setColor(0,200,0);
					if(m_aoGameMap[iY+m_iMapScreenY][iX+m_iMapScreenX] >  1)g.setColor(200,0,0);
					g.fillRect((iX*25)+m_iMapOffScreenX,(iY*25)+m_iMapOffScreenY,25,25);
				}
			}
		}
	
	}

	//------------------------------------------------------
	public void MapUpdate()
	//------------------------------------------------------
	{
		if(m_bMapMoving == false)return;
		m_bShipMoving = false;
		
		if(4 == m_iMapWay || m_bMapScrollN)
		{
			m_iMapOffScreenY++;
			if(m_iShipGraphicWay == 0x80)m_iShipX++;
			if(m_iShipGraphicWay == 0x20)m_iShipX--;	
		
			if(m_iMapOffScreenY >= 25)
			{
				m_iMapScreenY--;
				m_bMapMoving = false;
				m_iMapOffScreenY = 0;
				return;
			}
		}

		else if(8 == m_iMapWay || m_bMapScrollS)
		{
			m_iMapOffScreenY--;
			if(m_iShipGraphicWay == 0x40)m_iShipX++;
			if(m_iShipGraphicWay == 0x10)m_iShipX--;
			
			
			if(m_iMapOffScreenY <= -25)
			{
				m_iMapScreenY++;
				m_bMapMoving = false;
				m_iMapOffScreenY = 0;
				return;
			}
		}

		else if(2 == m_iMapWay || m_bMapScrollW)
		{
			m_iMapOffScreenX++;
			if(m_iShipGraphicWay == 0x20)m_iShipY--;
			if(m_iShipGraphicWay == 0x10)m_iShipY++;	
			
			if(m_iMapOffScreenX >= 25)
			{
				m_iMapScreenX--;
				m_bMapMoving = false;
				m_iMapOffScreenX = 0;
				return;
			}
		}

		else if(1 == m_iMapWay || m_bMapScrollE)
		{
			m_iMapOffScreenX--;
			if(m_iShipGraphicWay == 0x80)m_iShipY--;
			if(m_iShipGraphicWay == 0x40)m_iShipY++;				
			
			if(m_iMapOffScreenX <= -25)
			{
				m_iMapScreenX++;
				m_bMapMoving = false;
				m_iMapOffScreenX = 0;
				return;
			}

		}

		else if(0x20 == m_iMapWay)
		{
			m_iMapOffScreenX++;
			m_iMapOffScreenY++;

			if(m_iMapOffScreenX >= 25)
			{
				m_iMapScreenX--;
				m_iMapScreenY--;
				m_bMapMoving = false;
				m_iMapOffScreenX = 0;
				m_iMapOffScreenY = 0;
				return;
			}

		}
		else if(0x80 == m_iMapWay)
		{
			m_iMapOffScreenX--;
			m_iMapOffScreenY++;

			if(m_iMapOffScreenX <= -25)
			{
				m_iMapScreenX++;
				m_iMapScreenY--;
				m_bMapMoving = false;
				m_iMapOffScreenX = 0;
				m_iMapOffScreenY = 0;
				return;
			}
		}
		else if(0x40 == m_iMapWay)
		{
			m_iMapOffScreenX--;
			m_iMapOffScreenY--;

			if(m_iMapOffScreenX <= -25)
			{
				m_iMapScreenX++;
				m_iMapScreenY++;
				m_bMapMoving = false;
				m_iMapOffScreenX = 0;
				m_iMapOffScreenY = 0;
				return;
			}
		}
		else if(0x10 == m_iMapWay)
		{
			m_iMapOffScreenX++;
			m_iMapOffScreenY--;

			if(m_iMapOffScreenX >= 25)
			{
				m_iMapScreenX--;
				m_iMapScreenY++;
				m_bMapMoving = false;
				m_iMapOffScreenX = 0;
				m_iMapOffScreenY = 0;
				return;
			}
		}
	}

////////////////////////////////////////////////////////////
// KaperShip
////////////////////////////////////////////////////////////
	//------------------------------------------------------
	public void ShipDraw(Graphics g)
	//------------------------------------------------------
	{
		int iGraphic = 0;


			for(int i=0;i < 3;i++)
			{
				if(m_iShipWaveA[0] == 18){m_iShipWaveA[1] = 25;}
				if(m_iShipWaveA[0] == 10){m_iShipWaveA[2] = 25;}
			
				if(m_iShipWaveA[i] < 0)continue;
				if(m_iShipWaveA[i] == 25)
				{
					m_iShipWaveX[i] = m_iShipX;// - m_iMapOffScreenX;
					m_iShipWaveY[i] = m_iShipY;// - m_iMapOffScreenY;
	
					if(m_iShipGraphicWay == 1 || m_iShipGraphicWay == 2)m_iShipWaveY[i] += 5;
	
				}
	
				     if(m_iShipWaveA[i] <  6)iGraphic = 3;
				else if(m_iShipWaveA[i] < 12)iGraphic = 2;
				else if(m_iShipWaveA[i] < 18)iGraphic = 1;
				else if(m_iShipWaveA[i] < 25)iGraphic = 0;
	
				m_iShipWaveA[i]--;
				if(m_iShipWaveA[i] >= 25)continue;
	
				int iOffsetX = m_iMapOffScreenX;
				int iOffsetY = m_iMapOffScreenY;
	
				if(iOffsetX < 0)m_iShipWaveX[i]--;
				if(iOffsetX > 0)m_iShipWaveX[i]++;
				if(iOffsetY < 0)m_iShipWaveY[i]--;
				if(iOffsetY > 0)m_iShipWaveY[i]++;
	
				g.setClip(m_iShipWaveX[i],m_iShipWaveY[i],19,19);
				
				if(iGraphic == 0)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipWaveX[i]-100,m_iShipWaveY[i]-60,Graphics.TOP | Graphics.LEFT);
				if(iGraphic == 1)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipWaveX[i]- 80,m_iShipWaveY[i]-60,Graphics.TOP | Graphics.LEFT);
				if(iGraphic == 2)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipWaveX[i]- 60,m_iShipWaveY[i]-60,Graphics.TOP | Graphics.LEFT);
				if(iGraphic == 3)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipWaveX[i]- 40,m_iShipWaveY[i]-60,Graphics.TOP | Graphics.LEFT);
			}

		
		g.setClip(m_iShipX,m_iShipY,19,19);

		if(2 >= m_iShipLvl)
		{
			if(m_iShipGraphicWay == 1) g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipX-140,m_iShipY-80,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 2) g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipX,m_iShipY-80,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 8)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipX- 60,m_iShipY-80,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 4)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipX-120,m_iShipY-80,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x10) g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipX-20,m_iShipY-80,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x40) g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipX-40,m_iShipY-80,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x80) g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipX-100,m_iShipY-80,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x20) g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipX-80,m_iShipY-80,Graphics.TOP | Graphics.LEFT);
		}

		else if(2 < m_iShipLvl && 4 >= m_iShipLvl)
		{
			if(m_iShipGraphicWay == 1) g.drawImage(m_oCanvas.m_oImageArray[3],m_iShipX-140,m_iShipY-60,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 2) g.drawImage(m_oCanvas.m_oImageArray[3],m_iShipX,m_iShipY-60,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 8)g.drawImage(m_oCanvas.m_oImageArray[3],m_iShipX- 60,m_iShipY-60,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 4)g.drawImage(m_oCanvas.m_oImageArray[3],m_iShipX-120,m_iShipY-60,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x10) g.drawImage(m_oCanvas.m_oImageArray[3],m_iShipX-20,m_iShipY-60,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x40) g.drawImage(m_oCanvas.m_oImageArray[3],m_iShipX-40,m_iShipY-60,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x80) g.drawImage(m_oCanvas.m_oImageArray[3],m_iShipX-100,m_iShipY-60,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x20) g.drawImage(m_oCanvas.m_oImageArray[3],m_iShipX-80,m_iShipY-60,Graphics.TOP | Graphics.LEFT);
		}

		else if(4 < m_iShipLvl)
		{
			if(m_iShipGraphicWay == 1) g.drawImage(m_oCanvas.m_oImageArray[4],m_iShipX-140,m_iShipY,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 2) g.drawImage(m_oCanvas.m_oImageArray[4],m_iShipX,m_iShipY,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 8)g.drawImage(m_oCanvas.m_oImageArray[4],m_iShipX-60,m_iShipY,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 4)g.drawImage(m_oCanvas.m_oImageArray[4],m_iShipX-120,m_iShipY,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x10) g.drawImage(m_oCanvas.m_oImageArray[4],m_iShipX-20,m_iShipY,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x40) g.drawImage(m_oCanvas.m_oImageArray[4],m_iShipX-40,m_iShipY,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x80) g.drawImage(m_oCanvas.m_oImageArray[4],m_iShipX-100,m_iShipY,Graphics.TOP | Graphics.LEFT);
			if(m_iShipGraphicWay == 0x20) g.drawImage(m_oCanvas.m_oImageArray[4],m_iShipX-80,m_iShipY,Graphics.TOP | Graphics.LEFT);
		}

	}

	//------------------------------------------------------
	public void ShipUpdate()
	//------------------------------------------------------
	{
		if(m_bShipMoving == true && false == m_bMapMoving)
		{
			if(m_iShipGraphicWay == 2) {m_iShipX--;}
			if(m_iShipGraphicWay == 1) {m_iShipX++;}
			if(m_iShipGraphicWay == 4){m_iShipY--;}
			if(m_iShipGraphicWay == 8){m_iShipY++;}

			if(m_iShipGraphicWay == 0x80){m_iShipY--;m_iShipX++;}
			if(m_iShipGraphicWay == 0x20){m_iShipY--;m_iShipX--;}
			if(m_iShipGraphicWay == 0x10){m_iShipY++;m_iShipX--;}
			if(m_iShipGraphicWay == 0x40){m_iShipY++;m_iShipX++;}

			if((m_iShipX % 25) == 0 && (m_iShipY % 25) == 0)m_bShipMoving = false;
		}
	}

	// False: Move ship
	// True: Move map with ship
	//------------------------------------------------------
	public int ShipMove(int iWay,boolean bForce)
	//------------------------------------------------------
	{
		if(!bForce && m_bShipMoving)return 0x02;

		m_bShipMoving    = true;
		int iReturnValue = 0;

		m_iShipWaveA[0] = 25;

		if(iWay == 2)
		{
			// End of map or crashing to ground
			if(!bForce && 0 == MapCheckCollision((m_iShipX / 25)-1,(m_iShipY / 25)-0,true))
			{
				m_bShipMoving    = false;		
				return 0x02;
			}

			// City collision
			if(!bForce && 2 == MapCheckCollision((m_iShipX / 25)-1,(m_iShipY / 25)-0,true))
			{
				iReturnValue |= 0x04;
			}

			m_iShipGraphicWay = 2;

			if(!bForce && m_iShipX <= (25*2))return (iReturnValue | 0x01);
		}

		else if(iWay == 1)
		{
			// End of map or crashing to ground
			if(!bForce && 0 == MapCheckCollision((m_iShipX / 25)+1,(m_iShipY / 25)-0,true))
			{
				m_bShipMoving    = false;		
				return 0x02;
			}

			// City collision
			if(!bForce && 2 == MapCheckCollision((m_iShipX / 25)+1,(m_iShipY / 25)-0,true))
			{
				iReturnValue |= 0x04;
			}

			m_iShipGraphicWay = 1;

			if(!bForce && m_iShipX >= (m_oCanvas.m_iWidth-60-25))return (iReturnValue | 0x01);
		}

		else if(iWay == 4)
		{
			// End of map or crashing to ground
			if(!bForce && 0 == MapCheckCollision((m_iShipX / 25)-0,(m_iShipY / 25)-1,true))
			{
				m_bShipMoving    = false;		
				return 0x02;
			}

			// City collision
			if(!bForce && 2 == MapCheckCollision((m_iShipX / 25)-0,(m_iShipY / 25)-1,true))
			{
				iReturnValue |= 0x04;
			}

			m_iShipGraphicWay = 4;

			if(!bForce && m_iShipY <= (25*2))return (iReturnValue | 0x01);
		}

		else if(iWay == 8)
		{

			// End of map or crashing to ground
			if(!bForce && 0 == MapCheckCollision((m_iShipX / 25)-0,(m_iShipY / 25)+1,true))
			{
				m_bShipMoving    = false;		
				return 0x02;
			}

			// City collision
			if(!bForce && 2 == MapCheckCollision((m_iShipX / 25)-0,(m_iShipY / 25)+1,true))
			{
				iReturnValue |= 0x04;
			}

			m_iShipGraphicWay = 8;

			if(!bForce && m_iShipY >= (m_oCanvas.m_iHeight-60-20-25))return (iReturnValue | 0x01);
		}

		else if(iWay == 0x80)
		{

			// End of map or crashing to ground
			if(!bForce && 0 == MapCheckCollision((m_iShipX / 25)+1,(m_iShipY / 25)-1,true))
			{
				m_bShipMoving    = false;		
				return 0x02;
			}

			// City collision
			if(!bForce && 2 == MapCheckCollision((m_iShipX / 25)+1,(m_iShipY / 25)-1,true))
			{
				iReturnValue |= 0x04;
			}

			m_iShipGraphicWay = 0x80;

			if(!bForce && m_iShipY <= (25*2))return (iReturnValue | 0x01);
			if(!bForce && m_iShipX >= m_oCanvas.m_iWidth-60-25)return (iReturnValue | 0x01);
		}

		else if(iWay == 0x20)
		{

			// End of map or crashing to ground
			if(!bForce && 0 == MapCheckCollision((m_iShipX / 25)-1,(m_iShipY / 25)-1,true))
			{
				m_bShipMoving    = false;		
				return 0x02;
			}

			// City collision
			if(!bForce && 2 == MapCheckCollision((m_iShipX / 25)-1,(m_iShipY / 25)-1,true))
			{
				iReturnValue |= 0x04;
			}

			m_iShipGraphicWay = 0x20;

			if(!bForce && m_iShipY <= (25*2))return (iReturnValue | 0x01);
			if(!bForce && m_iShipX <= (25*2))return (iReturnValue | 0x01);
		}

		else if(iWay == 0x10)
		{

			// End of map or crashing to ground
			if(!bForce && 0 == MapCheckCollision((m_iShipX / 25)-1,(m_iShipY / 25)+1,true))
			{
				m_bShipMoving    = false;		
				return 0x02;
			}

			// City collision
			if(!bForce && 2 == MapCheckCollision((m_iShipX / 25)-1,(m_iShipY / 25)+1,true))
			{
				iReturnValue |= 0x04;
			}

			m_iShipGraphicWay = 0x10;

			if(!bForce && m_iShipY >= m_oCanvas.m_iHeight-60-20-25)return (iReturnValue | 0x01);
			if(!bForce && m_iShipX <= (25*2))return (iReturnValue | 0x01);
		}

		else if(iWay == 0x40)
		{

			// End of map or crashing to ground
			if(!bForce && 0 == MapCheckCollision((m_iShipX / 25)+1,(m_iShipY / 25)+1,true))
			{
				m_bShipMoving    = false;		
				return 0x02;
			}

			// City collision
			if(!bForce && 2 == MapCheckCollision((m_iShipX / 25)+1,(m_iShipY / 25)+1,true))
			{
				iReturnValue |= 0x04;
			}

			m_iShipGraphicWay = 0x40;

			if(!bForce && m_iShipY >= m_oCanvas.m_iHeight-60-20-25)return (iReturnValue | 0x01);
			if(!bForce && m_iShipX >= m_oCanvas.m_iWidth-60-25)return (iReturnValue | 0x01);
		}

		return iReturnValue;
	}


////////////////////////////////////////////////////////////
// EnemyShip
////////////////////////////////////////////////////////////
	
	//------------------------------------------------------
	public void EnemyShipUpdate()
	//------------------------------------------------------
	{
		for(int i=0;i < 10;i++)
		{
			if(!((m_iEnemyShipX[i] % 25) == 0 && (m_iEnemyShipY[i] % 25) == 0))
			{
				if(m_iEnemyShipGraphicWay[i] == 2) {m_iEnemyShipX[i]--;}
				if(m_iEnemyShipGraphicWay[i] == 1) {m_iEnemyShipX[i]++;}
				if(m_iEnemyShipGraphicWay[i] == 4){m_iEnemyShipY[i]--;}
				if(m_iEnemyShipGraphicWay[i] == 8){m_iEnemyShipY[i]++;}
		
				if(m_iEnemyShipGraphicWay[i] == 0x20) {m_iEnemyShipX[i]--;m_iEnemyShipY[i]--;}
				if(m_iEnemyShipGraphicWay[i] == 0x80) {m_iEnemyShipX[i]++;m_iEnemyShipY[i]--;}
				if(m_iEnemyShipGraphicWay[i] == 0x10) {m_iEnemyShipX[i]--;m_iEnemyShipY[i]++;}
				if(m_iEnemyShipGraphicWay[i] == 0x40) {m_iEnemyShipX[i]++;m_iEnemyShipY[i]++;}
			}	
		}
	}

	//------------------------------------------------------
	public void EnemyShipMove()
	//------------------------------------------------------
	{
		int iWay;
		
		for(int i=0;i < 10;i++)
		{
			//------------------------
			// Player near, then don't move
			//------------------------
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX + 1) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY))
				continue;
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX - 1) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY))
				continue;
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX + 0) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY + 1))
				continue;
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX + 0) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY - 1))
				continue;
	
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX + 1) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY + 1))
				continue;
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX - 1) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY - 1))
				continue;
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX + 1) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY - 1))
				continue;
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX - 1) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY + 1))
				continue;
	
			//------------------------
			// Move random
			//------------------------
			iWay = Math.abs(m_oCanvas.m_oRand.nextInt()%15);
	
			if(iWay == 0) // North
			{
				if(MapCheckCollision((m_iEnemyShipX[i] / 25)+0,(m_iEnemyShipY[i] / 25)-1,false) == 0)continue;
				if(EnemyShipCollosion((m_iEnemyShipX[i] / 25)+0,(m_iEnemyShipY[i] / 25)-1) == true)continue;
	
				m_iEnemyShipGraphicWay[i] = 4;
				
				m_iEnemyShipY[i]--;
			}
			else if(iWay == 1) // South
			{
				if(MapCheckCollision((m_iEnemyShipX[i] / 25)+0,(m_iEnemyShipY[i] / 25)+1,false) == 0)continue;
				if(EnemyShipCollosion((m_iEnemyShipX[i] / 25)+0,(m_iEnemyShipY[i] / 25)+1) == true)continue;
	
				m_iEnemyShipGraphicWay[i] = 8;
				
				m_iEnemyShipY[i]++;
			}
			else if(iWay == 2) // East
			{
				if(MapCheckCollision((m_iEnemyShipX[i] / 25)+1,(m_iEnemyShipY[i] / 25)-0,false) == 0)continue;
				if(EnemyShipCollosion((m_iEnemyShipX[i] / 25)+1,(m_iEnemyShipY[i] / 25)-0) == true)continue;
	
				m_iEnemyShipGraphicWay[i] = 1;
				
				m_iEnemyShipX[i]++;
			}
			else if(iWay == 3) // West
			{
				if(MapCheckCollision((m_iEnemyShipX[i] / 25)-1,(m_iEnemyShipY[i] / 25)-0,false) == 0)continue;
				if(EnemyShipCollosion((m_iEnemyShipX[i] / 25)-1,(m_iEnemyShipY[i] / 25)-0) == true)continue;
	
				m_iEnemyShipGraphicWay[i] = 2;
				
				m_iEnemyShipX[i]--;
			}
			else if(iWay == 4) // 0x20
			{
				if(MapCheckCollision((m_iEnemyShipX[i] / 25)-1,(m_iEnemyShipY[i] / 25)-1,false) == 0)continue;
				if(EnemyShipCollosion((m_iEnemyShipX[i] / 25)-1,(m_iEnemyShipY[i] / 25)-1) == true)continue;
	
				m_iEnemyShipGraphicWay[i] = 0x20;
				
				m_iEnemyShipX[i]--;m_iEnemyShipY[i]--;
			}
			else if(iWay == 5) // 0x80
			{
				if(MapCheckCollision((m_iEnemyShipX[i] / 25)+1,(m_iEnemyShipY[i] / 25)-1,false) == 0)continue;
				if(EnemyShipCollosion((m_iEnemyShipX[i] / 25)+1,(m_iEnemyShipY[i] / 25)-1) == true)continue;
	
				m_iEnemyShipGraphicWay[i] = 0x80;
				
				m_iEnemyShipX[i]++;m_iEnemyShipY[i]--;
			}
			else if(iWay == 6) // 0x10
			{
				if(MapCheckCollision((m_iEnemyShipX[i] / 25)-1,(m_iEnemyShipY[i] / 25)+1,false) == 0)continue;
				if(EnemyShipCollosion((m_iEnemyShipX[i] / 25)-1,(m_iEnemyShipY[i] / 25)+1) == true)continue;
	
				m_iEnemyShipGraphicWay[i] = 0x10;
				
				m_iEnemyShipX[i]--;m_iEnemyShipY[i]++;
			}
			else if(iWay == 7) // 0x40
			{
				if(MapCheckCollision((m_iEnemyShipX[i] / 25)+1,(m_iEnemyShipY[i] / 25)+1,false) == 0)continue;
				if(EnemyShipCollosion((m_iEnemyShipX[i] / 25)+1,(m_iEnemyShipY[i] / 25)+1) == true)continue;
	
				m_iEnemyShipGraphicWay[i] = 0x40;
				
				m_iEnemyShipX[i]++;m_iEnemyShipY[i]++;
			}
			else continue;
			m_iEnemyShipWaveA[i][0] = 25;
			
		}
	}

	//------------------------------------------------------
	private boolean EnemyShipCollosion(int iX,int iY)
	//------------------------------------------------------
	{
		// Enemy ships
		for(int i=0;i < 10;i++)
		{
			if((m_iEnemyShipX[i])/25 == iX)
			{
				if((m_iEnemyShipY[i])/25 == iY)
				{
					return true;
				}
			}
		}

		// Kaper ships
			if(((m_iShipX)/25) + m_iMapScreenX == iX)
			{
				if(((m_iShipY)/25) + m_iMapScreenY == iY)
				{
					return true;
				}
			}

		return false;
	}	
	
////////////////////////////////////////////////////////////
// Interface
////////////////////////////////////////////////////////////
	//------------------------------------------------------
	public boolean InterfaceNewTurn()
	//------------------------------------------------------
	{
		m_iTurn++;

		// Turn registr

		if(m_iTurn ==  50 || m_iTurn ==  51)
		{
			m_iEvent = 21;
			m_bWait  = true;
			m_iScoreTurn100 = m_iScore;
		}

		if(m_iTurn == 100)
		{
			m_iEvent = 21;
			m_bWait  = true;
			m_iScoreTurn100 = m_iScore;
		}

		if(m_iTurn == 300)
		{
			m_iEvent = 22;
			m_bWait = true;
			m_iScoreTurn300 = m_iScore;
		}

		// Starving
		if(m_iResourceCorn <= 0)
		{
			m_iResourcePirate /= 1;
			m_iResourcePirate--;
		}

		// GameOver
		if(m_iResourcePirate <= 0)
		{
			return true;
		}

		// Calculate Food back
		if(m_iResourceCorn > 0)
		{
			int iTemp = (m_iResourcePirate/10)+1;
			m_iResourceCorn -= (Math.abs(m_oCanvas.m_oRand.nextInt()%iTemp)+iTemp) % iTemp;
			if(m_iResourceCorn < 0)m_iResourceCorn   = 0;
		}

		return false;
	}
	
	//------------------------------------------------------
	public void InterfaceDraw(Graphics g,boolean bTurn)
	//------------------------------------------------------
	{
		g.setFont(m_oCanvas.m_oFontSmallP);

		// Draw black background
		g.setColor(0,0,0);
		g.fillRect(0,m_oCanvas.m_iHeight-20,m_oCanvas.m_iWidth,20);


		// Draw interface-Icon
		g.setClip(  0,(m_oCanvas.m_iHeight-17),13,14);
		g.drawImage(m_oCanvas.m_oImageArray[1]   ,(0-90)   ,(m_oCanvas.m_iHeight-17-49),Graphics.TOP | Graphics.LEFT);
		g.setClip( 32,(m_oCanvas.m_iHeight-17),14,14);
		g.drawImage(m_oCanvas.m_oImageArray[1]   ,(32-119) ,(m_oCanvas.m_iHeight-17-49),Graphics.TOP | Graphics.LEFT);
		g.setClip( 66,(m_oCanvas.m_iHeight-17),14,14);
		g.drawImage(m_oCanvas.m_oImageArray[1],(66-134)    ,(m_oCanvas.m_iHeight-17-49),Graphics.TOP | Graphics.LEFT);
		g.setClip(100,(m_oCanvas.m_iHeight-17),14,14);
		g.drawImage(m_oCanvas.m_oImageArray[1] ,(100-104)  ,(m_oCanvas.m_iHeight-17-49),Graphics.TOP | Graphics.LEFT);

		
		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
		
		if(bTurn)
		{
			// Turn
			g.setColor(0,0,0);

			g.drawString("Turn:",m_oCanvas.m_iWidth-50,0, Graphics.TOP | Graphics.LEFT);
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iTurn );
			stringBuffer.getChars( 0, stringBuffer.length(), stringChars, 0 );
			g.drawChars( stringChars,0,stringBuffer.length(),m_oCanvas.m_iWidth-23,0, Graphics.TOP | Graphics.LEFT );

			g.drawString("Score:",18,0, Graphics.TOP | Graphics.LEFT);
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iScore );
			stringBuffer.getChars( 0, stringBuffer.length(), stringChars, 0 );
			g.drawChars(stringChars,0,stringBuffer.length(),51,0, Graphics.TOP | Graphics.LEFT );
		}

		// Corn
		g.setColor(255,255,255);
		if((100*m_iShipLvl) == (m_iResourceCorn))g.setColor(255,0,0);

		if(m_iGameState == 1) // City State
		{
			if(m_oStateCity.m_cTarget == 1 && ((m_oStateCity.m_iBlink%2)==1))
			g.setColor(255,0,0);
		}

			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iResourceCorn );
			stringBuffer.getChars( 0, stringBuffer.length(), stringChars, 0 );
			g.drawChars(stringChars,0,stringBuffer.length(),15,m_oCanvas.m_iHeight-15,Graphics.TOP | Graphics.LEFT);	
		
		// Gold
		g.setColor(255,255,255);
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iResourceGold );
			stringBuffer.getChars( 0, stringBuffer.length(), stringChars, 0 );
			g.drawChars(stringChars,0,stringBuffer.length(),48,m_oCanvas.m_iHeight-15,Graphics.TOP | Graphics.LEFT);

		// Pirates
		g.setColor(255,255,255);
		if( 35 == (m_iResourcePirate) && m_iShipLvl == 1)g.setColor(255,0,0);
		if( 50 == (m_iResourcePirate) && m_iShipLvl == 2)g.setColor(255,0,0);
		if(100 == (m_iResourcePirate) && m_iShipLvl == 3)g.setColor(255,0,0);
		if(200 == (m_iResourcePirate) && m_iShipLvl == 4)g.setColor(255,0,0);
		if(350 == (m_iResourcePirate) && m_iShipLvl == 5)g.setColor(255,0,0);

		if(m_iGameState == 1) // City State
		{
			if(m_oStateCity.m_cTarget == 2 && ((m_oStateCity.m_iBlink%2)==1))
			g.setColor(255,0,0);
		}
		

			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iResourcePirate );
			stringBuffer.getChars( 0, stringBuffer.length(), stringChars, 0 );
			g.drawChars(stringChars,0,stringBuffer.length(),83,m_oCanvas.m_iHeight-15,Graphics.TOP | Graphics.LEFT);
		// Cannons
		g.setColor(255,255,255);
		if(  7 == (m_iResourceCannon) && m_iShipLvl == 1)g.setColor(255,0,0);
		if( 15 == (m_iResourceCannon) && m_iShipLvl == 2)g.setColor(255,0,0);
		if( 30 == (m_iResourceCannon) && m_iShipLvl == 3)g.setColor(255,0,0);
		if( 60 == (m_iResourceCannon) && m_iShipLvl == 4)g.setColor(255,0,0);
		if(100 == (m_iResourceCannon) && m_iShipLvl == 5)g.setColor(255,0,0);

		if(m_iGameState == 1) // City State
		{
			if(m_oStateCity.m_cTarget == 3 && ((m_oStateCity.m_iBlink%2)==1))
			g.setColor(255,0,0);
		}		
		
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iResourceCannon );
			stringBuffer.getChars( 0, stringBuffer.length(), stringChars, 0 );
			g.drawChars(stringChars,0,stringBuffer.length(),117,m_oCanvas.m_iHeight-15,Graphics.TOP | Graphics.LEFT);

		// Captured ship
		if(m_iCapturedShip != 0)
		{
			if(m_iCapturedShip == 12 || m_iCapturedShip == 13)
			{
				g.setClip(130,(m_oCanvas.m_iHeight-20),19,19);
				g.drawImage(m_oCanvas.m_oImageArray[4],(130-140),(m_oCanvas.m_iHeight-20)-40,Graphics.TOP | Graphics.LEFT);
			}
			if(m_iCapturedShip == 14 || m_iCapturedShip == 15 || m_iCapturedShip == 18)
			{
				g.setClip(130,(m_oCanvas.m_iHeight-20),19,19);
				g.drawImage(m_oCanvas.m_oImageArray[4],(130-140),(m_oCanvas.m_iHeight-20)-20,Graphics.TOP | Graphics.LEFT);
			}
			if(m_iCapturedShip == 16 || m_iCapturedShip == 17)
			{
				g.setClip(130,(m_oCanvas.m_iHeight-20),19,19);
				g.drawImage(m_oCanvas.m_oImageArray[4],(130-140),(m_oCanvas.m_iHeight-20)-60,Graphics.TOP | Graphics.LEFT);
			}

			g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
		}

		// UpdateFromInterface
		if(m_bWait == false)
		{

			if(m_iShipLvl == 1)
			{
				if((100 < m_iResourceCorn)  ){m_iResourceCorn   = 100;m_iAddFood = 0;}
				if(( 35 < m_iResourcePirate)){m_iResourcePirate =  35;m_iAddPirates = 0;}
				if((  7 < m_iResourceCannon)){m_iResourceCannon =   7;m_iAddCannons = 0;}
			}
			if(m_iShipLvl == 2)
			{
				if((200 < m_iResourceCorn)  ){m_iResourceCorn   = 200;m_iAddFood = 0;}
				if(( 50 < m_iResourcePirate)){m_iResourcePirate =  50;m_iAddPirates = 0;}
				if(( 15 < m_iResourceCannon)){m_iResourceCannon =  15;m_iAddCannons = 0;}
			}
			if(m_iShipLvl == 3)
			{
				if((300 < m_iResourceCorn)  ){m_iResourceCorn   = 300;m_iAddFood = 0;}
				if((100 < m_iResourcePirate)){m_iResourcePirate = 100;m_iAddPirates = 0;}
				if(( 30 < m_iResourceCannon)){m_iResourceCannon =  30;m_iAddCannons = 0;}
			}
			if(m_iShipLvl == 4)
			{
				if((400 < m_iResourceCorn)  ){m_iResourceCorn   = 400;m_iAddFood = 0;}
				if((200 < m_iResourcePirate)){m_iResourcePirate = 200;m_iAddPirates = 0;}
				if(( 60 < m_iResourceCannon)){m_iResourceCannon =  60;m_iAddCannons = 0;}
			}
			if(m_iShipLvl == 5)
			{
				if((500 < m_iResourceCorn)  ){m_iResourceCorn   = 500;m_iAddFood = 0;}
				if((350 < m_iResourcePirate)){m_iResourcePirate = 350;m_iAddPirates = 0;}
				if((100 < m_iResourceCannon)){m_iResourceCannon = 100;m_iAddCannons = 0;}
			}
	
			if(m_iAddFood != 0)
			{
				if(m_iAddFood < 0)
				{
					if(m_iAddFood < -100)
					{
						m_iResourceCorn -= 5;
						m_iAddFood += 5;
					}
					else if(m_iAddFood < -50)
					{
						m_iResourceCorn -= 2;
						m_iAddFood += 2;
					}
					else
					{
						m_iResourceCorn--;
						m_iAddFood++;
					}
				}
	
				if(m_iAddFood > 0)
				{
					if(m_iAddFood > 100)
					{
						m_iResourceCorn += 5;
						m_iAddFood -= 5;
					}
					else if(m_iAddFood > 50)
					{
						m_iResourceCorn += 2;
						m_iAddFood -= 2;
					}
					else
					{
						m_iResourceCorn++;
						m_iAddFood--;
					}
				}
			}
	
			if(m_iAddGold != 0)
			{
				if(m_iAddGold < 0)
				{
					if(m_iAddGold < -100)
					{
						m_iResourceGold -= 5;
						m_iAddGold += 5;
					}
					else if(m_iAddGold < -50)
					{
						m_iResourceGold -= 2;
						m_iAddGold += 2;
					}
					else
					{
						m_iResourceGold--;
						m_iAddGold++;
					}
				}
	
				if(m_iAddGold > 0)
				{
					if(m_iAddGold > 100)
					{
						m_iResourceGold += 5;
						m_iAddGold -= 5;
					}
					else if(m_iAddGold > 50)
					{
						m_iResourceGold += 2;
						m_iAddGold -= 2;
					}
					else
					{
						m_iResourceGold++;
						m_iAddGold--;
					}
	
				}
			}
	
			if(m_iAddPirates != 0)
			{
				if(m_iAddPirates < 0)
				{
					if(m_iAddPirates < -100)
					{
						m_iResourcePirate -= 5;
						m_iAddPirates += 5;
					}
					else if(m_iAddPirates < -50)
					{
						m_iResourcePirate -= 2;
						m_iAddPirates += 2;
					}
					else
					{
						m_iResourcePirate--;
						m_iAddPirates++;
					}
				}
	
				if(m_iAddPirates > 0)
				{
					if(m_iAddPirates > 100)
					{
						m_iResourcePirate += 5;
						m_iAddPirates -= 5;
					}
					else if(m_iAddPirates > 50)
					{
						m_iResourcePirate += 2;
						m_iAddPirates -= 2;
					}
					else
					{
						m_iResourcePirate++;
						m_iAddPirates--;
					}
				}
			}
	
			if(m_iAddCannons != 0)
			{
				if(m_iAddCannons < 0)
				{
					if(m_iAddCannons < -100)
					{
						m_iResourceCannon -= 5;
						m_iAddCannons += 5;
					}
					else if(m_iAddCannons < -50)
					{
						m_iResourceCannon -= 2;
						m_iAddCannons += 2;
					}
					else
					{
						m_iResourceCannon--;
						m_iAddCannons++;
					}
				}
	
				if(m_iAddCannons > 0)
				{
					if(m_iAddCannons > 100)
					{
						m_iResourceCannon += 5;
						m_iAddCannons -= 5;
					}
					else if(m_iAddCannons > 50)
					{
						m_iResourceCannon += 2;
						m_iAddCannons -= 2;
					}
					else
					{
						m_iResourceCannon++;
						m_iAddCannons--;
					}
				}
			}
			if(m_iResourceCorn   < 0)m_iResourceCorn   = 0;
			if(m_iResourceCannon < 0)m_iResourceCannon = 0;
			if(m_iResourceGold   < 0)m_iResourceGold   = 0;					
		}

		g.setFont(m_oCanvas.m_oFontSmallB);
	}	
	
	
	//------------------------------------------------------
	public void Draw(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		
		if(6 == m_iGameState)
		{
			MapDraw(g);
			DrawBlackBackGround(g);

			g.setColor(255,255,255);
			if(m_iWebRankShow == 1)g.drawString("Highscore   <Month>",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);
			if(m_iWebRankShow == 2)g.drawString("Highscore   <Today>",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);
			if(m_iWebRankShow == 3)g.drawString("Highscore   < All >",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);
			if(m_iWebRankShow == 4)g.drawString("Highscore100<Month>",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);
			if(m_iWebRankShow == 5)g.drawString("Highscore100< Day >",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);
			if(m_iWebRankShow == 6)g.drawString("Highscore100< All >",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);
			if(m_iWebRankShow == 7)g.drawString("Highscore300<Month>",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);
			if(m_iWebRankShow == 8)g.drawString("Highscore300<Today>",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);
			if(m_iWebRankShow == 9)g.drawString("Highscore300< All >",(m_oCanvas.m_iWidth/2)-60,60,Graphics.TOP | Graphics.LEFT);


		}

		if(0 == m_iGameState)
		{
			MapDraw(g);

			//Event Draw
			int iMapXOffset = (-m_iMapScreenX*25) + m_iMapOffScreenX;
			int iMapYOffset = (-m_iMapScreenY*25) + m_iMapOffScreenY;
	
			int i;
			
			for(i=0;i < 15;i++)
			{
				if((m_iEventX[i] + iMapXOffset) < -20 || (m_iEventX[i] + iMapXOffset) > m_oCanvas.m_iWidth)continue;
				if((m_iEventY[i] + iMapYOffset) < -20 || (m_iEventY[i] + iMapYOffset) > m_oCanvas.m_iHeight-20)continue;
				
				g.setClip(m_iEventX[i] + iMapXOffset,m_iEventY[i] + iMapYOffset,19,19);
				
				     if(m_iEventAnim <  5)g.drawImage(m_oCanvas.m_oImageArray[0],m_iEventX[i] + iMapXOffset-100,m_iEventY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
				else if(m_iEventAnim < 10)g.drawImage(m_oCanvas.m_oImageArray[0],m_iEventX[i] + iMapXOffset- 80,m_iEventY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
				else if(m_iEventAnim < 15)g.drawImage(m_oCanvas.m_oImageArray[0],m_iEventX[i] + iMapXOffset- 60,m_iEventY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
				else if(m_iEventAnim < 20)g.drawImage(m_oCanvas.m_oImageArray[0],m_iEventX[i] + iMapXOffset- 40,m_iEventY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
			}
	
			g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);		
			
			m_iEventAnim++;
			if(m_iEventAnim >= 30)m_iEventAnim = 0;

			//Event Draw-End
		
			ShipDraw(g);
			
			for(i=0;i < 10;i++)
			{
				if((m_iEnemyShipX[i] + iMapXOffset) < -20 || (m_iEnemyShipX[i] + iMapXOffset) > m_oCanvas.m_iWidth)continue;
				if((m_iEnemyShipY[i] + iMapYOffset) < -20 || (m_iEnemyShipY[i] + iMapYOffset) > m_oCanvas.m_iHeight-20)continue;


					int iGraphic = 0;
					
					for(int ii=0;ii < 3;ii++)
					{
						if(m_iEnemyShipWaveA[i][0] == 18){m_iEnemyShipWaveA[i][1] = 25;}
						if(m_iEnemyShipWaveA[i][0] == 10){m_iEnemyShipWaveA[i][2] = 25;}
					
						if(m_iEnemyShipWaveA[i][ii] < 0)continue;
						if(m_iEnemyShipWaveA[i][ii] == 25)
						{
							m_iEnemyShipWaveX[i][ii] = m_iEnemyShipX[i];// + (-m_oCanvas.m_oStateGame.m_iMapScreenX*25) ;
							m_iEnemyShipWaveY[i][ii] = m_iEnemyShipY[i];// + (-m_oCanvas.m_oStateGame.m_iMapScreenY*25) ;
			
							if(m_iEnemyShipGraphicWay[i] == 1 || m_iEnemyShipGraphicWay[i] == 2)m_iEnemyShipWaveY[i][ii] += 5;
						}
			
						     if(m_iEnemyShipWaveA[i][ii] <  6)iGraphic = 3;
						else if(m_iEnemyShipWaveA[i][ii] < 12)iGraphic = 2;
						else if(m_iEnemyShipWaveA[i][ii] < 18)iGraphic = 1;
						else if(m_iEnemyShipWaveA[i][ii] < 25)iGraphic = 0;
			
						m_iEnemyShipWaveA[i][ii]--;
						if(m_iEnemyShipWaveA[i][ii] >= 25)continue;
			
						g.setClip(m_iEnemyShipWaveX[i][ii]+ iMapXOffset,m_iEnemyShipWaveY[i][ii]+ iMapYOffset,19,19);
						
						if(iGraphic == 0)g.drawImage(m_oCanvas.m_oImageArray[0],m_iEnemyShipWaveX[i][ii]+ iMapXOffset-100,m_iEnemyShipWaveY[i][ii]+ iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
						if(iGraphic == 1)g.drawImage(m_oCanvas.m_oImageArray[0],m_iEnemyShipWaveX[i][ii]+ iMapXOffset- 80,m_iEnemyShipWaveY[i][ii]+ iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
						if(iGraphic == 2)g.drawImage(m_oCanvas.m_oImageArray[0],m_iEnemyShipWaveX[i][ii]+ iMapXOffset- 60,m_iEnemyShipWaveY[i][ii]+ iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
						if(iGraphic == 3)g.drawImage(m_oCanvas.m_oImageArray[0],m_iEnemyShipWaveX[i][ii]+ iMapXOffset- 40,m_iEnemyShipWaveY[i][ii]+ iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
					}
		
				g.setClip(m_iEnemyShipX[i] + iMapXOffset,m_iEnemyShipY[i] + iMapYOffset,19,19);
				
				if(m_iEnemyShipShipType[i] == 12 || m_iEnemyShipShipType[i] == 13)
				{
					     if(m_iEnemyShipGraphicWay[i] ==    1) g.drawImage(m_oCanvas.m_oImageArray[4],   m_iEnemyShipX[i] + iMapXOffset - 140 ,m_iEnemyShipY[i] + iMapYOffset - 40,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    2) g.drawImage(m_oCanvas.m_oImageArray[4],   m_iEnemyShipX[i] + iMapXOffset       ,m_iEnemyShipY[i] + iMapYOffset - 40,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    8) g.drawImage(m_oCanvas.m_oImageArray[4],    m_iEnemyShipX[i] + iMapXOffset -  60 ,m_iEnemyShipY[i] + iMapYOffset - 40,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    4) g.drawImage(m_oCanvas.m_oImageArray[4],    m_iEnemyShipX[i] + iMapXOffset - 120 ,m_iEnemyShipY[i] + iMapYOffset - 40,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x20) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset -  80 ,m_iEnemyShipY[i] + iMapYOffset - 40,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x80) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 100 ,m_iEnemyShipY[i] + iMapYOffset - 40,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x10) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset -  20 ,m_iEnemyShipY[i] + iMapYOffset - 40,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x40) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset -  40 ,m_iEnemyShipY[i] + iMapYOffset - 40,Graphics.TOP | Graphics.LEFT);
				}
				
				else if(m_iEnemyShipShipType[i] == 14 || m_iEnemyShipShipType[i] == 15 || m_iEnemyShipShipType[i] == 18)
				{
					     if(m_iEnemyShipGraphicWay[i] ==    1) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 140,m_iEnemyShipY[i] + iMapYOffset - 20,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    2) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset,m_iEnemyShipY[i] + iMapYOffset - 20,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    8) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 60,m_iEnemyShipY[i] + iMapYOffset - 20,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    4) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 120,m_iEnemyShipY[i] + iMapYOffset - 20,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x20) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset -  80,m_iEnemyShipY[i] + iMapYOffset - 20,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x80) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 100,m_iEnemyShipY[i] + iMapYOffset - 20,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x10) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset -  20,m_iEnemyShipY[i] + iMapYOffset - 20,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x40) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset -  40,m_iEnemyShipY[i] + iMapYOffset - 20,Graphics.TOP | Graphics.LEFT);
				}
				
				else if(m_iEnemyShipShipType[i] == 16 || m_iEnemyShipShipType[i] == 17)
				{
					     if(m_iEnemyShipGraphicWay[i] ==    1) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 140,m_iEnemyShipY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    2) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset,m_iEnemyShipY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    8) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 60,m_iEnemyShipY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] ==    4) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 120,m_iEnemyShipY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x20) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 80 ,m_iEnemyShipY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x80) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 100 ,m_iEnemyShipY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x10) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 20 ,m_iEnemyShipY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyShipGraphicWay[i] == 0x40) g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyShipX[i] + iMapXOffset - 40 ,m_iEnemyShipY[i] + iMapYOffset - 60,Graphics.TOP | Graphics.LEFT);
				}

			}
			
			
			if(m_oCanvas.m_oImageArray[5] != null)
			{
				for(i=0;i < 19;i++)
				{
					if(m_iSkyType[i] == 0){g.setClip(m_iSkyX[i]+iMapXOffset,m_iSkyY[i]+iMapYOffset,53,63); g.drawImage(m_oCanvas.m_oImageArray[5],m_iSkyX[i]+iMapXOffset,m_iSkyY[i]+iMapYOffset,Graphics.TOP | Graphics.LEFT);}
					if(m_iSkyType[i] == 1){g.setClip(m_iSkyX[i]+iMapXOffset,m_iSkyY[i]+iMapYOffset,51,63); g.drawImage(m_oCanvas.m_oImageArray[5],m_iSkyX[i]+iMapXOffset-56,m_iSkyY[i]+iMapYOffset,Graphics.TOP | Graphics.LEFT);}
					if(m_iSkyType[i] == 2){g.setClip(m_iSkyX[i]+iMapXOffset,m_iSkyY[i]+iMapYOffset,63,63); g.drawImage(m_oCanvas.m_oImageArray[5],m_iSkyX[i]+iMapXOffset-108,m_iSkyY[i]+iMapYOffset,Graphics.TOP | Graphics.LEFT);}
				}
			}

			g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);

			DrawBlackBackGround(g);

			if(m_bWait)
			{
				DrawFrame(g);
						//g.setFont(m_oCanvas.m_oFontSmallP);
				DrawEvent(g);
			}			
			
			InterfaceDraw(g,true);
		}

		if(5 == m_iGameState)
		{
			MapDraw(g);
			ShipDraw(g);

			DrawFrame(g);
//			InterfaceDraw(g,true);

			g.setColor(0,0,0);
			g.setFont(m_oCanvas.m_oFontMediumB);
			g.drawString("GAME OVER",(m_oCanvas.m_iWidth/2)-35,6,Graphics.TOP | Graphics.LEFT);

			g.drawString("Name: ",8,40,Graphics.TOP | Graphics.LEFT);
			g.drawString(m_sPlayerName,50,40,Graphics.TOP | Graphics.LEFT);
			
			g.drawLine(50+(m_iPlayerNamePos*7),55,56+(m_iPlayerNamePos*7),55);

			g.setFont(m_oCanvas.m_oFontSmallB);
			g.drawString("[1] Save to local score",8,55,Graphics.TOP | Graphics.LEFT);
			g.drawString("[3] Save to local/web (Wap)",8,65,Graphics.TOP | Graphics.LEFT);

			g.drawString("Total",10,20,Graphics.TOP | Graphics.LEFT);


//			g.drawString(Integer.toString(m_iScore   ),25,50,Graphics.TOP | Graphics.LEFT);
				stringBuffer.delete(0,stringBuffer.length());
				stringBuffer.append((int)m_iScore);
				stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
				g.drawChars(stringChars,0,stringBuffer.length(),15,30,Graphics.TOP | Graphics.LEFT);

		}

		if(2 == m_iGameState)
		{
			m_oStateSailingToCity.Draw(g);
		}

		if(1 == m_iGameState)
		{
			m_oStateCity.Draw(g);
			InterfaceDraw(g,false);
		}
		if(3 == m_iGameState)
		{
			m_oStateBoard.Draw(g);
		}
		if(4 == m_iGameState)
		{
			m_oStateAttack.Draw(g);
		}
	}	
	
	//------------------------------------------------------
	public void LoadGame()
	//------------------------------------------------------
	{
		try
		{
			byte []recordData;
			int    iLength;

			//---Open record
			RecordStore nameRecord = RecordStore.openRecordStore("KaperGame",true);

			if(nameRecord.getNumRecords() < 10)
			{
				nameRecord.closeRecordStore();
				RecordStore.deleteRecordStore("KaperGame");
				return;
			}

			recordData = new byte[nameRecord.getRecordSize(1)];
			iLength = nameRecord.getRecord(1,recordData,0);
			m_iResourceCannon = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(2)];
			iLength = nameRecord.getRecord(2,recordData,0);
			m_iResourceCorn = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(3)];
			iLength = nameRecord.getRecord(3,recordData,0);
			m_iResourceGold = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(4)];
			iLength = nameRecord.getRecord(4,recordData,0);
			m_iResourcePirate = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(5)];
			iLength = nameRecord.getRecord(5,recordData,0);
			m_iScore = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(6)];
			iLength = nameRecord.getRecord(6,recordData,0);
			m_iScoreTurn100 = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(7)];
			iLength = nameRecord.getRecord(7,recordData,0);
			m_iScoreTurn300 = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(8)];
			iLength = nameRecord.getRecord(8,recordData,0);
			m_iTurn = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(9)];
			iLength = nameRecord.getRecord(9,recordData,0);
			m_iCapturedShip = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(10)];
			iLength = nameRecord.getRecord(10,recordData,0);
			m_iMapScreenX = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(11)];
			iLength = nameRecord.getRecord(11,recordData,0);
			m_iMapScreenY = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(12)];
			iLength = nameRecord.getRecord(12,recordData,0);
			m_iShipX = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(13)];
			iLength = nameRecord.getRecord(13,recordData,0);
			m_iShipY = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(14)];
			iLength = nameRecord.getRecord(14,recordData,0);
			m_iPiratesOnCapturedShip = Integer.parseInt(new String(recordData, 0, iLength));

			recordData = new byte[nameRecord.getRecordSize(15)];
			iLength = nameRecord.getRecord(15,recordData,0);
			m_iShipLvl = Integer.parseInt(new String(recordData, 0, iLength));

			for(int i=0;i < 10;i++)
			{
				recordData = new byte[nameRecord.getRecordSize(16 + (i*5))];
				iLength = nameRecord.getRecord(16 + (i*5),recordData,0);
				m_iEnemyShipShipType[i] = Integer.parseInt(new String(recordData, 0, iLength));

				recordData = new byte[nameRecord.getRecordSize(17 + (i*5))];
				iLength = nameRecord.getRecord(17 + (i*5),recordData,0);
				m_iEnemyShipX[i] = Integer.parseInt(new String(recordData, 0, iLength));

				recordData = new byte[nameRecord.getRecordSize(18 + (i*5))];
				iLength = nameRecord.getRecord(18+ (i*5),recordData,0);
				m_iEnemyShipY[i] = Integer.parseInt(new String(recordData, 0, iLength));

				recordData = new byte[nameRecord.getRecordSize(19 + (i*5))];
				iLength = nameRecord.getRecord(19+ (i*5),recordData,0);
				m_iEnemyShipCannons[i] = Integer.parseInt(new String(recordData, 0, iLength));

				recordData = new byte[nameRecord.getRecordSize(20 + (i*5))];
				iLength = nameRecord.getRecord(20+ (i*5),recordData,0);
				m_iEnemyShipSoldiers[i] = Integer.parseInt(new String(recordData, 0, iLength));
			}

			//---Close record
			nameRecord.closeRecordStore();
		}
		catch(RecordStoreException e)
		{
		}
	}

	//------------------------------------------------------
	public void SaveGame()
	//------------------------------------------------------
	{
		try
		{
			CleanGame(true);

			//---Open record
			RecordStore nameRecord = RecordStore.openRecordStore("KaperGame",true);

			nameRecord.setRecord( 1,Integer.toString(m_iResourceCannon).getBytes(),0,Integer.toString(m_iResourceCannon).length());
			nameRecord.setRecord( 2,Integer.toString(m_iResourceCorn).getBytes(),0,Integer.toString(m_iResourceCorn).length());
			nameRecord.setRecord( 3,Integer.toString(m_iResourceGold).getBytes(),0,Integer.toString(m_iResourceGold).length());
			nameRecord.setRecord( 4,Integer.toString(m_iResourcePirate).getBytes(),0,Integer.toString(m_iResourcePirate).length());
			nameRecord.setRecord( 5,Integer.toString(m_iScore).getBytes(),0,Integer.toString(m_iScore).length());
			nameRecord.setRecord( 6,Integer.toString(m_iScoreTurn100).getBytes(),0,Integer.toString(m_iScoreTurn100).length());
			nameRecord.setRecord( 7,Integer.toString(m_iScoreTurn300).getBytes(),0,Integer.toString(m_iScoreTurn300).length());
			nameRecord.setRecord( 8,Integer.toString(m_iTurn).getBytes(),0,Integer.toString(m_iTurn).length());
			nameRecord.setRecord( 9,Integer.toString(m_iCapturedShip).getBytes(),0,Integer.toString(m_iCapturedShip).length());
			nameRecord.setRecord(10,Integer.toString(m_iMapScreenX).getBytes(),0,Integer.toString(m_iMapScreenX).length());
			nameRecord.setRecord(11,Integer.toString(m_iMapScreenY).getBytes(),0,Integer.toString(m_iMapScreenY).length());
			nameRecord.setRecord(12,Integer.toString(m_iShipX).getBytes(),0,Integer.toString(m_iShipX).length());
			nameRecord.setRecord(13,Integer.toString(m_iShipY).getBytes(),0,Integer.toString(m_iShipY).length());
			nameRecord.setRecord(14,Integer.toString(m_iPiratesOnCapturedShip).getBytes(),0,Integer.toString(m_iPiratesOnCapturedShip).length());
			nameRecord.setRecord(15,Integer.toString(m_iShipLvl).getBytes(),0,Integer.toString(m_iShipLvl).length());

			for(int i=0;i < 10;i++)
			{
				nameRecord.setRecord(16 +(i*5),Integer.toString(m_iEnemyShipShipType[i]).getBytes(),0,Integer.toString(m_iEnemyShipShipType[i]).length());
				nameRecord.setRecord(17 +(i*5),Integer.toString(m_iEnemyShipX[i]).getBytes(),0,Integer.toString(m_iEnemyShipX[i]).length());
				nameRecord.setRecord(18 +(i*5),Integer.toString(m_iEnemyShipY[i]).getBytes(),0,Integer.toString(m_iEnemyShipY[i]).length());
				nameRecord.setRecord(19 +(i*5),Integer.toString(m_iEnemyShipCannons[i]).getBytes(),0,Integer.toString(m_iEnemyShipCannons[i]).length());
				nameRecord.setRecord(20 +(i*5),Integer.toString(m_iEnemyShipSoldiers[i]).getBytes(),0,Integer.toString(m_iEnemyShipSoldiers[i]).length());
			}

			//---Close record
			nameRecord.closeRecordStore();
		}
		catch(RecordStoreException e)
		{
		}
	}

	//------------------------------------------------------
	public void SaveHighscore(String sName,int iScore)
	//------------------------------------------------------
	{
		String sNames[]  = new String[5];
		int    iScores[] = new int[5];
		byte []recordData;
		int    iLength;
		boolean bNewScore = false;

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
					if(i == 4)nameRecord.addRecord("Pete".getBytes(),0,"Pete".length());
					if(i == 3)nameRecord.addRecord("Kampfisk".getBytes(),0,"Kampfisk".length());
					if(i == 2)nameRecord.addRecord("Kizza".getBytes(),0,"Kizza".length());
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
				sNames[i]  = new String(recordData, 0, iLength);

				recordData = new byte[nameRecord.getRecordSize((i*2)+2)];
				iLength = nameRecord.getRecord((i*2)+2,recordData,0);
				iScores[i] = Integer.parseInt(new String(recordData, 0, iLength));
			}

			//----------------------------
			//--- Check if player score is high enought
			//----------------------------
			for(int i=0;i < 5;i++)
			{
				if(iScores[i] < iScore)
				{
					for(int ii=4;ii > i;ii--)
					{
						iScores[ii] = iScores[ii-1];
						sNames[ii]  = sNames[ii-1];
					}

					iScores[i] = iScore;
					sNames[i]  = sName;
					bNewScore  = true;

					break;
				}
			}

			//----------------------------
			//--- Save the new data
			//----------------------------
			if(bNewScore)
			{
				for(int i=0;i < 5;i++)
				{
					nameRecord.setRecord((i*2)+1,sNames[i].getBytes(),0,sNames[i].length());
					nameRecord.setRecord((i*2)+2,Integer.toString(iScores[i]).getBytes(),0,Integer.toString(iScores[i]).length());
				}
			}

			//----------------------------
			//---Close record
			//----------------------------
			nameRecord.closeRecordStore();
		}
		catch(RecordStoreException e)
		{
		}
	}

	//------------------------------------------------------
	public void CleanGame(boolean CreateDummy)
	//------------------------------------------------------
	{
		try
		{
			String sTemp = "0";
			byte bytes[] = sTemp.getBytes();

			//System.out.println("CleanGame");

			//---Create it, if first time run
			RecordStore nameRecord = RecordStore.openRecordStore("KaperGame",true);
			nameRecord.closeRecordStore();

			//---Delete Record
			RecordStore.deleteRecordStore("KaperGame");

			//---Open record
			nameRecord = RecordStore.openRecordStore("KaperGame",true);

			//---Delete every records inside
			RecordEnumeration re = nameRecord.enumerateRecords(null,null,false);

			while(re.hasNextElement())
			{
				nameRecord.deleteRecord(re.nextRecordId());
			}

			if(CreateDummy)
			{
				//---Add number of places to use
				for(int i=0;i< 70;i++)
				{
					nameRecord.addRecord(bytes,0,bytes.length);
				}
			}

			//---Close record
			nameRecord.closeRecordStore();
		}
		catch(RecordStoreException e)
		{
		}
	}	
	
	//------------------------------------------------------
	public boolean Update()
	//------------------------------------------------------
	{

		if(m_bClose)return true;
		if(m_bLocked)return false;

		if(0 == m_iGameState)
		{
			MapUpdate();
			ShipUpdate();

			m_iSkyCounterY--;

			for(int i=0;i < 19;i++)
			{
				if(m_oCanvas.m_oImageArray[5] == null)break;
				
				if((m_iSkyCounterY % 2) == 0)m_iSkyX[i]++;

				if(m_iSkyCounterY <= 0)
				{
					m_iSkyY[i]++;
				}


				// Respawn Skies

				if(m_iSkyX[i] > (25*29)+100)
				{
					if(Math.abs(m_oCanvas.m_oRand.nextInt()%50) == 0)
					{
						m_iSkyType[i] = Math.abs(m_oCanvas.m_oRand.nextInt()%3);

						m_iSkyX[i] = -100 - Math.abs(m_oCanvas.m_oRand.nextInt()%600);
						m_iSkyY[i] = Math.abs(m_oCanvas.m_oRand.nextInt()%(14*25));
					}
				}
			}

				if(m_iSkyCounterY <= 0)
				{
					m_iSkyCounterY = 16;
				}

			EnemyShipUpdate();

			// GameOver
			if(m_iResourcePirate <= 0 || m_iTurn >= 55)
			{
				GameOver();
			}

			if(m_bWaitingOnMove == true && m_bShipMoving == false && m_bMapMoving == false)
			{
				if(m_bToCity)
				{
					m_bToCity = false;
					m_iGameState = 2;
					m_oStateSailingToCity.Init();

					return false;
				}

				if(true == InterfaceNewTurn())
				{ // Game Over
					GameOver();
				}
				if(CheckBoatsCollision(false) == false)
				{
					if(CheckEventCollision() == true)
					{
						m_iEvent =  Math.abs(m_oCanvas.m_oRand.nextInt()%(12));
						m_bWait = true;						
					}
				}
				m_bWaitingOnMove = false;
			}

			KeyHandling();
		}

		if(2 == m_iGameState)
		{
			m_oStateSailingToCity.Update();
		}

		if(1 == m_iGameState)
		{
			m_oStateCity.Update();
		}

		if(3 == m_iGameState)
		{
			m_oStateBoard.Update();
		}
		if(4 == m_iGameState)
		{
			m_oStateAttack.Update();
		}

		return false;
	}	

	//------------------------------------------------------
	public void NormalButton(int iKey)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		KeyCheck(iKey); // Game keys

		// Normal Keys

		if(6 == m_iGameState)
		{
			if(iKey == 128)return;

			int keyState = 0;
			keyState  = m_oCanvas.getGameAction(iKey);

			if(keyState == Canvas.LEFT)
			{
				m_iWebRankShow--;
				if(m_iWebRankShow <= 0)m_iWebRankShow = 9;
				return;
			}
			else if(keyState == Canvas.RIGHT)
			{
				m_iWebRankShow++;
				if(m_iWebRankShow >= 10)m_iWebRankShow = 1;
				return;
			}
			else
			{

				m_bClose = true;
			}
		}

		if(5 == m_iGameState)
		{
			if(Canvas.KEY_NUM1 == iKey)
			{
				m_oCanvas.StopSound();

				// Register Score 
				SaveHighscore(m_sPlayerName,m_iScore);

				m_bClose = true;
				return;
			}

			if(Canvas.KEY_NUM3 == iKey)
			{
				m_oCanvas.StopSound();

				m_iGameState = 6;
				return;
			}

			if(iKey == 128)return;
			int keyState  = m_oCanvas.getGameAction(iKey);

			//	m_sPlayerName


			if(keyState == Canvas.UP)
			{
				m_sPlayerName = m_sPlayerName.substring(0,m_iPlayerNamePos) + SwitchChar(m_sPlayerName.charAt(m_iPlayerNamePos),true) + m_sPlayerName.substring(m_iPlayerNamePos+1);
			}

			if(keyState == Canvas.DOWN)
			{
				m_sPlayerName = m_sPlayerName.substring(0,m_iPlayerNamePos) + SwitchChar(m_sPlayerName.charAt(m_iPlayerNamePos),false) + m_sPlayerName.substring(m_iPlayerNamePos+1);
			}

			if(keyState == Canvas.LEFT)
			{
				if(m_iPlayerNamePos > 0)m_iPlayerNamePos--;

			}

			if(keyState == Canvas.RIGHT)
			{
				if(m_iPlayerNamePos > 10)return;

				if(m_sPlayerName.length() <= (m_iPlayerNamePos+1))m_sPlayerName += "a";
				m_iPlayerNamePos++;
			}

			return;
		}

		if(0 == m_iGameState && m_bWait == true)
		{
			if(Canvas.KEY_NUM7 == iKey){KeyPressedEvent(7);}
			if(Canvas.KEY_NUM9 == iKey){KeyPressedEvent(9);}
			if(Canvas.KEY_NUM5 == iKey){KeyPressedEvent(5);}
			if(Canvas.KEY_NUM1 == iKey){KeyPressedEvent(1);}
			if(Canvas.KEY_NUM3 == iKey){KeyPressedEvent(3);}
		}

		else if(2 == m_iGameState)
		{
			m_oStateSailingToCity.SoftKey(iKey);

			if(Canvas.KEY_NUM5 != iKey)
			{
				return;	
			}
			
			if(m_oStateSailingToCity.m_iStatus == 5)
			{
				m_oStateSailingToCity.m_bPaused = false;
				m_oStateSailingToCity.m_iStatus = 6;
			}

			if(m_oStateSailingToCity.m_iStatus == 1) // Docked
			{
				m_oStateCity.Init();
				m_iGameState = 1;
			}

			else if(m_oStateSailingToCity.m_iStatus == 2) // Crashed
			{
				m_oStateCity.Init();
				m_iGameState = 1;

				// Punish
				m_iAddCannons  = -1;
				m_iAddPirates -= (m_iResourcePirate / 2);
			}
		}

		else if(1 == m_iGameState)
		{
			if(-1 == m_oStateCity.SoftKey(iKey))
			{
				m_oStateCity.DeInit();
				m_iGameState = 0;
			}
		}

		else if(3 == m_iGameState)
		{
			int iStatus = 0;
			iStatus = m_oStateBoard.SoftKey(iKey);

			if(iStatus > 0) // Retreat
			{
				m_iResourcePirate = iStatus;
				m_oStateBoard.DeInit();
				m_iGameState = 0;
//				m_sEventMsg1 = "You escaped combat";
				CalculateFleePunish();
				m_iEvent = 20;
			}

			if(iStatus == -1) // Game Over
			{
				m_oStateBoard.DeInit();
				m_iGameState = 0;

				if(m_iResourcePirate <= 0) // GameOver
				{
					GameOver();
				}
				else // Won the battle
				{
					m_oCanvas.PlaySound("b.mid",false);

					boolean bShip = true;
					///////////////////////////
					// CLOSE_COMBAT WON
					///////////////////////////
					if(12 == m_iEventOld)
					{
						m_iAddGold    = 20;
						m_iAddFood    =  5;
						m_iAddCannons =  1;
						m_iScore  +=  10;
					}
					else if(13 == m_iEventOld)
					{
						m_iAddGold    = 30;
						m_iAddFood    =  7;
						m_iAddCannons =  1;
						m_iScore  +=  20;
					}
					else if(14 == m_iEventOld)
					{
						m_iAddGold    = 40;
						m_iAddFood    = 10;
						m_iAddCannons =  1;
						m_iScore  +=  30;
					}
					else if(15 == m_iEventOld)
					{
						m_iAddGold    = 50;
						m_iAddFood    = 20;
						m_iAddCannons =  2;
						m_iScore  +=  40;
					}
					else if(16 == m_iEventOld)
					{
						m_iAddGold    = 100;
						m_iAddFood    =  50;
						m_iAddCannons =  3;
						m_iScore  +=  50;
					}
					else if(17 == m_iEventOld)
					{
						m_iAddGold    = 300;
						m_iAddFood    = 150;
						m_iAddCannons =  10;
						m_iScore  +=  60;
					}
					else if(18 == m_iEventOld)
					{
						m_iAddGold    = 200;
						m_iAddFood    =  50;
						m_iAddCannons =   2;
						m_iScore  +=  20;
					}

					else // Non-ship attack
					{
						m_iAddGold    = 50;
						bShip = false; // false; Hårde ordre fra Jonas

						m_iEventOld = 13;
						m_iEvent    = 19;
						m_bWait     = true;
						m_iScore  +=  20;
					}

					if(bShip)
					{
						CheckBoatsCollision(true);
						m_iEvent    = 19;
						m_bWait     = true;
					}
				}
			}
		}
		else if(4 == m_iGameState)
		{
			int iStatus = 0;
			iStatus = m_oStateAttack.SoftKey(iKey);

			if(iStatus > 0) // Board
			{
				if(m_iResourcePirate <= 0) // GameOver
				{
					GameOver();
				}

//				m_oStateAttack.DeInit();
				if(m_oStateBoard == null){m_oCanvas.m_cGameMode = 6; return;}
				m_oStateBoard.Init(iStatus);
				m_iGameState = 3;
			}

			if(iStatus == -2) // Flee
			{
				if(m_iResourcePirate <= 0) // GameOver
				{
					GameOver();
				}

//				m_oStateAttack.DeInit();

				m_iGameState = 0;

				//m_sEventMsg1 = "You got away";
				CalculateFleePunish();
				m_iEvent = 20;
			}


			if(iStatus == -1)
			{
//				m_oStateAttack.DeInit();

				m_iGameState = 0;

				if(m_iResourcePirate <= 0) // GameOver
				{
					GameOver();
				}
				else // Won the battle
				{
					m_oCanvas.PlaySound("b.mid",false);

					///////////////////////////
					// RANGE_COMBAT WON
					///////////////////////////
					if(12 == m_iEventOld)
					{
						m_iAddFood    =   5;
						m_iAddGold    =  10;
						m_iAddPirates =   1;
						m_iScore  +=  10;
					}
					if(13 == m_iEventOld)
					{
						m_iAddFood    =   7;
						m_iAddGold    =  15;
						m_iAddPirates =   2;
						m_iScore  +=  20;
					}
					if(14 == m_iEventOld)
					{
						m_iAddFood    =  10;
						m_iAddGold    =  20;
						m_iAddPirates =   3;
						m_iScore  +=  30;
					}
					if(15 == m_iEventOld)
					{
						m_iAddFood    =  15;
						m_iAddGold    =  25;
						m_iAddPirates =   5;
						m_iScore  +=  40;
					}
					if(16 == m_iEventOld)
					{
						m_iAddFood    =  20;
						m_iAddGold    =  50;
						m_iAddPirates =   7;
						m_iScore  +=  50;
					}
					if(17 == m_iEventOld)
					{
						m_iAddFood    =  30;
						m_iAddGold    = 150;
						m_iAddPirates =  20;
						m_iScore  += 100;
					}
					if(18 == m_iEventOld)
					{
						m_iAddFood    =  20;
						m_iAddGold    = 100;
						m_iAddPirates =   7;
						m_iScore  +=  35;
					}

					m_sEventMsg1 = "You sank the enemy.";
					m_sEventMsg2 = "And salvage some resources";

					CheckBoatsCollision(true);

				}
			}
		}
	}

	//------------------------------------------------------
	private boolean CheckBoatsCollision(boolean bRemoveThem)
	//------------------------------------------------------
	{
		if(m_bLocked)return false;

		if(bRemoveThem)
		{
			for(int i=0;i < 10;i++)
			{
				if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY))
				{
					int iRandom;
		
					// Placing random place
					do
					{
						m_iEnemyShipX[i] = 25 * Math.abs(m_oCanvas.m_oRand.nextInt()%(29));
						m_iEnemyShipY[i] = 25 * Math.abs(m_oCanvas.m_oRand.nextInt()%(14));
					}
					while(m_oCanvas.m_oStateGame.MapCheckCollision((m_iEnemyShipX[i] / 25)+0,(m_iEnemyShipY[i] / 25)-0,false) == 0 || ((m_iEnemyShipX[i] / 25) == ((m_iShipX/25) + m_iMapScreenX) && (m_iEnemyShipY[i] / 25) == (m_iShipY/25) + m_iMapScreenY));
			
					m_iEnemyShipGraphicWay[i] = 1;
			//		m_iShipWaveA = new int[3];
			//		m_iShipWaveX = new int[3];
			//		m_iShipWaveY = new int[3];
			
					iRandom = Math.abs(m_oCanvas.m_oRand.nextInt()%(m_iShipLvl+1));
			
					m_iEnemyShipShipType[i] = 12 + iRandom;
			
					if(iRandom == 0)
					{
						m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  3)+  1 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
						m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()%  10)+  5 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
					}
			
					if(iRandom == 1)
					{
						m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  5)+  2 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
						m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 21)+  15 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
					}
					if(iRandom == 2)
					{
						m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  14)+  13 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
						m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 26)+ 35 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
					}
					if(iRandom == 3)
					{
						m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  26)+ 25 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
						m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 71)+ 40 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
					}
					if(iRandom == 4)
					{
						m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()% 51)+ 30 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
						m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 351)+ 100 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
					}
					if(iRandom == 5)
					{
						m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()% 101)+ 100 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
						m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 501)+ 500 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
					}
					if(iRandom == 6)
					{
						m_iEnemyShipCannons[i]  = (Math.abs(m_oCanvas.m_oRand.nextInt()%  101)+   50 + ((m_oCanvas.m_oStateGame.m_iResourceCannon * 30)/100));
						m_iEnemyShipSoldiers[i] = (Math.abs(m_oCanvas.m_oRand.nextInt()% 501)+  50 + ((m_oCanvas.m_oStateGame.m_iResourcePirate * 30)/100));
					}		
				}
			}

			return true;
		}

		for(int i=0;i < 10;i++)
		{
			if((m_iEnemyShipX[i]/25) == ((m_iShipX/25) + m_iMapScreenX) && (m_iEnemyShipY[i]/25) == ((m_iShipY/25) + m_iMapScreenY))
			{
				m_iEvent = m_iEnemyShipShipType[i];
				m_iShipNr = i;
				m_bWait  = true;

				return true;
			}
		}

		return false; // Roll normal events
	}

	//------------------------------------------------------
	private void GameOver()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		CleanGame(false);

		m_iGameState = 5;
		m_oCanvas.PlaySound("c.mid",false);
	}

	//------------------------------------------------------
	private int KeyCheck(int iKey)
	//------------------------------------------------------
	{
		if(m_bLocked)return -1;

		if(iKey != 128)
		{
			// Move ship phase
			if(m_bShipMoving == false && m_bMapMoving == false && m_bWait == false && m_iAddFood    == 0 &&
		   m_iAddGold    == 0 &&
		   m_iAddPirates == 0 &&
		   m_iAddCannons == 0)
			{
				if(m_iKeyCountDown <= 0)m_iKeyCountDown = 2;

				int keyState  = m_oCanvas.getGameAction(iKey);
				

				// Op
				if(keyState == Canvas.UP)
				{
					m_bKeyUp = true;
				}
	
				// Ned
				else if(keyState == Canvas.DOWN)
				{
					m_bKeyDown = true;
				}

				// Højre
				else if(keyState == Canvas.RIGHT)
				{
					m_bKeyRight = true;
				}
	
				// Venstre
				else if(keyState == Canvas.LEFT)
				{
					m_bKeyLeft = true;
				}
			}
		}

		return -1;
	}

	//------------------------------------------------------
	private char SwitchChar(char cChar,boolean bUp)
	//------------------------------------------------------
	{
		if(bUp)
		{
			     if('a' == cChar)cChar = 'b';
			else if('b' == cChar)cChar = 'c';
			else if('c' == cChar)cChar = 'd';
			else if('d' == cChar)cChar = 'e';
			else if('e' == cChar)cChar = 'f';
			else if('f' == cChar)cChar = 'g';
			else if('g' == cChar)cChar = 'h';
			else if('h' == cChar)cChar = 'i';
			else if('i' == cChar)cChar = 'j';
			else if('j' == cChar)cChar = 'k';
			else if('k' == cChar)cChar = 'l';
			else if('l' == cChar)cChar = 'm';
			else if('m' == cChar)cChar = 'n';
			else if('n' == cChar)cChar = 'o';
			else if('o' == cChar)cChar = 'p';
			else if('p' == cChar)cChar = 'q';
			else if('q' == cChar)cChar = 'r';
			else if('r' == cChar)cChar = 's';
			else if('s' == cChar)cChar = 't';
			else if('t' == cChar)cChar = 'v';
			else if('v' == cChar)cChar = 'u';
			else if('u' == cChar)cChar = 'w';
			else if('w' == cChar)cChar = 'x';
			else if('x' == cChar)cChar = 'y';
			else if('y' == cChar)cChar = 'z';
			else if('z' == cChar)cChar = ' ';
			else if(' ' == cChar)cChar = 'a';
		}
		else
		{
			if('a' == cChar)cChar = ' ';
			else if('b' == cChar)cChar = 'a';
			else if('c' == cChar)cChar = 'b';
			else if('d' == cChar)cChar = 'c';
			else if('e' == cChar)cChar = 'd';
			else if('f' == cChar)cChar = 'e';
			else if('g' == cChar)cChar = 'f';
			else if('h' == cChar)cChar = 'g';
			else if('i' == cChar)cChar = 'h';
			else if('j' == cChar)cChar = 'i';
			else if('k' == cChar)cChar = 'j';
			else if('l' == cChar)cChar = 'k';
			else if('m' == cChar)cChar = 'l';
			else if('n' == cChar)cChar = 'm';
			else if('o' == cChar)cChar = 'n';
			else if('p' == cChar)cChar = 'o';
			else if('q' == cChar)cChar = 'p';
			else if('r' == cChar)cChar = 'q';
			else if('s' == cChar)cChar = 'r';
			else if('t' == cChar)cChar = 's';
			else if('v' == cChar)cChar = 't';
			else if('u' == cChar)cChar = 'v';
			else if('w' == cChar)cChar = 'u';
			else if('x' == cChar)cChar = 'w';
			else if('y' == cChar)cChar = 'x';
			else if('z' == cChar)cChar = 'y';
			else if(' ' == cChar)cChar = 'z';
		}

		if(m_iPlayerNamePos == 0 && cChar == ' ')cChar = 'a';

		return cChar;
	}

	//------------------------------------------------------
	public void KeyPressedEvent(int iKey)
	//------------------------------------------------------
	{
		m_oCanvas.StopSound();

		if(iKey == 5 && m_iEvent == 20)
		{
			m_bWait = false;

			m_iAddPirates -= m_iFleePunishKapers;
			m_iAddCannons -= m_iFleePunishCannons;

			return;
		}

		if(iKey == 5 && m_iEvent == 21)
		{
			m_bWait = false;
			GameOver();
			return;
		}

		if(iKey == 5 && m_iEvent == 22)
		{
			m_bWait = false;
			return;
		}

		if(iKey == 5 && m_iEvent == -1) // Continue pressed
		{
			m_sEventMsg2   = "";
			m_bWait = false;

			if(m_iEventOld == 8)
			{
				if(m_oStateBoard == null){m_oCanvas.m_cGameMode = 6; return;}
				m_oStateBoard.Init(Math.abs(m_oCanvas.m_oRand.nextInt()%(m_iResourcePirate+1))+  1);
				m_iGameState = 3; //GAME_CLOSECOMBAT;
			}
			if(m_iEventOld == 4)
			{
				if(m_oStateBoard == null){m_oCanvas.m_cGameMode = 6; return;}
				m_oStateBoard.Init(Math.abs(m_oCanvas.m_oRand.nextInt()%(m_iResourcePirate+1))+  1);
				m_iGameState = 3; //GAME_CLOSECOMBAT;
			}
		}

		if(iKey == 9 && m_iEvent != -1)
		{

			     if(m_iEvent >= 12 && m_iEvent <= 18){m_iEvent = 20;CalculateFleePunish();return;}
			else if(m_iEvent == 20){return;}

			m_bWait = false;
		}


		if(iKey == 1) // Attack
		{
			     if(m_iEvent >= 12 && m_iEvent <= 18){}
			else return;

			if(m_oStateAttack == null){m_oCanvas.m_cGameMode = 6; return;}
			m_oStateAttack.Init(m_iEnemyShipCannons[m_iShipNr],m_iEnemyShipSoldiers[m_iShipNr]);

			m_iGameState = 4; // GAME_RANGECOMBAT;
			m_iEventOld = m_iEvent;
			m_iEvent = -1;
		}


		if(iKey == 7)
		{
			if(m_iEvent == 12      ||
			   m_iEvent == 13      ||
			   m_iEvent == 14      ||
		  	   m_iEvent == 15      ||
		   	   m_iEvent == 16      ||
		 	   m_iEvent == 17      ||
               m_iEvent == 20          ||
		   	   m_iEvent == 18)
			{
				return;
			}

			if(m_iEvent == 19)
			{
				if(m_iCapturedShip != 0)
				{
					m_sEventMsg1 = "You already got a prize ship";
				}
				else
				{
					m_sEventMsg1 = "Ship taken as prize";
					m_iAddPirates = -(m_iResourcePirate / 2);
					m_iPiratesOnCapturedShip = (m_iResourcePirate / 2);
					m_iCapturedShip = m_iEventOld;
				}
			}

			if(m_iEvent == 0)
			{
				m_iAddFood = Math.abs(m_oCanvas.m_oRand.nextInt()%75)+1+((m_iResourceCorn * 50)/100);
				m_sEventMsg1 = "Grain found: " + Integer.toString(m_iAddFood);
			}
			if(m_iEvent == 1)
			{
				m_iAddPirates = Math.abs(m_oCanvas.m_oRand.nextInt()%20)+1+((m_iResourcePirate * 50)/100);
				m_sEventMsg1   = "Survived sailors found: " + Integer.toString(m_iAddPirates);
			}
			if(m_iEvent == 2)
			{
				m_sEventMsg1   = "Nothing found...";
			}
			if(m_iEvent == 3)
			{
				m_iAddPirates = Math.abs(m_oCanvas.m_oRand.nextInt()%20)+1+((m_iResourcePirate * 30)/100); // %30
				m_sEventMsg1   = Integer.toString(m_iAddPirates) + " Crewmen dies from";
				m_sEventMsg2   = "the plague.";
				m_iAddPirates = -m_iAddPirates;
			}
			if(m_iEvent == 4)
			{
				m_sEventMsg1   = "Pirates!, prepare for battle";
			}
			if(m_iEvent == 5)
			{
				m_iAddCannons = Math.abs(m_oCanvas.m_oRand.nextInt()%9)+1+Math.abs(m_oCanvas.m_oRand.nextInt()%(m_iResourceCannon+1));
				m_sEventMsg1 = "Cannons found: " + Integer.toString(m_iAddCannons);
			}
			if(m_iEvent == 6)
			{
				m_sEventMsg1   = "Nothing found...";
			}
			if(m_iEvent == 7)
			{
				m_iAddGold = Math.abs(m_oCanvas.m_oRand.nextInt()%99)+1+ Math.abs(m_oCanvas.m_oRand.nextInt()%(m_iResourceGold+1));
				m_sEventMsg1 = "Gold found: " + Integer.toString(m_iAddGold);
			}
			if(m_iEvent == 8)
			{
				m_sEventMsg1   = "Pirates!, prepare for battle";
			}
			if(m_iEvent == 9)
			{
				m_sEventMsg1   = "Nothing found...";
			}
			if(m_iEvent == 10)
			{
				m_sEventMsg1   = "Crashed into rock.";
				m_iAddFood    = -1 - ((m_iResourceCorn   * 10)/100); // 10%
				m_iAddGold    = -1 - ((m_iResourceGold   *  5)/100); //  5%
				m_iAddPirates = -1 - ((m_iResourcePirate * 30)/100); // 30%
				m_iAddCannons = -1 - ((m_iResourceCannon * 50)/100); // 50%
			}
			if(m_iEvent == 11)
			{
				m_iAddGold = Math.abs(m_oCanvas.m_oRand.nextInt()%399) + 1 + Math.abs(m_oCanvas.m_oRand.nextInt()%(m_iResourceGold+1));
				m_sEventMsg1 = "Gold found: " + Integer.toString(m_iAddGold);
			}

			m_iEventOld = m_iEvent;
			m_iEvent = -1;
		}
	}	
	
	//------------------------------------------------------
	private void KeyHandling()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		if(!m_bKeyLeft && !m_bKeyRight && !m_bKeyUp && !m_bKeyDown)return;

		if(!(m_bWait == false && m_iAddFood    == 0 &&
		   m_iAddGold    == 0 &&
		   m_iAddPirates == 0 &&
		   m_iAddCannons == 0))
		{
			m_bKeyLeft  = false;
			m_bKeyRight = false;
			m_bKeyUp    = false;
			m_bKeyDown  = false;

			m_iKeyCountDown = 0;
		}

		int iMsg;

		if(m_iKeyCountDown <= 0)
		{
				if(m_bKeyUp && m_bKeyDown)
				{
					m_bKeyLeft  = false;
					m_bKeyRight = false;
					m_bKeyUp    = false;
					m_bKeyDown  = false;

					m_iKeyCountDown = 0;
				}
				if(m_bKeyRight && m_bKeyLeft)
				{
					m_bKeyLeft  = false;
					m_bKeyRight = false;
					m_bKeyUp    = false;
					m_bKeyDown  = false;

					m_iKeyCountDown = 0;
				}


				// 41
				     if(m_bKeyUp && m_bKeyRight)
				{
					iMsg = ShipMove(0x80,false);
	
					if((iMsg & 0x04) == 0x04) // City collision
					{
						m_bToCity = true;
						iMsg -= 0x04;
					}
	
					if(0x02 == iMsg)
					{
						m_bKeyLeft  = false;
						m_bKeyRight = false;
						m_bKeyUp    = false;
						m_bKeyDown  = false;

						m_iKeyCountDown = 0;
						return;
					}
					if(0x01 == iMsg)
					{
						MapIncSetXY(0x80);
					}
	
					EnemyShipMove();

					m_bWaitingOnMove = true;
				}

				// 42
				else if(m_bKeyUp && m_bKeyLeft)
				{
					iMsg = ShipMove(0x20,false);
	
					if((iMsg & 0x04) == 0x04) // City collision
					{
						m_bToCity = true;
						iMsg -= 0x04;
					}
	
					if(0x02 == iMsg)
					{
						m_bKeyLeft  = false;
						m_bKeyRight = false;
						m_bKeyUp    = false;
						m_bKeyDown  = false;

						m_iKeyCountDown = 0;
						return;
					}
					if(0x01 == iMsg)
					{
						MapIncSetXY(0x20);
					}
	
					EnemyShipMove();

					m_bWaitingOnMove = true;
				}

				// 82
				else if(m_bKeyDown && m_bKeyLeft)
				{
					iMsg = ShipMove(0x10,false);
	
					if((iMsg & 0x04) == 0x04) // City collision
					{
						m_bToCity = true;
						iMsg -= 0x04;
					}
	
					if(0x02 == iMsg)
					{
						m_bKeyLeft  = false;
						m_bKeyRight = false;
						m_bKeyUp    = false;
						m_bKeyDown  = false;

						m_iKeyCountDown = 0;
						return;
					}
					if(0x01 == iMsg)
					{
						MapIncSetXY(0x10);
					}
	
					EnemyShipMove();

					m_bWaitingOnMove = true;
				}

				// 81
				else if(m_bKeyDown && m_bKeyRight)
				{
					iMsg = ShipMove(0x40,false);
	
					if((iMsg & 0x04) == 0x04) // City collision
					{
						m_bToCity = true;
						iMsg -= 0x04;
					}
	
					if(0x02 == iMsg)
					{
						m_bKeyLeft  = false;
						m_bKeyRight = false;
						m_bKeyUp    = false;
						m_bKeyDown  = false;

						m_iKeyCountDown = 0;
						return;
					}
					if(0x01 == iMsg)
					{
						MapIncSetXY(0x40);
					}
	
					EnemyShipMove();

					m_bWaitingOnMove = true;
				}

				// Op
				else if(m_bKeyUp)
				{
					iMsg = ShipMove(4,false);
	
					if((iMsg & 0x04) == 0x04) // City collision
					{
						m_bToCity = true;
						iMsg -= 0x04;
					}
	
					if(0x02 == iMsg)
					{
						m_bKeyLeft  = false;
						m_bKeyRight = false;
						m_bKeyUp    = false;
						m_bKeyDown  = false;

						m_iKeyCountDown = 0;
						return;
					}
					if(0x01 == iMsg)
					{
						if(MapIncSetXY(4) == true)ShipMove(4,true);
					}
	
					EnemyShipMove();

					m_bWaitingOnMove = true;
				}
	
				// Ned
				else if(m_bKeyDown)
				{
					iMsg = ShipMove(8,false);
					if((iMsg & 0x04) == 0x04) // City collision
					{
						m_bToCity = true;
						iMsg -= 0x04;
					}
	
					if(0x02 == iMsg)
					{
						m_bKeyLeft  = false;
						m_bKeyRight = false;
						m_bKeyUp    = false;
						m_bKeyDown  = false;

						m_iKeyCountDown = 0;
						return;
					}
					if(0x01 == iMsg)
					{
						if(MapIncSetXY(8) == true)ShipMove(8,true);
					}
	
					EnemyShipMove();

					m_bWaitingOnMove = true;
				}

				// Højre
				else if(m_bKeyRight)
				{
					iMsg = ShipMove(1,false);
	
					if((iMsg & 0x04) == 0x04) // City collision
					{
						m_bToCity = true;
						iMsg -= 0x04;
					}
	
					if(0x02 == iMsg)
					{
						m_bKeyLeft  = false;
						m_bKeyRight = false;
						m_bKeyUp    = false;
						m_bKeyDown  = false;

						m_iKeyCountDown = 0;
						return;
					}
					if(0x01 == iMsg)
					{
						if(MapIncSetXY(1) == true)ShipMove(1,true);
					}

					EnemyShipMove();

					m_bWaitingOnMove = true;
				}
	
				// Venstre
				else if(m_bKeyLeft)
				{
					iMsg = ShipMove(2,false);
	
					if((iMsg & 0x04) == 0x04) // City collision
					{
						m_bToCity = true;
						iMsg -= 0x04;
					}
	
					if(0x02 == iMsg)
					{
						m_bKeyLeft  = false;
						m_bKeyRight = false;
						m_bKeyUp    = false;
						m_bKeyDown  = false;

						m_iKeyCountDown = 0;
						return;
					}
					if(0x01 == iMsg)
					{
						if(MapIncSetXY(2) == true)ShipMove(2,true);
					}
	
					EnemyShipMove();

					m_bWaitingOnMove = true;
				}

			m_bKeyLeft  = false;
			m_bKeyRight = false;
			m_bKeyUp    = false;
			m_bKeyDown  = false;
		}


		m_iKeyCountDown--;
	}

	//------------------------------------------------------
	public void CalculateFleePunish()
	//------------------------------------------------------
	{
		if(Math.abs(m_oCanvas.m_oRand.nextInt()%100) < 30)
		{
			m_iFleePunishKapers   = Math.abs(m_oCanvas.m_oRand.nextInt()%(1+(((m_iResourcePirate+1) * 30)/100)));
			m_iFleePunishCannons  = Math.abs(m_oCanvas.m_oRand.nextInt()%(1+(((m_iResourceCannon+1) * 50)/100)));
		}
		else
		{
			m_iFleePunishKapers  = 0;
			m_iFleePunishCannons = 0;
		}
	}	

	//------------------------------------------------------
	private void DrawEvent(Graphics g)
	//------------------------------------------------------
	{
		int iX = (m_oCanvas.m_iWidth-14)/9;
		int iY = (m_oCanvas.m_iHeight-34)/9;
		int iXStart = 0;
		int iYStart = 0;
		
		g.setFont(m_oCanvas.m_oFontSmallP);
		if(iX < 15)
		{
			iXStart = -9;
		}
		if(iY < 9)
		{
			iYStart = -9;
		}
		
		g.setColor(0,0,0);

		if(m_iEvent == -1) // Press ok to continue
		{
			g.drawString(m_sEventMsg1,iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
			g.drawString(m_sEventMsg2,iXStart+21,iYStart+21+7,Graphics.TOP | Graphics.LEFT);

			g.drawString("Press [5] to continue",iXStart+21,iYStart+21+37,Graphics.TOP | Graphics.LEFT);
		}

		if(m_iEvent == 0  ||
		   m_iEvent == 1  ||
		   m_iEvent == 2  ||
		   m_iEvent == 3  ||
		   m_iEvent == 4  ||
		   m_iEvent == 5)
		{
			g.drawString("An abandoned ship appears.",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);

			g.drawString("Investigate ship?",iXStart+21,iYStart+21+30,Graphics.TOP | Graphics.LEFT);
			g.drawString("Yes[7] No[9]",iXStart+21+5,iYStart+21+40,Graphics.TOP | Graphics.LEFT);

		}

		if(m_iEvent == 6      ||
		   m_iEvent == 7      ||
		   m_iEvent == 8)
		{
			g.drawString("Strange object in the mist.",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);

			g.drawString("Investigate mist?",iXStart+21,iYStart+21+30,Graphics.TOP | Graphics.LEFT);
			g.drawString("Yes[7] No[9]",iXStart+21+5,iYStart+21+40,Graphics.TOP | Graphics.LEFT);
		}

		if(m_iEvent == 9      ||
		   m_iEvent == 10     ||
		   m_iEvent == 11)
		{
			g.drawString("Floating debris.",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);

			g.drawString("Investigate?",iXStart+21,iYStart+21+30,Graphics.TOP | Graphics.LEFT);
			g.drawString("Yes[7] No[9]",iXStart+21+5,iYStart+21+40,Graphics.TOP | Graphics.LEFT);
		}

		if(m_iEvent == 19)
		{
			g.drawString("You won the battle!",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
			g.drawString("[7]Take as prize ship",iXStart+21,iYStart+21+10,Graphics.TOP | Graphics.LEFT);
			g.drawString("[9]Sink ship",iXStart+21,iYStart+21+20,Graphics.TOP | Graphics.LEFT);

		}

		if(m_iEvent == 21)
		{
			g.drawString("Turn50 passed.",iXStart+21,iYStart+21   ,Graphics.TOP | Graphics.LEFT);
			g.drawString("Demo Over..." ,iXStart+21,iYStart+21+15,Graphics.TOP | Graphics.LEFT);
			g.drawString("[5] Continue"   ,iXStart+21,iYStart+21+40,Graphics.TOP | Graphics.LEFT);
		}

		if(m_iEvent == 22)
		{
			g.drawString("Turn300 passed.",iXStart+21,iYStart+21   ,Graphics.TOP | Graphics.LEFT);
			g.drawString("Score saved..." ,iXStart+21,iYStart+21+15,Graphics.TOP | Graphics.LEFT);
			g.drawString("[5] Continue"   ,iXStart+21,iYStart+21+40,Graphics.TOP | Graphics.LEFT);
		}

		if(m_iEvent == 20)
		{
			g.drawString("You escaped combat",iXStart+21,iYStart+21   ,Graphics.TOP | Graphics.LEFT);

			if(m_iFleePunishKapers == 0 && m_iFleePunishCannons == 0)
			{
				g.drawString("without damage [5]",iXStart+21,iYStart+21+10,Graphics.TOP | Graphics.LEFT);
			}
			else
			{
				g.drawString("with some damage [5]",iXStart+21,iYStart+21+10,Graphics.TOP | Graphics.LEFT);
			}
		}

		if(m_iEvent == 12      ||
		   m_iEvent == 13      ||
		   m_iEvent == 14      ||
		   m_iEvent == 15      ||
		   m_iEvent == 16      ||
		   m_iEvent == 17      ||
		   m_iEvent == 18)
		{
			if(m_iEvent == 12)
			{
				g.drawString("English schooner",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
				//g.drawString("C[1-2] S[5-10]",21,21+10,Graphics.TOP | Graphics.LEFT);
			}

			if(m_iEvent == 13)
			{
				g.drawString("English merchant ship",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
				//g.drawString("C[1-3] S[5-15]",21,21+10,Graphics.TOP | Graphics.LEFT);
			}

			if(m_iEvent == 14)
			{
				g.drawString("English brig",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
				//g.drawString("C[5-8] S[25-40]",21,21+10,Graphics.TOP | Graphics.LEFT);
			}

			if(m_iEvent == 15)
			{
				g.drawString("English gunboat",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
				//g.drawString("C[7-10] S[35-50]",21,21+10,Graphics.TOP | Graphics.LEFT);
			}

			if(m_iEvent == 16)
			{
				g.drawString("English frigate",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
				//g.drawString("C[10-20] S[50-100]",21,21+10,Graphics.TOP | Graphics.LEFT);
			}

			if(m_iEvent == 17)
			{
				g.drawString("English Man-O-War",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
				//g.drawString("C[100-250] S[500-1250]",21,21+10,Graphics.TOP | Graphics.LEFT);
			}

			if(m_iEvent == 18)
			{
				g.drawString("Pirate ship!",iXStart+21,iYStart+21,Graphics.TOP | Graphics.LEFT);
				//g.drawString("C[x-xx] S[x-xxx]",21,21+10,Graphics.TOP | Graphics.LEFT);
			}

			g.drawString("Choose order.",iXStart+21,iYStart+21+30,Graphics.TOP | Graphics.LEFT);
			g.drawString("Attack[1] Flee[9]",iXStart+21+5,21+40,Graphics.TOP | Graphics.LEFT);
		}
		
		g.setFont(m_oCanvas.m_oFontSmallB);
	}	
	
	//------------------------------------------------------
	private void DrawBlackBackGround(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		//------------------------------------------
		// X coordinate
		//------------------------------------------

		// 29 tiles height (29*25)
		// m_iMapScreenX

		int iTempX = m_iMapOffScreenX;
		if(iTempX <  0)iTempX = -25 + iTempX;
		if(iTempX >  0)iTempX = -25 + iTempX;
		if(iTempX == 0)iTempX = -25;

		if((29*25)+20 < -iTempX + (m_iMapScreenX*25) + (m_oCanvas.m_iWidth))
		{
			g.setColor(0,0,0);

			     if(m_iMapOffScreenX < 0)g.fillRect((((m_oCanvas.m_iWidth+24) / 25)*25)+m_iMapOffScreenX,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
			else if(m_iMapOffScreenX > 0)g.fillRect((((m_oCanvas.m_iWidth+24) / 25)*25)-25+m_iMapOffScreenX,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
			else                                 g.fillRect((((m_oCanvas.m_iWidth+24) / 25)*25)-25,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
			
		}

		//------------------------------------------
		// Y coordinate
		//------------------------------------------

		// 14 tiles height (14*25)
		// m_iMapScreenY

		int iTempY = m_iMapOffScreenY;
		if(iTempY <  0)iTempY = -25 + iTempY;
		if(iTempY >  0)iTempY = -25 + iTempY;
		if(iTempY == 0)iTempY = -25;

		if((15*25)+20 < -iTempY + (m_iMapScreenY*25) + (m_oCanvas.m_iHeight))
		{
			g.setColor(0,0,0);

			     if(m_iMapOffScreenY < 0)g.fillRect(0,(((m_oCanvas.m_iHeight) / 25)*25)+m_iMapOffScreenY,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
			else if(m_iMapOffScreenY > 0)g.fillRect(0,(((m_oCanvas.m_iHeight) / 25)*25)-25+m_iMapOffScreenY,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
			else                                 g.fillRect(0,(((m_oCanvas.m_iHeight) / 25)*25)-25,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
			
		}
	}

	//------------------------------------------------------
	private void DrawFrame(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int iX = (m_oCanvas.m_iWidth-14)/9;
		int iY = (m_oCanvas.m_iHeight-26)/9;
		int iXStart = 9;
		int iYStart = 9;

		if(iX < 15)
		{
			iX++;
			iXStart = 0;
		}
		if(iY < 9)
		{
			iY++;
			if(iY < 9)iY++;
			iYStart = 0;
		}

		if(5 == m_iGameState){iY++;iX++;iXStart = 0;iYStart = 0;}	
		
		for(int x=0;x < iX;x++)
		{
			for(int y=0;y < iY;y++)
			{
				g.setClip(iXStart+(x*9),iYStart+(y*9),9,9);
				
				     if(x == 0      && y == 0)     g.drawImage(m_oCanvas.m_oImageArray[3],iXStart-135+(x*9),iYStart-9+(y*9),Graphics.TOP | Graphics.LEFT);
				else if(x == (iX-1) && y == 0)     g.drawImage(m_oCanvas.m_oImageArray[3],iXStart-117+(x*9),iYStart-9+(y*9),Graphics.TOP | Graphics.LEFT);
				else if(x == (iX-1) && y == (iY-1))g.drawImage(m_oCanvas.m_oImageArray[3],iXStart-135+(x*9),iYStart+(y*9),Graphics.TOP | Graphics.LEFT);
				else if(x == 0      && y == (iY-1))g.drawImage(m_oCanvas.m_oImageArray[3],iXStart-117+(x*9),iYStart+(y*9),Graphics.TOP | Graphics.LEFT);

				else if(x == 0      && y != 0)     g.drawImage(m_oCanvas.m_oImageArray[3],iXStart-144+(x*9),iYStart+(y*9),Graphics.TOP | Graphics.LEFT);
				else if(x == (iX-1) && y != 0)     g.drawImage(m_oCanvas.m_oImageArray[3],iXStart-144+(x*9),iYStart-9+(y*9),Graphics.TOP | Graphics.LEFT);
				else if(x != 0      && y == 0)     g.drawImage(m_oCanvas.m_oImageArray[3],iXStart-126+(x*9),iYStart-9+(y*9),Graphics.TOP | Graphics.LEFT);
				else if(x != 0      && y == (iY-1))g.drawImage(m_oCanvas.m_oImageArray[3],iXStart-128+(x*9),iYStart+(y*9),Graphics.TOP | Graphics.LEFT);
			}
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
		
		g.setColor(226,208,184);
		g.fillRect(iXStart+9,iYStart+9,(iX-2)*9,(iY-2)*9);
	}

	//------------------------------------------------------
	private boolean CheckEventCollision()
	//------------------------------------------------------
	{
		for(int i=0;i < 15;i++)
		{
			if((m_iEventX[i]/25) == ((m_iShipX/25) + m_iMapScreenX) && (m_iEventY[i]/25) == ((m_iShipY/25) + m_iMapScreenY))
			{
				do
				{
					m_iEventX[i] = 25 * Math.abs(m_oCanvas.m_oRand.nextInt()%(29));
					m_iEventY[i] = 25 * Math.abs(m_oCanvas.m_oRand.nextInt()%(14));
				}
				while(MapCheckCollision((m_iEventX[i] / 25)+0,(m_iEventY[i] / 25)-0,false) == 0 || ((m_iEventX[i] / 25) == ((m_iShipX/25) + m_iMapScreenX) && (m_iEventY[i] / 25) == ((m_iShipY/25) + m_iMapScreenY)));

				return true;
			}
		}

		return false;
	}



}




