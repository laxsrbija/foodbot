package rs.laxsrbija.foodbot.messaging.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import rs.laxsrbija.foodbot.messaging.model.EmojiSection;

@Repository
public class EmojiSectionsRepository extends AbstractMemoryRepository<List<EmojiSection>>
{
}
