package draganddrop.studdybuddy.model.module;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * List of Modules. Checks for duplicates. Will be stored in Json.
 */
public class ModuleList {
    private ObservableList<draganddrop.studdybuddy.model.module.Module> internalList = FXCollections.observableArrayList();

    public ModuleList() {

    }

    /**
     * To be executed every time whenever a module is added.
     *
     * @param module
     * @return if there is a duplicate
     */
    public boolean contains(draganddrop.studdybuddy.model.module.Module module) {
        final draganddrop.studdybuddy.model.module.Module moduleForPredicate = module;
        FilteredList<draganddrop.studdybuddy.model.module.Module> filteredList = internalList.filtered((x) -> x.equals(moduleForPredicate));
        return filteredList.isEmpty() ? false : true;

    }

    public ObservableList<draganddrop.studdybuddy.model.module.Module> getInternalList() {
        return internalList;
    }

    /**
     * checks for duplicate modules first, then add into the moduleList.
     *
     * @param module to be added to the ModuleList
     */
    public void add(draganddrop.studdybuddy.model.module.Module module) throws draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException {
        if (this.contains(module)) {
            throw new draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException("Duplicate modules");
        } else {
            System.out.println("adding");
            internalList.add(module);
        }
    }

    /**
     * Removes module from the list.
     *
     * @param module
     * @throws draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException
     */
    public void remove(draganddrop.studdybuddy.model.module.Module module) throws draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException {
        if (this.contains(module)) {
            internalList.remove(module);
        } else {
            throw new draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException("Module does not exist");
        }
    }

    /**
     * Retrieves a module with the original module name inside the moduleList.
     *
     * @param moduleCode
     * @return
     * @throws draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException
     */

    public draganddrop.studdybuddy.model.module.Module get(String moduleCode) throws draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException {
        draganddrop.studdybuddy.model.module.Module moduleToFind = new draganddrop.studdybuddy.model.module.Module(moduleCode);
        if (this.contains(moduleToFind)) {
            int index = this.internalList.indexOf(moduleToFind);
            return this.internalList.get(index);
        } else {
            throw new draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException("Module Not Found");
        }
    }

    public void setModuleList(List<draganddrop.studdybuddy.model.module.Module> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement);
    }
}
