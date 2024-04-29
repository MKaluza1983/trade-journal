package de.kaluza.trades.domains;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@Table(
        name = "sell_trades",
        indexes = {
                @Index(name = "idx_sell_trades_or_buy_trade_id", columnList = "or_buy_trade_id")
        }
)
public class SellTradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private double price;

    @Column(nullable = false, updatable = false)
    private double shares;

    @Column(nullable = false, updatable = false)
    private LocalDate tradedAt;

    @Column(nullable = false, updatable = false)
    private double performanceInMoney;

    @Column(nullable = false, updatable = false)
    private double performanceInPercent;

    @Column(nullable = false, updatable = false)
    private int tradingDays;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "or_buy_trade_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_buy_trade_to_sell_trade"))
    private BuyTradeEntity buyTrade;

}

