package com.example.PhonebookApp.views;

import com.example.PhonebookApp.dto.ContactDTO;
import com.example.PhonebookApp.mapper.ContactMapper;
import com.example.PhonebookApp.model.Contact;
import com.example.PhonebookApp.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {

    private final ContactService contactService;
    private final ContactMapper contactMapper;
    private final Grid<Contact> contactGrid = new Grid<>(Contact.class);
    private final Button addContactButton = new Button(new Icon(VaadinIcon.PLUS));

    @Autowired
    public MainView(ContactService contactService, ContactMapper contactMapper) {
        this.contactService = contactService;
        this.contactMapper = contactMapper;
        configureGrid();

        configureAddButton();

        add(contactGrid);

        refreshGrid();
    }

    private void configureGrid() {
        contactGrid.setColumns("name", "surname", "phoneNumber", "email");

        contactGrid.addComponentColumn(contact -> {
            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
            editButton.addClickListener(event -> openContactForm(contact));
            editButton.getStyle().set("color", "blue");


            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addClickListener(event -> confirmDelete(contact));
            deleteButton.getStyle().set("color", "red");


            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Actions").setAutoWidth(true);
    }

    private void configureAddButton() {
        addContactButton.addClickListener(event -> openContactForm(null));
        addContactButton.getStyle().set("background-color", "#007BFF");
        addContactButton.getStyle().set("color", "white");
        add(new HorizontalLayout(addContactButton));
    }

    private void openContactForm(Contact contact) {
        ContactFormDialog formDialog = new ContactFormDialog(contact == null ? null : contactMapper.mapTo(contact), contactService, this, contactMapper);
        formDialog.open();
    }

    private void confirmDelete(Contact contact) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Delete Contact");
        confirmDialog.setText("Are you sure you want to delete this contact?");
        confirmDialog.setCancelable(true);


        confirmDialog.setConfirmButton("Delete", event -> {
            contactService.deleteById(contact.getId());
            refreshGrid();
        });
        confirmDialog.setConfirmButtonTheme("error");

        confirmDialog.open();
    }

    public void refreshGrid() {
        contactGrid.setItems(contactService.findAll());
    }
}
