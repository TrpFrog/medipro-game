package net.trpfrog.medipro_game.mini_game.macho_game;

import net.trpfrog.medipro_game.mini_game.macho_game.symbols.Background;
import net.trpfrog.medipro_game.mini_game.macho_game.symbols.Counter;
import net.trpfrog.medipro_game.mini_game.macho_game.symbols.UpperArm;
import net.trpfrog.medipro_game.mini_game.macho_game.symbols.ForeArm;
import net.trpfrog.medipro_game.mini_game.macho_game.symbols.MuscleGauge;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.mini_game.GameOverWindow;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.player.Medal;
import net.trpfrog.medipro_game.player.MedalWindow;

import net.trpfrog.medipro_game.mini_game.macho_game.util.GameTimer;

import javax.swing.*;
import java.awt.*;


public class MachoModel extends GameModel
{
	private Background  background;
	private UpperArm    upperArm;
	private ForeArm     foreArm;
	private Counter		counter;
	private MuscleGauge	muscleGauge;
	private GameTimer	gameTimer = new GameTimer( 10 * 1000 );

	public MachoModel() {
		background  = new Background();
		upperArm    = new UpperArm();
		foreArm     = new ForeArm();
		counter     = new Counter();
		muscleGauge = new MuscleGauge();
	}

	public UpperArm getUpperArm()
	{
		return upperArm;
	}

	public ForeArm getForeArm()
	{
		return foreArm;
	}

	public Background getBackground()
	{
		return background;
	}

	public MuscleGauge getMuscleGauge()
	{
		return muscleGauge;
	}

	public Counter getCounter()
	{
		return counter;
	}

	public GameTimer getGameTimer()
	{
		return gameTimer;
	}

	public void loop()
	{
		//	タイマー監視
		if( getGameTimer().isLimit() ) {
			if( !getForeArm().isReleased ) {
				// getCounter().setCount( "0" );
				getForeArm().isReleased = true;
				endGame();
			}
		}
		getForeArm().releaseObserver();

		getCounter().setCount( getGameTimer().getRemainingTime() );

		//	マッスルゲージ更新
		double		mg = getForeArm().getAngleDegrees();
		getMuscleGauge().setGaugePercentage( 100.0 / 150.0 * mg );
	}

	public void endGame()
	{
		var		window = new GameOverWindow( "YOUR MUSCLE",
		                                     getMuscleGauge().getHighestPercent(),
		                                     new Color( 0x88E01D00, true )
		);

		var		scene = new DialogBackgroundScene( window, false );

		SceneManager.getInstance().push( scene );
		if( getMuscleGauge().getHighestPercent() < 100 ) return;
		MedalWindow.pushMedalWindow( new Medal(
				"KIN NIKU MUSCLE", new MachoScene().getStarImage() )
		);
	}


	@Override
	public void suspend() {
		gameTimer.stop();
	}

	@Override
	public void resume() {
		gameTimer.start();
	}
}
