import java.util.HashMap;
import java.util.Map;

/**
 * Created by mcanas on 9/20/16.
 */
public class Menu {
    private Map<String, String> mOptions;

    public Menu() {
        mOptions = new HashMap<>();
    }

    public Map<String, String> getMenuOptions() {
        return mOptions;
    }

    public void add(String key, String value) {
        mOptions.put(key, value);
    }
}
