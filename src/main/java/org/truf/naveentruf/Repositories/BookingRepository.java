package org.truf.naveentruf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.truf.naveentruf.Models.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByGroundIdAndStartTimeBetween(Long groundId, LocalDateTime start, LocalDateTime end);
    List<Booking> findByUserId(Long userId);


}
