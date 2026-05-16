package org.boiar.walletmanagement.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.boiar.walletmanagement.shared.entity.Currency;
import org.boiar.walletmanagement.shared.entity.EntityAuditTimingData;
import org.boiar.walletmanagement.shared.entity.Language;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_currencies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "is_primary", nullable = false)
    private boolean primary = false;


}
