package net.trpfrog.medipro_game.mini_game.macho_game.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UpperArm extends Symbol implements Drawable
{
	private Image	upperArmImage;
	private Image	foreArmImage;

	public UpperArm()
	{

		// 画像の設定
		Path	upperArmImagePath = Paths.get( ".", "resource", "mini_game", "macho_game", "arm_upper.png" );

		upperArmImage = Toolkit.getDefaultToolkit().getImage( String.valueOf( upperArmImagePath ) );

		// 初期位置の設定
		setLocation( 512, 512 );

		// Drawableを乗せる
		setDrawer( this );
	}

	@Override
	public void draw( Graphics2D g )
	{
		Rectangle	rect = new Rectangle( (int)getX(), (int)getY(), 0, 0 );
		rect.grow( 512, 512 );

		g.drawImage( upperArmImage, (int)rect.getX(),     (int)rect.getY(),
                                    (int)rect.getWidth(), (int)rect.getHeight(), null
		);
	}
}
