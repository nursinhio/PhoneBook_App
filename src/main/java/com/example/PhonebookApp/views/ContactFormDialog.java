package com.example.PhonebookApp.views;

import com.example.PhonebookApp.dto.ContactDTO;
import com.example.PhonebookApp.mapper.ContactMapper;
import com.example.PhonebookApp.model.Contact;
import com.example.PhonebookApp.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class ContactFormDialog extends Dialog {

    private final ContactService contactService;
    private final MainView mainView;


    private final TextField nameField = new TextField("Name");
    private final TextField surnameField = new TextField("Surname");
    private final TextField phoneNumberField = new TextField("Phone Number");
    private final EmailField emailField = new EmailField("Email");


    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private ContactDTO currentContactDto;
    private final ContactMapper contactMapper;

    private Binder<Contact> binder;

    public ContactFormDialog(ContactDTO contactDTO, ContactService contactService, MainView mainView, ContactMapper contactMapper) {
        this.contactService = contactService;
        this.mainView = mainView;
        this.currentContactDto = contactDTO;
        this.contactMapper = contactMapper;

        setUpForm();

        if (contactDTO != null) {
            nameField.setValue(contactDTO.getName());
            surnameField.setValue(contactDTO.getSurname());
            phoneNumberField.setValue(contactDTO.getPhoneNumber());
            emailField.setValue(contactDTO.getEmail());
        }

        configureBinder();

        setUpButton();

        add(saveButton, cancelButton);
    }

    private void setUpButton(){
        saveButton.addClickListener(event -> saveContact());
        cancelButton.addClickListener(event -> close());
    }

    private void setUpForm(){
        FormLayout formLayout = new FormLayout();
        formLayout.add(nameField, surnameField, phoneNumberField, emailField);
        add(formLayout);
    }

    private void configureBinder(){

        binder = new Binder<>(Contact.class);


        binder.forField(phoneNumberField)
                .withValidator(new StringLengthValidator("Fill this field", 1, 20))
                .withValidator(new RegexpValidator("Invalid phone number", "^\\+?[0-9]{10,15}$"))
                .bind(Contact::getPhoneNumber, Contact::setPhoneNumber);


        binder.forField(emailField)
                .withValidator(new StringLengthValidator("Fill this field", 1, 20))
                .withValidator(new EmailValidator("Invalid email format", true))
                .bind(Contact::getEmail, Contact::setEmail);


        binder.forField(nameField)
                .withValidator(new StringLengthValidator("Fill this field", 1, 20))
                .bind(Contact::getName, Contact::setName);

        binder.forField(surnameField)
                .withValidator(new StringLengthValidator("Fill this field", 1, 20))
                .bind(Contact::getSurname, Contact::setSurname);
    }

    private void saveContact() {

        if (binder.validate().isOk()) {

            if (currentContactDto == null) {
                currentContactDto = new ContactDTO();
            }


            currentContactDto.setName(nameField.getValue());
            currentContactDto.setSurname(surnameField.getValue());
            currentContactDto.setPhoneNumber(phoneNumberField.getValue());
            currentContactDto.setEmail(emailField.getValue());


            if (currentContactDto.getId() == null) {
                contactService.createOrUpdateContact(contactMapper.mapFrom(currentContactDto)); // Создание нового контакта
                Notification.show("Contact created successfully");
            } else {
                contactService.createOrUpdateContact(contactMapper.mapFrom(currentContactDto)); // Обновление существующего контакта
                Notification.show("Contact updated successfully");
            }

            mainView.refreshGrid();
            close();
        } else {
            Notification.show("Please fix the errors in the form", 3000, Notification.Position.BOTTOM_START)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}