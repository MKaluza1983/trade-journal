package de.kaluza.trades.domains;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@Table(
        name = "buy_trades",
        indexes = {
                @Index(name = "idx_customer_number", columnList = "customerId")
        }
)
public class BuyTradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private java.time.OffsetDateTime createdAt;

    @Column(nullable = false, updatable = false)
    private String customerId;

    @Column(nullable = false, updatable = false)
    private String stockSymbol;

    private double price;

    @Column(nullable = false, updatable = false)
    private double shares;

    @Column(nullable = false, updatable = false)
    private LocalDate tradedAt;

    @Column(nullable = false)
    private double availableShares;

    @Column(nullable = false)
    private double investedCapital;

    @Column(nullable = false)
    private boolean isTradeClosed;

    @Column(nullable = false)
    private Double performanceInMoney;

    @Column(nullable = false)
    private Double performanceInPercent;

    @Column
    private Integer tradingDays;

    @OneToMany(mappedBy = "buyTrade", fetch = FetchType.EAGER)
    @OrderBy("tradedAt DESC")
    private List<SellTradeEntity> sellTrades = new ArrayList<>();

    @PrePersist
    public void setCreationDate() {
        this.createdAt = java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC);
    }

}
