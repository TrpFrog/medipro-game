package net.trpfrog.medipro_game.mini_game.macho_game.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.symbol.Symbol;

import net.trpfrog.medipro_game.mini_game.macho_game.util.GameTimer;

import javax.swing.*;
import java.awt.*;

public class Counter extends Symbol implements Drawable
{
	private	String	count = "";

	public Counter()
	{
		// 初期位置の設定
		setLocation( 512, 512 );

		// Drawableを乗せる
		setDrawer( this );
	}

	public void setCount( long l ) 
	{
		count = Long.toString( l );
	}

	@Override
	public void draw( Graphics2D g )
	{
        var		mv = MainView.getInstance();

		g.setColor( Color.WHITE );
		g.setFont( new Font( g.getFont().getName(), g.getFont().getStyle(), 100 ) );
		g.translate( ( mv.getWidth() / 2 ) - ( g.getFontMetrics().stringWidth( count ) / 2 ) , g.getFont().getSize() );
		g.drawString( count, 0, 0 );
	}
}
