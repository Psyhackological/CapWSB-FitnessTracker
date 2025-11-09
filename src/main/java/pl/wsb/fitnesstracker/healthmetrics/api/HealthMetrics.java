package pl.wsb.fitnesstracker.healthmetrics.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.wsb.fitnesstracker.user.api.User;

@Getter
@Setter
@Entity
@Table(name = "health_metrics")
public class HealthMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Owning side of ManyToOne -> User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_health_metrics_user")
    )
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    // kilograms
    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight;

    // centimeters
    @Column(name = "height")
    private Integer height;

    @Column(name = "heart_rate")
    private Integer heartRate;

    public HealthMetrics() {
    }

    public HealthMetrics(User user, LocalDate date, BigDecimal weight, Integer height, Integer heartRate) {
        this.user = user;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.heartRate = heartRate;
    }
}
