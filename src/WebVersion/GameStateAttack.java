
import javax.microedition.lcdui.*;

//------------------------------------------------------
class CGameStateAttack
//------------------------------------------------------
{
	// Public:

	// Protected:
	// Private:

	private KaperCanvas   m_oCanvas;

	private int           m_iEnemyCannons;
	private int           m_iEnemySoldiers;
	private int           m_iFriendlyCannons;
	private int           m_iFriendlyPirates;

	private int           m_iFriendlyFireFlash;
	private int           m_iEnemyFireFlash;
	private int           m_iFriendlyFireFlashX;
	private int           m_iEnemyFireFlashX;
	private int           m_iFriendlyFireFlashY;
	private int           m_iEnemyFireFlashY;

	private int           m_iEnemyBoatX;
	private int           m_iEnemyBoatY;
	private int           m_iEnemyBoatBoost;
	private int           m_iFriendlyBoatX;
	private int           m_iFriendlyBoatY;
	private int           m_iFriendlyBoatBoost;
	private int           m_iFriendlyCoolDown;
	private int           m_iEnemyCoolDown;

	private int           m_iBrainX;
	private int           m_iBrainY;
	private int           m_iBrainFire;
	private int           m_iBrainNew;

	private int           m_iEnemyYSpeed;
	private int           m_iEnemyXSpeed;
	private int           m_iEnemyCannonBallsX[];
	private int           m_iEnemyCannonBallsY[];
	private int           m_iEnemyCannonBallsXSpeed[];
	private int           m_iEnemyCannonBallsYSpeed[];
	private int           m_iEnemyCannonBallsBoost[];
	private int           m_iFriendlyCannonBallsX[];
	private int           m_iFriendlyCannonBallsY[];
	private int           m_iFriendlyCannonBallsXSpeed[];
	private int           m_iFriendlyCannonBallsYSpeed[];
	private int           m_iFriendlyCannonBallsBoost[];
	private int           m_iFriendYSpeed;
	private int           m_iFriendXSpeed;

	private int           m_iWaterSplashX[];
	private int           m_iWaterSplashY[];
	private int           m_iWaterSplashLife[];
	private int           m_iExplosionX[];
	private int           m_iExplosionY[];
	private int           m_iExplosionLife[];

	private int           m_iWaterMoveOffset;
	private boolean       m_bGameOver;
	private boolean       m_bRetreat;
	private boolean       m_bBoard;

	private boolean       m_bLeft;
	private boolean       m_bRight;
	private boolean       m_bUp;
	private boolean       m_bDown;
	private boolean       m_bFire;

	private int           m_iShipEnemyWaveA[];
	private int           m_iShipEnemyWaveX[];
	private int           m_iShipEnemyWaveY[];
	private int           m_iShipFriendlyWaveA[];
	private int           m_iShipFriendlyWaveX[];
	private int           m_iShipFriendlyWaveY[];

	private int           m_iKeyPause;

	private int           m_iFriendlyShipType;

	private boolean       m_bLocked;

	private StringBuffer stringBuffer;
	private char[] stringChars;

	//------------------------------------------------------
	public CGameStateAttack(KaperCanvas oKaperCanvas)
	//------------------------------------------------------
	{
		m_bLocked      = true;
		m_oCanvas      = oKaperCanvas;
		
		m_iShipEnemyWaveA    = new int[3];
		m_iShipEnemyWaveX    = new int[3];
		m_iShipEnemyWaveY    = new int[3];
		m_iShipFriendlyWaveA = new int[3];
		m_iShipFriendlyWaveX = new int[3];
		m_iShipFriendlyWaveY = new int[3];
		
		stringBuffer       = new StringBuffer("0000");
		stringChars        = new char[stringBuffer.length()];
		
		m_iEnemyCannonBallsX        = new int[50];
		m_iEnemyCannonBallsY        = new int[50];
		m_iEnemyCannonBallsXSpeed   = new int[50];
		m_iEnemyCannonBallsYSpeed   = new int[50];
		m_iEnemyCannonBallsBoost    = new int[50];
		m_iFriendlyCannonBallsX     = new int[50];
		m_iFriendlyCannonBallsY     = new int[50];
		m_iFriendlyCannonBallsXSpeed = new int[50];
		m_iFriendlyCannonBallsYSpeed = new int[50];
		m_iFriendlyCannonBallsBoost = new int[50];

		m_iWaterSplashX             = new int[50];
		m_iWaterSplashY             = new int[50];
		m_iWaterSplashLife          = new int[50];
		m_iExplosionX               = new int[20];
		m_iExplosionY               = new int[20];
		m_iExplosionLife            = new int[20];
	}
	
	//------------------------------------------------------
	public void Init(int iEnemiesCannons,int iEnemiesSoldier)
	//------------------------------------------------------
	{
		m_bLocked          = true;

		m_iKeyPause        = 20;

		m_iEnemyCannons    = iEnemiesCannons;
		m_iEnemySoldiers   = iEnemiesSoldier;
		m_iFriendlyCannons = m_oCanvas.m_oStateGame.m_iResourceCannon;
		m_iFriendlyPirates = m_oCanvas.m_oStateGame.m_iResourcePirate;

		m_iShipEnemyWaveA[0]    = 0;
		m_iShipEnemyWaveA[1]    = 10;
		m_iShipEnemyWaveA[2]    = 20;
		m_iShipFriendlyWaveA[0] = 0;
		m_iShipFriendlyWaveA[1] = 10;
		m_iShipFriendlyWaveA[2] = 20;

		m_iShipEnemyWaveX[0]    = -20;
		m_iShipEnemyWaveX[1]    = -20;
		m_iShipEnemyWaveX[2]    = -20;
		m_iShipFriendlyWaveX[0] = -20;
		m_iShipFriendlyWaveX[1] = -20;
		m_iShipFriendlyWaveX[2] = -20;

		for(int i=0;i < 50;i++)
		{
			m_iEnemyCannonBallsBoost[i]    = 0;
			m_iFriendlyCannonBallsBoost[i] = 0;
			m_iWaterSplashLife[i]          = 0;			

			if(i < 20)m_iExplosionLife[i] = 0;
		}

		m_iBrainFire = Math.abs(m_oCanvas.m_oRand.nextInt()%200)+10+20;
		m_iBrainX = 0;
		m_iBrainY = 0;
		m_iBrainNew = 0;

		m_iFriendlyCoolDown = 20;
		m_iEnemyCoolDown    = 20;

		m_iEnemyBoatX        = m_oCanvas.m_iWidth/2;
		m_iEnemyBoatY        =  5;
		m_iEnemyBoatBoost    = 0;
		m_iFriendlyBoatX     = m_oCanvas.m_iWidth/2;
		m_iFriendlyBoatY     = m_oCanvas.m_iHeight-25;
		m_iFriendlyBoatBoost = 0;

		m_iWaterMoveOffset   = 0;

		m_bGameOver = false;
		m_bRetreat  = false;
		m_bBoard    = false;

		m_bLeft  = false;
		m_bRight = false;
		m_bUp    = false;
		m_bDown  = false;
		m_bFire  = false;

		//m_iFriendlyShipType = (m_oCanvas.m_oStateGame.m_iResourceCannon*10) + m_oCanvas.m_oStateGame.m_iResourcePirate;
		m_iFriendlyShipType = m_oCanvas.m_oStateGame.m_iShipLvl;

		//m_oCanvas.PlaySound("snd/RangeCombat.mid",true,true);

		m_bLocked          = false;
	}
/*
	//------------------------------------------------------
	public void DeInit()
	//------------------------------------------------------
	{
		m_bLocked          = true;

		//m_oCanvas.StopSound();

		m_iEnemyCannonBallsX = null;
		m_iEnemyCannonBallsY = null;
		m_iEnemyCannonBallsXSpeed = null;
		m_iEnemyCannonBallsYSpeed = null;
		m_iEnemyCannonBallsBoost = null;
		m_iFriendlyCannonBallsX  = null;
		m_iFriendlyCannonBallsY  = null;
		m_iFriendlyCannonBallsXSpeed  = null;
		m_iFriendlyCannonBallsYSpeed  = null;
		m_iFriendlyCannonBallsBoost = null;

		m_iWaterSplashX           = null;
		m_iWaterSplashY           = null;
		m_iWaterSplashLife        = null;
		m_iExplosionX             = null;
		m_iExplosionY             = null;
		m_iExplosionLife          = null;

		m_iShipEnemyWaveA         = null;
		m_iShipEnemyWaveX         = null;
		m_iShipEnemyWaveY         = null;
		m_iShipFriendlyWaveA      = null;
		m_iShipFriendlyWaveX      = null;
		m_iShipFriendlyWaveY      = null;

		System.gc();
	}	
*/
	//------------------------------------------------------
	public void Draw(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;



//			Image oImgWater = Image.createImage(25,25);
//			oImgWater.getGraphics().drawImage(m_oCanvas.m_oImageArray[1],-165,-20,Graphics.TOP | Graphics.LEFT);
			
			// Draw DeepWater
			for(int x=0;x <= (m_oCanvas.m_iWidth/25);x++)
			{
				for(int y=-1;y <= (m_oCanvas.m_iHeight/25);y++)
				{
					g.setClip(x*25,(y*25)+m_iWaterMoveOffset,25,25);
					g.drawImage(m_oCanvas.m_oImageArray[1],(x*25)-165,(y*25)+m_iWaterMoveOffset-20,Graphics.TOP | Graphics.LEFT);
//					g.drawImage(oImgWater,(x*25),(y*25)+m_iWaterMoveOffset,Graphics.TOP | Graphics.LEFT);
				}
			}	


		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);

			m_iWaterMoveOffset++;
			if(m_iWaterMoveOffset >= 25)m_iWaterMoveOffset = 0;

			//Draw wakes
			DrawWakes(g);

			//Draw Splash
			DrawSplash(g);
	

		//Draw Boost
		DrawBoost(g);

		//Draw Boat
		DrawBoats(g);

		//Draw Shots
		DrawBalls(g);
	
			//Draw Smoke
			DrawFlash(g);

		
		//Draw Interface
		DrawInterface(g);

		g.setFont(m_oCanvas.m_oFontMediumB);
		
		if(m_bGameOver == true)
		{
			g.setColor(0,0,0);
			g.drawString("Battle over",(m_oCanvas.m_iWidth/2)-38,(m_oCanvas.m_iHeight/2),Graphics.TOP | Graphics.LEFT);
		}
		if(m_bRetreat == true)
		{
			g.setColor(0,0,0);
			g.drawString("You ran like a dog",(m_oCanvas.m_iWidth/2)-64,(m_oCanvas.m_iHeight/2),Graphics.TOP | Graphics.LEFT);
		}
		if(m_bBoard == true)
		{
			g.setColor(0,0,0);
			g.drawString("ATTACK!",(m_oCanvas.m_iWidth/2)-23,(m_oCanvas.m_iHeight/2),Graphics.TOP | Graphics.LEFT);
		}

		g.setFont(m_oCanvas.m_oFontSmallB);
	}


	//------------------------------------------------------
	public int Update()
	//------------------------------------------------------
	{
		if(m_bLocked)return 0;

		if(m_bGameOver || m_bRetreat || m_bBoard)
		{
			if(m_iKeyPause > 0)m_iKeyPause--;
			return 0;
		}

		// Retreat
		if(m_iFriendlyBoatY > m_oCanvas.m_iHeight)
		{
			m_bRetreat = true;
			return 0;
		}

		// Cooldown
		if(m_iFriendlyCoolDown > 0)m_iFriendlyCoolDown--;
		if(m_iEnemyCoolDown > 0)   m_iEnemyCoolDown--;

		// GameOver, no more soldiers
		if(m_iEnemySoldiers <= 0 || m_iFriendlyPirates <= 0)m_bGameOver = true;

		//Move ship
		Move();

		// Board Collision check
		if(m_iFriendlyBoatY < m_iEnemyBoatY+15 &&m_iFriendlyBoatY > m_iEnemyBoatY-15)
		{
			if(m_iFriendlyBoatX < m_iEnemyBoatX+15 && m_iFriendlyBoatX > m_iEnemyBoatX-15)
			{
				m_bBoard = true;
				return 0;
			}
		}

		//Update splash
		for(int i=0;i < 50;i++)
		{
			if(m_iWaterSplashLife[i] > 0)m_iWaterSplashLife[i] -= 2;
		}
		for(int i=0;i < 20;i++)
		{
			if(m_iExplosionLife[i] > 0)m_iExplosionLife[i] -= 2;
		}

		// Move balls
		for(int i=0;i < 49;i++)
		{
			if(m_iFriendlyCannonBallsBoost[i] > 1)
			{
				m_iFriendlyCannonBallsBoost[i]--;
				m_iFriendlyCannonBallsX[i] += m_iFriendlyCannonBallsXSpeed[i];
				m_iFriendlyCannonBallsY[i] += m_iFriendlyCannonBallsYSpeed[i];
			}
			else if(m_iFriendlyCannonBallsBoost[i] == 1)
			{
				boolean bHit = false;

				m_iFriendlyCannonBallsBoost[i]--;

				if((m_iFriendlyCannonBallsY[i]/10) < m_iEnemyBoatY+20 && (m_iFriendlyCannonBallsY[i]/10) > m_iEnemyBoatY)
				{
					if((m_iFriendlyCannonBallsX[i]/10) < m_iEnemyBoatX+20 && (m_iFriendlyCannonBallsX[i]/10) > m_iEnemyBoatX)
					{
						m_iEnemyCannons  -= Math.abs(m_oCanvas.m_oRand.nextInt()%3);
						m_iEnemySoldiers -= Math.abs(m_oCanvas.m_oRand.nextInt()%(15+1))+2;

						m_oCanvas.m_oStateGame.m_iScore++;

						if(m_iEnemyCannons < 0)m_iEnemyCannons   = 0;
						if(m_iEnemySoldiers < 0)m_iEnemySoldiers = 0;

						bHit = true;
					}
				}

				if(bHit == false) // Water splash
				{
					for(int iSplash=0;iSplash < 50;iSplash++)
					{
						if(m_iWaterSplashLife[iSplash] <= 0)
						{
							m_iWaterSplashLife[iSplash] = 30;
							m_iWaterSplashX[iSplash]    = (m_iFriendlyCannonBallsX[i] / 10);
							m_iWaterSplashY[iSplash]    = (m_iFriendlyCannonBallsY[i] / 10);
							break;
						}
					}

				}
				else // Explosion
				{
					for(int iSplash=0;iSplash < 20;iSplash++)
					{
						if(m_iExplosionLife[iSplash] <= 0)
						{
							m_iExplosionLife[iSplash] = 30;
							m_iExplosionX[iSplash]    = (m_iFriendlyCannonBallsX[i] / 10);
							m_iExplosionY[iSplash]    = (m_iFriendlyCannonBallsY[i] / 10);
							break;
						}
					}

				}

			}

			if(m_iEnemyCannonBallsBoost[i] > 1)
			{
				m_iEnemyCannonBallsBoost[i]--;

				m_iEnemyCannonBallsY[i] += m_iEnemyCannonBallsYSpeed[i];
				m_iEnemyCannonBallsX[i] += m_iEnemyCannonBallsXSpeed[i];
			}
			else if(m_iEnemyCannonBallsBoost[i] == 1)
			{
				boolean bHit = false;

				m_iEnemyCannonBallsBoost[i]--;

				if((m_iEnemyCannonBallsY[i]/10) < m_iFriendlyBoatY+20 && (m_iEnemyCannonBallsY[i]/10) > m_iFriendlyBoatY)
				{
					if(m_iEnemyCannonBallsX[i]/10 < m_iFriendlyBoatX+20 && m_iEnemyCannonBallsX[i]/10 > m_iFriendlyBoatX)
					{
						m_iFriendlyCannons -= Math.abs(m_oCanvas.m_oRand.nextInt()%3);
						m_iFriendlyPirates -= Math.abs(m_oCanvas.m_oRand.nextInt()%(15+1))+2;

						if(m_iFriendlyCannons < 0 )m_iFriendlyCannons   = 0;
						if(m_iFriendlyPirates < 0)m_iFriendlyPirates = 0;

						bHit = true;
					}
				}

				if(bHit == false) // Water splash
				{
					for(int iSplash=0;iSplash < 50;iSplash++)
					{
						if(m_iWaterSplashLife[iSplash] <= 0)
						{
							m_iWaterSplashLife[iSplash] = 30;
							m_iWaterSplashX[iSplash]    = (m_iEnemyCannonBallsX[i] / 10);
							m_iWaterSplashY[iSplash]    = (m_iEnemyCannonBallsY[i] / 10);
							break;
						}
					}

				}
				else // Explosion
				{
					for(int iSplash=0;iSplash < 20;iSplash++)
					{
						if(m_iExplosionLife[iSplash] <= 0)
						{
							m_iExplosionLife[iSplash] = 30;
							m_iExplosionX[iSplash]    = (m_iEnemyCannonBallsX[i] / 10);
							m_iExplosionY[iSplash]    = (m_iEnemyCannonBallsY[i] / 10);
							break;
						}
					}

				}
			}
		}

		// AI Handle
		TheBrain();

		return 0;
	}	

	//------------------------------------------------------
	public int SoftKey(int iKey)
	//------------------------------------------------------
	{
		if(m_bLocked)return 0;

		KeyCheck(iKey);

		if(m_bGameOver && m_iKeyPause <= 0)
		{
			m_oCanvas.m_oStateGame.m_iResourceCannon = m_iFriendlyCannons;
			m_oCanvas.m_oStateGame.m_iResourcePirate = m_iFriendlyPirates;

			m_oCanvas.m_oStateGame.m_iEnemyShipCannons[m_oCanvas.m_oStateGame.m_iShipNr]  = m_iEnemyCannons;
			m_oCanvas.m_oStateGame.m_iEnemyShipSoldiers[m_oCanvas.m_oStateGame.m_iShipNr] = m_iEnemySoldiers;

			return -1;
		}

		if(m_bRetreat && m_iKeyPause <= 0) // Flee
		{
			m_oCanvas.m_oStateGame.m_iResourceCannon = m_iFriendlyCannons;
			m_oCanvas.m_oStateGame.m_iResourcePirate = m_iFriendlyPirates;

			m_oCanvas.m_oStateGame.m_iEnemyShipCannons[m_oCanvas.m_oStateGame.m_iShipNr]  = m_iEnemyCannons;
			m_oCanvas.m_oStateGame.m_iEnemyShipSoldiers[m_oCanvas.m_oStateGame.m_iShipNr] = m_iEnemySoldiers;

			return -2;
		}

		if(m_bBoard && m_iKeyPause <= 0) // Board
		{
			m_oCanvas.m_oStateGame.m_iResourceCannon = m_iFriendlyCannons;
			m_oCanvas.m_oStateGame.m_iResourcePirate = m_iFriendlyPirates;

			m_oCanvas.m_oStateGame.m_iEnemyShipCannons[m_oCanvas.m_oStateGame.m_iShipNr]  = m_iEnemyCannons;
			m_oCanvas.m_oStateGame.m_iEnemyShipSoldiers[m_oCanvas.m_oStateGame.m_iShipNr] = m_iEnemySoldiers;

			return m_iEnemySoldiers;
		}

		return 0;
	}

	//------------------------------------------------------
	public void SoftKeyRelease(int iKey)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		
		
		if(iKey == Canvas.KEY_NUM5 || iKey == Canvas.KEY_NUM0 )
		{
			m_bFire  = false;

			if(m_iFriendlyBoatBoost != 0)
			{
				CalulateSpeedFriend();

				for(int iCan = 0;iCan <= (m_iFriendlyCannons/3);iCan++)
				{
					if(m_iFriendlyCannons == 0)break;
					for(int i=0;i < 49;i++)
					{
						if(m_iFriendlyCannonBallsBoost[i] <= 0)
						{
							m_iFriendlyCannonBallsX[i]     = 10 * ((m_iFriendlyBoatX+10)  + (m_oCanvas.m_oRand.nextInt()%7));
							m_iFriendlyCannonBallsY[i]     = 10 * (m_iFriendlyBoatY+10  + (m_oCanvas.m_oRand.nextInt()%7));
							m_iFriendlyCannonBallsBoost[i] = m_iFriendlyBoatBoost   + (m_oCanvas.m_oRand.nextInt()%7);

							m_iFriendlyCannonBallsYSpeed[i] = m_iFriendYSpeed;
							m_iFriendlyCannonBallsXSpeed[i] = m_iFriendXSpeed;

							break;
						}
					}
				}
				m_iFriendlyBoatBoost = 0;
				m_iFriendlyCoolDown = 30;

				m_iFriendlyFireFlash  = 10;
				m_iFriendlyFireFlashX = m_iFriendlyBoatX;
				m_iFriendlyFireFlashY = m_iFriendlyBoatY;

			}
		}		
		
		
		
		int keyState  = m_oCanvas.getGameAction(iKey);

		if((keyState == Canvas.LEFT) ){m_bLeft   = false;}
		if((keyState == Canvas.RIGHT)){m_bRight  = false;}
		if((keyState == Canvas.UP)   ){m_bUp     = false;}
		if((keyState == Canvas.DOWN) ){m_bDown   = false;}
	}

	//------------------------------------------------------
	private void DrawBalls(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		for(int i=0;i < 49;i++)
		{
			if(m_iFriendlyCannonBallsBoost[i] > 0)
			{
				g.setClip((m_iFriendlyCannonBallsX[i] / 10),(m_iFriendlyCannonBallsY[i] / 10),3,3);
				g.drawImage(m_oCanvas.m_oImageArray[3],(m_iFriendlyCannonBallsX[i] / 10)-171,(m_iFriendlyCannonBallsY[i] / 10),Graphics.TOP | Graphics.LEFT);
			}
			if(m_iEnemyCannonBallsBoost[i] > 0)
			{
				g.setClip((m_iEnemyCannonBallsX[i] / 10),(m_iEnemyCannonBallsY[i] / 10),3,3);
				g.drawImage(m_oCanvas.m_oImageArray[3],(m_iEnemyCannonBallsX[i] / 10)-171,(m_iEnemyCannonBallsY[i] / 10),Graphics.TOP | Graphics.LEFT);
			}
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
	}

	//------------------------------------------------------
	private void DrawBoost(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		// Enemy boost strengt
		g.setColor(200,20,20);
		g.fillRect(m_oCanvas.m_iWidth-9,0,m_oCanvas.m_iWidth-3,m_iEnemyBoatBoost);

		// Friendly boost strengt
		g.setColor(20,200,20);
		g.fillRect(m_oCanvas.m_iWidth-9,m_oCanvas.m_iHeight-m_iFriendlyBoatBoost,9,m_iFriendlyBoatBoost);

		// Enemy Cooldown
		g.setColor(200,200,20);
		g.fillRect(m_oCanvas.m_iWidth-9,0,m_oCanvas.m_iWidth-3,m_iEnemyCoolDown);

		// Friendly Cooldown
		g.setColor(200,200,20);
		g.fillRect(m_oCanvas.m_iWidth-9,m_oCanvas.m_iHeight-m_iFriendlyCoolDown,9,m_iFriendlyCoolDown);

		if(m_iFriendlyBoatBoost > 0)
		{
			if((m_iFriendlyBoatBoost % 5) == 1)CalulateSpeedFriend();
			
			// Bullet Target
			
			
			//g.setColor(255,0,0);
			//g.drawArc(m_iFriendlyBoatX+((m_iFriendlyBoatBoost*m_iFriendXSpeed)/10)+5,m_iFriendlyBoatY+((m_iFriendYSpeed*m_iFriendlyBoatBoost)/10),10,10,0,360);

			g.setClip(m_iFriendlyBoatX+((m_iFriendlyBoatBoost*m_iFriendXSpeed)/10)+5,m_iFriendlyBoatY+((m_iFriendYSpeed*m_iFriendlyBoatBoost)/10),16,16);		
			g.drawImage(m_oCanvas.m_oImageArray[3],(m_iFriendlyBoatX+((m_iFriendlyBoatBoost*m_iFriendXSpeed)/10)+5)-162,(m_iFriendlyBoatY+((m_iFriendYSpeed*m_iFriendlyBoatBoost)/10))-62,Graphics.TOP | Graphics.LEFT);
		}
	}

	//------------------------------------------------------
	private void DrawBoats(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		// Draw Kaper Boat
		g.setClip(m_iFriendlyBoatX,m_iFriendlyBoatY,19,19);

			
			if(2 >= m_iFriendlyShipType)
				g.drawImage(m_oCanvas.m_oImageArray[0],m_iFriendlyBoatX-140,m_iFriendlyBoatY-80,Graphics.TOP | Graphics.LEFT);
			if(2 < m_iFriendlyShipType && 4 >= m_iFriendlyShipType)
				g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlyBoatX-140,m_iFriendlyBoatY-60,Graphics.TOP | Graphics.LEFT);
			if(4 < m_iFriendlyShipType)
				g.drawImage(m_oCanvas.m_oImageArray[4],m_iFriendlyBoatX-140,m_iFriendlyBoatY,Graphics.TOP | Graphics.LEFT);

			
		// Draw Enemy Boat
		g.setClip(m_iEnemyBoatX,m_iEnemyBoatY,19,19);
		if(m_oCanvas.m_oStateGame.m_iEventOld == 12 || m_oCanvas.m_oStateGame.m_iEventOld == 13)
			g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyBoatX-140,m_iEnemyBoatY-40,Graphics.TOP | Graphics.LEFT);
		if(m_oCanvas.m_oStateGame.m_iEventOld == 14 || m_oCanvas.m_oStateGame.m_iEventOld == 15 || m_oCanvas.m_oStateGame.m_iEventOld == 18)
			g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyBoatX-140,m_iEnemyBoatY-20,Graphics.TOP | Graphics.LEFT);
		if(m_oCanvas.m_oStateGame.m_iEventOld == 16 || m_oCanvas.m_oStateGame.m_iEventOld == 17)
			g.drawImage(m_oCanvas.m_oImageArray[4],m_iEnemyBoatX-140,m_iEnemyBoatY-60,Graphics.TOP | Graphics.LEFT);

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
	}

	//------------------------------------------------------
	private void DrawInterface(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		///////////////////
		//--- PIRATES ---//
		///////////////////

		// Draw black background
//		g.setColor(0,0,0);
//		g.fillRect(0,m_oCanvas.m_iHeight-20,m_oCanvas.m_iWidth,20);

		// Draw interface-Icon

			g.setClip( 0,m_oCanvas.m_iHeight-17,14,14);
			g.drawImage(m_oCanvas.m_oImageArray[3]   ,(0)-190  ,(m_oCanvas.m_iHeight-17),Graphics.TOP | Graphics.LEFT);
			g.setClip(40,m_oCanvas.m_iHeight-17,14,14);
			g.drawImage(m_oCanvas.m_oImageArray[3]   ,(40)-175 ,(m_oCanvas.m_iHeight-17),Graphics.TOP | Graphics.LEFT);
			g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);

		// Pirates
		g.setColor(255,255,255);

			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iFriendlyPirates);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
			g.drawChars(stringChars,0,stringBuffer.length(),15,m_oCanvas.m_iHeight-15,Graphics.TOP | Graphics.LEFT);	

		// Cannons
		g.setColor(255,255,255);
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iFriendlyCannons);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
			g.drawChars(stringChars,0,stringBuffer.length(),56,m_oCanvas.m_iHeight-15,Graphics.TOP | Graphics.LEFT);	


		///////////////////
		//--- SOLDIER ---//
		///////////////////

		// Draw black background
