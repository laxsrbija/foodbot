package rs.laxsrbija.foodbot.common.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.laxsrbija.foodbot.common.entity.WeeklyMenuNotification;
import rs.laxsrbija.foodbot.common.repository.WeeklyMenuNotificationRepository;

@Service
@RequiredArgsConstructor
public class WeeklyMenuNotificationService
{
	private final WeeklyMenuNotificationRepository _weeklyMenuNotificationRepository;

	public WeeklyMenuNotification save(final WeeklyMenuNotification weeklyMenuNotification) {
		return _weeklyMenuNotificationRepository.save(weeklyMenuNotification);
	}

	public Iterable<WeeklyMenuNotification> findAll()
	{
		return _weeklyMenuNotificationRepository.findAll();
	}

	public long count()
	{
		return _weeklyMenuNotificationRepository.count();
	}
}
