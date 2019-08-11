
import javax.microedition.lcdui.*;

//------------------------------------------------------
class CGameStateCity
//------------------------------------------------------
{
	// Public:

	// Protected:
	// Private:

	private KaperCanvas   m_oCanvas;
	private int           m_iFoodPriceBuy;
	private int           m_iPiratePriceBuy;
	private int           m_iCannonPriceBuy;
	private int           m_iFoodPriceSell;
	private int           m_iPiratePriceSell;
	private int           m_iCannonPriceSell;

	public int            m_iBlink;
	public char           m_cTarget;

	private int           m_iTarget;
	private int           m_iAmount;

	public String         m_sCityName;

	private boolean       m_bLocked;

	//------------------------------------------------------
	public CGameStateCity(KaperCanvas oKaperCanvas)
	//------------------------------------------------------
	{
		m_bLocked      = true;
		m_oCanvas      = oKaperCanvas;
	}
	
	//------------------------------------------------------
	public void Init()
	//------------------------------------------------------
	{
		m_bLocked          = true;

		m_iBlink  = 0;
		m_cTarget = 0;
		
		m_iTarget = 0;
		m_iAmount = 1;

		m_iFoodPriceBuy   = 9999;
		m_iPiratePriceBuy = 9999;
		m_iCannonPriceBuy = 9999;
		m_sCityName       = "Unknown";

		//----------------//
		//  Cities Price  //
		//----------------//
		if(((m_oCanvas.m_oStateGame.m_iShipX+2)/25) + m_oCanvas.m_oStateGame.m_iMapScreenX == 10)
		{
			if(((m_oCanvas.m_oStateGame.m_iShipY)/25) + m_oCanvas.m_oStateGame.m_iMapScreenY == 12)
			{
				m_iFoodPriceBuy   = Math.abs(m_oCanvas.m_oRand.nextInt()% 2)+ 1;
				m_iPiratePriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+ 5;
				m_iCannonPriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+45;
				m_sCityName = "(Kalundborg)";
			}
		}

		if(((m_oCanvas.m_oStateGame.m_iShipX+2)/25) + m_oCanvas.m_oStateGame.m_iMapScreenX == 6)
		{
			if(((m_oCanvas.m_oStateGame.m_iShipY)/25) + m_oCanvas.m_oStateGame.m_iMapScreenY == 3)
			{
				m_iFoodPriceBuy   = Math.abs(m_oCanvas.m_oRand.nextInt()% 2)+ 1;
				m_iPiratePriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+10;
				m_iCannonPriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+50;
				m_sCityName = "(Ebeltoft)";
			}
		}

		if(((m_oCanvas.m_oStateGame.m_iShipX+2)/25) + m_oCanvas.m_oStateGame.m_iMapScreenX == 8)
		{
			if(((m_oCanvas.m_oStateGame.m_iShipY)/25) + m_oCanvas.m_oStateGame.m_iMapScreenY == 0)
			{
				m_iFoodPriceBuy   = Math.abs(m_oCanvas.m_oRand.nextInt()% 2)+ 1;
				m_iPiratePriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+10;
				m_iCannonPriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+45;
				m_sCityName = "(Grenaa)";
			}
		}

		if(((m_oCanvas.m_oStateGame.m_iShipX+2)/25) + m_oCanvas.m_oStateGame.m_iMapScreenX == 24)
		{
			if(((m_oCanvas.m_oStateGame.m_iShipY)/25) + m_oCanvas.m_oStateGame.m_iMapScreenY == 11)
			{
				m_iFoodPriceBuy   = Math.abs(m_oCanvas.m_oRand.nextInt()% 2)+ 3;
				m_iPiratePriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+25;
				m_iCannonPriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+25;
				m_sCityName = "(København)";
			}
		}

		if(((m_oCanvas.m_oStateGame.m_iShipX+2)/25) + m_oCanvas.m_oStateGame.m_iMapScreenX == 24)
		{
			if(((m_oCanvas.m_oStateGame.m_iShipY)/25) + m_oCanvas.m_oStateGame.m_iMapScreenY == 6)
			{
				m_iFoodPriceBuy   = Math.abs(m_oCanvas.m_oRand.nextInt()% 2)+ 3;
				m_iPiratePriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+20;
				m_iCannonPriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+50;
				m_sCityName = "(Helsingør)";
			}
		}

		if(((m_oCanvas.m_oStateGame.m_iShipX+2)/25) + m_oCanvas.m_oStateGame.m_iMapScreenX == 17)
		{
			if(((m_oCanvas.m_oStateGame.m_iShipY)/25) + m_oCanvas.m_oStateGame.m_iMapScreenY == 7)
			{
				m_iFoodPriceBuy   = Math.abs(m_oCanvas.m_oRand.nextInt()% 2)+ 2;
				m_iPiratePriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+10;
				m_iCannonPriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+50;
				m_sCityName = "(Hundested)";
			}
		}

		if(((m_oCanvas.m_oStateGame.m_iShipX+2)/25) + m_oCanvas.m_oStateGame.m_iMapScreenX == 23)
		{
			if(((m_oCanvas.m_oStateGame.m_iShipY)/25) + m_oCanvas.m_oStateGame.m_iMapScreenY == 2)
			{
				m_iFoodPriceBuy   = Math.abs(m_oCanvas.m_oRand.nextInt()% 2)+ 4;
				m_iPiratePriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+15;
				m_iCannonPriceBuy = Math.abs(m_oCanvas.m_oRand.nextInt()%10)+35;
				m_sCityName = "(Helsingborg)";
			}
		}

		//----------------//
		//   Sell price   //
		//----------------//
		m_iFoodPriceSell   = 1; //+ ((m_iFoodPriceBuy   * 10)/100);
		m_iPiratePriceSell = 1 + ((m_iPiratePriceBuy * 65)/100);
		m_iCannonPriceSell = 1 + ((m_iCannonPriceBuy * 65)/100);
		if(m_sCityName == "(København)")m_iFoodPriceSell = 2;
		
		//----------------//
		//  Captured Ship //
		//----------------//
		if(m_oCanvas.m_oStateGame.m_iCapturedShip == 12)
		{
			m_oCanvas.m_oStateGame.m_iAddGold =  50;
			m_oCanvas.m_oStateGame.m_iScore  +=  100;
		}
		if(m_oCanvas.m_oStateGame.m_iCapturedShip == 13)
		{
			m_oCanvas.m_oStateGame.m_iAddGold = 100;
			m_oCanvas.m_oStateGame.m_iScore  +=  200;
		}
		if(m_oCanvas.m_oStateGame.m_iCapturedShip == 14)
		{
			m_oCanvas.m_oStateGame.m_iAddGold = 200;
			m_oCanvas.m_oStateGame.m_iScore  +=  300;
		}
		if(m_oCanvas.m_oStateGame.m_iCapturedShip == 15)
		{
			m_oCanvas.m_oStateGame.m_iAddGold = 300;
			m_oCanvas.m_oStateGame.m_iScore  +=  400;
		}
		if(m_oCanvas.m_oStateGame.m_iCapturedShip == 16)
		{
			m_oCanvas.m_oStateGame.m_iAddGold = 400;
			m_oCanvas.m_oStateGame.m_iScore  +=  500;
		}
		if(m_oCanvas.m_oStateGame.m_iCapturedShip == 17)
		{
			m_oCanvas.m_oStateGame.m_iAddGold = 700;
			m_oCanvas.m_oStateGame.m_iScore  +=  600;
		}
		if(m_oCanvas.m_oStateGame.m_iCapturedShip == 18)
		{
			m_oCanvas.m_oStateGame.m_iAddGold =  150;
			m_oCanvas.m_oStateGame.m_iScore  +=  200;
		}

		m_oCanvas.m_oStateGame.m_iAddPirates += m_oCanvas.m_oStateGame.m_iPiratesOnCapturedShip;

		m_oCanvas.m_oStateGame.m_iPiratesOnCapturedShip = 0;
		m_oCanvas.m_oStateGame.m_iCapturedShip = 0; // Remove captured ship

		m_oCanvas.PlaySound("d.mid",true);

		m_bLocked          = false;
	}	

	//------------------------------------------------------
	public void DeInit()
	//------------------------------------------------------
	{
		m_bLocked          = true;

		m_oCanvas.StopSound();

		m_sCityName     = null;

		System.gc();
	}	

	//------------------------------------------------------
	public void Draw(Graphics g)
	//------------------------------------------------------
	{
		if(m_bLocked)return;

		int iYOffSet = 0;

		if(((m_oCanvas.m_iHeight/2)-50) <= 50)
		{
			iYOffSet = 50 - ((m_oCanvas.m_iHeight/2)-50);
		}

		if(m_oCanvas.m_iHeight <= (iYOffSet+(m_oCanvas.m_iHeight/2)-0+(m_iTarget*10)))
		{
			iYOffSet -= -m_oCanvas.m_iHeight + (iYOffSet+(m_oCanvas.m_iHeight/2)-0+(m_iTarget*10));
		}

		//Draw Background
		g.setColor(169,148,113);
		g.fillRect(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);

		//Draw Text
		g.setColor(251,205,80);
		m_oCanvas.DrawString("City trade center",(m_oCanvas.m_iWidth/2)-55,0,true,1);

		m_oCanvas.DrawString(m_sCityName,(m_oCanvas.m_iWidth/2)-35,13,true,1);

		g.setColor(200,200,0);
		
		if(m_iTarget == 1) // Grain
		{
			//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)- 8,26,true,1);
/*
				stringBuffer.delete(0,stringBuffer.length());
				stringBuffer.append((int)(m_iFoodPriceBuy*m_iAmount));
				stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
*/
			m_oCanvas.DrawString((m_iFoodPriceBuy*m_iAmount),(m_oCanvas.m_iWidth/2)+29,26,false,1);

			//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)+57,26,true,1);
			/*
				stringBuffer.delete(0,stringBuffer.length());
				stringBuffer.append((int)(m_iFoodPriceSell*m_iAmount));
				stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
			*/
			m_oCanvas.DrawString((m_iFoodPriceSell*m_iAmount),(m_oCanvas.m_iWidth/2)-26,26,false,1);
		}
		if(m_iTarget == 2) // Kapers
		{
			//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)- 8,26,true,1);
			/*
				stringBuffer.delete(0,stringBuffer.length());
				stringBuffer.append((int)(m_iPiratePriceBuy*m_iAmount));
				stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
			*/
			m_oCanvas.DrawString((m_iPiratePriceBuy*m_iAmount),(m_oCanvas.m_iWidth/2)+29,26,false,1);

			//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)+57,26,true,1);
			/*
				stringBuffer.delete(0,stringBuffer.length());
				stringBuffer.append((int)(m_iPiratePriceSell*m_iAmount));
				stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
				*/
			m_oCanvas.DrawString((m_iPiratePriceSell*m_iAmount),(m_oCanvas.m_iWidth/2)-26,26,false,1);
		}

		if(m_iTarget == 3) // Cannons
		{
			//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)- 8,26,true,1);
			/*
				stringBuffer.delete(0,stringBuffer.length());
				stringBuffer.append((int)(m_iCannonPriceBuy*m_iAmount));
				stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
				*/
			m_oCanvas.DrawString((m_iCannonPriceBuy*m_iAmount),(m_oCanvas.m_iWidth/2)+29,26,false,1);

			//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)+57,26,true,1);
			/*
				stringBuffer.delete(0,stringBuffer.length());
				stringBuffer.append((int)(m_iCannonPriceSell*m_iAmount));
				stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
				*/
			m_oCanvas.DrawString((m_iCannonPriceSell*m_iAmount),(m_oCanvas.m_iWidth/2)-26,26,false,1);

		}

		g.setColor(200,200,200);
		m_oCanvas.DrawString("Buy:" ,(m_oCanvas.m_iWidth/2)+10,26,false,1);
		m_oCanvas.DrawString("Sell:",(m_oCanvas.m_iWidth/2)-50,26,false,1);
		
		g.setClip(0,40,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
		

		m_oCanvas.DrawString("Amount",(m_oCanvas.m_iWidth/2)-30,iYOffSet+(m_oCanvas.m_iHeight/2)-50,false,1);
		/*
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iAmount);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
		*/
		m_oCanvas.DrawString(m_iAmount,(m_oCanvas.m_iWidth/2)+15,iYOffSet+(m_oCanvas.m_iHeight/2)-50,false,1);

		g.setColor(100,100,100);
		m_oCanvas.DrawString("Grain",(m_oCanvas.m_iWidth/2)-40,iYOffSet+(m_oCanvas.m_iHeight/2)-35,false,0);
		m_oCanvas.DrawString("Crew",(m_oCanvas.m_iWidth/2)-40,iYOffSet+(m_oCanvas.m_iHeight/2)-25   ,false,0);
		m_oCanvas.DrawString("Cannons",(m_oCanvas.m_iWidth/2)-40,iYOffSet+(m_oCanvas.m_iHeight/2)-15,false,0);

		if(m_oCanvas.m_oStateGame.m_iShipLvl == 1)m_oCanvas.DrawString("Ship L2",(m_oCanvas.m_iWidth/2)-40,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,0);
		if(m_oCanvas.m_oStateGame.m_iShipLvl == 2)m_oCanvas.DrawString("Ship L3",(m_oCanvas.m_iWidth/2)-40,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,0);
		if(m_oCanvas.m_oStateGame.m_iShipLvl == 3)m_oCanvas.DrawString("Ship L4",(m_oCanvas.m_iWidth/2)-40,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,0);
		if(m_oCanvas.m_oStateGame.m_iShipLvl == 4)m_oCanvas.DrawString("Ship L5",(m_oCanvas.m_iWidth/2)-40,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,0);
		if(m_oCanvas.m_oStateGame.m_iShipLvl == 5)m_oCanvas.DrawString("Ship L6",(m_oCanvas.m_iWidth/2)-40,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,0);

		for(int i=0;i< 4;i++)
		{
			m_oCanvas.DrawString("<>",(m_oCanvas.m_iWidth/2)+7,iYOffSet+(m_oCanvas.m_iHeight/2)-35+(i*10),false,0);
		}

		g.setColor(200,200,0);

		//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)+40,iYOffSet+(m_oCanvas.m_iHeight/2)-35,true,1);
/*
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iFoodPriceBuy);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
*/
		m_oCanvas.DrawString(m_iFoodPriceBuy,(m_oCanvas.m_iWidth/2)+20,iYOffSet+(m_oCanvas.m_iHeight/2)-35,false,3);

		//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)+40,iYOffSet+(m_oCanvas.m_iHeight/2)-25,true,1);
/*
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iPiratePriceBuy);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
*/
		m_oCanvas.DrawString(m_iPiratePriceBuy,(m_oCanvas.m_iWidth/2)+20,iYOffSet+(m_oCanvas.m_iHeight/2)-25,false,3);

		//m_oCanvas.DrawString("g",(m_oCanvas.m_iWidth/2)+40,iYOffSet+(m_oCanvas.m_iHeight/2)-15,true,1);
/*
			stringBuffer.delete(0,stringBuffer.length());
			stringBuffer.append((int)m_iCannonPriceBuy);
			stringBuffer.getChars(0,stringBuffer.length(),stringChars,0);
*/
		m_oCanvas.DrawString(m_iCannonPriceBuy,(m_oCanvas.m_iWidth/2)+20,iYOffSet+(m_oCanvas.m_iHeight/2)-15,false,3);


		if(m_oCanvas.m_oStateGame.m_iShipLvl == 1)m_oCanvas.DrawString("150",                                   (m_oCanvas.m_iWidth/2)+20,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,3);
		if(m_oCanvas.m_oStateGame.m_iShipLvl == 2)m_oCanvas.DrawString("200",                                   (m_oCanvas.m_iWidth/2)+20,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,3);
		if(m_oCanvas.m_oStateGame.m_iShipLvl == 3)m_oCanvas.DrawString("400",                                   (m_oCanvas.m_iWidth/2)+20,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,3);
		if(m_oCanvas.m_oStateGame.m_iShipLvl == 4)m_oCanvas.DrawString("600",                                  (m_oCanvas.m_iWidth/2)+20,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,3);
		if(m_oCanvas.m_oStateGame.m_iShipLvl == 5)m_oCanvas.DrawString("1500",                                  (m_oCanvas.m_iWidth/2)+20,iYOffSet+(m_oCanvas.m_iHeight/2)-5,false,3);

		g.setColor(100,0,0);
		m_oCanvas.DrawString("Exit",(m_oCanvas.m_iWidth/2)-10,iYOffSet+(m_oCanvas.m_iHeight/2)+10,false,2);

		iYOffSet += 4;
		
		if(m_iTarget == 0)
		{
			g.setClip((m_oCanvas.m_iWidth/2)+55,iYOffSet+(m_oCanvas.m_iHeight/2)-50,10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)+55-131,iYOffSet+(m_oCanvas.m_iHeight/2)-50-63,Graphics.TOP | Graphics.LEFT);
			g.setClip((m_oCanvas.m_iWidth/2)-66,iYOffSet+(m_oCanvas.m_iHeight/2)-50,10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)-66-120,iYOffSet+(m_oCanvas.m_iHeight/2)-50-63,Graphics.TOP | Graphics.LEFT);
		}
		else if(m_iTarget == 5)
		{
			g.setClip((m_oCanvas.m_iWidth/2)+35,iYOffSet+(m_oCanvas.m_iHeight/2)+9,10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)+35-131,iYOffSet+(m_oCanvas.m_iHeight/2)+9-63,Graphics.TOP | Graphics.LEFT);
			g.setClip((m_oCanvas.m_iWidth/2)-46,iYOffSet+(m_oCanvas.m_iHeight/2)+9,10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)-46-120,iYOffSet+(m_oCanvas.m_iHeight/2)+9-63,Graphics.TOP | Graphics.LEFT);
		}
		else if(m_iTarget > 0)
		{
			g.setClip((m_oCanvas.m_iWidth/2)+55,iYOffSet+(m_oCanvas.m_iHeight/2)-45+(m_iTarget*10),10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)+55-131,iYOffSet+(m_oCanvas.m_iHeight/2)-45+(m_iTarget*10)-63,Graphics.TOP | Graphics.LEFT);
			g.setClip((m_oCanvas.m_iWidth/2)-66,iYOffSet+(m_oCanvas.m_iHeight/2)-45+(m_iTarget*10),10,11);
			g.drawImage(m_oCanvas.m_oImageArray[0],(m_oCanvas.m_iWidth/2)-66-120,iYOffSet+(m_oCanvas.m_iHeight/2)-45+(m_iTarget*10)-63,Graphics.TOP | Graphics.LEFT);
		}

		g.setClip(0,0,m_oCanvas.m_iWidth,m_oCanvas.m_iHeight);
	}


	//------------------------------------------------------
	public int Update()
	//------------------------------------------------------
	{
		if(m_iBlink > 0)m_iBlink--;
		
		return 0;
	}	

	//------------------------------------------------------
	public int SoftKey(int iKey)
	//------------------------------------------------------
	{
		if(m_bLocked)return 0;

		if(Canvas.KEY_NUM5 == iKey)
		{
			//Instant gold
			m_oCanvas.m_oStateGame.m_iResourceGold   += m_oCanvas.m_oStateGame.m_iAddGold;    m_oCanvas.m_oStateGame.m_iAddGold = 0;
			m_oCanvas.m_oStateGame.m_iResourceCorn   += m_oCanvas.m_oStateGame.m_iAddFood;    m_oCanvas.m_oStateGame.m_iAddFood = 0;
			m_oCanvas.m_oStateGame.m_iResourceCannon += m_oCanvas.m_oStateGame.m_iAddCannons; m_oCanvas.m_oStateGame.m_iAddCannons = 0;
			m_oCanvas.m_oStateGame.m_iResourcePirate += m_oCanvas.m_oStateGame.m_iAddPirates; m_oCanvas.m_oStateGame.m_iAddPirates = 0;
		}		

		if(!(m_oCanvas.m_oStateGame.m_iAddFood    == 0 &&
		   m_oCanvas.m_oStateGame.m_iAddGold      == 0 &&
		   m_oCanvas.m_oStateGame.m_iAddPirates   == 0 &&
		   m_oCanvas.m_oStateGame.m_iAddCannons   == 0))return 0;

		if(iKey == 128)return 0;

		int keyState  = m_oCanvas.getGameAction(iKey);

		if(keyState == Canvas.LEFT) // Sell
		{
			if(m_iTarget == 0 && m_iAmount >= 10)
			{
				if(m_iAmount == 10)m_iAmount = 1;
				else               m_iAmount-= 10;
			}

			if(m_iTarget == 1) // Grain
			{
				if(m_oCanvas.m_oStateGame.m_iResourceCorn < m_iAmount)return 0;

				m_oCanvas.m_oStateGame.m_iAddFood -= m_iAmount;
				m_oCanvas.m_oStateGame.m_iAddGold = +m_iFoodPriceSell * m_iAmount;
			}
			if(m_iTarget == 2) // Kapers
			{
				if(m_oCanvas.m_oStateGame.m_iResourcePirate < m_iAmount)return 0;

				m_oCanvas.m_oStateGame.m_iAddPirates -= m_iAmount;
				m_oCanvas.m_oStateGame.m_iAddGold = +m_iPiratePriceSell * m_iAmount;
			}
			if(m_iTarget == 3) // Cannons
			{
				if(m_oCanvas.m_oStateGame.m_iResourceCannon < m_iAmount)return 0;

				m_oCanvas.m_oStateGame.m_iAddCannons -= m_iAmount;
				m_oCanvas.m_oStateGame.m_iAddGold = +m_iCannonPriceSell * m_iAmount;
			}
			if(m_iTarget == 4) // Ship
			{
				     if(m_oCanvas.m_oStateGame.m_iShipLvl == 6)
				     {
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCorn    > 500){m_iBlink = 20;m_cTarget = 1;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourcePirate  > 350){m_iBlink = 20;m_cTarget = 2;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCannon  > 100){m_iBlink = 20;m_cTarget = 3;return 0;}
				    	 
				    	 m_oCanvas.m_oStateGame.m_iAddGold = 1500;
				     }
				     else if(m_oCanvas.m_oStateGame.m_iShipLvl == 5)
				     {
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCorn    > 400){m_iBlink = 20;m_cTarget = 1;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourcePirate  > 200){m_iBlink = 20;m_cTarget = 2;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCannon  >  60){m_iBlink = 20;m_cTarget = 3;return 0;}
				    	 
				    	 m_oCanvas.m_oStateGame.m_iAddGold = 600;
				     }
				     else if(m_oCanvas.m_oStateGame.m_iShipLvl == 4)
				     {
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCorn    > 300){m_iBlink = 20;m_cTarget = 1;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourcePirate  > 100){m_iBlink = 20;m_cTarget = 2;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCannon  >  30){m_iBlink = 20;m_cTarget = 3;return 0;}

				    	 m_oCanvas.m_oStateGame.m_iAddGold =  400;				    	 
				     }
				     else if(m_oCanvas.m_oStateGame.m_iShipLvl == 3)
				     {
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCorn    > 200){m_iBlink = 20;m_cTarget = 1;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourcePirate  >  50){m_iBlink = 20;m_cTarget = 2;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCannon  >  15){m_iBlink = 20;m_cTarget = 3;return 0;}
				    	 
				    	 m_oCanvas.m_oStateGame.m_iAddGold =  200;
				     }
				     else if(m_oCanvas.m_oStateGame.m_iShipLvl == 2)
				     {
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCorn    > 100){m_iBlink = 20;m_cTarget = 1;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourcePirate  >  35){m_iBlink = 20;m_cTarget = 2;return 0;}
				    	 if(m_oCanvas.m_oStateGame.m_iResourceCannon  >   7){m_iBlink = 20;m_cTarget = 3;return 0;}
				    	 
				    	 m_oCanvas.m_oStateGame.m_iAddGold =  150;				    	 
				     }

				     else return 0;

				m_oCanvas.m_oStateGame.m_iShipLvl--;
			}
		}
		if(keyState == Canvas.RIGHT) // Buy
		{
			if(m_iTarget == 0 && m_iAmount < 500)
			{
				if(m_iAmount == 1)m_iAmount = 10;
				else              m_iAmount+= 10;
			}

			if(m_iTarget == 1) // Grain
			{
				if(m_oCanvas.m_oStateGame.m_iResourceGold >= (m_iFoodPriceBuy * m_iAmount))
				{
					if(100 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 1)
					{
						m_iAmount = 100 - m_oCanvas.m_oStateGame.m_iResourceCorn;
						if(100 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 1)
						return 0;
					}
					if(200 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
					{
						m_iAmount = 200 - m_oCanvas.m_oStateGame.m_iResourceCorn;
						if(200 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
						return 0;
					}
					if(300 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 3)
					{
						m_iAmount = 300 - m_oCanvas.m_oStateGame.m_iResourceCorn;
						if(300 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
						return 0;
					}
					if(400 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 4)
					{
						m_iAmount = 400 - m_oCanvas.m_oStateGame.m_iResourceCorn;
						if(400 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
						return 0;
					}
					if(500 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 5)
					{
						m_iAmount = 500 - m_oCanvas.m_oStateGame.m_iResourceCorn;
						if(500 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCorn) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
						return 0;
					}
					m_oCanvas.m_oStateGame.m_iAddFood += m_iAmount;
					m_oCanvas.m_oStateGame.m_iAddGold = -m_iFoodPriceBuy * m_iAmount;
				}
			}
			if(m_iTarget == 2) // Kapers
			{
				if(m_oCanvas.m_oStateGame.m_iResourceGold >= (m_iPiratePriceBuy * m_iAmount))
				{
					if( 35 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 1)
					{
						m_iAmount = 35 - m_oCanvas.m_oStateGame.m_iResourcePirate;
						if( 35 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 1)
						return 0;
					}
					if( 50 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
					{
						m_iAmount = 50 - m_oCanvas.m_oStateGame.m_iResourcePirate;
						if( 50 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
						return 0;

					}
					if(100 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 3)
					{
						m_iAmount = 100 - m_oCanvas.m_oStateGame.m_iResourcePirate;
						if(100 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)

						return 0;
					}
					if(200 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 4)
					{
						m_iAmount = 200 - m_oCanvas.m_oStateGame.m_iResourcePirate;
						if(200 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 4)
						return 0;
					}
					if(350 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 5)
					{
						m_iAmount = 350 - m_oCanvas.m_oStateGame.m_iResourcePirate;
						if(350 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourcePirate) && m_oCanvas.m_oStateGame.m_iShipLvl == 5)
						return 0;
					}
					m_oCanvas.m_oStateGame.m_iAddPirates += m_iAmount;
					m_oCanvas.m_oStateGame.m_iAddGold    = -m_iPiratePriceBuy * m_iAmount;
				}
			}
			if(m_iTarget == 3) // Cannons
			{
				if(m_oCanvas.m_oStateGame.m_iResourceGold >= (m_iCannonPriceBuy * m_iAmount))
				{
					if(  7 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 1)
					{
						m_iAmount = 7 - m_oCanvas.m_oStateGame.m_iResourceCannon;
						if(  7 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 1)
						return 0;
					}
					if( 15 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
					{
						m_iAmount = 15 - m_oCanvas.m_oStateGame.m_iResourceCannon;
						if( 15 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 2)
						return 0;
					}
					if( 30 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 3)
					{
						m_iAmount = 30 - m_oCanvas.m_oStateGame.m_iResourceCannon;
						if( 30 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 3)
						return 0;
					}
					if( 60 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 4)
					{
						m_iAmount = 60 - m_oCanvas.m_oStateGame.m_iResourceCannon;
						if( 60 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 4)
						return 0;
					}
					if(100 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 5)
					{
						m_iAmount = 100 - m_oCanvas.m_oStateGame.m_iResourceCannon;
						if(100 < (m_iAmount+m_oCanvas.m_oStateGame.m_iResourceCannon) && m_oCanvas.m_oStateGame.m_iShipLvl == 5)
						return 0;
					}

					m_oCanvas.m_oStateGame.m_iAddCannons += m_iAmount;
					m_oCanvas.m_oStateGame.m_iAddGold = -m_iCannonPriceBuy * m_iAmount;
				}
			}
			if(m_iTarget == 4) // Ship
			{
				     if(m_oCanvas.m_oStateGame.m_iShipLvl == 5 && m_oCanvas.m_oStateGame.m_iResourceGold >= 1500)m_oCanvas.m_oStateGame.m_iAddGold = -1500;
				else if(m_oCanvas.m_oStateGame.m_iShipLvl == 4 && m_oCanvas.m_oStateGame.m_iResourceGold >=  600)m_oCanvas.m_oStateGame.m_iAddGold = - 600;
				else if(m_oCanvas.m_oStateGame.m_iShipLvl == 3 && m_oCanvas.m_oStateGame.m_iResourceGold >=  400)m_oCanvas.m_oStateGame.m_iAddGold = - 400;
				else if(m_oCanvas.m_oStateGame.m_iShipLvl == 2 && m_oCanvas.m_oStateGame.m_iResourceGold >=  200)m_oCanvas.m_oStateGame.m_iAddGold = - 200;
				else if(m_oCanvas.m_oStateGame.m_iShipLvl == 1 && m_oCanvas.m_oStateGame.m_iResourceGold >=  150)m_oCanvas.m_oStateGame.m_iAddGold = - 150;
				else return 0;

				m_oCanvas.m_oStateGame.m_iShipLvl++;
			}
		}
		
		if(keyState == Canvas.UP)
		{
			     if(m_iTarget  > 0)m_iTarget--;
			else if(m_iTarget == 0)m_iTarget = 5;
		}
		if(keyState == Canvas.DOWN)
		{
			     if(m_iTarget  < 5)m_iTarget++;
			else if(m_iTarget == 5)m_iTarget = 0;
		}
		if(keyState == Canvas.FIRE)
		{
			if(m_iTarget == 5)return -1;
		}

		if(m_iAmount <= 9)m_iAmount = 1;

		return 0;
	}
}
