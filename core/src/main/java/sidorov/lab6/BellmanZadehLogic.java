package sidorov.lab6;

import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;
import sidorov.common.jsonreader.lab6.BellmanZadehPojo;
import sidorov.common.jsonreader.JsonReader;

import java.io.IOException;
import java.util.Arrays;

public class BellmanZadehLogic implements Logic {
    @Override
    public Result uploadData() {
        BellmanZadehPojo bellmanZadehPojo;
        try {
            bellmanZadehPojo = new JsonReader().loadBellmanZadehData();
            System.out.println(Arrays.toString(bellmanZadehPojo.getCriteria()));
            System.out.println(Arrays.toString(bellmanZadehPojo.getVariants()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(Status.SUCCESS, "OK");
    }

    @Override
    public Result solveTask() {
        return null;
    }
}
