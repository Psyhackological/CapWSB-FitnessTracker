package pl.wsb.fitnesstracker.statistics.api;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.wsb.fitnesstracker.user.api.User;

@Getter
@Setter
@Entity
@Table(name = "statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Owning side of OneToOne -> User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(name = "fk_statistics_user")
    )
    private User user;

    @Column(name = "total_trainings", nullable = false)
    private Integer totalTrainings = 0;

    @Column(name = "total_distance", precision = 10, scale = 2)
    private BigDecimal totalDistance;

    @Column(name = "total_calories_burned")
    private Integer totalCaloriesBurned;

    public Statistics() {
    }

    public Statistics(User user) {
        this.user = user;
    }
}