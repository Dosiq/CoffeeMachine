package kz.solva.task.coffe_machine.service;

import kz.solva.task.coffe_machine.domain.Recipe;
import kz.solva.task.coffe_machine.domain.Statistics;
import kz.solva.task.coffe_machine.repository.Statistics_repository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final Statistics_repository statisticsRepository;

    public void updateStatistics(Recipe drink){
        Statistics statistics = statisticsRepository.findByDrink(drink);
        if(statistics == null){
            Statistics newStatistics = new Statistics();
            newStatistics.setOrderCount(1);
            newStatistics.setDrink(drink);
            statisticsRepository.save(newStatistics);
        }else{
            statistics.setOrderCount(statistics.getOrderCount() + 1);
            statisticsRepository.save(statistics);
        }
    }

    public ResponseEntity<String> getStatistics(){
        List<Statistics> statistics = statisticsRepository.findAll();
        String allStatistics = statistics.stream()
                .map(stat -> stat.getDrink().getName() + ": " + stat.getOrderCount())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(allStatistics, HttpStatus.OK);
    }
}
