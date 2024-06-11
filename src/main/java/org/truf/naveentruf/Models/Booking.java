package org.truf.naveentruf.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ground_id" , nullable = false)
    private Truf ground;

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false )
    private TrufUser user;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDate bookedDate;
    private Boolean Status=false;
    private Double bookedPrice;

    @PrePersist
    protected void onCreate() {
        bookedDate = LocalDate.now();
    }





}
