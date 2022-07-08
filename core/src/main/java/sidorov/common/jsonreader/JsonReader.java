package sidorov.common.jsonreader;

import com.fasterxml.jackson.databind.ObjectMapper;
import sidorov.common.jsonreader.lab6.BellmanZadehPojo;

import java.io.IOException;
import java.nio.file.Paths;

public class JsonReader {

    private final String path = "BellmanZadehData.json";

    public BellmanZadehPojo loadBellmanZadehData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(Paths.get(path).toFile(), BellmanZadehPojo.class);
    }

}
