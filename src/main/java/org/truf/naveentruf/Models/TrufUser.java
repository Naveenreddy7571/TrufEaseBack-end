package org.truf.naveentruf.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrufUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private Long number;
    private int age;
    private String address;
    private String city;
    private int pinCode;
    private String role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Booking> bookings;

}
