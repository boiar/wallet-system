package org.boiar.walletmanagement.shared.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.boiar.walletmanagement.shared.converter.JsonMapConverter;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String code;    // "USD", "EGP", "EUR"

    @Column(nullable = false, unique = true, length = 10)
    private String symbol;    // "$", "£", "ج.م"

    @Type(JsonBinaryType.class)
    @Column(name = "name", columnDefinition = "jsonb", nullable = false)
    @Builder.Default
    private Map<String, String> name = new HashMap<>();


    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private boolean active = true;

}