//		g.setColor(0,0,0);
//		g.fillRect(0,0,m_oCanvas.m_iWidth,20);

			// Draw interface-Icon
			g.setClip( 0,2,14,14);
			g.drawImage(m_oCanvas.m_oImageArray[3]   ,(0)-190  ,2,Graphics.TOP | Graphics.LEFT);
			g.setClip(40,2,14,14);
			g.drawImage(m_oCanvas.m_oImageArray[3]   ,(40)-175 ,2,Graphics.TOP | Graphics.LEFT);
			g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);

		// Soldiers
		g.setColor(255,255,255);
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iEnemySoldiers);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
			g.drawChars(stringChars,0,stringBuffer.length(),15,4,Graphics.TOP | Graphics.LEFT);	


		// Cannons
		g.setColor(255,255,255);
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iEnemyCannons);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
			g.drawChars(stringChars,0,stringBuffer.length(),56,4,Graphics.TOP | Graphics.LEFT);	
	}

	//------------------------------------------------------
	private void Move()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		if(m_bLeft) {if(m_iFriendlyBoatX > 1)m_iFriendlyBoatX--;}
		if(m_bRight){if(m_iFriendlyBoatX+20 < m_oCanvas.m_iWidth)m_iFriendlyBoatX++;}
		if(m_bDown) {m_iFriendlyBoatY++;}
		if(m_bUp)   {if(m_iFriendlyBoatY > 1)m_iFriendlyBoatY--;}
		if(m_bFire && m_iFriendlyCoolDown <= 0 && m_iFriendlyCannons != 0) {m_iFriendlyBoatBoost += 2;}
	}

	//------------------------------------------------------
	private void KeyCheck(int iKey)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		if(iKey == Canvas.KEY_NUM5 || iKey == Canvas.KEY_NUM0){m_bFire  = true;}
		
		int keyState  = m_oCanvas.getGameAction(iKey);

		if((keyState == Canvas.LEFT) ){m_bLeft  = true;}
		if((keyState == Canvas.RIGHT)){m_bRight = true;}
		if((keyState == Canvas.UP) )  {m_bUp    = true;}
		if((keyState == Canvas.DOWN)) {m_bDown  = true;}
