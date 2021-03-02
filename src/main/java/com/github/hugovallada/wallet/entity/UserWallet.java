package com.github.hugovallada.wallet.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_wallet")
@Data
public class UserWallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //default é eager -> carrega tudo por padrão ... LAZY só carrega qnd é chamado
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet", referencedColumnName = "id")
    private Wallet wallet;
}