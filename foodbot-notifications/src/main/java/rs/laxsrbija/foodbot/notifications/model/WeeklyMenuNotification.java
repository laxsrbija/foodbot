package rs.laxsrbija.foodbot.notifications.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeeklyMenuNotification
{
	private Integer id;
	private String sender;
	private LocalDateTime dateSent;
	private LocalDateTime dateReceived;
	private String rawText;
	private List<ReceivedMenuItem> receivedMenuItems;
}
