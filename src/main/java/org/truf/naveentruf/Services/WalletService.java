package org.truf.naveentruf.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.truf.naveentruf.CustomExceptions.InsufficientBalanceException;
import org.truf.naveentruf.Models.Wallet;
import org.truf.naveentruf.Repositories.WalletRepository;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    public Wallet addMoneyToWallet(Long userId, Double amount) throws InsufficientBalanceException {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() + amount);
            return walletRepository.save(wallet);
        }
        throw new InsufficientBalanceException("Wallet not found for user id : "+userId);
    }

    public Double fetchBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        return wallet.getBalance();
    }

    public void removeMoneyFromWallet(Long userId, Double amountToBeDeducted) throws InsufficientBalanceException {
        Wallet wallet = walletRepository.findByUserId(userId);

        if (wallet == null) {
            throw new InsufficientBalanceException("Wallet not found for user id: " + userId);
        }

        Double walletBalance = wallet.getBalance();

        if (walletBalance >= amountToBeDeducted) {
            wallet.setBalance(walletBalance - amountToBeDeducted);
            walletRepository.save(wallet);
        } else {
            throw new InsufficientBalanceException("Insufficient balance in your account.");
        }
    }


}
