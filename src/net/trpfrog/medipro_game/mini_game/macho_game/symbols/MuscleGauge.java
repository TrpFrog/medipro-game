package net.trpfrog.medipro_game.mini_game.macho_game.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.symbol.Symbol;

import net.trpfrog.medipro_game.MainView;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MuscleGauge extends Symbol implements Drawable
{
	private double	percentage = 0.0;
	private double	highestPer = 0.0;

	public MuscleGauge()
	{
		// 初期位置の設定
		setLocation( 512, 512 );

		// Drawableを乗せる
		setDrawer( this );
	}

	public void setGaugePercentage( double percent )
	{
		percentage = percent;
		if( highestPer < percentage ) highestPer = percentage;
	}

	public int getHighestPercent()
	{
		return (int)Math.floor( highestPer );
	}

	@Override
	public void draw( Graphics2D g1 )
	{
		final Graphics2D	g  = ( Graphics2D )g1.create();
		var					mv = MainView.getInstance();

		int					gaugeWidth  = ( mv.getWidth() / 2 ) + ( mv.getWidth() / 4 );
		int					gaugeHeight = 64;

		g.setColor( new Color( 0x333333 ) );
		g.translate( ( mv.getWidth() / 2 ) - ( gaugeWidth / 2 ), mv.getHeight() / 16 );
		g.fillRect( 0, 0, gaugeWidth, gaugeHeight );

		g.setColor( new Color( 0x00ff00 ) );

		if( (int)Math.floor( percentage ) == 100 ) {
			g.setColor( new Color( 0x880088 ) );
		} else if( (int)Math.floor( percentage ) > 75 ) {
			g.setColor( new Color( 0xdd0000 ) );
		} else if( (int)Math.floor( percentage ) > 50 ) {
			g.setColor( new Color( 0xdddd00 ) );
		}

		g.fillRect( 0, 0, (int)Math.floor( percentage * ( (double)gaugeWidth / 100.0 ) ), gaugeHeight );

		try {
		} finally {
			g.dispose();
		}
	}
}
