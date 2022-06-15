package tools;

import java.util.concurrent.ThreadLocalRandom;

public class RandomTool {
    public static long getRandomId() {
        return ThreadLocalRandom.current().nextLong(10000, 1000000);
    }
}
