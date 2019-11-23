package rs.laxsrbija.foodbot.common.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuReviewEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String sender;

	@Column
	private LocalDateTime dateSent;

	@Column
	private LocalDateTime dateReceived;

	@Column
	private String rawText;

	@JoinColumn(name = "notification_id")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReceivedMenuItemEntity> receivedMenuItemEntities;
}
