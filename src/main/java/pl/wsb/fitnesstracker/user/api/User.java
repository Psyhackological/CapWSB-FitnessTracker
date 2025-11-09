package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.wsb.fitnesstracker.healthmetrics.api.HealthMetrics;
import pl.wsb.fitnesstracker.statistics.api.Statistics;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Setter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Setter
    @Column(name = "birthday")
    private LocalDate birthday;

    @Setter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // One-to-one: each user has one statistics row
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Statistics statistics;

    // Many-to-one inverse: user has many health metrics (owned by HealthMetrics)
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HealthMetrics> healthMetrics = new LinkedHashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, LocalDate birthday, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
    }
}
