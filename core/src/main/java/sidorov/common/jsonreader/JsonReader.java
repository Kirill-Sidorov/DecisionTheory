package sidorov.common.jsonreader;

import com.fasterxml.jackson.databind.ObjectMapper;
import sidorov.common.jsonreader.lab6.BellmanZadehPojo;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    private final String path = "C:\\Users\\user\\IdeaProjects\\DecisionTheory\\core\\src\\main\\resources\\BellmanZadehData.json";

    public BellmanZadehPojo loadBellmanZadehData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), BellmanZadehPojo.class);
    }

}
