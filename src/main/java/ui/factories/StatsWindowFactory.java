package ui.factories;

import ui.windows.stats.StatsController;
import ui.windows.stats.StatsDelegator;
import ui.windows.stats.StatsDisplay;
import ui.windows.stats.StatsWindow;
import ui.windows.Window;
import use_cases.DataAccess;

public class StatsWindowFactory implements WindowFactory {
    private DataAccess dataAccess;

    /**
     * Constructs a new StatsWindowFactory given a DataAccess.
     * @param dataAccess The DataAccess belonging to this StatsWindowFactory.
     */
    public StatsWindowFactory(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    /**
     * Creates a controller, delegator and display for statistics page usage, before returning a new
     * StatsWindow using the three previously mentioned classes.
     * @return A new StatsWindow to display statistics on.
     */
    @Override
    public Window createWindow() {
        StatsController controller = new StatsController(dataAccess);
        StatsDelegator delegator = new StatsDelegator(controller);
        StatsDisplay display = new StatsDisplay(delegator);
        controller.setDisplay(display);

        return new StatsWindow(display);
    }
}
