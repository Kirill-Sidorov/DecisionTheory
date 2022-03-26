package sidorov.app;

import sidorov.common.Logic;
import sidorov.common.WithInputData;
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
    private final Map<ModeType, Mode> modeMap = new HashMap<>();

    public AppController() {
        UI = new UI(this::uploadData, this::solveTask, this::createChart);

        Mode mode1 = new MixedStrategiesMode(UI);
        Mode mode2 = new PureStrategiesMode(UI);
        Mode mode3 = new ReductionMode(UI);
        Mode mode4 = new SolutionMatrixGame2x2Mode(UI);
        Mode mode5 = new SolutionMatrixGame2xNorNx2Mode(UI);

        modeMap.put(mode1.getModeType(), mode1);
        modeMap.put(mode2.getModeType(), mode2);
        modeMap.put(mode3.getModeType(), mode3);
        modeMap.put(mode4.getModeType(), mode4);
        modeMap.put(mode5.getModeType(), mode5);

        currentMode = mode1;
        UI.initialize(this::selectMode, ModeType.values());
    }

    private void selectMode(ActionEvent event) {
        ModeType modeType = ModeType.valueOf(event.getActionCommand());
        currentMode = modeMap.get(modeType);
        currentMode.setNecessaryUIItems();
    }

    private void uploadData(ActionEvent event) {
        new UploadTask(currentMode.getLogic(), this::processResult).execute();
    }

    private void solveTask(ActionEvent event) {
        Logic currentLogic = currentMode.getLogic();
        if (currentLogic instanceof WithInputData) {
            boolean success = false;
            ValidateAndInput validateAndInput = new ValidateAndInput(UI);

            switch (currentMode.getModeType()) {
                case SOLUTION_MATRIX_GAME_2xN_OR_Nx2:
                    success = validateAndInput.inputDataForMatrixGame2xNorNx2((WithInputData) currentLogic);
                    break;
            }

            if (!success) {
                return;
            }
        }
        new SolveTask(currentLogic, this::processResult).execute();
    }

    private void createChart(ActionEvent event) {
        new CreateChartTask(currentMode.getLogic(), this::processResult).execute();
    }

    private void processResult(Result result) {
        switch (result.status) {
            case SUCCESS:
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
