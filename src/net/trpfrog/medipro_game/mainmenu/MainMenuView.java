package net.trpfrog.medipro_game.mainmenu;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.SpaceScene;
import net.trpfrog.medipro_game.space.symbols.Star;
import net.trpfrog.medipro_game.util.CustomFont;
import net.trpfrog.medipro_game.util.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainMenuView extends GameView {

    private JButton start, quit;
    private String loadingMessage = "Now loading";
    private Timer mainMenuTimer = new Timer(50, e ->{ repaint(); loadingMessage += "."; });
    private Font strFont = new Font("",Font.BOLD,30);
    private MainView mainView = MainView.getInstance();
    private int mvWidth, mvHeight;
    private Path[] imagePath = {
                Paths.get(".","resource","main_menu","main_menu1.jpg"),
                Paths.get(".","resource","main_menu","main_menu2.jpg"),
                Paths.get(".","resource","main_menu","main_menu3.jpg"),
                Paths.get(".","resource","main_menu","main_menu4.jpg")
            };
    private Path mainMenuImagePath = imagePath[(int)(Math.random()*imagePath.length)];
    private Image mainMenuImage = Toolkit.getDefaultToolkit().getImage(mainMenuImagePath.toString());
    private Font titleFont = new Font(CustomFont.LETS_GO_DIGITAL, Font.PLAIN, 50);

    public MainMenuView(GameModel model) {
        super(model);
        setBGM(MusicPlayer.MENU_THEME);

        Rectangle buttonArea = new Rectangle(300, 100);

        int w = MainView.getInstance().getWidth();
        int h = MainView.getInstance().getHeight();

        setBorder(BorderFactory.createEmptyBorder(
                (h - buttonArea.height)/2+100,
                (w - buttonArea.width)/2,
                (h - buttonArea.height)/2-100,
                (w - buttonArea.width)/2
        ));

        start = new JButton("START");
        quit  = new JButton("QUIT");
        start.setPreferredSize(new Dimension(50,50));
        quit.setPreferredSize(new Dimension(50,50));

        start.addActionListener(e -> SceneManager.getInstance().push(new SpaceScene()));
        quit .addActionListener(e -> SceneManager.getInstance().pop());

        setLayout(new BorderLayout());
        add(start, BorderLayout.NORTH);
        add(quit, BorderLayout.SOUTH);

        mvWidth = mainView.getWidth();
        mvHeight = mainView.getHeight();
        setFont(strFont);

        JLabel titleLabel = new JLabel("Space Wandering!!");
        add(titleLabel);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        // ローディングの文字と背景画像の描画
        g.drawString(loadingMessage,mainView.getWidth()/2,mainView.getHeight()/2+200);
        g.drawImage(mainMenuImage,0,0,mvWidth+=1,mvHeight+=1,null);

        // タイトル描画エリア
        int titleWidth = 450;
        int titleHeight = 200;
        g.setColor(Color.GRAY);
        g.fill3DRect(mainView.getWidth()/2-titleWidth/2,100,titleWidth,titleHeight,false);
        // タイトルに星をキラキラさせる
        Star star = new Star();
        star.setX(Math.random()*mainView.getWidth());
        star.setY(Math.random()*(mainView.getHeight()/2));
        star.getDrawer().draw((Graphics2D) g);

        // タイトルの枠線
        g.setColor(Color.BLACK);
        g.drawRect(mainView.getWidth()/2-titleWidth/2,100,titleWidth,titleHeight);
        setFont(titleFont);
        g.setColor(Color.WHITE);
        g.drawString("Space Wandering!!",mainView.getWidth()/2-titleWidth/2+30,200);


    }

    @Override
    public void suspend() {
        start.setEnabled(false);
        quit.setEnabled(false);
        mainMenuTimer.stop();
    }

    @Override
    public void resume() {
        start.setEnabled(true);
        quit.setEnabled(true);
        mainMenuTimer.start();
    }
}
