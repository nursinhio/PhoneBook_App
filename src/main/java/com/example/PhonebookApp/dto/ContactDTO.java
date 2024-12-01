package com.example.PhonebookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.atmosphere.config.service.Get;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ContactDTO {
    private String id;
    private String phoneNumber;
    private String name;
    private String surname;
    private String email;
}
