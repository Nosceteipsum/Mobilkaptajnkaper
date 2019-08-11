
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

//------------------------------------------------------
public class Kaper extends MIDlet implements CommandListener
//------------------------------------------------------
{
	// Public:
	public boolean m_bPaused;
	public boolean m_bSnd;

	// Protected:
	// Private:

	public KaperCanvas m_oKaperCanvas;

//	private Command m_oSndOffCommand;
//	private Command m_oSndOnCommand;
//	private Command m_oExitCommand;


	//------------------------------------------------------
	public Kaper()
	//------------------------------------------------------
	{
		m_oKaperCanvas = null;
		m_bSnd         = false;

//		m_oExitCommand   = new Command("EXIT",Command.EXIT, 2);
//		m_oSndOffCommand = new Command("Snd Off",Command.OK, 1);
//		m_oSndOnCommand  = new Command("Snd On ",Command.OK, 1);
	}

	//------------------------------------------------------
	public void pauseApp()
	//------------------------------------------------------
	{
		m_bPaused = true;

		// Disable sound
		m_bSnd = false;
//		m_oKaperCanvas.removeCommand(m_oSndOnCommand);
//		m_oKaperCanvas.addCommand(m_oSndOffCommand);
		if(m_oKaperCanvas != null)m_oKaperCanvas.SoftButtonSND(m_bSnd);
	}

	//------------------------------------------------------
	public void destroyApp(boolean unconditional)
	//------------------------------------------------------
	{
		m_bPaused = true;


		if(m_oKaperCanvas != null)
		{
			if(m_oKaperCanvas.m_cGameMode == 2)
			{
				m_oKaperCanvas.m_oStateGame.SaveGame();
			}
		}

	}

	//------------------------------------------------------
	public void startApp() throws MIDletStateChangeException
	//------------------------------------------------------
	{
		m_bPaused = false;

		if(m_oKaperCanvas == null)
		{
			Display m_oDisplay  = Display.getDisplay(this);
			m_oKaperCanvas      = new KaperCanvas();
			m_oKaperCanvas.m_oKaperApp = this;

			m_oKaperCanvas.Init(0);

//			m_oKaperCanvas.addCommand(m_oExitCommand);
//			m_oKaperCanvas.addCommand(m_oSndOffCommand);
//			m_oKaperCanvas.addCommand(new Command("EXIT",Command.CANCEL, 1));
			
			Thread runner = new Thread(m_oKaperCanvas);
			runner.start();

			m_oDisplay.setCurrent(m_oKaperCanvas);

			m_oKaperCanvas.setCommandListener(this);

			m_oKaperCanvas.repaint();
			m_oKaperCanvas.serviceRepaints();
		}

	}

	//------------------------------------------------------
	public void commandAction(Command c,Displayable s)
	//------------------------------------------------------
	{
		/*

//		if(c == m_oExitCommand)
//		{
			if(m_oKaperCanvas != null)m_oKaperCanvas.ShutDown();
			else
			{
				destroyApp(false);
				notifyDestroyed();
			}
//		}

		if(c == m_oSndOnCommand || c == m_oSndOffCommand)
		{
			if(m_bSnd == true)
			{
				m_bSnd = false;
				m_oKaperCanvas.removeCommand(m_oSndOnCommand);
				m_oKaperCanvas.addCommand(m_oSndOffCommand);
			}
			else
			{
		        m_bSnd = true;
				m_oKaperCanvas.removeCommand(m_oSndOffCommand);
				m_oKaperCanvas.addCommand(m_oSndOnCommand);
			}
			if(m_oKaperCanvas != null)m_oKaperCanvas.SoftButtonSND(m_bSnd);
		}
*/
	}

}