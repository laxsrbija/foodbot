package rs.laxsrbija.foodbot.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.laxsrbija.foodbot.common.entity.WeeklyMenuNotification;

@Repository
public interface WeeklyMenuNotificationRepository extends CrudRepository<WeeklyMenuNotification, Long>
{
}
