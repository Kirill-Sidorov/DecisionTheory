package sidorov.app;

import sidorov.common.result.Result;
import sidorov.mode.MixedStrategiesMode;
import sidorov.mode.Mode;
import sidorov.mode.ModeType;
import sidorov.mode.PureStrategiesMode;
import sidorov.mode.ReductionMode;
import sidorov.mode.SolutionMatrixGame2x2Mode;
import sidorov.mode.SolutionMatrixGame2xNorNx2Mode;
import sidorov.task.CreateChartTask;
import sidorov.task.SolveTask;
import sidorov.task.UploadTask;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class AppController {

    private Mode currentMode;
    private final UI UI;
    private final Map<String, Mode> modeMap = new HashMap<>();

    public AppController() {
        UI = new UI(this::uploadData, this::solveTask, this::createChart);

        modeMap.put(ModeType.MIXED_STRATEGIES.name(), new MixedStrategiesMode(UI));
        modeMap.put(ModeType.PURE_STRATEGIES.name(), new PureStrategiesMode(UI));
        modeMap.put(ModeType.REDUCTION.name(), new ReductionMode(UI));
        modeMap.put(ModeType.SOLUTION_MATRIX_GAME_2x2.name(), new SolutionMatrixGame2x2Mode(UI));
        modeMap.put(ModeType.SOLUTION_MATRIX_GAME_2xN_OR_Nx2.name(), new SolutionMatrixGame2xNorNx2Mode(UI));

        ModeType[] modeTypes = ModeType.values();
        currentMode = modeMap.get(modeTypes[0].name());
        UI.initialize(this::selectMode, modeTypes);
    }

    private void selectMode(ActionEvent event) {
        if (modeMap.get(event.getActionCommand()) != null) {
            currentMode = modeMap.get(event.getActionCommand());
            currentMode.setNecessaryUIItems();
        }
    }

    private void uploadData(ActionEvent event) {
        new UploadTask(currentMode.getLogic(), this::processResult).execute();
    }

    private void solveTask(ActionEvent event) {
        new SolveTask(currentMode.getLogic(), this::processResult).execute();
    }

    private void createChart(ActionEvent event) {
        new CreateChartTask(currentMode.getLogic(), this::processResult).execute();
    }

    private void processResult(Result result) {
        switch (result.status) {
            case TASK_SOLVED:
                currentMode.handleTaskSolved(result);
                break;
            case DATA_UPLOADED:
                currentMode.handleDataUploaded(result);
                break;
            case INFO:
                currentMode.handleInfo(result);
                break;
            case ERROR:
                currentMode.handleError(result);
                break;
        }
    }
}
