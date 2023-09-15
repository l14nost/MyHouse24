package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Statistic;

import java.util.List;

public interface StatisticService {
    List<Statistic> getAllStatistics();

    void updateStatistic(Statistic statistic);

    void saveStatistic(Statistic statistic);
}
