package rs.laxsrbija.foodbot.persistence.repository;

import java.time.DayOfWeek;
import org.springframework.data.repository.CrudRepository;
import rs.laxsrbija.foodbot.persistence.model.entity.DailyMenu;

public interface DailyMenuRepository extends CrudRepository<DailyMenu, DayOfWeek>
{
}
