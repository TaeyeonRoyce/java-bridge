package bridge;

import bridge.domain.bridge.application.BridgeGameController;
import bridge.domain.bridge.model.BridgeMap;
import bridge.ui.input.InputView;
import bridge.ui.output.OutputView;

public class Application {

    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BridgeGameController bridgeGameController = new BridgeGameController(inputView, outputView);

        bridgeGameController.initGame();

        BridgeNumberGenerator randomNumberGenerator = new BridgeRandomNumberGenerator();
        BridgeMap bridgeMap = bridgeGameController.generateBridgeGameMap(randomNumberGenerator);
    }
}
