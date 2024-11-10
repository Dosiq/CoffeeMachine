package kz.solva.task.coffe_machine.controller;

import kz.solva.task.coffe_machine.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/allStatistics")
    public ResponseEntity<String> getALl(){
        return statisticsService.getStatistics();
    }
}
