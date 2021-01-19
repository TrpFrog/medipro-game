package net.trpfrog.medipro_game.space.ui;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.space.SpaceModel;
import net.trpfrog.medipro_game.space.symbols.Rocket;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IndicatorUI implements Drawable {
    private MainView mainView;
    private SpaceModel model;
    private Rocket rocket;
    private Rectangle drawRange;
    private final float alpha = 0.8f;
    private Calendar calendar;
    private SimpleDateFormat sdf;
    private String contentStr;

    public IndicatorUI(SpaceModel model){
        this.model = model;
        this.rocket = model.getRocket();
        this.mainView = MainView.getInstance();
        this.sdf = new SimpleDateFormat("[HH:mm] ");
    }

    @Override
    public void draw(Graphics2D g) {

        //現在時刻の取得
        calendar = Calendar.getInstance();

        //描画する文字列
        contentStr = sdf.format(calendar.getTime())+ "MapNo."+ rocket.getDepth()+"  CLEARED:0"+
                "   (x,y) = ("+ (int)rocket.getX() +","+ (int)rocket.getY()+")";

        drawRange = new Rectangle(10,10,contentStr.length()*10,25);

        // 書き出し位置
        int rangeX = (int)drawRange.getX()+20;
        int rangeY = (int)drawRange.getY()+25;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        //背景の色
        g.setColor(Color.BLACK);
        g.fill(drawRange);
        //文字列の色
        g.setColor(new Color(2686840));
        g.setFont(new Font("Dialog",Font.PLAIN,20));

       //文字列の描画
        g.drawString(contentStr,rangeX,rangeY-6);
    }
}
