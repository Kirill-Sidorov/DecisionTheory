package sidorov.app;

import sidorov.common.Result;
import sidorov.mode.BellmanZadehMode;
import sidorov.mode.DirectOutputMethodMode;
import sidorov.mode.MixedStrategiesMode;
import sidorov.mode.Mode;
import sidorov.mode.ModeType;
import sidorov.mode.PureStrategiesMode;
import sidorov.mode.ReductionMode;
import sidorov.mode.SolutionMatrixGame2x2Mode;
import sidorov.mode.SolutionMatrixGame2xNorNx2Mode;
import sidorov.mode.StatisticalGamesMode;
import sidorov.task.CreateChartTask;
import sidorov.task.SolveTask;
import sidorov.task.UploadTask;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AppController {

    private final InputDataCheckup checkup;
    private final Map<ModeType, Mode> modeMap = new HashMap<>();

    private Mode currentMode;

    public AppController() {
        UI UI = new UI(this::uploadData, this::solveTask, this::createChart);
        checkup = new InputDataCheckup(UI);

        Mode mode1 = new MixedStrategiesMode(UI);
        Mode mode2 = new PureStrategiesMode(UI);
        Mode mode3 = new ReductionMode(UI);
        Mode mode4 = new SolutionMatrixGame2x2Mode(UI);
        Mode mode5 = new SolutionMatrixGame2xNorNx2Mode(UI);
        Mode mode6 = new StatisticalGamesMode(UI);
        Mode mode7 = new BellmanZadehMode(UI);
        Mode mode8 = new DirectOutputMethodMode(UI);

        modeMap.put(mode1.getModeType(), mode1);
        modeMap.put(mode2.getModeType(), mode2);
        modeMap.put(mode3.getModeType(), mode3);
        modeMap.put(mode4.getModeType(), mode4);
        modeMap.put(mode5.getModeType(), mode5);
        modeMap.put(mode6.getModeType(), mode6);
        modeMap.put(mode7.getModeType(), mode7);
        modeMap.put(mode8.getModeType(), mode8);

        Set<ModeType> currentModeTypes = modeMap.keySet();
        currentMode = modeMap.get(currentModeTypes.iterator().next());
        UI.initialize(this::selectMode, currentModeTypes);
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
        if (checkup.validateDataAndInput(currentMode)) {
            new SolveTask(currentMode.getLogic(), this::processResult).execute();
        }
    }

    private void createChart(ActionEvent event) {
        new CreateChartTask(currentMode.getLogic(), this::processResult).execute();
    }

    private void processResult(Result result) {
        switch (result.status()) {
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
