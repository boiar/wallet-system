package org.boiar.walletmanagement.shared.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.boiar.walletmanagement.shared.converter.JsonMapConverter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @Type(JsonBinaryType.class)
    @Column(name = "name", columnDefinition = "jsonb", nullable = false)
    @Builder.Default
    private Map<String, String> name = new HashMap<>();

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(name = "is_rtl", nullable = false)
    @Builder.Default
    private boolean rtl = true;

    @Embedded
    private EntityAuditTimingData timingData = new EntityAuditTimingData();

    @PrePersist
    public void prePersist() {
        if (timingData == null) timingData = new EntityAuditTimingData();
        timingData.setCreatedDate(LocalDateTime.now());
        timingData.setUpdatedDate(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        if (timingData == null) timingData = new EntityAuditTimingData();
        timingData.setUpdatedDate(LocalDateTime.now());
    }
}
