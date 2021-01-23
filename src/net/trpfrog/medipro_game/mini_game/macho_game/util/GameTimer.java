package net.trpfrog.medipro_game.mini_game.macho_game.util;

import javax.swing.*;
import java.awt.*;

public class GameTimer
{
	private boolean	enabled;
	private long	startTime;
	private long	setTime;
	private long	elapsedTime;
	// private long	stoppedTime;

	public GameTimer( long milliSeconds )
	{
		enabled     = false;
		setTime     = milliSeconds;
		startTime   = 0;
		elapsedTime = 0;
		// stoppedTime = 0;
	}

	public void start()
	{
		startTime = System.currentTimeMillis();
		enabled = true;
	}

	public void stop()
	{
		setTime     = getRemainingTime();
		// stoppedTime = setTime;
		enabled     = false;
	}

	public boolean isLimit()
	{
		if( !enabled ) return false;
		if( setTime - elapsedTime <= 0 ) {
			elapsedTime = setTime;
			return true;
		}
		elapsedTime = System.currentTimeMillis() - startTime;
		return elapsedTime >= setTime;
	}

	public long getRemainingTime()
	{
		if( setTime < 0 ) setTime = 0;
		if( !enabled ) return setTime;
		return setTime - elapsedTime;
	}
}