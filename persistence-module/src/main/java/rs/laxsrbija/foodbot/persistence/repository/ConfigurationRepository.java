package rs.laxsrbija.foodbot.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import rs.laxsrbija.foodbot.persistence.model.entity.ConfigurationEntry;
import rs.laxsrbija.foodbot.persistence.model.ConfigurationKeys;


public interface ConfigurationRepository extends CrudRepository<ConfigurationEntry, ConfigurationKeys>
{

}
