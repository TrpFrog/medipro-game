package net.trpfrog.medipro_game.mainmenu;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.space.SpaceScene;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainMenuView extends GameView {

    private JButton start, quit;
    private String loadingMessage = "Now loading";
    private Timer mainMenuTimer = new Timer(100, e ->{ repaint(); loadingMessage += "."; });
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

    public MainMenuView(GameModel model) {
        super(model);

        Rectangle buttonArea = new Rectangle(400, 300);
        int w = MainView.getInstance().getWidth();
        int h = MainView.getInstance().getHeight();

        setBorder(BorderFactory.createEmptyBorder(
                (h - buttonArea.height)/2,
                (w - buttonArea.width)/2,
                (h - buttonArea.height)/2,
                (w - buttonArea.width)/2
        ));

        start = new JButton("START");
        quit  = new JButton("QUIT");

        start.addActionListener(e -> SceneManager.getInstance().push(new SpaceScene()));
        quit .addActionListener(e -> SceneManager.getInstance().pop());

        setLayout(new BorderLayout());
        add(start, BorderLayout.NORTH);
        add(quit, BorderLayout.SOUTH);

        mvWidth = mainView.getWidth();
        mvHeight = mainView.getHeight();
        setFont(strFont);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawString(loadingMessage,mainView.getWidth()/2,mainView.getHeight()/2);
        g.drawImage(mainMenuImage,0,0,mvWidth+=1,mvHeight+=1,null);
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
