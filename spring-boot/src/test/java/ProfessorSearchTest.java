import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class ProfessorListDisplayTest {

    private ProfessorListDisplay display;

    @BeforeEach
    void setUp() {
        display = new ProfessorListDisplay();
    }

    @Test
    void testProfessorListInitialization() {
        // Get the JList from the display
        JList<String> list = (JList<String>) ((JScrollPane) display.getContentPane().getComponent(0)).getViewport().getView();

        // Check if the list is not null
        assertNotNull(list, "Professor list should not be null");

        // Verify the list has exactly 1 professor
        assertEquals(1, list.getModel().getSize(), "Professor list should contain exactly one professor");

        // Check if the correct professor is present
        assertEquals("Dr. David James - Average Rating: 4.5", list.getModel().getElementAt(0), "Professor name should match expected value");
    }
}
