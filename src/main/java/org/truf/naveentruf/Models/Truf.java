package org.truf.naveentruf.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Truf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groundName;
    private String GroundDescription;
    private int groundWidth;
    private int groundHeight;
    private Double price;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] groundImg;
    @OneToMany(mappedBy = "ground", cascade = CascadeType.ALL)
    private Set<Booking> bookings;

}
