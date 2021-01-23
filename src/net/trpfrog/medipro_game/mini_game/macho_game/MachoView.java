package net.trpfrog.medipro_game.mini_game.macho_game;

import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;


import javax.swing.*;
import java.awt.*;

public class MachoView extends GameView
{
	private MachoModel  model;
	private Timer       paintTimer = new Timer( 10, g -> repaint() );

	public MachoView( MachoModel model )
	{
		super( model );
		this.model = model;
		setBackground( new Color( 0x111111 ) );
	}


	@Override
	protected void paintComponent( Graphics g )
	{
		super.paintComponent( g );

		// 背景を描画
		model.getBackground().getDrawer().draw( (Graphics2D)g );

		// 腕を描画
		model.getUpperArm().getDrawer().draw( (Graphics2D)g );
		model.getForeArm().getDrawer().draw(  (Graphics2D)g );

		//	マッスルゲージを描画
		model.getMuscleGauge().getDrawer().draw( (Graphics2D)g );

		//	カウンターを描画
		model.getCounter().getDrawer().draw( (Graphics2D)g );

		model.loop();

	}


	// paint用のタイマーを制御
	@Override
	public void suspend()
	{
		// paintTimer.stop();
	}

	@Override
	public void resume()
	{
		paintTimer.start();
	}
}
