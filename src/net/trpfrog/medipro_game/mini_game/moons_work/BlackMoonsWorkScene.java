package net.trpfrog.medipro_game.mini_game.moons_work;

public class BlackMoonsWorkScene extends MoonsWorkScene {
    public BlackMoonsWorkScene() {
        super(2);
        MoonsWorkModel.clearCondition = 30;
        setGameTitle("BLACK Moon's Work");
        setCreatorName("もうへとへと");
        setGameDescription("もっと！速く！");
        makeDescriptionDialog();
    }
}
