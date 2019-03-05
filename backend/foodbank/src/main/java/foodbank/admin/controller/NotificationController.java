package foodbank.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;

import java.util.List;

import javax.transaction.Transactional;

@RestController
@CrossOrigin
@Transactional
public class NotificationController {

	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	//public static final Map<String, Boolean> notifications = Collections.synchronizedMap(new HashMap<String, Boolean>());
	
	@MessageMapping("/server/notifications")
	@SendTo("/client/notifications")
	public void retrieveNotifications() {
		evaluateInventoryValue();
	}
	
	@Scheduled(fixedRate = 10000)
	public void evaluateInventoryValue() {
		Boolean notificationPopup = Boolean.FALSE;
		List<FoodItem> foodItems = foodRepository.findByValueEquals(Double.valueOf(0));
		if(foodItems.isEmpty()) {
			notificationPopup = Boolean.TRUE;
		}
		this.template.convertAndSend("/client/notifications", notificationPopup);
	}
}
