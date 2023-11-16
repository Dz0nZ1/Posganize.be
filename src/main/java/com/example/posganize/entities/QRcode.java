package com.example.posganize.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "qrcode")
public class QRcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qrcode_id;
    private String qrcodeValue;
    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users users;
    private boolean active;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "expire_date")
    private LocalDateTime expireDate;

}
