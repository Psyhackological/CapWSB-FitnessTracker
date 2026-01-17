package pl.wsb.fitnesstracker.training.internal;

import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
class TrainingController {

    private final TrainingRepository repository;

    TrainingController(TrainingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Training> getAllTrainings() {
        return repository.findAll();
    }

    @GetMapping("/{userId}")
    List<Training> getTrainingsByUser(@PathVariable Long userId) {
        return repository.findAll().stream()
                .filter(t -> t.getUser().getId().equals(userId))
                .toList();
    }
}
