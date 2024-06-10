package org.truf.naveentruf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.truf.naveentruf.Models.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Wallet findByUserId(Long userId);
}
