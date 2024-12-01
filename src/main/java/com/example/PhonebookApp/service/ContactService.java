package com.example.PhonebookApp.service;

import com.example.PhonebookApp.exception.exception.InvalidInputException;
import com.example.PhonebookApp.exception.exception.NotFoundException;
import com.example.PhonebookApp.model.Contact;
import com.example.PhonebookApp.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public void createOrUpdateContact(Contact contact) {
        if (contact == null) {
            throw new InvalidInputException("Contact cannot be null");
        }

        if (contact.getPhoneNumber() == null || contact.getPhoneNumber().isEmpty()) {
            throw new InvalidInputException("Phone number cannot be empty");
        }
        if(contact.getSurname()==null || contact.getSurname().isEmpty()){
            throw new InvalidInputException("Surname con not be empty");
        }
        if(contact.getName() == null || contact.getName().isEmpty()){
            throw new InvalidInputException("Name con not be empty");
        }

        if (contact.getEmail() == null || contact.getEmail().isEmpty()) {
            throw new InvalidInputException("Email cannot be empty");
        }

        contactRepository.save(contact);
    }


    public List<Contact> findAll() {
        List<Contact> contacts = contactRepository.findAll();
        if (contacts.isEmpty()) {
            throw new NotFoundException("No contacts found");
        }
        return contacts;
    }

    public Contact findById(String id){
        return contactRepository.findById(id).orElseThrow(()-> new NotFoundException("No contact with that id is found"));
    }

    public void deleteById(String id){
        Contact deleted = contactRepository.findById(id).orElseThrow(()-> new NotFoundException("Contact with that is already not in database"));
        contactRepository.deleteById(id);
    }
}
