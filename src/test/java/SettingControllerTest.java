import de.wagentim.collector.controller.SettingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SettingControllerTest {

    SettingController settingController;

    @BeforeEach
    void init() {
        settingController = new SettingController();
    }

    @DisplayName("Test Loading setting file")
    @Test
    void testLoadProperties() {
        Properties prop = settingController.loadProperties();
        assertNotNull(prop);
        assertEquals(prop.get("test"), "test");
    }
}
