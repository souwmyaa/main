package draganddrop.studdybuddy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import draganddrop.studdybuddy.commons.exceptions.IllegalValueException;
import draganddrop.studdybuddy.model.tag.Tag;

/**
 * Jackson-friendly version of {@link draganddrop.studdybuddy.model.person.Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(draganddrop.studdybuddy.model.person.Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public draganddrop.studdybuddy.model.person.Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, draganddrop.studdybuddy.model.person.Name.class.getSimpleName()));
        }
        if (!draganddrop.studdybuddy.model.person.Name.isValidName(name)) {
            throw new IllegalValueException(draganddrop.studdybuddy.model.person.Name.MESSAGE_CONSTRAINTS);
        }
        final draganddrop.studdybuddy.model.person.Name modelName = new draganddrop.studdybuddy.model.person.Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, draganddrop.studdybuddy.model.person.Phone.class.getSimpleName()));
        }
        if (!draganddrop.studdybuddy.model.person.Phone.isValidPhone(phone)) {
            throw new IllegalValueException(draganddrop.studdybuddy.model.person.Phone.MESSAGE_CONSTRAINTS);
        }
        final draganddrop.studdybuddy.model.person.Phone modelPhone = new draganddrop.studdybuddy.model.person.Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, draganddrop.studdybuddy.model.person.Email.class.getSimpleName()));
        }
        if (!draganddrop.studdybuddy.model.person.Email.isValidEmail(email)) {
            throw new IllegalValueException(draganddrop.studdybuddy.model.person.Email.MESSAGE_CONSTRAINTS);
        }
        final draganddrop.studdybuddy.model.person.Email modelEmail = new draganddrop.studdybuddy.model.person.Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, draganddrop.studdybuddy.model.person.Address.class.getSimpleName()));
        }
        if (!draganddrop.studdybuddy.model.person.Address.isValidAddress(address)) {
            throw new IllegalValueException(draganddrop.studdybuddy.model.person.Address.MESSAGE_CONSTRAINTS);
        }
        final draganddrop.studdybuddy.model.person.Address modelAddress = new draganddrop.studdybuddy.model.person.Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new draganddrop.studdybuddy.model.person.Person(modelName, modelPhone, modelEmail, modelAddress, modelTags);
    }

}
