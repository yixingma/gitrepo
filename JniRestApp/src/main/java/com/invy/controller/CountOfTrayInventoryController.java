/**
 * 
 */
package com.invy.controller;

import hello.HelloJNI;
import hello.pojo.ItemInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ma.glasnost.orika.MapperFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.invy.commons.tool.CompressionUtils;
import com.invy.database.DemoRepository;
import com.invy.database.jpa.data.Item;
import com.invy.database.jpa.data.Itemref;
import com.invy.database.jpa.data.Itemtx;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Requestimage;
import com.invy.database.jpa.data.Requestmaster;
import com.invy.database.jpa.data.Subkit;
import com.invy.database.jpa.data.Subkittype;
import com.invy.endpoint.CompareKitWithExistingRequest;
import com.invy.endpoint.CompareKitWithExistingResponse;
import com.invy.endpoint.ItemBinding;
import com.invy.endpoint.KitBinding;
import com.invy.endpoint.SubkitBinding;

/**
 * @author ema
 * 
 */
@Controller
public class CountOfTrayInventoryController {
	@Autowired
	DemoRepository demoRepository;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CountOfTrayInventoryController.class);
	@Autowired
	MapperFacade mapper;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@RequestMapping(value = "/get-kits-by-username/{username}", method = RequestMethod.GET)
	public @ResponseBody
	List<KitBinding> getKitsByUsername(@PathVariable("username") String username) {
		List<Kit> kits = demoRepository.searchKitsByUserId(username);
		List<KitBinding> kitBindings = mapper.mapAsList(kits, KitBinding.class);
		LOGGER.info("kits {}", kits);
		LOGGER.info("kitBindings {}", kitBindings);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("kits {}", kits);
			LOGGER.debug("kitBindings {}", kitBindings);
		}
		return kitBindings;
	}
	
	@RequestMapping(value = "/get-kit-by-kitid/{kitId}", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public @ResponseBody
	KitBinding getKitByKitId(@PathVariable("kitId") int kitId) {
		LOGGER.info("kitId {}", kitId);
		Kit kit = demoRepository.findById(kitId,Kit.class);
		KitBinding kitBinding = mapper.map(kit, KitBinding.class);
		return kitBinding;
	}
	
	@Transactional
	@RequestMapping("/compare-kit-with-existing")
	public @ResponseBody
	CompareKitWithExistingResponse compareKitWithExisting(
			@RequestBody final CompareKitWithExistingRequest compareKitWithExistingRequest) {
		//TODO: add implementation
		LOGGER.info("CompareKitWithExistingRequest ID {}",
				compareKitWithExistingRequest.getRequestId());
		int kitId = compareKitWithExistingRequest.getKitBinding().getKitId();
		LOGGER.info("kitId {}", kitId);
		Kit kit = demoRepository.findById(kitId, Kit.class);
		LOGGER.info("kit {}", kit);
		SubkitBinding subkitBinding = compareKitWithExistingRequest.getKitBinding()
				.getSubkitBindings().get(0);
		int subkitTypeId = subkitBinding.getSubkitTypeId();
		int subkitId = subkitBinding.getSubkitId();
		LOGGER.info("subkitTypeId {}", subkitTypeId);
		LOGGER.info("subkitId {}", subkitId);
		Subkit subkit = demoRepository.findById(subkitId,
				Subkit.class);
		Subkittype subkitType = demoRepository.findById(subkitTypeId,
				Subkittype.class);

		Requestmaster requestMaster = new Requestmaster();
		Date createAndUpdateDate = new Date();
		requestMaster.setCreateDateTime(createAndUpdateDate);
		requestMaster.setUpdateDateTime(createAndUpdateDate);
		requestMaster.setKit(kit);
		requestMaster.setKittype(kit.getKittype());
		requestMaster.setTransactionID(compareKitWithExistingRequest.getTransactionId());
		requestMaster.setTransactionSeq(compareKitWithExistingRequest
				.getTransactionSequenceNumber());
		Requestimage requestImage = new Requestimage();
		requestImage.setCreateDateTime(createAndUpdateDate);
		requestImage.setUpdateDateTime(createAndUpdateDate);
		requestImage.setSubkittype(subkitType);
		requestImage.setRequestmaster(requestMaster);
		try {
			requestImage.setRequestImage(CompressionUtils.compress(subkitBinding.getImage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<Requestimage> requestImages = new HashSet<>();
		requestImages.add(requestImage);
		requestMaster.setRequestimages(requestImages);
		
		demoRepository.addObject(requestMaster);
		LOGGER.info("PERSIST DONE");
		
		HelloJNI helloJNI = new HelloJNI();
		List<ItemInstance> itemInstances = helloJNI.getElementsByteArray(
				requestMaster.getRequestimages().iterator().next()
						.getRequestImage(), kit.getKittype().getId(), subkitTypeId);
		Set<Itemtx> itemtxs = new HashSet<>();
		Set<Integer> newItemRefIds = new HashSet<>();
		Map<Integer,ItemBinding> newItemBindingsMap = new HashMap<>();
		for (ItemInstance ii : itemInstances) {
			LOGGER.info("ItemInstance name=" + ii.getName());
			LOGGER.info("ItemInstance id=" + ii.getId());
			LOGGER.info("ItemInstance quantity=" + ii.getQuantity());
			//Persist to database
			Itemref itemRef = demoRepository.findById(Integer.valueOf(ii.getId()), Itemref.class);
			
			Itemtx itemTx = new Itemtx();
			itemTx.setItemref(itemRef);
			itemTx.setUnitNum(ii.getQuantity());
			itemtxs.add(itemTx);
			itemTx.setRequestimage(requestImage);
			
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setDescription(itemRef.getDescription());
			itemBinding.setName(itemRef.getName());
			itemBinding.setUnitNum(ii.getQuantity());
			itemBinding.setItemrefId(itemRef.getId());
			newItemRefIds.add(itemRef.getId());
			newItemBindingsMap.put(itemRef.getId(), itemBinding);
		}
		requestImage.setItemtxs(itemtxs);
		Set<Item> existingItems = subkit.getItems();
		Set<Integer> originalItemRefIds = new HashSet<>();
		List<ItemBinding> originalItemBindings = new ArrayList<>();
		Map<Integer,ItemBinding> originalItemBindingsMap = new HashMap<>();
		Set<Integer> intersectDiffSet = new HashSet<>();
		for(Item item:existingItems){
			LOGGER.info("Item name=" + item.getItemref().getName());
			LOGGER.info("Item id=" + item.getId());
			LOGGER.info("Item quantity=" + item.getUnitNum());
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setDescription(item.getItemref().getDescription());
			itemBinding.setName(item.getItemref().getName());
			itemBinding.setUnitNum(item.getUnitNum());
			itemBinding.setItemrefId(item.getItemref().getId());
			originalItemBindings.add(itemBinding);
			originalItemRefIds.add(item.getItemref().getId());
			originalItemBindingsMap.put(item.getItemref().getId(), itemBinding);
			ItemBinding newItemBinding = newItemBindingsMap.get(item.getItemref().getId());
			if(newItemBinding!=null && newItemBinding.getUnitNum()!=itemBinding.getUnitNum()){
				intersectDiffSet.add(item.getItemref().getId());
			}	
		}
		SetView<Integer> differenceItemRefIdSet = Sets.symmetricDifference(originalItemRefIds, newItemRefIds);
		differenceItemRefIdSet.copyInto(intersectDiffSet);
		CompareKitWithExistingResponse compareKitWithExistingResponse = new CompareKitWithExistingResponse();
		compareKitWithExistingResponse.setRequestId(compareKitWithExistingRequest.getRequestId());
		compareKitWithExistingResponse.setTransactionId(compareKitWithExistingRequest
				.getTransactionId());
		compareKitWithExistingResponse.setTransactionSequenceNumber(compareKitWithExistingRequest
				.getTransactionSequenceNumber());
		KitBinding kitBinding = compareKitWithExistingRequest.getKitBinding();
		compareKitWithExistingResponse.setKitBinding(kitBinding);
		kitBinding.getSubkitBindings().get(0).setItemBindings(originalItemBindings);
		LOGGER.info("CompareKitWithExistingResponse {}", compareKitWithExistingResponse);
		compareKitWithExistingResponse.setOriginalItems(originalItemBindingsMap);
		compareKitWithExistingResponse.setNewItems(newItemBindingsMap);
		compareKitWithExistingResponse.setDiffItemRefIds(intersectDiffSet);
		return compareKitWithExistingResponse;
	}
}
