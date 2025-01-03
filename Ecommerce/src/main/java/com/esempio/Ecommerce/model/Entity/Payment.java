package com.esempio.Ecommerce.model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private WebOrder order; // Ogni pagamento è legato a un ordine specifico.

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; // E.g., "credit_card", "paypal"

    @Column(name = "amount_paid", nullable = false)
    private Double amountPaid;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus; // E.g., "completed", "failed", "pending"

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
