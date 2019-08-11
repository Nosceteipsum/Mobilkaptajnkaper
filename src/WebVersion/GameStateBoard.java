
import javax.microedition.lcdui.*;

//------------------------------------------------------
class CGameStateBoard
//------------------------------------------------------
{
	// Public:

	// Protected:
	// Private:

	private KaperCanvas   m_oCanvas;
	private int           m_iEnemySoldier;
	private int           m_iFriendlySoldier;

	private boolean       m_bAllowToShot;
	private boolean       m_bFriendlyLeaderPause8;
	private boolean       m_bFriendlyLeaderPause2;
	private boolean       m_bFriendlyLeaderPause4;
	private boolean       m_bFriendlyLeaderPause6;
	private boolean       m_bEnemyLeaderPause8;
	private boolean       m_bEnemyLeaderPause4;
	private boolean       m_bEnemyLeaderPause2;
	private boolean       m_bEnemyLeaderPause6;

	private int           m_iFriendlyMovePause;
	private int           m_iEnemyMovePause;
	private int           m_iFriendlyFireFlash;
	private int           m_iEnemyFireFlash;
	private int           m_iFriendlyCoolDown;
	private int           m_iEnemyCoolDown;

	private int           m_iFriendlyLeaderX;
	private int           m_iFriendlyLeaderY;
	private int           m_iFriendlySoldierFight[];
	private int           m_iFriendlySoldierX[];
	private int           m_iFriendlySoldierY[];
	private boolean       m_bFriendlySoldierDead[];
	private int           m_iFriendlySoldierDeadAnim[];
	private int           m_iEnemySoldierFight[];
	private int           m_iEnemyLeaderX;
	private int           m_iEnemyLeaderY;
	private int           m_iEnemySoldierX[];
	private int           m_iEnemySoldierY[];
	private boolean       m_bEnemySoldierDead[];
	private int           m_iEnemySoldierDeadAnim[];
	private boolean       m_bGameOver;
	public boolean       m_bRetreat;

	private int			  m_iDifficult;

	private int           m_iFriendlyBulletX[];
	private int           m_iFriendlyBulletY[];
	private boolean       m_bFriendlyBulletDead[];
	private int           m_iEnemyBulletX[];
	private int           m_iEnemyBulletY[];
	private boolean       m_bEnemyBulletDead[];

	private int           m_iObjectType[];
	private int           m_iObjectX[];
	private int           m_iObjectY[];

	private int           m_iFriendlyBezerk;
	private int           m_iEnemyBezerk;

	private int           m_iEnemyBullets;
	private int           m_iFriendlyBullets;

	private int           m_iFriendlyAnim;
	private int           m_iEnemyAnim;

	private boolean       m_bLeft;
	private boolean       m_bRight;
	private boolean       m_bUp;
	private boolean       m_bDown;

	private	boolean       m_bAILeft;
	private	boolean       m_bAIRight;
	private	boolean       m_bAIUp;
	private	boolean       m_bAIDown;
	private int           m_iAIAimTargets;
	private int           m_iAIFlee;

	private boolean       m_bLocked;

	private int           m_iKeyPause;

	private StringBuffer stringBuffer;
	private char[] stringChars;

	private char m_iNumberOfMenToCreate;
	
	//------------------------------------------------------
	public CGameStateBoard(KaperCanvas oKaperCanvas)
	//------------------------------------------------------
	{
		m_bLocked      = true;
		m_oCanvas      = oKaperCanvas;
		
		// Create array for every men
		m_iFriendlySoldierX        = new int[12*3];
		m_iFriendlySoldierY        = new int[12*3];
		m_bFriendlySoldierDead     = new boolean[12];
		m_iFriendlySoldierDeadAnim = new int[12];
		m_iFriendlySoldierFight    = new int[12];

		m_iEnemySoldierX           = new int[12*3];
		m_iEnemySoldierY           = new int[12*3];
		m_bEnemySoldierDead        = new boolean[12];
		m_iEnemySoldierDeadAnim    = new int[12];
		m_iEnemySoldierFight       = new int[12];

		// Create array for every Objects
		m_iObjectType              = new int[5];
		m_iObjectX                 = new int[5];
		m_iObjectY                 = new int[5];
		
		stringBuffer       = new StringBuffer("0000");
		stringChars        = new char[stringBuffer.length()];

		// Create array for every Bullets
		m_iFriendlyBulletX    = new int[18];
		m_iFriendlyBulletY    = new int[18];
		m_bFriendlyBulletDead = new boolean[18];
		m_iEnemyBulletX       = new int[18];
		m_iEnemyBulletY       = new int[18];
		m_bEnemyBulletDead    = new boolean[18];		
	}
	
	//------------------------------------------------------
	public void Init(int iEnemiesSoldier)
	//------------------------------------------------------
	{
		m_bLocked          = true;

		m_iNumberOfMenToCreate = 2;
		
		m_iKeyPause        = 20;

		// Get number of Men
  		m_iEnemySoldier    = iEnemiesSoldier;
		m_iFriendlySoldier = m_oCanvas.m_oStateGame.m_iResourcePirate;		

		int iHeight = (m_oCanvas.m_iHeight / 20)-2; // Number of platforms
		
		if(iHeight == 2)m_iNumberOfMenToCreate = 4;
		if(iHeight == 3)m_iNumberOfMenToCreate = 4;
		if(iHeight == 4)m_iNumberOfMenToCreate = 6;
		if(iHeight == 5)m_iNumberOfMenToCreate = 10;	
		if(iHeight >= 6)m_iNumberOfMenToCreate = 12;
		
		m_bAllowToShot = false;
		

		

		for(int i = 0;i < 12;i++)
		{
			m_bFriendlySoldierDead[i] = true;
			m_bEnemySoldierDead[i]    = true;

			m_iEnemySoldierX[i]       = -30;
			m_iFriendlySoldierX[i]    = -30;
		}

		for(int i = 0;i < 2;i++) // (0) 1 2 4
		{
			if(iHeight <= 4 && i != 0){m_iObjectType[i] = 0;continue;}
			
			m_iObjectType[i] = Math.abs(m_oCanvas.m_oRand.nextInt()%4);
			if(m_iObjectType[i] == 3)m_iObjectType[i] = 4;

			m_iObjectX[i]    = 2+Math.abs(m_oCanvas.m_oRand.nextInt()%((m_oCanvas.m_iWidth  / 20)-4));
			m_iObjectY[i]    = 3+Math.abs(m_oCanvas.m_oRand.nextInt()%((m_oCanvas.m_iHeight / 20)-3));
		}

		for(int i = 2;i < 5;i++) // (0) 3 5 6
		{
			m_iObjectType[i] = Math.abs(m_oCanvas.m_oRand.nextInt()%4);

			if(m_iObjectType[i] == 1)m_iObjectType[i] = 5;
			if(m_iObjectType[i] == 2)m_iObjectType[i] = 6;

			m_iObjectX[i]    = 2+Math.abs(m_oCanvas.m_oRand.nextInt()%((m_oCanvas.m_iWidth  / 20)-4));
			m_iObjectY[i]    = 4+Math.abs(m_oCanvas.m_oRand.nextInt()%((m_oCanvas.m_iHeight / 20)-2));
		}

		for(int i = 0;i < 5;i++)
		{
			for(int ii = 0;ii < 5;ii++)
			{
				if(i == ii)continue;

				if(m_iObjectX[i] == m_iObjectX[ii] && m_iObjectY[i] == m_iObjectY[ii])
				{
					m_iObjectY[i] = -5;
					m_iObjectType[i] = 0;
				}
			}
		}

		m_iFriendlyCoolDown = 0;
		m_iEnemyCoolDown    = 0;

		for(int i = 0;i < 18;i++)
		{
			m_bFriendlyBulletDead[i] = true;m_bEnemyBulletDead[i] = true;
		}

		m_iFriendlyAnim = 10;
		m_iEnemyAnim    = 10;

		CreateSoldier(true,true);

		m_bRetreat  = false;
		m_bGameOver = false;

		m_bAILeft   = false;
		m_bAIRight  = false;
		m_bAIUp     = false;
		m_bAIDown   = false;

		m_iFriendlyBezerk = 0;
		m_iEnemyBezerk    = 0;

		//-----------------
		// Corpses
		//-----------------
		for(int i = 12;i < 12*3;i++)
		{
			m_iEnemySoldierX[i] = -50;
			m_iFriendlySoldierX[i] = -50;
			m_iEnemySoldierY[i] = -50;
			m_iFriendlySoldierY[i] = -50;
		}

		m_bLocked          = false;
	}	

	//------------------------------------------------------
	public void DeInit()
	//------------------------------------------------------
	{
		m_bLocked          = true;

		//m_oCanvas.StopSound();
/*
		m_iFriendlySoldierFight    = null;
		m_iFriendlySoldierX        = null;
		m_iFriendlySoldierY        = null;
		m_bFriendlySoldierDead     = null;
		m_iFriendlySoldierDeadAnim = null;
		m_iEnemySoldierFight       = null;
		m_iEnemySoldierX           = null;
		m_iEnemySoldierY           = null;
		m_bEnemySoldierDead        = null;
		m_iEnemySoldierDeadAnim    = null;

		m_iFriendlyBulletX    = null;
		m_iFriendlyBulletY    = null;
		m_bFriendlyBulletDead = null;
		m_iEnemyBulletX       = null;
		m_iEnemyBulletY       = null;
		m_bEnemyBulletDead    = null;

		m_iObjectType = null;
		m_iObjectX    = null;
		m_iObjectY    = null;

		System.gc();
*/
	}	

	//------------------------------------------------------
	public void Draw(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

			
			//Draw Background
			DrawBackGround(g);
			
		
		//Draw Cooldown
		DrawCooldown(g);
		
		//Draw Men
		DrawMen(g);

		//Draw Smoke
		DrawFlash(g);

		//Draw Bullets
		DrawBullets(g);

		//Draw Text
		DrawText(g);

		//----------------------
		// AI Handling
		//----------------------
		//HandleAI(g);
	}

	//------------------------------------------------------
	public int Update()
	//------------------------------------------------------
	{
		if(m_bLocked)return 0;

		int i;

		//----------------------
		// Dead Animation
		//----------------------
		for(i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_iEnemySoldierDeadAnim[i] < 5)m_iEnemySoldierDeadAnim[i]++;
			if(m_iFriendlySoldierDeadAnim[i] < 5)m_iFriendlySoldierDeadAnim[i]++;
		}

		if(m_bGameOver || m_bRetreat)
		{
			if(m_iKeyPause > 0)m_iKeyPause--;
			return 0;
		}
		//----------------------
		// GameOver
		//----------------------
		if(m_iEnemySoldier <= 0 || m_iFriendlySoldier <= 0)
		{
			m_bGameOver = true;
			return 0;
		}

		//----------------------
		// Cooldown
		//----------------------
		if(m_iFriendlyCoolDown > 0)m_iFriendlyCoolDown--;
		if(m_iEnemyCoolDown > 0)   m_iEnemyCoolDown--;

		//----------------------
		// Check if time to spawn new soldiers
		//----------------------
		boolean bAllDead = true;
		for(i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bFriendlySoldierDead[i] == false){bAllDead =false;break;}
		}
		
		if(bAllDead)CreateSoldier(true,false);

		bAllDead = true;

		for(i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bEnemySoldierDead[i] == false){bAllDead =false;break;}
		}
		
		if(bAllDead)CreateSoldier(false,true);

		//----------------------
		// AI Handling
		//----------------------
		HandleAI();

		//----------------------
		// Move men to Command Point
		//----------------------
		MoveToCommandPoint();

		//----------------------
		// Check for Retreat
		//----------------------
		if(m_iFriendlyLeaderX < -20)
			m_bRetreat = true;

		//----------------------
		// Moving Men
		//----------------------

		if(m_iFriendlyMovePause > 0)m_iFriendlyAnim-=2;

		boolean bLegalMoving;
		boolean bOnly1Move = true;

		if(m_bFriendlyLeaderPause4 == false && m_bLeft && m_iFriendlyMovePause <= 0 && m_iFriendlyFireFlash <= 0)
		{
			if(bOnly1Move){m_iFriendlyAnim-=2;bOnly1Move=false;}
			if(m_iFriendlyBullets > 0 || m_iFriendlyBezerk > 0)m_iFriendlyLeaderX--; 
			else                      m_iFriendlyLeaderX-= 2;
		}

		if(m_bFriendlyLeaderPause6 == false && m_bRight && m_iFriendlyMovePause <= 0 && m_iFriendlyFireFlash <= 0)
		{
			if(bOnly1Move){m_iFriendlyAnim-=2;bOnly1Move=false;}

			bLegalMoving = true;

			for(i = 0;i < 12;i++)
			{
				if(m_bFriendlySoldierDead[i] == true)continue;
				if(m_iFriendlySoldierX[i] >= (m_oCanvas.m_iWidth-20))bLegalMoving = false;
			}

			if(bLegalMoving)
			{
				if(m_iFriendlyBullets > 0 || m_iFriendlyBezerk > 0)m_iFriendlyLeaderX++;
				else                      m_iFriendlyLeaderX+= 2;
			}
		}

		if(m_bFriendlyLeaderPause8 == false && m_bUp && m_iFriendlyMovePause <= 0 && m_iFriendlyFireFlash <= 0)
		{
			if(bOnly1Move){m_iFriendlyAnim-=2;bOnly1Move=false;}

			bLegalMoving = true;

			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(m_bFriendlySoldierDead[i] == true)continue;
//				if(m_iFriendlySoldierY[i] <= (20*3))bLegalMoving = false;
			}

			if(bLegalMoving && m_iFriendlyLeaderY > (20*2)-50)
			{
				if(m_iFriendlyBullets > 0 || m_iFriendlyBezerk > 0)m_iFriendlyLeaderY--;
				else                      m_iFriendlyLeaderY-= 2;
			}
		}

		if(m_bFriendlyLeaderPause2 == false && m_bDown && m_iFriendlyMovePause <= 0 && m_iFriendlyFireFlash <= 0)
		{
			if(bOnly1Move){m_iFriendlyAnim-=2;bOnly1Move=false;}

			bLegalMoving = true;

			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(m_bFriendlySoldierDead[i] == true)continue;
//				if(m_iFriendlySoldierY[i] >= (m_oCanvas.m_iHeight-20))bLegalMoving = false;
			}


			if(bLegalMoving && m_iFriendlyLeaderY < (m_oCanvas.m_iHeight-20))
			{
				if(m_iFriendlyBullets > 0 || m_iFriendlyBezerk > 0)m_iFriendlyLeaderY++;
				else                      m_iFriendlyLeaderY+= 2;
			}
		}

		//----------------------
		// Bullets
		//----------------------
		for(i=0;i < 18;i++)
		{
			// Friendly
			if(m_iFriendlyBulletX[i] > m_oCanvas.m_iWidth)m_bFriendlyBulletDead[i] = true;

			if(m_bFriendlyBulletDead[i] == false)
			{
				m_iFriendlyBulletX[i] += 3;
			}

			// Enemy
			if(m_bEnemyBulletDead[i] == false)
			{
				m_iEnemyBulletX[i] -= 3;
			}		

			if(m_iEnemyBulletX[i] < -10)m_bEnemyBulletDead[i] = true;

		}

		//----------------------
		// Collision Check - Fighting Scan
		//----------------------
		boolean bFighting;

		for(i=0;i < m_iNumberOfMenToCreate;i++)
		{
			bFighting = false;

			if(m_iFriendlySoldierFight[i] == 0)  continue;
			if(m_bFriendlySoldierDead[i] == true)continue;

			for(int ii=0;ii < m_iNumberOfMenToCreate;ii++)
			{
				if(m_bEnemySoldierDead[ii]   == true)continue;

				if(m_iFriendlySoldierY[i] < m_iEnemySoldierY[ii]+10 && m_iFriendlySoldierY[i] > m_iEnemySoldierY[ii]-5)
				{
					if(m_iFriendlySoldierX[i] < m_iEnemySoldierX[ii]+10 && m_iFriendlySoldierX[i] > m_iEnemySoldierX[ii]-5)
					{
						bFighting = true;
					}
				}
			}

			if(bFighting == false)m_iFriendlySoldierFight[i] = 0;
		}

		for(i=0;i < m_iNumberOfMenToCreate;i++)
		{
			bFighting = false;

			if(m_iEnemySoldierFight[i] == 0)  continue;
			if(m_bEnemySoldierDead[i] == true)continue;

			for(int ii=0;ii < m_iNumberOfMenToCreate;ii++)
			{
				if(m_bFriendlySoldierDead[ii]   == true)continue;

				if(m_iFriendlySoldierY[ii] < m_iEnemySoldierY[i]+10 && m_iFriendlySoldierY[ii] > m_iEnemySoldierY[i]-5)
				{
					if(m_iFriendlySoldierX[ii] < m_iEnemySoldierX[i]+10 && m_iFriendlySoldierX[ii] > m_iEnemySoldierX[i]-5)
					{
						bFighting = true;
					}
				}
			}

			if(bFighting == false)m_iEnemySoldierFight[i] = 0;
		}

		//----------------------
		// Collision Check - Men
		//----------------------

		for(i=0;i < m_iNumberOfMenToCreate;i++)
		{
			for(int ii=0;ii < m_iNumberOfMenToCreate;ii++)
			{
				if(m_bEnemySoldierDead[ii]   == true)continue;
				if(m_bFriendlySoldierDead[i] == true)continue;

				if(m_iFriendlySoldierY[i] < m_iEnemySoldierY[ii]+10 && m_iFriendlySoldierY[i] > m_iEnemySoldierY[ii]-5)
				{
					if(m_iFriendlySoldierX[i] < m_iEnemySoldierX[ii]+10 && m_iFriendlySoldierX[i] > m_iEnemySoldierX[ii]-5)
					{
						int iRand = Math.abs(m_oCanvas.m_oRand.nextInt()%150);
						if(iRand == 1)
						{
							m_iFriendlySoldierFight[i]   = 0;
							m_iEnemySoldierFight[ii]     = 0;

							m_bFriendlySoldierDead[i] = true;
							m_iFriendlySoldierDeadAnim[i] = 0;

							m_iFriendlySoldier -= 1;
							if(m_iFriendlySoldier < 0)m_iFriendlySoldier = 0;
						}
						else if(iRand == 2)
						{
							m_iFriendlySoldierFight[i]   = 0;
							m_iEnemySoldierFight[ii]     = 0;

							m_iEnemySoldierDeadAnim[ii]   = 0;
							m_bEnemySoldierDead[ii]   = true;

							m_oCanvas.m_oStateGame.m_iScore++;

							m_iEnemySoldier -= 1;
							if(m_iEnemySoldier < 0)m_iEnemySoldier = 0;
						}
						else
						{
							m_iEnemySoldierFight[ii]++;
							m_iFriendlySoldierFight[i]++;

							if(m_iEnemySoldierFight[ii]   > 5)m_iEnemySoldierFight[ii]   = 1;
							if(m_iFriendlySoldierFight[i] > 5)m_iFriendlySoldierFight[i] = 1;
						}
					}
				}
			}
		}
		//----------------------
		// Collision Check - Bullets Friendly
		//----------------------
		for(i=0;i < 18;i++)
		{
			if(m_bFriendlyBulletDead[i] == true)continue;

			// Soldiers
			for(int ii=0;ii < m_iNumberOfMenToCreate;ii++)
			{
				if(m_bEnemySoldierDead[ii] == true)continue;

				if(m_iFriendlyBulletY[i] < m_iEnemySoldierY[ii]+14 && m_iFriendlyBulletY[i] > m_iEnemySoldierY[ii])
				{
					if(m_iFriendlyBulletX[i] < m_iEnemySoldierX[ii]+12 && m_iFriendlyBulletX[i] > m_iEnemySoldierX[ii])
					{
						m_bFriendlyBulletDead[i] = true;
						m_bEnemySoldierDead[ii]  = true;
						m_iEnemySoldierDeadAnim[ii] = 0;

						m_oCanvas.m_oStateGame.m_iScore++;

						m_iEnemySoldier -= 1;
						if(m_iEnemySoldier < 0)m_iEnemySoldier = 0;
						break;
					}
				}
			}

			// Objects
			for(int ii=0;ii < 5;ii++)
			{
				if((m_iObjectY[ii]*20) < m_iFriendlyBulletY[i] && (m_iObjectY[ii]*20) > m_iFriendlyBulletY[i]-20)
				{
					if((m_iObjectX[ii]*20) < m_iFriendlyBulletX[i] && (m_iObjectX[ii]*20) > m_iFriendlyBulletX[i]-20)
					{
						if(m_iObjectType[ii] == 1)m_bFriendlyBulletDead[i] = true;
						if(m_iObjectType[ii] == 2)m_bFriendlyBulletDead[i] = true;
						if(m_iObjectType[ii] == 4)m_bFriendlyBulletDead[i] = true;
					}
				}
			}
		}

		//----------------------
		// Collision Check - Bullets Enemy
		//----------------------
		for(i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bEnemyBulletDead[i] == true)continue;

			// Soldiers
			for(int ii=0;ii < m_iNumberOfMenToCreate;ii++)
			{
				if(m_bFriendlySoldierDead[ii] == true)continue;

				if(m_iEnemyBulletY[i] < m_iFriendlySoldierY[ii]+14 && m_iEnemyBulletY[i] > m_iFriendlySoldierY[ii])
				{
					if(m_iEnemyBulletX[i] < m_iFriendlySoldierX[ii]+12 && m_iEnemyBulletX[i] > m_iFriendlySoldierX[ii])
					{
						m_bEnemyBulletDead[i] = true;
						m_bFriendlySoldierDead[ii]  = true;
						m_iFriendlySoldierDeadAnim[ii] = 0;

						m_iFriendlySoldier -= 1;
						if(m_iFriendlySoldier < 0)m_iFriendlySoldier = 0;

						break;
					}
				}
			}

			// Objects
			for(int ii=0;ii < 5;ii++)
			{
				if((m_iObjectY[ii]*20) < m_iEnemyBulletY[i] && (m_iObjectY[ii]*20) > m_iEnemyBulletY[i]-20)
				{
					if((m_iObjectX[ii]*20) < m_iEnemyBulletX[i] && (m_iObjectX[ii]*20) > m_iEnemyBulletX[i]-20)
					{
						if(m_iObjectType[ii] == 1)m_bEnemyBulletDead[i] = true;
						if(m_iObjectType[ii] == 2)m_bEnemyBulletDead[i] = true;
						if(m_iObjectType[ii] == 4)m_bEnemyBulletDead[i] = true;
					}
				}
			}
		}

		return 0;
	}	

	//------------------------------------------------------
	public int SoftKey(int iKey)
	//------------------------------------------------------
	{
		if(m_bLocked)return 0;

		//------------
		// Retreat
		//------------
		if(m_bRetreat == true && m_iKeyPause <= 0)
		{
			if(m_oCanvas.m_oStateGame.m_iEvent != 4 && m_oCanvas.m_oStateGame.m_iEvent != 8)m_oCanvas.m_oStateGame.m_iEnemyShipSoldiers[m_oCanvas.m_oStateGame.m_iShipNr] = m_iEnemySoldier;

			return m_iFriendlySoldier;
		}

		//------------
		// GameOver
		//------------
		if(m_bGameOver == true && m_iKeyPause <= 0)
		{
			//m_oCanvas.m_oStateGame.m_oEnemyShips[m_oCanvas.m_oStateGame.m_oEvents.m_iShipNr].m_iSoldiers = m_iEnemySoldier;

			m_oCanvas.m_oStateGame.m_iResourcePirate = m_iFriendlySoldier;
			return -1;
		}

		if(m_bGameOver == false && m_bRetreat == false && (iKey == Canvas.KEY_NUM5 || iKey == Canvas.KEY_NUM0) && m_iFriendlyBullets > 0 && m_iFriendlyMovePause <= 0 && m_iEnemyMovePause <= 0)
		{
			if(m_iFriendlyCoolDown <= 0)
			{
				m_iFriendlyBullets--;
				m_iFriendlyFireFlash = 10;
				m_iFriendlyCoolDown = 50;

				for(int i=12;i < 18;i++)
				{
					m_iFriendlyBulletX[i]    = m_iFriendlyBulletX[i-6];
					m_iFriendlyBulletY[i]    = m_iFriendlyBulletY[i-6];
					m_bFriendlyBulletDead[i] = m_bFriendlyBulletDead[i-6];
				}
				for(int i=6;i < 12;i++)
				{
					m_iFriendlyBulletX[i]    = m_iFriendlyBulletX[i-6];
					m_iFriendlyBulletY[i]    = m_iFriendlyBulletY[i-6];
					m_bFriendlyBulletDead[i] = m_bFriendlyBulletDead[i-6];
				}
				for(int i=0;i < 12;i+=2)
				{
					if(m_bFriendlySoldierDead[i+1] == false && m_iFriendlySoldierFight[i+1] == 0)
					{
						m_iFriendlyBulletX[i/2]    = m_iFriendlySoldierX[i+1]+1;
						m_iFriendlyBulletY[i/2]    = m_iFriendlySoldierY[i+1]+4;
						m_bFriendlyBulletDead[i/2] = m_bFriendlySoldierDead[i+1];
					}
					else
					{
						m_iFriendlyBulletX[i/2]    = m_iFriendlySoldierX[i]+1;
						m_iFriendlyBulletY[i/2]    = m_iFriendlySoldierY[i]+4;
						m_bFriendlyBulletDead[i/2] = m_bFriendlySoldierDead[i];
					}
				}

				if(m_iAIAimTargets != 0)AIShoot();
			}
		}
		
		
		//------------
		// GameKeys
		//------------
		int keyState  = m_oCanvas.getGameAction(iKey);

		//------------
		// Player control
		//------------
		if((keyState == Canvas.LEFT) ){m_bLeft  = true;}
		if((keyState == Canvas.RIGHT)){m_bRight = true;}
		if((keyState == Canvas.UP)   ){m_bUp    = true;}
		if((keyState == Canvas.DOWN) ){m_bDown  = true;}

		return 0;
	}

	//------------------------------------------------------
	public void SoftKeyRelease(int iKey)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int keyState  = m_oCanvas.getGameAction(iKey);

		if((keyState == Canvas.LEFT) ){m_bLeft  = false;}
		if((keyState == Canvas.RIGHT)){m_bRight = false;}
		if((keyState == Canvas.UP)   ){m_bUp    = false;}
		if((keyState == Canvas.DOWN) ){m_bDown  = false;}
	}

	//------------------------------------------------------
	private void DrawBackGround(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int iWidth  = m_oCanvas.m_iWidth  / 20;
		int iHeight = m_oCanvas.m_iHeight / 20;
		int x,y;

		g.setColor(145,227,255);
		g.fillRect(0,0,m_oCanvas.m_iWidth,40);

//		Image oImgEdge = Image.createImage(20,20);
//		oImgEdge.getGraphics().drawImage(m_oCanvas.m_oImageArray[1],-130,-20,Graphics.TOP | Graphics.LEFT);
		
		// Draw ship edge
		for(x = 0;x <= iWidth;x++)
		{
			g.setClip(x*20,(1*20),20,20);
			g.drawImage(m_oCanvas.m_oImageArray[1],(x*20)-130,(1*20)-20,Graphics.TOP | Graphics.LEFT);
//			g.drawImage(oImgEdge,(x*20),(1*20),Graphics.TOP | Graphics.LEFT);

		}

//		Image oImgPlatform = Image.createImage(20,20);
//		oImgPlatform.getGraphics().drawImage(m_oCanvas.m_oImageArray[1],-90,0,Graphics.TOP | Graphics.LEFT);
				
		// Draw ship platform
		for(x = 0;x <= iWidth;x++)
		{
			for(y = 2;y <= iHeight;y++)
			{
				g.setClip(x*20,y*20,20,20);
				g.drawImage(m_oCanvas.m_oImageArray[1],(x*20)-90,(y*20),Graphics.TOP | Graphics.LEFT);
//				g.drawImage(oImgPlatform,(x*20),(y*20),Graphics.TOP | Graphics.LEFT);
			}
		}

		// Draw ship Objects
		for(x=0; x < 5;x++)
		{
			g.setClip(m_iObjectX[x]*20,m_iObjectY[x]*20,20,20);
							
			if(m_iObjectType[x] == 1)g.drawImage(m_oCanvas.m_oImageArray[1],(m_iObjectX[x]*20)-110,(m_iObjectY[x]*20),Graphics.TOP | Graphics.LEFT);
			if(m_iObjectType[x] == 2)g.drawImage(m_oCanvas.m_oImageArray[1],(m_iObjectX[x]*20)-130,(m_iObjectY[x]*20),Graphics.TOP | Graphics.LEFT);
			if(m_iObjectType[x] == 3)g.drawImage(m_oCanvas.m_oImageArray[1],(m_iObjectX[x]*20)-150,(m_iObjectY[x]*20),Graphics.TOP | Graphics.LEFT);
			if(m_iObjectType[x] == 4)g.drawImage(m_oCanvas.m_oImageArray[1],(m_iObjectX[x]*20)-170,(m_iObjectY[x]*20),Graphics.TOP | Graphics.LEFT);
			if(m_iObjectType[x] == 5)g.drawImage(m_oCanvas.m_oImageArray[1],(m_iObjectX[x]*20)-90,(m_iObjectY[x]*20)-20,Graphics.TOP | Graphics.LEFT);
			if(m_iObjectType[x] == 6)g.drawImage(m_oCanvas.m_oImageArray[1],(m_iObjectX[x]*20)-110,(m_iObjectY[x]*20)-20,Graphics.TOP | Graphics.LEFT);
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
	}

	//------------------------------------------------------
	private void DrawMen(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int i;

		//-----------------
		// Corpses
		//-----------------
		for(i = 12;i < 12*3;i++)
		{
			g.setClip(m_iEnemySoldierX[i]+5,m_iEnemySoldierY[i],19,19);
			g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]+5-100,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
			g.setClip(m_iFriendlySoldierX[i]-5,m_iFriendlySoldierY[i],19,19);
			g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-5-100,m_iFriendlySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
		}

		for(i = 0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bFriendlySoldierDead[i])
			{
				g.setClip(m_iFriendlySoldierX[i]-(m_iFriendlySoldierDeadAnim[i]),m_iFriendlySoldierY[i],19,19);
				
				     if(m_iFriendlySoldierDeadAnim[i] < 2)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-(m_iFriendlySoldierDeadAnim[i])-140,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
				else if(m_iFriendlySoldierDeadAnim[i] < 5)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-(m_iFriendlySoldierDeadAnim[i])-120,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
				else                                      g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-(m_iFriendlySoldierDeadAnim[i])-100,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
			}

			if(m_bEnemySoldierDead[i])
			{
				g.setClip(m_iEnemySoldierX[i]+(m_iEnemySoldierDeadAnim[i]),m_iEnemySoldierY[i],19,19);
				
				     if(m_iEnemySoldierDeadAnim[i] < 2)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]+(m_iEnemySoldierDeadAnim[i])-140,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
				else if(m_iEnemySoldierDeadAnim[i] < 5)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]+(m_iEnemySoldierDeadAnim[i])-120,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
				else                                   g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]+(m_iEnemySoldierDeadAnim[i])-100,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
			}
		}

		//-----------------
		// Friendly
		//-----------------

		if(m_iFriendlyAnim < 0)m_iFriendlyAnim = 10;

		if(m_iFriendlyAnim < 5)
		{
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(!m_bFriendlySoldierDead[i])
				{
					g.setClip(m_iFriendlySoldierX[i],m_iFriendlySoldierY[i],19,19);
					
					if(m_iFriendlySoldierFight[i] != 0)
					{
						int iMuf = (m_oCanvas.m_oRand.nextInt()%2);
						g.setClip(m_iFriendlySoldierX[i]+iMuf,m_iFriendlySoldierY[i],19,19);
						if(m_iFriendlySoldierFight[i] > 3)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]+iMuf-80,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
						else                              g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]+iMuf-60,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
					}
					
					else if(m_iFriendlyFireFlash > 0)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-40,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
					else if(m_iFriendlyBullets > 0)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-20,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
					else                           g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-180,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
				}
			}
		}
		else
		{
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(!m_bFriendlySoldierDead[i])
				{
					g.setClip(m_iFriendlySoldierX[i],m_iFriendlySoldierY[i],19,19);
										
					if(m_iFriendlySoldierFight[i] != 0)
					{
						int iMuf = (m_oCanvas.m_oRand.nextInt()%2);
						g.setClip(m_iFriendlySoldierX[i]+iMuf,m_iFriendlySoldierY[i],19,19);
						
						if(m_iFriendlySoldierFight[i] > 3) g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]+iMuf-80,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
						else                               g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]+iMuf-60,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
					}
					else if(m_iFriendlyFireFlash > 0)   g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-40,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
					else if(m_iFriendlyBullets > 0)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i],m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
					else                           g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]-160,m_iFriendlySoldierY[i]-21,Graphics.TOP | Graphics.LEFT);
				}
			}
		}

		//-----------------
		// Enemy
		//-----------------
		if(m_iEnemyAnim < 0)m_iEnemyAnim = 10;

		if(m_iEnemyAnim < 5)
		{
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(!m_bEnemySoldierDead[i])
				{
					g.setClip(m_iEnemySoldierX[i],m_iEnemySoldierY[i],19,19);
										
					if(m_iEnemySoldierFight[i] != 0)
					{
						int iMuf = (m_oCanvas.m_oRand.nextInt()%2);
						g.setClip(m_iEnemySoldierX[i]+iMuf,m_iEnemySoldierY[i],19,19);
						
						if(m_iEnemySoldierFight[i] > 3)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]+iMuf-80,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
						else                            g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]+iMuf-60,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
					}
					else if(m_iEnemyFireFlash > 0)   g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-40,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyBullets > 0)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-20,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
					else                        g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-180,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
				}
			}
		}
		else
		{
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(!m_bEnemySoldierDead[i])
				{
					g.setClip(m_iEnemySoldierX[i],m_iEnemySoldierY[i],19,19);
										
					if(m_iEnemySoldierFight[i] != 0)
					{
						int iMuf = (m_oCanvas.m_oRand.nextInt()%2);
						g.setClip(m_iEnemySoldierX[i]+iMuf,m_iEnemySoldierY[i],19,19);
						
						if(m_iEnemySoldierFight[i] > 3)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]+iMuf-80,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
						else                           g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]+iMuf-60,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
					}
					else if(m_iEnemyFireFlash > 0)   g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-40,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
					else if(m_iEnemyBullets > 0)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i],m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
					else                        g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-160,m_iEnemySoldierY[i]-41,Graphics.TOP | Graphics.LEFT);
				}
			}
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
	}

	//------------------------------------------------------
	private void DrawFlash(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int i;

		//Friendly
		if(m_iFriendlyFireFlash > 0)
		{
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(m_bFriendlySoldierDead[i])continue;

				g.setClip(m_iFriendlySoldierX[i]+3,m_iFriendlySoldierY[i]-5,20,20);
				
				if(m_iFriendlyFireFlash >  9 && m_iFriendlyFireFlash <= 10)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]+3-63,m_iFriendlySoldierY[i]-5,Graphics.TOP | Graphics.LEFT);
				if(m_iFriendlyFireFlash >  6 && m_iFriendlyFireFlash <=  9)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]+3-42,m_iFriendlySoldierY[i]-5,Graphics.TOP | Graphics.LEFT);
				if(m_iFriendlyFireFlash >  3 && m_iFriendlyFireFlash <=  6)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]+3-21,m_iFriendlySoldierY[i]-5,Graphics.TOP | Graphics.LEFT);
				if(m_iFriendlyFireFlash >  0 && m_iFriendlyFireFlash <=  3)g.drawImage(m_oCanvas.m_oImageArray[3],m_iFriendlySoldierX[i]+3  ,m_iFriendlySoldierY[i] -5,Graphics.TOP | Graphics.LEFT);
			}

			m_iFriendlyFireFlash--;
		}


		//Enemy
		if(m_iEnemyFireFlash > 0)
		{
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(m_bEnemySoldierDead[i])continue;

				g.setClip(m_iEnemySoldierX[i]-10,m_iEnemySoldierY[i]-5,20,20);				
				
				if(m_iEnemyFireFlash >  9 && m_iEnemyFireFlash <= 10)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-10-63,m_iEnemySoldierY[i]-5,Graphics.TOP | Graphics.LEFT);
				if(m_iEnemyFireFlash >  6 && m_iEnemyFireFlash <=  9)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-10-42,m_iEnemySoldierY[i]-5,Graphics.TOP | Graphics.LEFT);
				if(m_iEnemyFireFlash >  3 && m_iEnemyFireFlash <=  6)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-10-21,m_iEnemySoldierY[i]-5,Graphics.TOP | Graphics.LEFT);
				if(m_iEnemyFireFlash >  0 && m_iEnemyFireFlash <=  3)g.drawImage(m_oCanvas.m_oImageArray[3],m_iEnemySoldierX[i]-10  ,m_iEnemySoldierY[i] -5,Graphics.TOP | Graphics.LEFT);
			}

			m_iEnemyFireFlash--;
		}
		
		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);		
	}

	//------------------------------------------------------
	private void DrawBullets(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int i;

		//Draw Bullets interface
		for(i = 0;i < m_iFriendlyBullets;i++)
		{
			g.setClip(5 + (i*8),15,4,13);
			g.drawImage(m_oCanvas.m_oImageArray[1],5 + (i*8)-149,15-49,Graphics.TOP | Graphics.LEFT);
		}

		for(i = 0;i < m_iEnemyBullets;i++)
		{
			g.setClip(m_oCanvas.m_iWidth - 10 - (i*8),15,4,13);
			g.drawImage(m_oCanvas.m_oImageArray[1],m_oCanvas.m_iWidth - 10 - (i*8)-149,15-49,Graphics.TOP | Graphics.LEFT);
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);		
		
		//Draw Bullets
		g.setColor(0,0,0);
		for(i = 0;i < 18;i++)
		{
			if(m_bFriendlyBulletDead[i] == false)
			{
				g.drawRect(m_iFriendlyBulletX[i],m_iFriendlyBulletY[i],1,1);
			}

			if(m_bEnemyBulletDead[i] == false)
			{
				g.drawRect(m_iEnemyBulletX[i],m_iEnemyBulletY[i],1,1);
			}
		}

	}

	//------------------------------------------------------
	private void DrawText(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		//Draw Text
		g.setColor(100,0,0);
		g.drawString("Enemy:",m_oCanvas.m_iWidth - 60,1,Graphics.TOP | Graphics.LEFT);
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iEnemySoldier);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
			g.drawChars(stringChars,0,stringBuffer.length(),m_oCanvas.m_iWidth - 21,1,Graphics.TOP | Graphics.LEFT);

		//Draw Text
		g.setColor(0,100,0);
		g.drawString("Friendly:",1,1,Graphics.TOP | Graphics.LEFT);
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iFriendlySoldier);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
			g.drawChars(stringChars,0,stringBuffer.length(),49,1,Graphics.TOP | Graphics.LEFT);

		g.setFont(m_oCanvas.m_oFontMediumB);
			
		//Draw GameOver
		if(m_bGameOver == true)
		{
			g.setColor(0,0,0);
			g.drawString("Battle over",(m_oCanvas.m_iWidth/2)-38,(m_oCanvas.m_iHeight/2),Graphics.TOP | Graphics.LEFT);
		}

		//Draw Retreat
		if(m_bRetreat == true)
		{
			g.setColor(0,0,0);
			g.drawString("Retreat!",(m_oCanvas.m_iWidth/2)-26,(m_oCanvas.m_iHeight/2),Graphics.TOP | Graphics.LEFT);
		}
		
		g.setFont(m_oCanvas.m_oFontSmallB);
	}

	//------------------------------------------------------
	private void CreateSoldier(boolean bFriendly,boolean bEnemy)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int i;
		
		if(bEnemy)
		{
			m_iEnemyMovePause    = 50;
		 	m_iEnemyBullets    = 5;
			m_iEnemyLeaderX   = m_oCanvas.m_iWidth-30;
			m_iEnemyLeaderY   = m_oCanvas.m_iHeight/2;
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				m_iEnemySoldierFight[i] = 0;
				m_iFriendlySoldierFight[i] = 0;
			}
			for(i = 0;i < 18;i++)
			{
				m_bFriendlyBulletDead[i] = true;
			}
		}
		if(bFriendly)
		{
			m_iFriendlyMovePause = 50;
			m_iFriendlyBullets = 5;
			m_iFriendlyLeaderX = 15;
			m_iFriendlyLeaderY = m_oCanvas.m_iHeight/2;

			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				m_iFriendlySoldierFight[i] = 0;
				m_iEnemySoldierFight[i] = 0;
			}
			for(i = 0;i < 18;i++)
			{
				m_bEnemyBulletDead[i]    = true;
			}
		}

		//-----------------------------
		//---- Keep Corpses
		//-----------------------------
		if(bFriendly == false || bEnemy == false)
		{
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(bFriendly)
				{
					m_iFriendlySoldierX[24+i] = m_iFriendlySoldierX[12+i];
					m_iFriendlySoldierY[24+i] = m_iFriendlySoldierY[12+i];
				}
				if(bEnemy)
				{
					m_iEnemySoldierX[24+i]    = m_iEnemySoldierX[12+i];
					m_iEnemySoldierY[24+i]    = m_iEnemySoldierY[12+i];
				}
			}
			for(i = 0;i < m_iNumberOfMenToCreate;i++)
			{
				if(bFriendly)
				{
					m_iFriendlySoldierX[12+i] = m_iFriendlySoldierX[i];
					m_iFriendlySoldierY[12+i] = m_iFriendlySoldierY[i];
				}
				if(bEnemy)
				{
					m_iEnemySoldierX[12+i]    = m_iEnemySoldierX[i];
					m_iEnemySoldierY[12+i]    = m_iEnemySoldierY[i];
				}
			}
		}

		for(i = 0;i < m_iNumberOfMenToCreate;i++)
		{
			if(bFriendly)
			{
				if((i*1) < m_iFriendlySoldier)
				{
					m_bFriendlySoldierDead[i] = false;
					m_iFriendlySoldierX[i] = - 25;
					m_iFriendlySoldierY[i] = (m_oCanvas.m_iHeight/2)+20;
				}
			}

			if(bEnemy)
			{
				if((i*1) < m_iEnemySoldier)
				{
					m_bEnemySoldierDead[i] = false;
					m_iEnemySoldierX[i] = m_oCanvas.m_iWidth+20;
					m_iEnemySoldierY[i] = (m_oCanvas.m_iHeight/2)+20;
				}
			}
		}
	}

	//------------------------------------------------------
	private boolean CheckObjectCollision(int iX,int iY)
	//------------------------------------------------------
	{
		if(m_bLocked)return false;

		if(iY < (20*2))return true;
		if(iY > (m_oCanvas.m_iHeight-20))return true;


		for(int i=0; i < 5;i++)
		{
			if((m_iObjectY[i]*20) < iY+10 && (m_iObjectY[i]*20) > iY-15)
			{
				if((m_iObjectX[i]*20) < iX+15 && (m_iObjectX[i]*20) > iX-15)
				{
					if(m_iObjectType[i] == 1)return true;
					if(m_iObjectType[i] == 2)return true;
					if(m_iObjectType[i] == 4)return true;
				}
			}
		}

		return false;
	}

	//------------------------------------------------------
	private void MoveToCommandPoint()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int i;
		boolean bMove = true;

		if(m_iFriendlyMovePause > 0)m_iFriendlyMovePause--;
		if(m_iEnemyMovePause > 0)   m_iEnemyMovePause--;

		int iFriendlySpeed = 1;
		int iEnemySpeed    = 1;

		if(m_iEnemyBullets <= 0 && m_iEnemyBezerk <= 0)
		{
			iEnemySpeed       = 2;
			m_iEnemyBezerk = 3;
		}
		if(m_iFriendlyBullets <= 0 && m_iFriendlyBezerk <= 0)
		{
			iFriendlySpeed = 2;
			m_iFriendlyBezerk = 3;
		}

		m_bFriendlyLeaderPause2 = false;
		m_bFriendlyLeaderPause4 = false;
		m_bFriendlyLeaderPause6 = false;
		m_bFriendlyLeaderPause8 = false;

		m_bEnemyLeaderPause2    = false;
		m_bEnemyLeaderPause4    = false;
		m_bEnemyLeaderPause6    = false;
		m_bEnemyLeaderPause8    = false;

		if(m_iFriendlyBezerk > 0)m_iFriendlyBezerk--;
		if(m_iEnemyBezerk > 0)   m_iEnemyBezerk--;

		for(i = 0;i < 12;i+= 2)
		{
			// Friendly
			bMove = true;
			if((m_iFriendlyLeaderX+0) < m_iFriendlySoldierX[i])
			{
				if(m_bFriendlySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iFriendlyLeaderX+25) < m_iFriendlySoldierX[i])m_bFriendlyLeaderPause4 = true;
				}
				if(m_iFriendlySoldierFight[i] != 0)bMove = false;
				if(CheckObjectCollision(m_iFriendlySoldierX[i]-iFriendlySpeed,m_iFriendlySoldierY[i]))bMove = false;
				if(bMove)m_iFriendlySoldierX[i]-= iFriendlySpeed;
			}
	
			bMove = true;
			if((m_iFriendlyLeaderX+0) > m_iFriendlySoldierX[i])
			{
				if(m_bFriendlySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iFriendlyLeaderX-25) > m_iFriendlySoldierX[i])m_bFriendlyLeaderPause6 = true;
				}
				if(m_iFriendlySoldierFight[i] != 0)bMove = false;
				if(m_iFriendlySoldierX[i] > (m_oCanvas.m_iWidth))bMove = false;
				if(CheckObjectCollision(m_iFriendlySoldierX[i]+iFriendlySpeed,m_iFriendlySoldierY[i]))bMove = false;
				if(bMove)m_iFriendlySoldierX[i]+= iFriendlySpeed;
			}
	
			bMove = true;
			if((m_iFriendlyLeaderY+((i/2)*10)) < m_iFriendlySoldierY[i])
			{
				if(m_bFriendlySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iFriendlyLeaderY+((i/2)*10))+25 < m_iFriendlySoldierY[i])m_bFriendlyLeaderPause8 = true;
				}
				if(m_iFriendlySoldierFight[i] != 0)bMove = false;
				if(m_iFriendlySoldierY[i] < (20*2))bMove = false;
				if(CheckObjectCollision(m_iFriendlySoldierX[i],m_iFriendlySoldierY[i]-iFriendlySpeed))bMove = false;
				if(bMove)m_iFriendlySoldierY[i]-= iFriendlySpeed;
			}
	
			bMove = true;
			if((m_iFriendlyLeaderY+((i/2)*10)) > m_iFriendlySoldierY[i])
			{
				if(m_bFriendlySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iFriendlyLeaderY+((i/2)*10))-25 > m_iFriendlySoldierY[i])m_bFriendlyLeaderPause2 = true;
				}
				if(m_iFriendlySoldierFight[i] != 0)bMove = false;
				if(m_iFriendlySoldierY[i] > (m_oCanvas.m_iHeight-20))bMove = false;
				if(CheckObjectCollision(m_iFriendlySoldierX[i],m_iFriendlySoldierY[i]+iFriendlySpeed))bMove = false;
				if(bMove)m_iFriendlySoldierY[i]+= iFriendlySpeed;
			}

			// Enemy
			bMove = true;
			if((m_iEnemyLeaderX+0) < m_iEnemySoldierX[i])
			{
				if(m_bEnemySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iEnemyLeaderX+25) < m_iEnemySoldierX[i])m_bEnemyLeaderPause4 = true;
				}
				if(m_iEnemySoldierFight[i] != 0)bMove = false;
				if(CheckObjectCollision(m_iEnemySoldierX[i]-iEnemySpeed,m_iEnemySoldierY[i]))bMove = false;
				if(bMove)m_iEnemySoldierX[i]-= iEnemySpeed;
			}

			bMove = true;
			if((m_iEnemyLeaderX+0) > m_iEnemySoldierX[i])
			{
				if(m_bEnemySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iEnemyLeaderX-25) > m_iEnemySoldierX[i])m_bEnemyLeaderPause6 = true;
				}
				if(m_iEnemySoldierFight[i] != 0)bMove = false;
				if(m_iEnemySoldierX[i]+10 > (m_oCanvas.m_iWidth))bMove = false;
				if(CheckObjectCollision(m_iEnemySoldierX[i]+iEnemySpeed,m_iEnemySoldierY[i]))bMove = false;
				if(bMove)m_iEnemySoldierX[i]+=iEnemySpeed;
			}

			bMove = true;
			if((m_iEnemyLeaderY+((i/2)*10)) < m_iEnemySoldierY[i])
			{
				if(m_bEnemySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iEnemyLeaderY+((i/2)*10))+25 < m_iEnemySoldierY[i])m_bEnemyLeaderPause8 = true;
				}
				if(m_iEnemySoldierFight[i] != 0)bMove = false;
				if(m_iEnemySoldierY[i] < (20*2))bMove = false;
				if(CheckObjectCollision(m_iEnemySoldierX[i],m_iEnemySoldierY[i]-iEnemySpeed))bMove = false;
				if(bMove)m_iEnemySoldierY[i]-= iEnemySpeed;
			}

			bMove = true;
			if((m_iEnemyLeaderY+((i/2)*10)) > m_iEnemySoldierY[i])
			{
				if(m_bEnemySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iEnemyLeaderY+((i/2)*10))-25 > m_iEnemySoldierY[i])m_bEnemyLeaderPause2 = true;
				}
				if(m_iEnemySoldierFight[i] != 0)bMove = false;
				if(m_iEnemySoldierY[i] > (m_oCanvas.m_iHeight-20))bMove = false;
				if(CheckObjectCollision(m_iEnemySoldierX[i],m_iEnemySoldierY[i]+iEnemySpeed))bMove = false;
				if(bMove)m_iEnemySoldierY[i]+= iEnemySpeed;
			}

		}

		for(i = 1;i < 12;i+= 2)
		{
			// Friendly
			bMove = true;
			if((m_iFriendlyLeaderX+10) < m_iFriendlySoldierX[i])
			{
				if(m_bFriendlySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iFriendlyLeaderX+10+25) < m_iFriendlySoldierX[i])m_bFriendlyLeaderPause4 = true;
				}
				if(m_iFriendlySoldierFight[i] != 0)bMove = false;
				if(CheckObjectCollision(m_iFriendlySoldierX[i]-iFriendlySpeed,m_iFriendlySoldierY[i]))bMove = false;
				if(bMove)m_iFriendlySoldierX[i]-= iFriendlySpeed;
			}
	
			bMove = true;
			if((m_iFriendlyLeaderX+10) > m_iFriendlySoldierX[i])
			{
				if(m_bFriendlySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iFriendlyLeaderX+10-25) > m_iFriendlySoldierX[i])m_bFriendlyLeaderPause6 = true;
				}
				if(m_iFriendlySoldierFight[i] != 0)bMove = false;
				if(m_iFriendlySoldierX[i] > (m_oCanvas.m_iWidth))bMove = false;
				if(CheckObjectCollision(m_iFriendlySoldierX[i]+iFriendlySpeed,m_iFriendlySoldierY[i]))bMove = false;
				if(bMove)m_iFriendlySoldierX[i]+= iFriendlySpeed;
			}
	
			bMove = true;
			if((m_iFriendlyLeaderY+(((i-1)/2)*10)) < m_iFriendlySoldierY[i])
			{
				if(m_bFriendlySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iFriendlyLeaderY+(((i-1)/2)*10))+25 < m_iFriendlySoldierY[i])m_bFriendlyLeaderPause8 = true;
				}
				if(m_iFriendlySoldierFight[i] != 0)bMove = false;
				if(m_iFriendlySoldierY[i] < (20*2))bMove = false;
				if(CheckObjectCollision(m_iFriendlySoldierX[i],m_iFriendlySoldierY[i]-iFriendlySpeed))bMove = false;
				if(bMove)m_iFriendlySoldierY[i]-= iFriendlySpeed;
			}
	
			bMove = true;
			if((m_iFriendlyLeaderY+(((i-1)/2)*10)) > m_iFriendlySoldierY[i])
			{
				if(m_bFriendlySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iFriendlyLeaderY+(((i-1)/2)*10))-25 > m_iFriendlySoldierY[i])m_bFriendlyLeaderPause2 = true;
				}
				if(m_iFriendlySoldierFight[i] != 0)bMove = false;
				if(m_iFriendlySoldierY[i] > (m_oCanvas.m_iHeight-20))bMove = false;
				if(CheckObjectCollision(m_iFriendlySoldierX[i],m_iFriendlySoldierY[i]+iFriendlySpeed))bMove = false;
				if(bMove)m_iFriendlySoldierY[i]+= iFriendlySpeed;
			}

			// Enemy
			bMove = true;
			if((m_iEnemyLeaderX+10) < m_iEnemySoldierX[i])
			{
				if(m_bEnemySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iEnemyLeaderX+10+25) < m_iEnemySoldierX[i])m_bEnemyLeaderPause4 = true;
				}
				if(m_iEnemySoldierFight[i] != 0)bMove = false;
				if(CheckObjectCollision(m_iEnemySoldierX[i]-iEnemySpeed,m_iEnemySoldierY[i]))bMove = false;
				if(bMove)m_iEnemySoldierX[i]-= iEnemySpeed;
			}
			bMove = true;
			if((m_iEnemyLeaderX+10) > m_iEnemySoldierX[i])
			{
				if(m_bEnemySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iEnemyLeaderX+10-25) > m_iEnemySoldierX[i])m_bEnemyLeaderPause6 = true;
				}
				if(m_iEnemySoldierFight[i] != 0)bMove = false;
				if(m_iEnemySoldierX[i]+10 > (m_oCanvas.m_iWidth))bMove = false;
				if(CheckObjectCollision(m_iEnemySoldierX[i]+iEnemySpeed,m_iEnemySoldierY[i]))bMove = false;
				if(bMove)m_iEnemySoldierX[i]+= iEnemySpeed;
			}
			bMove = true;
			if((m_iEnemyLeaderY+(((i-1)/2)*10)) < m_iEnemySoldierY[i])
			{
				if(m_bEnemySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iEnemyLeaderY+(((i-1)/2)*10))+25 < m_iEnemySoldierY[i])m_bEnemyLeaderPause8 = true;
				}
				if(m_iEnemySoldierFight[i] != 0)bMove = false;
				if(m_iEnemySoldierY[i] < (20*2))bMove = false;
				if(CheckObjectCollision(m_iEnemySoldierX[i],m_iEnemySoldierY[i]-iEnemySpeed))bMove = false;
				if(bMove)m_iEnemySoldierY[i]-= iEnemySpeed;
			}
			bMove = true;
			if((m_iEnemyLeaderY+(((i-1)/2)*10)) > m_iEnemySoldierY[i])
			{
				if(m_bEnemySoldierDead[i] == true)bMove = false;
				if(bMove)
				{
					if((m_iEnemyLeaderY+(((i-1)/2)*10))-25 > m_iEnemySoldierY[i])m_bEnemyLeaderPause2 = true;
				}
				if(m_iEnemySoldierFight[i] != 0)bMove = false;
				if(m_iEnemySoldierY[i] > (m_oCanvas.m_iHeight-20))bMove = false;
				if(CheckObjectCollision(m_iEnemySoldierX[i],m_iEnemySoldierY[i]+iEnemySpeed))bMove = false;
				if(bMove)m_iEnemySoldierY[i]+= iEnemySpeed;
			}
		}
	}
	//------------------------------------------------------
	private void HandleAI()
	//------------------------------------------------------
	{
		if(m_bLocked)return;
		
		m_bAIUp         = false;
		m_bAIDown       = false;
		m_bAIRight      = false;
		m_bAILeft       = false;
		m_iAIAimTargets = 0;

		//////////////////////
		// Analyze and find tactic
		//////////////////////

		m_iDifficult--;
		if(m_iDifficult <= 0 || m_oCanvas.m_oStateGame.m_iTurn >= 200)
		{
			
			
			m_iDifficult = 40 - (m_oCanvas.m_oStateGame.m_iTurn / 5);
		
			//-------------------------------
			// Check for shoot first
			//-------------------------------
			AIAvoidShoot();
	
			//-------------------------------
			// Shoot analyze
			//-------------------------------
			AIAnalyzeShoot();
		}

		//-------------------------------
		// Moving Analyze
		//-------------------------------
		AIAnalyzeMove();		
		
		//////////////////////
		// Moving code
		//////////////////////
		if(m_iEnemyMovePause > 0)m_iEnemyAnim-=2;

		boolean bLegalMoving;
		boolean bOnly1Move = true;
		int i;

		if(m_iEnemyMovePause <= 0 && m_iEnemyFireFlash <= 0)
		{
			if(m_bAILeft && m_bEnemyLeaderPause4 == false)
			{
				if(bOnly1Move){m_iEnemyAnim-=2;bOnly1Move = false;}

				if(m_iEnemyBullets > 0 || m_iEnemyBezerk > 0)m_iEnemyLeaderX--;
				else                   m_iEnemyLeaderX-= 2;
			}

			if(m_bAIRight && m_bEnemyLeaderPause6 == false)
			{
				if(bOnly1Move){m_iEnemyAnim-=2;bOnly1Move = false;}

				bLegalMoving = true;

				for(i = 0;i < 12;i++)
				{
					if(m_bEnemySoldierDead[i] == true)continue;
					if(m_iEnemySoldierX[i] >= (m_oCanvas.m_iWidth)-20)bLegalMoving = false;
				}

				if(bLegalMoving)
				{
					if(m_iEnemyBullets > 0 || m_iEnemyBezerk > 0)m_iEnemyLeaderX++;
					else                   m_iEnemyLeaderX+= 2;
				}	
			}
		
			if(m_bAIUp && m_bEnemyLeaderPause8 == false)
			{
				if(bOnly1Move){m_iEnemyAnim-=2;bOnly1Move = false;}

				bLegalMoving = true;

				for(i = 0;i < 12;i++)
				{
					if(m_bEnemySoldierDead[i] == true)continue;
//					if(m_iEnemySoldierY[i] <= (20*3))bLegalMoving = false;
				}

				if(bLegalMoving)
				{
					if(m_iEnemyBullets > 0 || m_iEnemyBezerk > 0)m_iEnemyLeaderY--;
					else                   m_iEnemyLeaderY-= 2;
				}
			}

			if(m_bAIDown && m_bEnemyLeaderPause2 == false)
			{
				if(bOnly1Move){m_iEnemyAnim-=2;bOnly1Move = false;}

				bLegalMoving = true;

				for(i = 0;i < 12;i++)
				{
					if(m_bEnemySoldierDead[i] == true)continue;
//					if(m_iEnemySoldierY[i] >= (m_oCanvas.m_iHeight-20))bLegalMoving = false;
				}

				if(bLegalMoving)
				{
					if(m_iEnemyBullets > 0 || m_iEnemyBezerk > 0)m_iEnemyLeaderY++;
					else                   m_iEnemyLeaderY+= 2;
				}
			}
		}
	}

	//------------------------------------------------------
	private void AIAvoidShoot()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int iUp   = 0;
		int iDown = 0;

		for(int i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bEnemySoldierDead[i] == true)continue;

			for(int ii=0;ii < 18;ii++)
			{
				if(m_bFriendlyBulletDead[ii] == true)continue;

				if(m_iFriendlyBulletY[ii] < m_iEnemySoldierY[i]+14 && m_iFriendlyBulletY[ii] > m_iEnemySoldierY[i])
				{
					if(AILineOfSight(m_iFriendlyBulletX[ii],m_iEnemySoldierX[i],m_iEnemySoldierY[i]))continue;
					if(m_iFriendlyBulletX[ii] > m_iEnemySoldierX[i])continue;
					if(m_iFriendlyBulletX[ii] > m_iEnemySoldierX[i]-50 && m_iEnemyBullets > 0)
					{
						m_bAIRight = true;
//						g.setColor(200,200,0);
//						g.drawRect(m_iEnemySoldierX[i]+1,m_iEnemySoldierY[i]+1,8,8);
					}

//					g.setColor(255,0,0);
//					g.drawRect(m_iEnemySoldierX[i],m_iEnemySoldierY[i],10,10);

					if(i <= (m_iNumberOfMenToCreate/2)) // Down
					{
						if(m_iAIFlee != 0){iDown++;continue;}
						// Check if Room
						if(CheckObjectCollision(m_iEnemySoldierX[i],m_iEnemySoldierY[i]+8))
						{
							iUp++;
						}
						else
						{
							iDown++;
						}
					}

					if(i > (m_iNumberOfMenToCreate/2) ) // Up
					{
						if(m_iAIFlee != 0){iUp++;continue;}
						// Check if Room
						if(CheckObjectCollision(m_iEnemySoldierX[i],m_iEnemySoldierY[i]-8))
						{
							iDown++;
						}
						else
						{
							iUp++;
						}

					}
					break;
				}
			}
		}

		if(iUp == 0 && iDown == 0)
		{
			m_iAIFlee = 0;
			return;
		}
		if(m_iAIFlee != 0)
		{
			if(m_iAIFlee == 1)m_bAIDown = true;
			if(m_iAIFlee == 2)m_bAIUp   = true;
		}

		if(iUp < iDown)
		{
			m_iAIFlee = 1;
			m_bAIDown = true;
		}
		else
		{
			m_iAIFlee = 2;
			m_bAIUp   = true;
		}
	}

	//------------------------------------------------------
	private boolean AILineOfSight(int iXDes,int iXSrc,int iYSrc)
	//------------------------------------------------------
	{
		if(m_bLocked)return false;

		int iRange = iXSrc - iXDes;
		if(iRange <= 0)return false;

		for(int i=0; i < 5;i++)
		{
			if((m_iObjectY[i]*20) < iYSrc+10 && (m_iObjectY[i]*20) > iYSrc-15)
			{
				if((m_iObjectX[i]*20) < iXSrc && (m_iObjectX[i]*20) > iXSrc-iRange)
				{
					if(m_iObjectType[i] == 1)return true;
					if(m_iObjectType[i] == 2)return true;
					if(m_iObjectType[i] == 4)return true;
				}
			}
		}

		return false;
	}

	//------------------------------------------------------
	private void AIAnalyzeShoot()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		for(int i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bEnemySoldierDead[i] == true)continue;

			for(int ii=0;ii < m_iNumberOfMenToCreate;ii++)
			{
				if(m_bFriendlySoldierDead[ii] == true)continue;

				if(m_iFriendlySoldierY[ii] < m_iEnemySoldierY[i]+5 && m_iFriendlySoldierY[ii]+5 > m_iEnemySoldierY[i])
				{
					if(AILineOfSight(m_iFriendlySoldierX[ii],m_iEnemySoldierX[i],m_iEnemySoldierY[i]))continue;

					if(m_iFriendlySoldierX[ii] < m_iEnemySoldierX[i] && m_iFriendlySoldierX[ii] > m_iEnemySoldierX[i]-150)
					{
//						g.setColor(0,255,0);
//						g.drawLine(m_iFriendlySoldierX[ii],m_iFriendlySoldierY[ii]+5,m_iEnemySoldierX[i],m_iEnemySoldierY[i]+5);
						m_iAIAimTargets++;
						break;
					}
				}
			}
		}

		//---- Check number of Friendly back
		int iFriendlyBack = 0;
		for(int i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bFriendlySoldierDead[i] == true)continue;
			iFriendlyBack++;
		}

		//---- Check number of Enemy back
		int iEnemyBack = 0;
		for(int i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bEnemySoldierDead[i] == true)continue;
			iEnemyBack++;
		}

		int iTargetReq = (iEnemyBack/2);
		if(iTargetReq >= iFriendlyBack)iTargetReq = (iFriendlyBack/2);
		if(iTargetReq <= 0)iTargetReq = 1;
		//---- Shoot if 50% in range
		if(m_iAIAimTargets >= iTargetReq)
		{
			m_bAllowToShot = true;
			AIShoot();
		}
		else
		{
			m_bAllowToShot = false;
		}
	}

	//------------------------------------------------------
	private void AIShoot()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		if(m_iEnemyBullets > 0 && m_iEnemyMovePause <= 0 && m_iFriendlyMovePause <= 0)
		{
			if(m_iEnemyCoolDown <= 0)
			{
				m_iEnemyBullets--;
				m_iEnemyFireFlash = 10;
				m_iEnemyCoolDown = 50;

				for(int i=12;i < 18;i++)
				{
					m_iEnemyBulletX[i]    = m_iEnemyBulletX[i-6];
					m_iEnemyBulletY[i]    = m_iEnemyBulletY[i-6];
					m_bEnemyBulletDead[i] = m_bEnemyBulletDead[i-6];
				}
				for(int i=6;i < 12;i++)
				{
					m_iEnemyBulletX[i]    = m_iEnemyBulletX[i-6];
					m_iEnemyBulletY[i]    = m_iEnemyBulletY[i-6];
					m_bEnemyBulletDead[i] = m_bEnemyBulletDead[i-6];
				}
				for(int i=0;i < 12;i+=2)
				{
					if(m_bEnemySoldierDead[i] == false && m_iEnemySoldierFight[i] == 0)
					{
						m_iEnemyBulletX[i/2]    = m_iEnemySoldierX[i]+1;
						m_iEnemyBulletY[i/2]    = m_iEnemySoldierY[i]+4;
						m_bEnemyBulletDead[i/2] = m_bEnemySoldierDead[i];
					}
					else
					{
						m_iEnemyBulletX[i/2]    = m_iEnemySoldierX[i+1]+1;
						m_iEnemyBulletY[i/2]    = m_iEnemySoldierY[i+1]+4;
						m_bEnemyBulletDead[i/2] = m_bEnemySoldierDead[i+1];
					}
				}
			}
		}
	}

	//------------------------------------------------------
	private void AIAnalyzeMove()
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		if(m_iEnemyMovePause > 0)return;
		if(m_bAILeft || m_bAIRight)return;

		//---------------------------------------
		//--- Analyze From/Back
		//---------------------------------------

		if(m_iEnemyBullets > 0 && m_bAllowToShot == true)
		{
			if(m_bAIUp || m_bAIDown)return;

			if(m_iFriendlyLeaderX < m_iEnemyLeaderX-135)m_bAILeft  = true;
			if(m_iFriendlyLeaderX > m_iEnemyLeaderX- 90)m_bAIRight = true;
		}
		else // Close combat
		{
			int iBack  = 0;
			int iFront = 0;

			if(m_bFriendlySoldierDead[0]  == false) iBack++;
			if(m_bFriendlySoldierDead[2]  == false) iBack++;
			if(m_bFriendlySoldierDead[4]  == false) iBack++;
			if(m_bFriendlySoldierDead[6]  == false) iBack++;
			if(m_bFriendlySoldierDead[8]  == false) iBack++;
			if(m_bFriendlySoldierDead[10] == false) iBack++;
			if(m_bFriendlySoldierDead[1]  == false) iFront++;
			if(m_bFriendlySoldierDead[3]  == false) iFront++;
			if(m_bFriendlySoldierDead[5]  == false) iFront++;
			if(m_bFriendlySoldierDead[7]  == false) iFront++;
			if(m_bFriendlySoldierDead[9]  == false) iFront++;
			if(m_bFriendlySoldierDead[11] == false) iFront++;

			if(iBack < iFront)
			{
				if(m_iFriendlyLeaderX < m_iEnemyLeaderX-2)m_bAILeft  = true;
				if(m_iFriendlyLeaderX > m_iEnemyLeaderX-2)m_bAIRight = true;
			}
			else
			{
				if(m_iFriendlyLeaderX < m_iEnemyLeaderX+8)m_bAILeft  = true;
				if(m_iFriendlyLeaderX > m_iEnemyLeaderX+8)m_bAIRight = true;	
			}
		}

		//---------------------------------------
		//--- Analyze Up/Down
		//---------------------------------------
		if(m_bAIUp || m_bAIDown)return;

		int iUp   = 0;
		int iDown = 0;
		int iStraith = 0;

		for(int i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bEnemySoldierDead[i] == true)continue;

			for(int ii=0;ii < m_iNumberOfMenToCreate;ii++)
			{
				if(m_bFriendlySoldierDead[ii] == true)continue;
				
				     if(m_iEnemySoldierY[i]+3 < m_iFriendlySoldierY[ii])iDown++;
				else if(m_iEnemySoldierY[i]-3 > m_iFriendlySoldierY[ii])iUp++;
				else iStraith++;
			}
		}

		//---- Check number of Friendly back
		int iFriendlyBack = 0;
		for(int i=0;i < m_iNumberOfMenToCreate;i++)
		{
			if(m_bFriendlySoldierDead[i] == true)continue;
			iFriendlyBack++;
		}

		if(!m_bEnemyLeaderPause6 && !m_bEnemyLeaderPause4 && ((iFriendlyBack)) < iStraith)return;

		if(m_bEnemyLeaderPause4)
		{
			m_bAIUp = true;
			return;
		}

		boolean bLegal = true;
		if(iUp > iDown)
		{
			for(int i=0;i < m_iNumberOfMenToCreate;i++)
			{
				if(m_bEnemySoldierDead[i] == true)continue;

				for(int ii=0;ii < 12;ii++)
				{
					if(m_bFriendlyBulletDead[ii] == true)continue;

					if(AILineOfSight(m_iFriendlyBulletX[ii],m_iEnemySoldierX[i],m_iEnemySoldierY[i]))continue;
					if(m_iFriendlyBulletX[ii] > 9+m_iEnemySoldierX[i])continue;

					if(m_iFriendlyBulletY[ii]+3 > m_iEnemySoldierY[i])
					{
						bLegal = false;
					}
				}
			}

			if(bLegal)m_bAIUp = true;
		}
		if(iUp < iDown)
		{
			for(int i=0;i < m_iNumberOfMenToCreate;i++)
			{
				if(m_bEnemySoldierDead[i] == true)continue;

				for(int ii=0;ii < 18;ii++)
				{
					if(m_bFriendlyBulletDead[ii] == true)continue;

					if(AILineOfSight(m_iFriendlyBulletX[ii],m_iEnemySoldierX[i],m_iEnemySoldierY[i]))continue;
					if(m_iFriendlyBulletX[ii] > 9+m_iEnemySoldierX[i])continue;

					if(m_iFriendlyBulletY[ii] < m_iEnemySoldierY[i]+16)
					{
						bLegal = false;
					}
				}
			}

			if(bLegal)m_bAIDown = true;
		}
	}

	//------------------------------------------------------
	private void DrawCooldown(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		g.setColor(200,10,10);

		// Enemy Cooldown
		g.fillRect(m_oCanvas.m_iWidth-3-m_iEnemyCoolDown,28,m_iEnemyCoolDown,5);

		// Friendly Cooldown
		g.fillRect(5,28,m_iFriendlyCoolDown,5);
	}
}
