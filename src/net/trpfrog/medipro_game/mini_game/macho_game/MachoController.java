package net.trpfrog.medipro_game.mini_game.macho_game;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.pause.EscapeToPause;
import net.trpfrog.medipro_game.scene.GameController;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MachoController extends GameController implements MouseListener, MouseMotionListener, KeyListener
{
	private MachoModel	model;
	private MachoView	view;
	private Point		clickPoint;

	public MachoController( MachoModel model, MachoView view )
	{
		super(model, view);
		this.model = model;
		this.view = view;
		clickPoint = new Point();

		// view に listener を add する
		view.addKeyListener( new EscapeToPause() );
		view.addKeyListener( this );
        view.addMouseMotionListener( this );
        view.addMouseListener( this );
	}

	@Override
	public void mousePressed( MouseEvent e )
	{
		clickPoint = e.getPoint();
		model.getForeArm().memorizeDegree();
// System.out.println( clickPoint );
	}

	@Override
	public void mouseEntered( MouseEvent e ) {}

	@Override
	public void mouseReleased( MouseEvent e ) {}

	@Override
	public void mouseClicked( MouseEvent e ) {}

	@Override
	public void mouseExited( MouseEvent e ) {}


	@Override
	public void mouseDragged( MouseEvent e )
	{
		// model.getForeArm().setAngleDegrees( (double)( ( e.getPoint().x - clickPoint.x ) ) );
		model.getForeArm().setAngleDegrees( (double)( ( e.getPoint().x - clickPoint.x ) / 256 ) );
	}

	@Override
	public void mouseMoved( MouseEvent e ) {}

	@Override
	public void keyTyped( KeyEvent e ) {}

	@Override
	public void keyPressed( KeyEvent e ) {}

	@Override
	public void keyReleased( KeyEvent e ) {}

	@Override
	public void suspend() {}

	@Override
	public void resume() {}
}
