package pl.wsb.fitnesstracker.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailSender;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class WeeklyTrainingReportJob {

    private final TrainingRepository trainingRepository;
    private final EmailSender emailSender;

    @Scheduled(cron = "0 0 8 * * MON")
    void generateWeeklyReport() {

        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        Instant start = startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = endOfWeek.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();

        List<Training> trainings = trainingRepository.findAll().stream()
                .filter(t ->
                        !t.getStartTime().toInstant().isBefore(start) &&
                        !t.getStartTime().toInstant().isAfter(end)
                )
                .toList();

        Map<User, List<Training>> trainingsPerUser = trainings.stream()
                .collect(Collectors.groupingBy(Training::getUser));

        trainingsPerUser.forEach((user, userTrainings) -> {

            int totalTrainings = userTrainings.size();

            log.info(
                    "RAPORT TYGODNIOWY | Użytkownik: {} {} | Liczba treningów: {}",
                    user.getFirstName(),
                    user.getLastName(),
                    totalTrainings
            );

            emailSender.send(
                    new EmailDto(
                            user.getEmail(),
                            "Tygodniowe podsumowanie treningów",
                            "W tym tygodniu masz zarejestrowanych treningów: " + totalTrainings
                    )
            );
        });
    }
}
