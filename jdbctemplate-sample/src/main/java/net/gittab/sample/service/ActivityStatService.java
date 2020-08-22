package net.gittab.sample.service;

import java.util.List;

import net.gittab.sample.domain.ActivityStat;

/**
 * ActivityStatService.
 *
 * @author xiaohua zhou
 **/
public interface ActivityStatService {

	ActivityStat findById(Long id);

	Long findTimesViewedById(Long id);

	List<ActivityStat> findByWorksCount(Long worksCount);

	List<Long> findIdByWorksCount(Long worksCount);

	void insertStats(List<ActivityStat> activityStatList);

	void batchInsertStats(List<ActivityStat> activityStatList);

}