//		if((keyState == Canvas.FIRE) ){m_bFire  = true;}
	}

	//------------------------------------------------------
	private void TheBrain()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		if(m_iBrainX == m_iEnemyBoatX && m_iBrainY == m_iEnemyBoatY)
		{
			if(Math.abs(m_oCanvas.m_oRand.nextInt()%(30)) == 0)
			{
				m_iBrainNew = 0;
			}
		}

		if(m_iBrainNew <= 0 && m_iEnemyCannons <= 0)
		{
				m_iBrainX   = m_iFriendlyBoatX + (m_oCanvas.m_oRand.nextInt()%5);
				m_iBrainY   = m_iFriendlyBoatY + (m_oCanvas.m_oRand.nextInt()%5);
				m_iBrainNew = Math.abs(m_oCanvas.m_oRand.nextInt()%100);
		}

		else if(m_iBrainNew <= 0)
		{
			if(Math.abs(m_oCanvas.m_oRand.nextInt()%(2)) == 0)
			{
				m_iBrainX   = Math.abs(m_oCanvas.m_oRand.nextInt()%(m_oCanvas.m_iWidth-20));
				m_iBrainY   = 20 + Math.abs((m_oCanvas.m_oRand.nextInt()%50));
			}
			else
			{
				m_iBrainX   = m_iFriendlyBoatX + (m_oCanvas.m_oRand.nextInt()%8);
				m_iBrainY   = 20 + Math.abs((m_oCanvas.m_oRand.nextInt()%(1+m_iFriendlyBoatY)));
			}
			//m_iBrainY   = 20 + Math.abs(m_oCanvas.m_oRand.nextInt()%40);
			m_iBrainNew = Math.abs(m_oCanvas.m_oRand.nextInt()%150) + 50;
		}

		if(m_iBrainFire <= 0 && m_iEnemyCoolDown <= 0 && m_iEnemyCannons != 0) // Fire shot
		{
			m_iBrainFire = Math.abs(m_oCanvas.m_oRand.nextInt()%200)+10;

			CalulateSpeedEnemy();

			for(int iCan = 0;iCan <= (m_iEnemyCannons/3);iCan++)
			{
				for(int i=0;i < 49;i++)
				{
					if(m_iEnemyCannonBallsBoost[i] <= 0)
					{
						m_iEnemyCannonBallsX[i]      = 10 * ((m_iEnemyBoatX+10) + (m_oCanvas.m_oRand.nextInt()%7));
						m_iEnemyCannonBallsY[i]      = 10 * (m_iEnemyBoatY+10 + (m_oCanvas.m_oRand.nextInt()%7));
						m_iEnemyCannonBallsBoost[i]  = m_iEnemyBoatBoost + (m_oCanvas.m_oRand.nextInt()%7);

						m_iEnemyCannonBallsYSpeed[i] = m_iEnemyYSpeed;
						m_iEnemyCannonBallsXSpeed[i] = m_iEnemyXSpeed;

						break;
					}
				}
			}
			m_iEnemyBoatBoost = 0;
			m_iEnemyCoolDown  = 30;

			m_iEnemyFireFlash  = 10;
			m_iEnemyFireFlashX = m_iEnemyBoatX;
			m_iEnemyFireFlashY = m_iEnemyBoatY;
		}

		int iDistance,iYDistance = 0,iXDistance = 0;
		if(m_iEnemyBoatY < m_iFriendlyBoatY){iYDistance = m_iFriendlyBoatY - m_iEnemyBoatY;}
		if(m_iEnemyBoatY > m_iFriendlyBoatY){iYDistance = m_iEnemyBoatY    - m_iFriendlyBoatY;}
		if(m_iEnemyBoatX < m_iFriendlyBoatX){iXDistance = m_iFriendlyBoatX - m_iEnemyBoatX;}
		if(m_iEnemyBoatX > m_iFriendlyBoatX){iXDistance = m_iEnemyBoatX    - m_iFriendlyBoatX;}

		if(iYDistance < iXDistance)
			iDistance = (iXDistance + (5 * (iYDistance / 16)));
		else
			iDistance =  (iYDistance + (5 * (iXDistance / 16)));


		if(((m_iEnemyBoatBoost*3)) > (iDistance-10))
		{
			if(Math.abs(m_oCanvas.m_oRand.nextInt()%(2)) == 0)m_iBrainFire = 0;
		}

		m_iBrainNew--;
		m_iBrainFire--;
		if(m_iEnemyCoolDown <= 0 && m_iEnemyCannons != 0)m_iEnemyBoatBoost += 2;

		if(m_iBrainX < m_iEnemyBoatX)m_iEnemyBoatX--;
		if(m_iBrainX > m_iEnemyBoatX)m_iEnemyBoatX++;
		if(m_iBrainY < m_iEnemyBoatY)m_iEnemyBoatY--;
		if(m_iBrainY > m_iEnemyBoatY)m_iEnemyBoatY++;

	}

	//------------------------------------------------------
	private void DrawSplash(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		for(int iSplash=0;iSplash < 50;iSplash++)
		{
			if(m_iWaterSplashLife[iSplash] > 0)
			{
				g.setClip(m_iWaterSplashX[iSplash],m_iWaterSplashY[iSplash],10,10);
				
				     if(m_iWaterSplashLife[iSplash] > 20)g.drawImage(m_oCanvas.m_oImageArray[0],m_iWaterSplashX[iSplash]-7 ,m_iWaterSplashY[iSplash]-62,Graphics.TOP | Graphics.LEFT);
				else if(m_iWaterSplashLife[iSplash] > 10)g.drawImage(m_oCanvas.m_oImageArray[0],m_iWaterSplashX[iSplash]-18,m_iWaterSplashY[iSplash]-62,Graphics.TOP | Graphics.LEFT);
				else if(m_iWaterSplashLife[iSplash] >  0)g.drawImage(m_oCanvas.m_oImageArray[0],m_iWaterSplashX[iSplash]-29,m_iWaterSplashY[iSplash]-62,Graphics.TOP | Graphics.LEFT);
			}
		}

		for(int iSplash=0;iSplash < 20;iSplash++)
		{
			if(m_iExplosionLife[iSplash] > 0)
			{
				g.setClip(m_iExplosionX[iSplash],m_iExplosionY[iSplash],10,10);

				     if(m_iExplosionLife[iSplash] > 20)g.drawImage(m_oCanvas.m_oImageArray[3],m_iExplosionX[iSplash]-106,m_iExplosionY[iSplash],Graphics.TOP | Graphics.LEFT);
				else if(m_iExplosionLife[iSplash] > 10)g.drawImage(m_oCanvas.m_oImageArray[3],m_iExplosionX[iSplash]-95,m_iExplosionY[iSplash],Graphics.TOP | Graphics.LEFT);
				else if(m_iExplosionLife[iSplash] >  0)g.drawImage(m_oCanvas.m_oImageArray[3],m_iExplosionX[iSplash]-84,m_iExplosionY[iSplash],Graphics.TOP | Graphics.LEFT);
			}
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
	}

	//------------------------------------------------------
	private void DrawWakes(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int iGraphic = 0;

		// Enemy wakes
		for(int i=0;i < 3;i++)
		{
			m_iShipEnemyWaveA[i]--;
			if(m_iShipEnemyWaveA[i] < 0)
			{
				m_iShipEnemyWaveA[i] = 30;
				m_iShipEnemyWaveX[i] = m_iEnemyBoatX;
				m_iShipEnemyWaveY[i] = m_iEnemyBoatY;
			}

			if(m_iShipEnemyWaveA[i] > 25)continue;

			     if(m_iShipEnemyWaveA[i] <  6)iGraphic = 3;
			else if(m_iShipEnemyWaveA[i] < 12)iGraphic = 2;
			else if(m_iShipEnemyWaveA[i] < 18)iGraphic = 1;
			else if(m_iShipEnemyWaveA[i] < 25)iGraphic = 0;

    		g.setClip(m_iShipEnemyWaveX[i],m_iShipEnemyWaveY[i],19,19);
    			
			if(iGraphic == 0)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipEnemyWaveX[i]-100 ,m_iShipEnemyWaveY[i]-60,Graphics.TOP | Graphics.LEFT);
			if(iGraphic == 1)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipEnemyWaveX[i]-80 ,m_iShipEnemyWaveY[i]-60,Graphics.TOP | Graphics.LEFT);
			if(iGraphic == 2)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipEnemyWaveX[i]-60 ,m_iShipEnemyWaveY[i]-60,Graphics.TOP | Graphics.LEFT);
			if(iGraphic == 3)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipEnemyWaveX[i]-40 ,m_iShipEnemyWaveY[i]-60 ,Graphics.TOP | Graphics.LEFT);
		}

		// Friendly wakes
		for(int i=0;i < 3;i++)
		{
			m_iShipFriendlyWaveA[i]--;
			if(m_iShipFriendlyWaveA[i] < 0)
			{
				m_iShipFriendlyWaveA[i] = 30;
				m_iShipFriendlyWaveX[i] = m_iFriendlyBoatX;
				m_iShipFriendlyWaveY[i] = m_iFriendlyBoatY;
			}

			if(m_iShipFriendlyWaveA[i] > 25)continue;

			     if(m_iShipFriendlyWaveA[i] <  6)iGraphic = 3;
			else if(m_iShipFriendlyWaveA[i] < 12)iGraphic = 2;
			else if(m_iShipFriendlyWaveA[i] < 18)iGraphic = 1;
			else if(m_iShipFriendlyWaveA[i] < 25)iGraphic = 0;

    		g.setClip(m_iShipFriendlyWaveX[i],m_iShipFriendlyWaveY[i],19,19);			     
			     
			if(iGraphic == 0)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipFriendlyWaveX[i]-100 ,m_iShipFriendlyWaveY[i]-60,Graphics.TOP | Graphics.LEFT);
			if(iGraphic == 1)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipFriendlyWaveX[i]-80 ,m_iShipFriendlyWaveY[i] -60,Graphics.TOP | Graphics.LEFT);
			if(iGraphic == 2)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipFriendlyWaveX[i]-60 ,m_iShipFriendlyWaveY[i] -60,Graphics.TOP | Graphics.LEFT);
			if(iGraphic == 3)g.drawImage(m_oCanvas.m_oImageArray[0],m_iShipFriendlyWaveX[i]-40 ,m_iShipFriendlyWaveY[i] -60,Graphics.TOP | Graphics.LEFT);
		}
		
   		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);		
	}

	//------------------------------------------------------
	private void DrawFlash(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		//Friendly
		if(m_iFriendlyFireFlash > 0)
		{
    		g.setClip(m_iFriendlyFireFlashX,m_iFriendlyFireFlashY-5,20,20);	
			    		
			if(m_iFriendlyFireFlash >  9 && m_iFriendlyFireFlash <= 10)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlyFireFlashX-63,m_iFriendlyFireFlashY-5,Graphics.TOP | Graphics.LEFT);
			if(m_iFriendlyFireFlash >  6 && m_iFriendlyFireFlash <=  9)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlyFireFlashX-42,m_iFriendlyFireFlashY-5,Graphics.TOP | Graphics.LEFT);
			if(m_iFriendlyFireFlash >  3 && m_iFriendlyFireFlash <=  6)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlyFireFlashX-21,m_iFriendlyFireFlashY-5,Graphics.TOP | Graphics.LEFT);
			if(m_iFriendlyFireFlash >  0 && m_iFriendlyFireFlash <=  3)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlyFireFlashX  ,m_iFriendlyFireFlashY-5,Graphics.TOP | Graphics.LEFT);

			m_iFriendlyFireFlash--;
		}

		//Enemy
		if(m_iEnemyFireFlash > 0)
		{
    		g.setClip(m_iEnemyFireFlashX,m_iEnemyFireFlashY+5,20,20);	
			
			if(m_iEnemyFireFlash >  9 && m_iEnemyFireFlash <= 10)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemyFireFlashX-63,m_iEnemyFireFlashY+5,Graphics.TOP | Graphics.LEFT);
			if(m_iEnemyFireFlash >  6 && m_iEnemyFireFlash <=  9)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemyFireFlashX-42,m_iEnemyFireFlashY+5,Graphics.TOP | Graphics.LEFT);
			if(m_iEnemyFireFlash >  3 && m_iEnemyFireFlash <=  6)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemyFireFlashX-21,m_iEnemyFireFlashY+5,Graphics.TOP | Graphics.LEFT);
			if(m_iEnemyFireFlash >  0 && m_iEnemyFireFlash <=  3)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemyFireFlashX  ,m_iEnemyFireFlashY+5,Graphics.TOP | Graphics.LEFT);

			m_iEnemyFireFlash--;
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);	
	}

	//------------------------------------------------------
	private void CalulateSpeedEnemy()
	//------------------------------------------------------
	{
		int iXDistance   = 0;
		int iYDistance   = 0;
		int iDistance    = 0;

		if(m_iEnemyBoatY < m_iFriendlyBoatY){iYDistance = m_iFriendlyBoatY - m_iEnemyBoatY;}
		if(m_iEnemyBoatY > m_iFriendlyBoatY){iYDistance = m_iEnemyBoatY    - m_iFriendlyBoatY;}
		if(m_iEnemyBoatX < m_iFriendlyBoatX){iXDistance = m_iFriendlyBoatX - m_iEnemyBoatX;}
		if(m_iEnemyBoatX > m_iFriendlyBoatX){iXDistance = m_iEnemyBoatX    - m_iFriendlyBoatX;}

		if(iYDistance < iXDistance)
			iDistance = (iXDistance + (5 * (iYDistance / 16)));
		else
			iDistance =  (iYDistance + (5 * (iXDistance / 16)));

		m_iEnemyXSpeed = (((m_iFriendlyBoatX - m_iEnemyBoatX)*6) / iDistance);
		m_iEnemyYSpeed = (((m_iFriendlyBoatY - m_iEnemyBoatY)*30) / iDistance);

		if(m_iEnemyYSpeed > 0  && m_iEnemyYSpeed < 15)
		{
			m_iEnemyYSpeed =  15;
		}
		if(m_iEnemyYSpeed <= 0 && m_iEnemyYSpeed > -15)
		{
			m_iEnemyYSpeed = -15;
		}
	}

	//------------------------------------------------------
	private void CalulateSpeedFriend()
	//------------------------------------------------------
	{
		int iXDistance   = 0;
		int iYDistance   = 0;
		int iDistance    = 0;

		if(m_iEnemyBoatY < m_iFriendlyBoatY){iYDistance = m_iFriendlyBoatY - m_iEnemyBoatY;}
		if(m_iEnemyBoatY > m_iFriendlyBoatY){iYDistance = m_iEnemyBoatY    - m_iFriendlyBoatY;}
		if(m_iEnemyBoatX < m_iFriendlyBoatX){iXDistance = m_iFriendlyBoatX - m_iEnemyBoatX;}
		if(m_iEnemyBoatX > m_iFriendlyBoatX){iXDistance = m_iEnemyBoatX    - m_iFriendlyBoatX;}

		if(iYDistance < iXDistance)
			iDistance = (iXDistance + (5 * (iYDistance / 16)));
		else
			iDistance =  (iYDistance + (5 * (iXDistance / 16)));

		m_iFriendXSpeed = (((m_iEnemyBoatX - m_iFriendlyBoatX)*6) / iDistance);
		m_iFriendYSpeed = (((m_iEnemyBoatY - m_iFriendlyBoatY)*30) / iDistance);


		if(m_iFriendYSpeed > 0  && m_iFriendYSpeed < 15)
		{
			m_iFriendYSpeed =  15;
		}
		if(m_iFriendYSpeed <= 0 && m_iFriendYSpeed > -15)
		{
			m_iFriendYSpeed = -15;
		}
	}
}

