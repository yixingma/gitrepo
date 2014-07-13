/**
 * 
 */
package com.invy.controller;

import hello.HelloJNI;
import hello.pojo.ItemInstance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import com.invy.endpoint.CompareSubkitAfterOperationRequest;
import com.invy.endpoint.CompareSubkitAfterOperationResponse;
import com.invy.endpoint.CompareSubkitBeforeOperationRequest;
import com.invy.endpoint.CompareSubkitBeforeOperationResponse;
import com.invy.endpoint.ConfirmSubkitAfterOperationRequest;
import com.invy.endpoint.ConfirmSubkitAfterOperationResponse;
import com.invy.endpoint.ConfirmSubkitBeforeOperationRequest;
import com.invy.endpoint.ConfirmSubkitBeforeOperationResponse;
import com.invy.endpoint.ItemBinding;
import com.invy.endpoint.ItemCharge;
import com.invy.endpoint.KitBinding;
import com.invy.endpoint.SubkitBinding;

/**
 * @author ema
 * 
 */
@Controller
public class BeforeAfterSurgeryReportController {
	@Autowired
	DemoRepository demoRepository;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BeforeAfterSurgeryReportController.class);
	@Autowired
	MapperFacade mapper;

	// Use search kits by userId
	@Deprecated
	@RequestMapping(value = "/get-all-kits-by-username/{username}", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public @ResponseBody
	List<KitBinding> getAllKitsByUsername(
			@PathVariable("username") String username) {
		LOGGER.info("username {}", username);
		List<Kit> kits = demoRepository.searchKitsByUserId(username);
		return mapper.mapAsList(kits, KitBinding.class);
	}

	@Transactional
	@RequestMapping("/compare-subkit-before-operation")
	public @ResponseBody
	CompareSubkitBeforeOperationResponse compareSubkitBeforeOperation(
			@RequestBody final CompareSubkitBeforeOperationRequest compareSubkitBeforeOperationRequest) {
		LOGGER.info("CompareSubkitBeforeOperationRequest {}",
				compareSubkitBeforeOperationRequest);
		int kitId = compareSubkitBeforeOperationRequest.getKitBinding()
				.getKitId();
		LOGGER.info("kitId {}", kitId);
		Kit kit = demoRepository.findById(kitId, Kit.class);
		LOGGER.info("kit {}", kit);
		if (kit == null) {
			throw new RuntimeException(
					"Kit does not exist. Please setup kit first.");
		}
		SubkitBinding subkitBinding = compareSubkitBeforeOperationRequest
				.getKitBinding().getSubkitBindings().get(0);
		int subkitTypeId = subkitBinding.getSubkitTypeId();
		int subkitId = subkitBinding.getSubkitId();
		LOGGER.info("subkitTypeId {}", subkitTypeId);
		LOGGER.info("subkitId {}", subkitId);
		Subkit subkit = demoRepository.findById(subkitId, Subkit.class);
		Subkittype subkitType = demoRepository.findById(subkitTypeId,
				Subkittype.class);
		if (subkit == null) {
			throw new RuntimeException(
					"Subkit does not exist. Please setup subkit first.");
		}
		Set<Item> existingItems = subkit.getItems();

		Requestmaster requestMaster = new Requestmaster();
		Date createAndUpdateDate = new Date();
		requestMaster.setCreateDateTime(createAndUpdateDate);
		requestMaster.setUpdateDateTime(createAndUpdateDate);
		requestMaster.setKit(kit);
		requestMaster.setKittype(kit.getKittype());
		requestMaster.setTransactionID(compareSubkitBeforeOperationRequest
				.getTransactionId());
		requestMaster.setTransactionSeq(compareSubkitBeforeOperationRequest
				.getTransactionSequenceNumber());
		Requestimage requestImage = new Requestimage();
		requestImage.setCreateDateTime(createAndUpdateDate);
		requestImage.setUpdateDateTime(createAndUpdateDate);
		requestImage.setSubkittype(subkitType);
		requestImage.setRequestmaster(requestMaster);
		try {
			requestImage.setRequestImage(CompressionUtils
					.compress(subkitBinding.getImage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e);
		}
		// LOGGER.info("subkitBinding.getImage {}", subkitBinding.getImage());
		Set<Requestimage> requestImages = new HashSet<>();
		requestImages.add(requestImage);
		requestMaster.setRequestimages(requestImages);

		demoRepository.addObject(requestMaster);
		LOGGER.info("PERSIST DONE");

		HelloJNI helloJNI = new HelloJNI();
		List<ItemInstance> itemInstances = helloJNI.getElementsByteArray(
				requestMaster.getRequestimages().iterator().next()
						.getRequestImage(), kit.getKittype().getId(),
				subkitTypeId);
		Set<Itemtx> itemtxs = new HashSet<>();
		Set<Integer> newItemRefIds = new HashSet<>();
		Map<Integer, ItemBinding> newItemBindingsMap = new HashMap<>();
		Set<Integer> intersectDiffSet = new HashSet<>();
		// List<ItemBinding> itemBindings = new ArrayList<>();
		for (ItemInstance ii : itemInstances) {
			LOGGER.info("ItemInstance name=" + ii.getName());
			LOGGER.info("ItemInstance id=" + ii.getId());
			LOGGER.info("ItemInstance quantity=" + ii.getQuantity());
			// Persist to database
			Itemref itemRef = demoRepository.findById(
					Integer.valueOf(ii.getId()), Itemref.class);
			Itemtx itemTx = new Itemtx();
			itemTx.setItemref(itemRef);
			itemTx.setUnitNum(ii.getQuantity());
			itemtxs.add(itemTx);
			itemTx.setRequestimage(requestImage);
			// TODO: Persist the items/map to the response itemBindings
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setItemId(itemTx.getId());
			itemBinding.setDescription(itemRef.getDescription());
			itemBinding.setName(itemRef.getName());
			itemBinding.setUnitNum(ii.getQuantity());
			itemBinding.setItemrefId(itemRef.getId());
			// itemBindings.add(itemBinding);
			newItemBindingsMap.put(itemRef.getId(), itemBinding);
			newItemRefIds.add(itemRef.getId());
		}
		List<ItemBinding> existingItemBindings = mapper.mapAsList(
				existingItems, ItemBinding.class);
		Set<Integer> existingItemRefIds = new HashSet<>();
		Map<Integer, ItemBinding> existingItemBindingsMap = new HashMap<>();
		for (ItemBinding itemBinding : existingItemBindings) {
			existingItemRefIds.add(itemBinding.getItemrefId());
			ItemBinding newItemBinding = newItemBindingsMap.get(itemBinding
					.getItemrefId());
			if (newItemBinding != null
					&& newItemBinding.getUnitNum() != itemBinding.getUnitNum()) {
				intersectDiffSet.add(itemBinding.getItemrefId());
			}
			existingItemBindingsMap
					.put(itemBinding.getItemrefId(), itemBinding);
		}
		requestImage.setItemtxs(itemtxs);
		//
		SetView<Integer> differenceItemRefIdSet = Sets.symmetricDifference(
				newItemRefIds, existingItemRefIds);
		LOGGER.info("differenceItemRefIdSet {}", differenceItemRefIdSet);
		LOGGER.info("intersectDiffSet {}", intersectDiffSet);
		differenceItemRefIdSet.copyInto(intersectDiffSet);
		LOGGER.info("intersectDiffSet {}", intersectDiffSet);

		CompareSubkitBeforeOperationResponse compareSubkitBeforeOperationResponse = new CompareSubkitBeforeOperationResponse();
		compareSubkitBeforeOperationResponse
				.setRequestId(requestMaster.getId());
		compareSubkitBeforeOperationResponse
				.setTransactionId(compareSubkitBeforeOperationRequest
						.getTransactionId());
		compareSubkitBeforeOperationResponse
				.setTransactionSequenceNumber(compareSubkitBeforeOperationRequest
						.getTransactionSequenceNumber());
		KitBinding kitBinding = compareSubkitBeforeOperationRequest
				.getKitBinding();
		compareSubkitBeforeOperationResponse.setKitBinding(kitBinding);
		kitBinding.getSubkitBindings().get(0)
				.setItemBindings(existingItemBindings);
		compareSubkitBeforeOperationResponse
				.setDiffItemRefIds(intersectDiffSet);
		compareSubkitBeforeOperationResponse
				.setExistingItems(existingItemBindingsMap);
		compareSubkitBeforeOperationResponse.setNewItems(newItemBindingsMap);
		LOGGER.info("CompareSubkitBeforeOperationResponse {}",
				compareSubkitBeforeOperationResponse);
		return compareSubkitBeforeOperationResponse;
	}

	@Transactional
	@RequestMapping("/confirm-subkit-before-operation")
	public @ResponseBody
	ConfirmSubkitBeforeOperationResponse confirmSubkitBeforeOperation(
			@RequestBody final ConfirmSubkitBeforeOperationRequest confirmSubkitBeforeOperationRequest) {
		LOGGER.info("confirmSubkitBeforeOperationRequest requestID {}", confirmSubkitBeforeOperationRequest.getRequestId());
		ConfirmSubkitBeforeOperationResponse response = new ConfirmSubkitBeforeOperationResponse();
		Requestmaster requestMaster = demoRepository.findById(confirmSubkitBeforeOperationRequest.getRequestId(), Requestmaster.class);
		Kit kit = requestMaster.getKit();
		Set<Requestimage> requestImages = requestMaster.getRequestimages();
		if(requestImages!=null && requestImages.size()>0){
			Requestimage requestImage = requestImages.iterator().next();
			
			Subkittype subkitType = requestImage.getSubkittype();
			List<Subkit> subKitList = demoRepository.searchSubkitByKitIdAndSubkitTypeId(kit.getId(),subkitType.getId());
			if(subKitList==null || subKitList.size()!=1){
				throw new RuntimeException("Subkit not found!");
			}
			Subkit subKit = subKitList.get(0);
			int itemsRemoved = demoRepository.removeAllItemsBySubkitId(subKit.getId());
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("itemsRemoved {}",itemsRemoved);
			}
			Set<Itemtx> itemTxs = requestImage.getItemtxs();
			List<ItemBinding> itemBindings = new ArrayList<>();
			for(Itemtx itemTx :itemTxs){
				LOGGER.info("Itemtx {}",itemTx);
				Item item = new Item();
				item.setItemref(itemTx.getItemref());
				item.setSubkit(subKit);
				item.setUnitNum(itemTx.getUnitNum());
				demoRepository.addObject(item);
				ItemBinding itemBinding = new ItemBinding();
				itemBinding.setItemId(item.getId());
				itemBinding.setDescription(item.getItemref().getDescription());
				itemBinding.setName(item.getItemref().getName());
				itemBinding.setUnitNum(item.getUnitNum());
				itemBinding.setItemrefId(item.getItemref().getId());
				itemBindings.add(itemBinding);
			}
			KitBinding kitBinding = mapper.map(kit, KitBinding.class);
			List<SubkitBinding> subkitBindingList = kitBinding.getSubkitBindings();
			for(Iterator<SubkitBinding> subkitBindingListIterator = subkitBindingList.iterator(); subkitBindingListIterator.hasNext();){
				SubkitBinding subkitBinding = subkitBindingListIterator.next();
				if(subkitBinding.getSubkitId()!=subKit.getId()){
					subkitBindingListIterator.remove();
				}else{
					subkitBinding.setItemBindings(itemBindings);
				}
			}
			response.setKitBinding(kitBinding);
			response.setRequestId(confirmSubkitBeforeOperationRequest.getRequestId());
			response.setTransactionId(confirmSubkitBeforeOperationRequest.getTransactionId());
			response.setTransactionSequenceNumber(confirmSubkitBeforeOperationRequest.getTransactionSequenceNumber());
		}else{
			throw new RuntimeException("Previous upload not found!");
		}
		return response;
	}
	
	
	//TODO: Refactor dedup
	@Transactional
	@RequestMapping("/compare-subkit-after-operation")
	public @ResponseBody
	CompareSubkitAfterOperationResponse compareSubkitAfterOperation(
			@RequestBody final CompareSubkitAfterOperationRequest compareSubkitAfterOperationRequest) {
		LOGGER.info("CompareSubkitAfterOperationRequest {}",
				compareSubkitAfterOperationRequest);
		int kitId = compareSubkitAfterOperationRequest.getKitBinding()
				.getKitId();
		LOGGER.info("kitId {}", kitId);
		Kit kit = demoRepository.findById(kitId, Kit.class);
		LOGGER.info("kit {}", kit);
		if (kit == null) {
			throw new RuntimeException(
					"Kit does not exist. Please setup kit first.");
		}
		SubkitBinding subkitBinding = compareSubkitAfterOperationRequest
				.getKitBinding().getSubkitBindings().get(0);
		int subkitTypeId = subkitBinding.getSubkitTypeId();
		int subkitId = subkitBinding.getSubkitId();
		LOGGER.info("subkitTypeId {}", subkitTypeId);
		LOGGER.info("subkitId {}", subkitId);
		Subkit subkit = demoRepository.findById(subkitId, Subkit.class);
		Subkittype subkitType = demoRepository.findById(subkitTypeId,
				Subkittype.class);
		if (subkit == null) {
			throw new RuntimeException(
					"Subkit does not exist. Please setup subkit first.");
		}
		Set<Item> existingItems = subkit.getItems();

		Requestmaster requestMaster = new Requestmaster();
		Date createAndUpdateDate = new Date();
		requestMaster.setCreateDateTime(createAndUpdateDate);
		requestMaster.setUpdateDateTime(createAndUpdateDate);
		requestMaster.setKit(kit);
		requestMaster.setKittype(kit.getKittype());
		requestMaster.setTransactionID(compareSubkitAfterOperationRequest
				.getTransactionId());
		requestMaster.setTransactionSeq(compareSubkitAfterOperationRequest
				.getTransactionSequenceNumber());
		Requestimage requestImage = new Requestimage();
		requestImage.setCreateDateTime(createAndUpdateDate);
		requestImage.setUpdateDateTime(createAndUpdateDate);
		requestImage.setSubkittype(subkitType);
		requestImage.setRequestmaster(requestMaster);
		try {
			requestImage.setRequestImage(CompressionUtils
					.compress(subkitBinding.getImage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e);
		}
		// LOGGER.info("subkitBinding.getImage {}", subkitBinding.getImage());
		Set<Requestimage> requestImages = new HashSet<>();
		requestImages.add(requestImage);
		requestMaster.setRequestimages(requestImages);

		demoRepository.addObject(requestMaster);
		LOGGER.info("PERSIST DONE");

		HelloJNI helloJNI = new HelloJNI();
		List<ItemInstance> itemInstances = helloJNI.getElementsByteArray(
				requestMaster.getRequestimages().iterator().next()
						.getRequestImage(), kit.getKittype().getId(),
				subkitTypeId);
		Set<Itemtx> itemtxs = new HashSet<>();
		Set<Integer> newItemRefIds = new HashSet<>();
		Map<Integer, ItemBinding> newItemBindingsMap = new HashMap<>();
		Set<Integer> intersectDiffSet = new HashSet<>();
		// List<ItemBinding> itemBindings = new ArrayList<>();
		for (ItemInstance ii : itemInstances) {
			LOGGER.info("ItemInstance name=" + ii.getName());
			LOGGER.info("ItemInstance id=" + ii.getId());
			LOGGER.info("ItemInstance quantity=" + ii.getQuantity());
			// Persist to database
			Itemref itemRef = demoRepository.findById(
					Integer.valueOf(ii.getId()), Itemref.class);
			Itemtx itemTx = new Itemtx();
			itemTx.setItemref(itemRef);
			itemTx.setUnitNum(ii.getQuantity());
			itemtxs.add(itemTx);
			itemTx.setRequestimage(requestImage);
			// TODO: Persist the items/map to the response itemBindings
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setItemId(itemTx.getId());
			itemBinding.setDescription(itemRef.getDescription());
			itemBinding.setName(itemRef.getName());
			itemBinding.setUnitNum(ii.getQuantity());
			itemBinding.setItemrefId(itemRef.getId());
			// itemBindings.add(itemBinding);
			newItemBindingsMap.put(itemRef.getId(), itemBinding);
			newItemRefIds.add(itemRef.getId());
		}
		List<ItemBinding> existingItemBindings = mapper.mapAsList(
				existingItems, ItemBinding.class);
		Set<Integer> existingItemRefIds = new HashSet<>();
		Map<Integer, ItemBinding> existingItemBindingsMap = new HashMap<>();
		for (ItemBinding itemBinding : existingItemBindings) {
			existingItemRefIds.add(itemBinding.getItemrefId());
			ItemBinding newItemBinding = newItemBindingsMap.get(itemBinding
					.getItemrefId());
			if (newItemBinding != null
					&& newItemBinding.getUnitNum() != itemBinding.getUnitNum()) {
				intersectDiffSet.add(itemBinding.getItemrefId());
			}
			existingItemBindingsMap
					.put(itemBinding.getItemrefId(), itemBinding);
		}
		requestImage.setItemtxs(itemtxs);
		//
		SetView<Integer> differenceItemRefIdSet = Sets.symmetricDifference(
				newItemRefIds, existingItemRefIds);
		LOGGER.info("differenceItemRefIdSet {}", differenceItemRefIdSet);
		LOGGER.info("intersectDiffSet {}", intersectDiffSet);
		differenceItemRefIdSet.copyInto(intersectDiffSet);
		LOGGER.info("intersectDiffSet {}", intersectDiffSet);

		CompareSubkitAfterOperationResponse compareSubkitAfterOperationResponse = new CompareSubkitAfterOperationResponse();
		compareSubkitAfterOperationResponse
				.setRequestId(requestMaster.getId());
		compareSubkitAfterOperationResponse
				.setTransactionId(compareSubkitAfterOperationRequest
						.getTransactionId());
		compareSubkitAfterOperationResponse
				.setTransactionSequenceNumber(compareSubkitAfterOperationRequest
						.getTransactionSequenceNumber());
		KitBinding kitBinding = compareSubkitAfterOperationRequest
				.getKitBinding();
		compareSubkitAfterOperationResponse.setKitBinding(kitBinding);
		kitBinding.getSubkitBindings().get(0)
				.setItemBindings(existingItemBindings);
		compareSubkitAfterOperationResponse
				.setDiffItemRefIds(intersectDiffSet);
		compareSubkitAfterOperationResponse
				.setExistingItems(existingItemBindingsMap);
		compareSubkitAfterOperationResponse.setNewItems(newItemBindingsMap);
		LOGGER.info("CompareSubkitAfterOperationResponse {}",
				compareSubkitAfterOperationResponse);
		return compareSubkitAfterOperationResponse;
	}
	
	@Transactional
	@RequestMapping("/confirm-subkit-after-operation")
	public @ResponseBody
	ConfirmSubkitAfterOperationResponse confirmSubkitAfterOperation(
			@RequestBody final ConfirmSubkitAfterOperationRequest confirmSubkitAfterOperationRequest) {
		LOGGER.info("confirmSubkitAfterOperationRequest requestID {}", confirmSubkitAfterOperationRequest.getRequestId());
		ConfirmSubkitAfterOperationResponse response = new ConfirmSubkitAfterOperationResponse();
		Requestmaster requestMaster = demoRepository.findById(confirmSubkitAfterOperationRequest.getRequestId(), Requestmaster.class);
		Kit kit = requestMaster.getKit();
		Set<Requestimage> requestImages = requestMaster.getRequestimages();
		if(requestImages!=null && requestImages.size()>0){
			Requestimage requestImage = requestImages.iterator().next();
			
			Subkittype subkitType = requestImage.getSubkittype();
			List<Subkit> subKitList = demoRepository.searchSubkitByKitIdAndSubkitTypeId(kit.getId(),subkitType.getId());
			if(subKitList==null || subKitList.size()!=1){
				throw new RuntimeException("Subkit not found!");
			}
			Subkit subKit = subKitList.get(0);
			
			Set<Itemtx> itemTxs = requestImage.getItemtxs();
			List<ItemBinding> itemBindings = new ArrayList<>();
			List<Item> detachedItemList = new ArrayList<>();
			Map<Integer,ItemBinding> newItemBindings = new HashMap<>();
			for(Itemtx itemTx :itemTxs){
				LOGGER.info("Itemtx {}",itemTx);
				Item item = new Item();
				item.setItemref(itemTx.getItemref());
				item.setSubkit(subKit);
				item.setUnitNum(itemTx.getUnitNum());
				ItemBinding itemBinding = new ItemBinding();
				itemBinding.setItemId(item.getId());
				itemBinding.setDescription(item.getItemref().getDescription());
				itemBinding.setName(item.getItemref().getName());
				itemBinding.setUnitNum(item.getUnitNum());
				itemBinding.setItemrefId(item.getItemref().getId());
				itemBinding.setUnitPrice(new BigDecimal(item.getItemref().getUnitPrice()));
				itemBindings.add(itemBinding);
				newItemBindings.put(item.getItemref().getId(), itemBinding);
			}
			Set<Item> originalItems = subKit.getItems();
			Map<Integer,ItemCharge> itemChargeMap = new HashMap<>();
			for(Item item:originalItems){
				ItemBinding newItemBinding = newItemBindings.get(item.getItemref().getId());
				BigDecimal unitPrice = new BigDecimal(item.getItemref().getUnitPrice());
				int quantityDiff = item.getUnitNum()-newItemBinding.getUnitNum();
				ItemCharge itemCharge = new ItemCharge();
				itemCharge.setCost(unitPrice.multiply(new BigDecimal(quantityDiff)));
				itemCharge.setDescription(newItemBinding.getDescription());
				itemCharge.setName(newItemBinding.getName());
				itemCharge.setItemrefId(newItemBinding.getItemrefId());
				itemCharge.setQuantity(quantityDiff);
				itemCharge.setUnitPrice(unitPrice);
				itemChargeMap.put(item.getItemref().getId(), itemCharge);
			}
			int itemsRemoved = demoRepository.removeAllItemsBySubkitId(subKit.getId());
			for(Item item:detachedItemList){
				demoRepository.addObject(item);
			}
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("itemsRemoved {}",itemsRemoved);
			}
			
			KitBinding kitBinding = mapper.map(kit, KitBinding.class);
			List<SubkitBinding> subkitBindingList = kitBinding.getSubkitBindings();
			for(Iterator<SubkitBinding> subkitBindingListIterator = subkitBindingList.iterator(); subkitBindingListIterator.hasNext();){
				SubkitBinding subkitBinding = subkitBindingListIterator.next();
				if(subkitBinding.getSubkitId()!=subKit.getId()){
					subkitBindingListIterator.remove();
				}else{
					subkitBinding.setItemBindings(itemBindings);
				}
			}
			response.setKitBinding(kitBinding);
			response.setRequestId(confirmSubkitAfterOperationRequest.getRequestId());
			response.setTransactionId(confirmSubkitAfterOperationRequest.getTransactionId());
			response.setTransactionSequenceNumber(confirmSubkitAfterOperationRequest.getTransactionSequenceNumber());
			response.setItemChargeMap(itemChargeMap);
		}else{
			throw new RuntimeException("Previous upload not found!");
		}
		return response;
	}
	
	@RequestMapping(value = "/update-itemtx-quantity/{itemTxId}/{quantity}", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody
	ItemBinding updateItemTxQuantity(@PathVariable("itemTxId") int itemTxId,@PathVariable("quantity") int quantity) {
		LOGGER.info("itemTxId {}", itemTxId);
		LOGGER.info("quantity {}", quantity);
		Itemtx itemTx = demoRepository.findById(itemTxId,Itemtx.class);
		itemTx.setUnitNum(quantity);
		ItemBinding itemBinding = mapper.map(itemTx, ItemBinding.class);
		return itemBinding;
	}
}
