package draganddrop.studdybuddy.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * pending
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel.fxml";
    private final Logger logger = draganddrop.studdybuddy.commons.core.LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private ListView<draganddrop.studdybuddy.model.module.Module> moduleListView;

    public ModuleListPanel(ObservableList<draganddrop.studdybuddy.model.module.Module> moduleList) {
        super(FXML);
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
    }

    /**
    * cells for moduleList
    */
    class ModuleListViewCell extends ListCell<draganddrop.studdybuddy.model.module.Module> {
        @Override
        protected void updateItem(draganddrop.studdybuddy.model.module.Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                ModCard modcard = new ModCard(module, getIndex() + 1);
                setGraphic(modcard.getRoot());
            }
        }

    }
}

