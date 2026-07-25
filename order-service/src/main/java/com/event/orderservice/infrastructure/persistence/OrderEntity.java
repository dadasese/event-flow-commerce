package com.event.orderservice.infrastructure.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * R2DBC-mapped row. This is deliberately a SEPARATE class from the domain Order —
 * that separation is what makes this "real" hexagonal architecture rather than
 * just slapping @Table on the domain object.
 */
@Setter
@Getter
@Table("orders")
public class OrderEntity {

    @Id
    private String id;
    private String customerId;
    private BigDecimal amount;
    private String status;
    private Instant createdAt;

    @Version
    private Long version;

}
