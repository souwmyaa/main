package seedu.address.model.module.exceptions;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Exception for module code
 */
public class ModuleCodeException extends ParseException {
    public ModuleCodeException(String message) {
        super(message);
    }
}
