package pl.wsb.fitnesstracker.event;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class EventRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Event findById(Long id) {
        return entityManager.find(Event.class, id);
    }

    public Event save(Event event) {
        if (event.getId() == null) {
            entityManager.persist(event);
            return event;
        } else {
            return entityManager.merge(event);
        }
    }

    // Example JPQL query
    public List<Event> findByCity(String city) {
        return entityManager.createQuery(
                        "select e from Event e where e.city = :city",
                        Event.class
                )
                .setParameter("city", city)
                .getResultList();
    }

    // Example native SQL query (optional, but shows both styles)
    public List<Event> findByCountryNative(String country) {
        return entityManager.createNativeQuery(
                        "select * from event where country = :country",
                        Event.class
                )
                .setParameter("country", country)
                .getResultList();
    }
}
