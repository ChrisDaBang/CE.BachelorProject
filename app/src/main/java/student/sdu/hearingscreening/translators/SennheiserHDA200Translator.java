package student.sdu.hearingscreening.translators;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bogs on 13-04-2017.
 */

public class SennheiserHDA200Translator implements ITranslator {
    public SennheiserHDA200Translator() {
        initializeMap();
    }
    private Map<Integer, Float> translateMap;

    @Override
    public float translate(float input, int frequencyNumber) {
        return input - translateMap.get(frequencyNumber);
    }

    private void initializeMap() {
        translateMap = new HashMap();
        translateMap.put(0, 18.0f);
        translateMap.put(1, 11.0f);
        translateMap.put(2, 5.5f);
        translateMap.put(3, 4.5f);
        translateMap.put(4, 2.5f);
        translateMap.put(5, 9.5f);
        translateMap.put(6, 17.0f);
        translateMap.put(7, 17.5f);

    }

}
