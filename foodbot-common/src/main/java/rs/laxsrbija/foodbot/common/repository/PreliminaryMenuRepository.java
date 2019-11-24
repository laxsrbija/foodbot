package rs.laxsrbija.foodbot.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.laxsrbija.foodbot.common.model.entity.PreliminaryMenuEntity;

@Repository
public interface PreliminaryMenuRepository extends CrudRepository<PreliminaryMenuEntity, Long>
{
}
