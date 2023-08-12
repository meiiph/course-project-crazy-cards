
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import controllers.GameCreationController;
import controllers.PlayerGameController;
import database.CSVDatabase;
import entities.*;
import enums.Rank;
import enums.Suit;
import enums.WindowName;
import ui.components.NavigationButton;
import ui.factories.*;
import ui.windows.layout_managers.CardLayoutManager;
import ui.windows.layout_managers.ICardLayoutManager;
import ui.windows.menu.MenuWindow;
import ui.windows.stats.StatsController;
import ui.windows.stats.StatsDisplay;
import ui.windows.layout_managers.PaneDelegator;
import ui.windows.Window;
import ui.windows.creation.CreationDisplay;
import use_cases.DataAccess;
import use_cases.GameCreationInputBoundary;
import use_cases.GameCreationInteractor;
import use_cases.GameState;
import use_cases.PlayerGameInputBoundary;
import use_cases.PlayerGameInteractor;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        GameManager manager = new GameManager();
        GameState state = new GameState(manager);
        manager.addObserver(state);

        DataAccess dataAccess = new CSVDatabase();

        IObserverNotifier notifier = new ObserverNotifier(manager);

        PlayerGameInputBoundary gameBoundary = new PlayerGameInteractor(manager, notifier, state);
        GameCreationInputBoundary creationBoundary = new GameCreationInteractor(dataAccess, manager);

        PlayerGameController gameController = new PlayerGameController(gameBoundary);
        GameCreationController creationController = new GameCreationController(creationBoundary);

        ICardLayoutManager layoutManager = new CardLayoutManager(frame);

        MenuWindowFactory menuFactory = new MenuWindowFactory();
        RuleWindowFactory ruleFactory = new RuleWindowFactory();
        StatsWindowFactory statsFactory = new StatsWindowFactory(new CSVDatabase());
        GameWindowFactory gameFactory = new GameWindowFactory(gameController);
        CreatorWindowFactory creatorFactory = new CreatorWindowFactory(creationController);
        HowtoWindowFactory howtoFactory = new HowtoWindowFactory();

        Window menuWindow = menuFactory.createWindow();
        Window ruleWindow = ruleFactory.createWindow();
        Window statsWindow = statsFactory.createWindow();
        Window gameWindow = gameFactory.createWindow();
        Window creatorWindow = creatorFactory.createWindow();
        Window howtoWindow = howtoFactory.createWindow();

        layoutManager.addPane(menuWindow);
        layoutManager.addPane(ruleWindow);
        layoutManager.addPane(statsWindow);
        layoutManager.addPane(gameWindow);
        layoutManager.addPane(creatorWindow);
        layoutManager.addPane(howtoWindow);

        PaneDelegator paneDelegator = new PaneDelegator(layoutManager);
        menuWindow.setNavigator(paneDelegator);
        statsWindow.setNavigator(paneDelegator);
        ruleWindow.setNavigator(paneDelegator);
        creatorWindow.setNavigator(paneDelegator);

        frame.setVisible(true);
    }
}
