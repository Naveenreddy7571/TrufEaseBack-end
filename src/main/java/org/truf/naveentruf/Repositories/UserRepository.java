package org.truf.naveentruf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.truf.naveentruf.Models.TrufUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TrufUser, Long> {
    public Optional<TrufUser> findByEmail(String email);
}
