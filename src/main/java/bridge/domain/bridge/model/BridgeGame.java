package bridge.domain.bridge.model;

import bridge.domain.game.GamePlayer;
import bridge.ui.output.dto.BridgeGameMapState;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {
    private final BridgeMap bridgeMap;
    private final GamePlayer gamePlayer;

    private BridgeGame(BridgeMap bridgeMap, GamePlayer gamePlayer) {
        this.bridgeMap = bridgeMap;
        this.gamePlayer = gamePlayer;
    }

    public static BridgeGame of(BridgeMap bridgeMap, GamePlayer gamePlayer) {
        return new BridgeGame(bridgeMap, gamePlayer);
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void move(BridgeDirection bridgeDirection) {
        movePlayerByDirection(bridgeDirection);
    }

    private void movePlayerByDirection(BridgeDirection bridgeDirection) {
        int position = gamePlayer.getPosition();
        gamePlayer.move();
        if (!bridgeMap.isCorrectDirection(position, bridgeDirection)) {
            gamePlayer.stopMoving();
        }
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
    }

    public BridgeGameMapState toBridgeGameMapState() {
        return new BridgeGameMapState(
                bridgeMap,
                gamePlayer.getPosition(),
                gamePlayer.isMovable()
        );
    }
}
