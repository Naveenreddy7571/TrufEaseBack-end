package org.truf.naveentruf.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.truf.naveentruf.CustomExceptions.InsufficientBalanceException;
import org.truf.naveentruf.Models.Wallet;
import org.truf.naveentruf.Services.WalletService;

import java.util.Optional;

@Controller
@RequestMapping("/api/user/wallet")

public class WalletController {

    @Autowired
    private WalletService walletService;
    @PostMapping("/{userId}/add/{amount}")
    public ResponseEntity<?> addMoneyToWallet(@PathVariable Long userId, @PathVariable Double amount)
    {
        try {
            Wallet wallet = walletService.addMoneyToWallet(userId,amount);
            return new ResponseEntity<>("Money added to Wallet Successfully",HttpStatus.OK);

        } catch (InsufficientBalanceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetchmoney/{userId}")
    public ResponseEntity<?> fetchWalletMoney(@PathVariable  Long userId)
    {
        try {
            Double balance = walletService.fetchBalance(userId);
            return new ResponseEntity<>(balance,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/detectmoney/{userId}/{amount}")
    public ResponseEntity<?> detectMoneyFromWallet(@PathVariable  Long userId , @PathVariable Double amount)
    {
        try {
            walletService.removeMoneyFromWallet(userId,amount);
            return new ResponseEntity<>("Money Detected Successfully",HttpStatus.OK);

        } catch (InsufficientBalanceException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
