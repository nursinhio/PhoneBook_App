package com.example.PhonebookApp.mapper;

import com.example.PhonebookApp.dto.ContactDTO;
import com.example.PhonebookApp.model.Contact;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ContactMapper implements Mapper<Contact, ContactDTO> {
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ContactDTO mapTo(Contact contact) {
        return modelMapper.map(contact, ContactDTO.class);
    }

    @Override
    public Contact mapFrom(ContactDTO contactDTO) {
        return modelMapper.map(contactDTO, Contact.class);
    }
}
