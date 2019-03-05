package foodbank.packing.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import foodbank.packing.dto.PackedItemDTO;
import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.dto.PackingUpdateDTO;
import foodbank.packing.service.PackingService;

@RestController
@CrossOrigin
@Transactional
public class PackingWsController {

	@Autowired
	private PackingService packingService;
	
	private static final Map<String, PackingListDTO> activeLists = Collections.synchronizedMap(new HashMap<String, PackingListDTO>());
	
	@MessageMapping("/server/packing/{listId}")
	@SendTo("/client/packing/{listId}")
	public PackingListDTO dynamicListUpdate(@DestinationVariable String listId, PackingUpdateDTO packingUpdate) {
        PackingListDTO activeList = activeLists.get(listId);
		if(activeList == null || activeList.getPackedItems().isEmpty()) {
			activeList = packingService.findById(listId);
			activeLists.put(listId, activeList);
			return activeList;
		}
		Integer itemIndex = packingUpdate.getItemIndex();
		if(itemIndex == -1) { return activeList; }
		Integer packedQuantity = packingUpdate.getPackedQuantity();
		Boolean itemPackingStatus = packingUpdate.getItemPackingStatus();
		List<PackedItemDTO> packedItemsList = activeList.getPackedItems();
		PackedItemDTO itemToEdit = packedItemsList.get(itemIndex);
		itemToEdit.setPackedQuantity(packedQuantity);
		itemToEdit.setItemPackingStatus(itemPackingStatus);
		return activeList;
	}
	
}
