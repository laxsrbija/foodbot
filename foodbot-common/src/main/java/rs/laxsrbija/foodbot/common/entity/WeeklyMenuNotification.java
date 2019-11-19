package rs.laxsrbija.foodbot.common.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
public class WeeklyMenuNotification
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

	// TODO https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
	@JoinColumn(name = "notification_id")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReceivedMenuItem> receivedMenuItems;
}
