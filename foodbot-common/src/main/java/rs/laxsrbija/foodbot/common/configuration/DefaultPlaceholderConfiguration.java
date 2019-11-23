package rs.laxsrbija.foodbot.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.laxsrbija.foodbot.common.model.entity.PlaceholderEntity;

@Data
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "foodbot.default-placeholders")
public class DefaultPlaceholderConfiguration
{
	private PlaceholderEntity greeting;
	private PlaceholderEntity mainCourse;
	private PlaceholderEntity salad;
}
