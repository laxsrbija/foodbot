package rs.laxsrbija.foodbot.common.repository;

import java.time.DayOfWeek;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.laxsrbija.foodbot.common.model.entity.MenuEntity;

@Repository
public interface MenuRepository extends CrudRepository<MenuEntity, DayOfWeek>
{
}
