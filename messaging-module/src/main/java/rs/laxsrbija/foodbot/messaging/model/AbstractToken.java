package rs.laxsrbija.foodbot.messaging.model;

import java.time.LocalDateTime;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
public abstract class AbstractToken
{
	private String token;
	private LocalDateTime expirationDate;
}
