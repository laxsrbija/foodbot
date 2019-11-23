package rs.laxsrbija.foodbot.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.laxsrbija.foodbot.common.model.entity.ConfigurationEntity;

@Repository
public interface ConfigurationRepository extends CrudRepository<ConfigurationEntity, String>
{
	void deleteAllByKeyIsNot(String key);
}
