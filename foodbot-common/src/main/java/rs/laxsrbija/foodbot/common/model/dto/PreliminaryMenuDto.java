package rs.laxsrbija.foodbot.common.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreliminaryMenuDto
{
	private Long id;
	private String sender;
	private String dateSent;
	private String dateReceived;
	private String rawText;
	private List<ReceivedMenuItemDto> receivedMenuItems;
}
