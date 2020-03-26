package draganddrop.studdybuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static draganddrop.studdybuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static draganddrop.studdybuddy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static draganddrop.studdybuddy.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import blah.hello.testutil.*;
import draganddrop.studybuddy.testutil.*;
import ohno.studybuddy.testutil.*;
import org.junit.jupiter.api.Test;

import draganddrop.studdybuddy.logic.commands.ClearTasksCommand;
import draganddrop.studybuddy.logic.commands.oldcommands.AddCommand;
import draganddrop.studybuddy.logic.commands.oldcommands.DeleteCommand;
import draganddrop.studybuddy.logic.commands.oldcommands.EditCommand;
import draganddrop.studybuddy.logic.commands.oldcommands.EditCommand.EditPersonDescriptor;
import draganddrop.studybuddy.logic.commands.oldcommands.ExitCommand;
import draganddrop.studybuddy.logic.commands.oldcommands.FindCommand;
import draganddrop.studybuddy.logic.commands.oldcommands.HelpCommand;
import draganddrop.studybuddy.logic.commands.oldcommands.ListCommand;
import draganddrop.studdybuddy.logic.parser.exceptions.ParseException;
import draganddrop.studdybuddy.model.person.NameContainsKeywordsPredicate;
import draganddrop.studdybuddy.model.person.Person;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new draganddrop.studdybuddy.testutil.PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(draganddrop.studdybuddy.testutil.PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearTasksCommand.COMMAND_WORD) instanceof ClearTasksCommand);
        assertTrue(parser.parseCommand(ClearTasksCommand.COMMAND_WORD + " 3") instanceof ClearTasksCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + draganddrop.studdybuddy.testutil.TypicalIndexes.INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(draganddrop.studdybuddy.testutil.TypicalIndexes.INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new draganddrop.studdybuddy.testutil.PersonBuilder().build();
        EditPersonDescriptor descriptor = new draganddrop.studdybuddy.testutil.EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + draganddrop.studdybuddy.testutil.TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + draganddrop.studdybuddy.testutil.PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(draganddrop.studdybuddy.testutil.TypicalIndexes.INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
