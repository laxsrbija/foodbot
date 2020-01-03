package rs.laxsrbija.foodbot.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.laxsrbija.foodbot.common.model.entity.ArrivalEntity;

@Repository
public interface ArrivalRepository extends CrudRepository<ArrivalEntity, Long>
{
}
