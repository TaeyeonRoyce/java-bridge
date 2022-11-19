package bridge.domain.bridge.application;

import bridge.BridgeMaker;
import bridge.BridgeNumberGenerator;
import bridge.domain.bridge.model.BridgeDirection;
import bridge.domain.bridge.model.BridgeGame;
import bridge.domain.bridge.model.BridgeMap;
import bridge.domain.game.GamePlayer;
import bridge.ui.input.InputView;
import bridge.ui.output.OutputView;
import java.util.List;

public class BridgeGameController {
    private final InputView inputView;
    private final OutputView outputView;

    public BridgeGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void launchGame() {
        outputView.printInitMessage();
    }

    public BridgeMap generateBridgeGameMap(BridgeNumberGenerator generator) {
        int bridgeSize = readBridgeSize();
        BridgeMaker bridgeMaker = new BridgeMaker(generator);
        List<String> bridge = bridgeMaker.makeBridge(bridgeSize);

        return BridgeMap.from(bridge);
    }

    private int readBridgeSize() {
        return inputView.readBridgeSize().toInteger();
    }

    public void playGame(BridgeMap bridgeMap) {
        BridgeGame bridgeGame = setupGame(bridgeMap);

        do {
            BridgeDirection bridgeDirection = readBridgeDirection();
            bridgeGame.move(bridgeDirection);
            outputView.printMap(bridgeGame.toBridgeGameMapState());
        } while (isPlayable(bridgeGame));
    }

    private BridgeGame setupGame(BridgeMap bridgeMap) {
        GamePlayer gamePlayer = GamePlayer.withDefaultValue();
        return BridgeGame.of(bridgeMap, gamePlayer);
    }

    private BridgeDirection readBridgeDirection() {
        return inputView.readMoving().toBridgeDirection();
    }

    private boolean isPlayable(BridgeGame bridgeGame) {
        if (bridgeGame.isEndCondition()) {
            return false;
        }
        return inputView.readGameCommand();
    }
}
