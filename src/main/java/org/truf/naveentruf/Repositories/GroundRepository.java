package org.truf.naveentruf.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.truf.naveentruf.Models.Booking;
import org.truf.naveentruf.Models.Truf;

import java.util.List;

@Repository
public interface GroundRepository extends JpaRepository<Truf,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Truf t WHERE t.groundName = :groundNaame")
    void deleteByGroundName(String groundNaame);

}
