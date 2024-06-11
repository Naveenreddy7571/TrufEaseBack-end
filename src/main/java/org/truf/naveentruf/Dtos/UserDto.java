package org.truf.naveentruf.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private  String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private String address;
}
