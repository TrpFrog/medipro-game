package net.trpfrog.medipro_game.mini_game.macho_game.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.Suspendable;
// import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.symbol.Symbol;


import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ForeArm extends Symbol implements Drawable
{
	public	boolean isReleased = false;

	private Image	foreArmImage;
	private Point	centerPoint     = new Point( 512 - 32 + 140, 512 + 32 );
	private double	memorizedDegree = 0;

	public ForeArm()
	{

		// 画像の設定
		Path	foreArmImagePath  = Paths.get( ".", "resource", "mini_game", "macho_game", "arm_fore.png"  );

		foreArmImage  = Toolkit.getDefaultToolkit().getImage( String.valueOf( foreArmImagePath  ) );

		// 初期位置の設定
		setLocation( 512, 512 );

		// Drawableを乗せる
		setDrawer( this );
	}

	public void memorizeDegree()
	{
		memorizedDegree = getAngleDegrees();
	}

	public void releaseObserver()
	{
		if( isReleased ) {
			super.setAngleDegrees( getAngleDegrees() / 2 );
			return;
		}
	}

	public int getPower()
	{
		return (int)Math.floor( getAngleDegrees() );
	}

	@Override
	public void draw( Graphics2D g1 )
	{
		Rectangle	rect = new Rectangle( (int)getX(), (int)getY(), 0, 0 );
		rect.grow( 512, 512 );

		final Graphics2D	g = ( Graphics2D )g1.create();

		g.translate( centerPoint.x, centerPoint.y );
		g.rotate( getAngleDegrees() / ( 180 / Math.PI ) );
		g.translate( -centerPoint.x, -centerPoint.y );
		g.drawImage( foreArmImage, (int)rect.getX(),     (int)rect.getY(),
								   (int)rect.getWidth(), (int)rect.getHeight(), null
		);

		try {
		} finally {
			g.dispose();
		}

		// g.translate( centerPoint.x, centerPoint.y );
		// g.fillRect( -8, -8, 16, 16 );
		// g.translate( -centerPoint.x, -centerPoint.y );
	}

	@Override
	public void setAngleDegrees( double angleDegrees )
	{
		double	ad = memorizedDegree + angleDegrees;

		if( isReleased ) return;

		//	可動域設定
		if( ad <= 0 ) {
			super.setAngleDegrees( 0 );
			return;
		} else if( ad >= 150 ) {
			super.setAngleDegrees( 150 );
			return;
		}

// System.out.println( ad );
		super.setAngleDegrees( ad );
	}
}
