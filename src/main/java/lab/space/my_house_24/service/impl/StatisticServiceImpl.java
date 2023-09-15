package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Statistic;
import lab.space.my_house_24.repository.StatisticRepository;
import lab.space.my_house_24.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Override
    public List<Statistic> getAllStatistics() {
        log.info("Try to find all Statistics");
        List<Statistic> statistics = statisticRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
        if (statistics.size() > 1) log.error("Statistics table size > 1");
        log.info("Success find all Statistics");
        return statistics;
    }

    @Override
    public void updateStatistic(Statistic statistic) throws EntityNotFoundException {
        log.info("Try to update Statistic");
        List<Statistic> statistics = getAllStatistics();
        if (statistics.isEmpty()) {
            throw new EntityNotFoundException("Statistic entity not found");
        }
        Statistic statisticDB = statistics.get(0);
        if (statistic.getCashBoxState().compareTo(BigDecimal.ZERO) != 0){
            statisticDB.setCashBoxState(statisticDB.getCashBoxState().add(statistic.getCashBoxState()));
            saveStatistic(statisticDB);
        }else if (statistic.getBankBookBalance().compareTo(BigDecimal.ZERO) != 0){
            statisticDB.setBankBookBalance(statisticDB.getBankBookBalance().add(statistic.getBankBookBalance()));
            saveStatistic(statisticDB);
        }else if (statistic.getBankBookExpense().compareTo(BigDecimal.ZERO) != 0){
            statisticDB.setBankBookExpense(statisticDB.getBankBookExpense().add(statistic.getBankBookExpense()));
            saveStatistic(statisticDB);
        }
        log.info("Success update Statistic");
    }

    @Override
    public void saveStatistic(Statistic statistic) {
        log.info("Try to update Statistic");
        statisticRepository.save(statistic);
        log.info("Success update Statistic");
    }
}
