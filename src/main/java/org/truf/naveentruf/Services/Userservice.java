package org.truf.naveentruf.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.truf.naveentruf.Models.TrufUser;
import org.truf.naveentruf.Models.Wallet;
import org.truf.naveentruf.Repositories.UserRepository;
import org.truf.naveentruf.Repositories.WalletRepository;

import java.util.List;
import java.util.Optional;

@Service
public class Userservice {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;


    public TrufUser registerUser(TrufUser user) {
        TrufUser savedUser = userRepository.save(user);
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(0.0);
        walletRepository.save(wallet);
        return savedUser;
    }

    public List<TrufUser> getAllCustomers() {
        return userRepository.findAll();
    }
}
