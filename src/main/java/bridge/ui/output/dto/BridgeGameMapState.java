package bridge.ui.output.dto;

import bridge.domain.bridge.model.BridgeDirection;
import bridge.domain.bridge.model.BridgeMap;
import bridge.ui.ConsolePrintable;
import bridge.ui.output.ResultOutputConstants;
import java.util.ArrayList;
import java.util.List;

public class BridgeGameMapState implements ConsolePrintable {
    private final BridgeMap bridgeMap;
    private final int movedAmount;
    private final boolean movable;

    public BridgeGameMapState(BridgeMap bridgeMap, int movedAmount, boolean movable) {
        this.movedAmount = movedAmount;
        this.bridgeMap = bridgeMap;
        this.movable = movable;
    }

    @Override
    public String toConsolePrintFormat() {
        List<String> upwardMap = new ArrayList<>();
        List<String> downwardMap = new ArrayList<>();

        fillBridgeGameMap(upwardMap, downwardMap);
        if (!movable) {
            updateWrongSelection(upwardMap, downwardMap);
        }
        return joinToBridgeMap(upwardMap, downwardMap);
    }

    private String joinToBridgeMap(List<String> upwardMap, List<String> downwardMap) {
        String upward = String.join(ResultOutputConstants.BRIDGE_STEP_DIAMETER, upwardMap);
        String downward = String.join(ResultOutputConstants.BRIDGE_STEP_DIAMETER, downwardMap);

        return String.format(ResultOutputConstants.BRIDGE_MAP_FORMAT, upward, downward);
    }

    private void fillBridgeGameMap(List<String> upwardMap, List<String> downwardMap) {
        List<BridgeDirection> bridgeDirections = bridgeMap.getBridgeDirections();
        for (int i = 0; i < movedAmount; i++) {
            if (bridgeDirections.get(i) == BridgeDirection.UP) {
                addSelectedDirectionResult(upwardMap, downwardMap);
                continue;
            }
            addSelectedDirectionResult(downwardMap, upwardMap);
        }
    }

    private void addSelectedDirectionResult(List<String> selectedDirectionMap, List<String> notSelectedDirectionMap) {
        selectedDirectionMap.add(ResultOutputConstants.CORRECT);
        notSelectedDirectionMap.add(ResultOutputConstants.EMPTY);
    }

    /**
     * 다리 건너기를 실패한 경우 (마지막으로 선택한 방향이 실제 다리와 일치하지 않는 경우) 잘못 고른 결과를 반영 해주어야 한다
     */
    private void updateWrongSelection(List<String> upwardMap, List<String> downwardMap) {
        int lastIndex = movedAmount - 1;
        if (upwardMap.get(lastIndex).equals(ResultOutputConstants.CORRECT)) {
            changeSelectedDirection(downwardMap, upwardMap, lastIndex);
            return;
        }
        changeSelectedDirection(upwardMap, downwardMap, lastIndex);
    }

    private void changeSelectedDirection(
            List<String> wrongSelectedDirection, List<String> correctDirectionMap, int lastIndex
    ) {
        wrongSelectedDirection.set(lastIndex, ResultOutputConstants.WRONG);
        correctDirectionMap.set(lastIndex, ResultOutputConstants.EMPTY);
    }
}
