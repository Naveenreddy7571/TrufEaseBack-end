package org.truf.naveentruf.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.Mapping;

@Entity
@Getter
@Setter
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Double balance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "truf_id")
    private TrufUser user;

}
