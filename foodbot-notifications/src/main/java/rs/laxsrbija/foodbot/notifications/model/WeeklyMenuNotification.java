package rs.laxsrbija.foodbot.notifications.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class WeeklyMenuNotification
{
	private Integer id;
	private String sender;
	private LocalDateTime timeOfArrival;
	private String rawText;
	private List<ReceivedMenuItem> receivedMenuItems = new ArrayList<>();
}
