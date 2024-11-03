package kz.solva.task.coffe_machine.service;

import kz.solva.task.coffe_machine.domain.Holiday;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class HolidayService {

    private final RestTemplate restTemplate;
    private final String API = "https://date.nager.at/Api";

    @Cacheable("holidays")
    public boolean isHoliday(LocalDate date, String country){
        String url = API + "/v3/PublicHolidays/" + date.getYear() + "/" + country;

        Holiday[] holidays = restTemplate.getForObject(url, Holiday[].class);

        if(holidays != null){
            for (int i = 0; i < holidays.length; i++) {
                if(holidays[i].getDate().equals(date)){
                    return true;
                }
            }
        }
        return false;
    }
}
