package org.boiar.walletmanagement.notifications.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.boiar.walletmanagement.notifications.enums.NotificationEvent;
import org.boiar.walletmanagement.notifications.enums.NotificationStatus;
import org.boiar.walletmanagement.notifications.enums.NotificationType;
import org.boiar.walletmanagement.shared.entity.EntityAuditTimingData;
import org.boiar.walletmanagement.user.entity.User;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationEvent event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private NotificationStatus status = NotificationStatus.PENDING;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> metadata;

    private String failureReason;
    private LocalDateTime readAt;
    private LocalDateTime sentAt;

    @Embedded
    private EntityAuditTimingData timingData = new EntityAuditTimingData();

    public void markSent() {
        this.status  = NotificationStatus.SENT;
        this.sentAt  = LocalDateTime.now();
    }

    public void markFailed(String reason) {
        this.status        = NotificationStatus.FAILED;
        this.failureReason = reason;
    }

    public void markRead() {
        this.status = NotificationStatus.READ;
        this.readAt = LocalDateTime.now();
    }

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
