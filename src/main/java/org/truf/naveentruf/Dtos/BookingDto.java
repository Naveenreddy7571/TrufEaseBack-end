package org.truf.naveentruf.Dtos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDto {

    private Long groundId;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
