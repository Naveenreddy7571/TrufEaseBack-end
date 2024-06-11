package org.truf.naveentruf.Dtos;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AllBookingsGround {

    private String groundName;
    private Long bookingID;
    private String customerName;
    private Long customerNumber;
    private LocalDate bookingDate;
    private String bookingSlot;
    private Double paidAmount;
    private Boolean status;




}
