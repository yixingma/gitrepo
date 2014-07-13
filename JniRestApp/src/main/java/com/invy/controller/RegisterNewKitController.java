/**
 * 
 */
package com.invy.controller;

import hello.HelloJNI;
import hello.pojo.ItemInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

import com.invy.commons.tool.CompressionUtils;
import com.invy.database.DemoRepository;
import com.invy.database.jpa.data.Item;
import com.invy.database.jpa.data.Itemref;
import com.invy.database.jpa.data.Itemtx;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Kittype;
import com.invy.database.jpa.data.Owner;
import com.invy.database.jpa.data.Requestimage;
import com.invy.database.jpa.data.Requestmaster;
import com.invy.database.jpa.data.Subkit;
import com.invy.database.jpa.data.Subkittype;
import com.invy.domain.KitTypePojo;
import com.invy.domain.SubkitTypePojo;
import com.invy.endpoint.AddNewSubkitRequest;
import com.invy.endpoint.AddNewSubkitResponse;
import com.invy.endpoint.ItemBinding;
import com.invy.endpoint.KitBinding;
import com.invy.endpoint.RegisterNewKitRequest;
import com.invy.endpoint.RegisterNewKitResponse;
import com.invy.endpoint.SubkitBinding;
import com.invy.endpoint.UpdateSubkitRequest;
import com.invy.endpoint.UpdateSubkitResponse;

/**
 * @author ema
 * 
 */
@Controller
public class RegisterNewKitController {
	@Autowired
	DemoRepository demoRepository;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RegisterNewKitController.class);
	@Autowired
	MapperFacade mapper;

	@RequestMapping("/get-all-kittypes")
	public @ResponseBody
	List<KitTypePojo> getAllKittypes() {
		List<Kittype> allKitTypes = demoRepository.getAllKitTypes();
		List<KitTypePojo> kitTypePojos = mapper.mapAsList(allKitTypes,
				KitTypePojo.class);
		LOGGER.info("allKitTypes {}", allKitTypes);
		LOGGER.info("kitTypePojos {}", kitTypePojos);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("allKitTypes {}", allKitTypes);
			LOGGER.debug("kitTypePojos {}", kitTypePojos);
		}
		return kitTypePojos;
	}

	@Transactional
	@RequestMapping("/regsiter-new-kit")
	public @ResponseBody
	RegisterNewKitResponse registerNewKit(
			@RequestBody final RegisterNewKitRequest registerNewKitRequest) {
		LOGGER.info("RegisterNewKitRequest ID {}",
				registerNewKitRequest.getRequestId());
		Requestmaster requestMaster = mapper.map(registerNewKitRequest,
				Requestmaster.class);
		LOGGER.info("requestMaster - requestImage {}", requestMaster
				.getRequestimages().size());
		LOGGER.info("requestMaster - kit - subkit {}", requestMaster.getKit()
				.getSubkits().size());
		int kitTypeId = registerNewKitRequest.getKitBinding().getKitTypeId();
		Kittype kitType = demoRepository.findById(kitTypeId, Kittype.class);
		// TODO: get from kitType in memory
		int subkitTypeId = registerNewKitRequest.getKitBinding()
				.getSubkitBindings().get(0).getSubkitTypeId();
		
		Subkittype subkitType = demoRepository.findById(subkitTypeId,
				Subkittype.class);
		Owner owner = demoRepository.searchOwnerByUserId(registerNewKitRequest
				.getKitBinding().getOwnerId());
		LOGGER.info("requestMaster {}", requestMaster);
		LOGGER.info("kitType {}", kitType);
		LOGGER.info("subkitType {}", subkitType);
		LOGGER.info("owner {}", owner);
		requestMaster.setKittype(kitType);
		Requestimage requestImage = requestMaster.getRequestimages().iterator().next();
		Subkit subKit = requestMaster.getKit().getSubkits().iterator().next();
		requestImage.setSubkittype(subkitType);
		requestMaster.getKit().setOwner(owner);
		requestMaster.getKit().setKittype(kitType);
		subKit.setSubkittype(subkitType);
		demoRepository.addObject(requestMaster);
		//Call image processing library
		HelloJNI helloJNI = new HelloJNI();
		List<ItemInstance> itemInstances = helloJNI.getElementsByteArray(
				requestImage.getRequestImage(), kitTypeId, subkitTypeId);
		Set<Itemtx> itemtxs = new HashSet<>();
		List<ItemBinding> itemBindings = new ArrayList<>();
		for (ItemInstance ii : itemInstances) {
			LOGGER.info("ItemInstance name {}", ii.getName());
			LOGGER.info("ItemInstance id {}", ii.getId());
			LOGGER.info("ItemInstance quantity {}", ii.getQuantity());
			//Persist to database
			Itemref itemRef = demoRepository.findById(Integer.valueOf(ii.getId()), Itemref.class);
			LOGGER.info("ItemRef {}",itemRef);
			Item item = new Item();
			item.setItemref(itemRef);
			item.setSubkit(subKit);
			item.setUnitNum(ii.getQuantity());
			demoRepository.addObject(item);
			Itemtx itemTx = new Itemtx();
			itemTx.setItemref(itemRef);
			itemTx.setUnitNum(ii.getQuantity());
			itemTx.setRequestimage(requestImage);
			itemtxs.add(itemTx);
			//TODO: Persist the items/map to the response itemBindings
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setItemId(item.getId());
			itemBinding.setDescription(item.getItemref().getDescription());
			itemBinding.setName(item.getItemref().getName());
			itemBinding.setUnitNum(item.getUnitNum());
			itemBinding.setItemrefId(item.getItemref().getId());
			itemBindings.add(itemBinding);
		}
		requestImage.setItemtxs(itemtxs);
		// TODO: Map result to registerNewKitResponse
		RegisterNewKitResponse registerNewKitResponse = new RegisterNewKitResponse();
		registerNewKitResponse.setRequestId(registerNewKitRequest
				.getRequestId());
		registerNewKitResponse.setTransactionId(registerNewKitRequest
				.getTransactionId());
		registerNewKitResponse
				.setTransactionSequenceNumber(registerNewKitRequest
						.getTransactionSequenceNumber());
		KitBinding kitBinding = registerNewKitRequest.getKitBinding();
		registerNewKitResponse.setKitBinding(kitBinding);
		kitBinding.getSubkitBindings().get(0).setItemBindings(itemBindings);
		kitBinding.setKitId(requestMaster.getKit().getId());
		//TODO: If the subkittype is the last subkittype, set kit completion equals true
		
		LOGGER.info("RegisterNewKitResponse {}", registerNewKitResponse);
		return registerNewKitResponse;
	}

	@RequestMapping(value = "/get-leftover-subkittypes/{kitId}", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public @ResponseBody
	List<SubkitTypePojo> getLeftoverSubkittypes(@PathVariable("kitId") int kitId) {
		Kit kit = demoRepository.findById(kitId, Kit.class);
		if (kit == null) {
			throw new RuntimeException("Kit {" + kitId + "} does not exist!");
		}
		LOGGER.info("kit {}", kit);
		Kittype kitType = kit.getKittype();
		LOGGER.info("kitType {}", kitType);
		Set<Subkittype> subkitTypes = kitType.getSubkittypes();

		LOGGER.info("subkitTypes {}", subkitTypes);
		Set<Subkit> subkits = kit.getSubkits();

		List<Subkittype> subkitTypeList = new ArrayList<>();
		subkitTypeList.addAll(subkitTypes);
		for (Subkit subkit : subkits) {
			subkitTypeList.remove(subkit.getSubkittype());
		}
		List<SubkitTypePojo> subkitTypePojos = mapper.mapAsList(subkitTypeList,
				SubkitTypePojo.class);
		LOGGER.info("subkitTypePojos {}", subkitTypePojos);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("kit {}", kit);
			LOGGER.debug("kitType {}", kitType);
		}
		return subkitTypePojos;
	}

	@Transactional
	@RequestMapping("/add-new-subkit")
	public @ResponseBody
	AddNewSubkitResponse addNewSubkit(
			@RequestBody final AddNewSubkitRequest addNewSubkitRequest) {
		LOGGER.info("AddNewSubkitRequest ID {}",
				addNewSubkitRequest.getRequestId());
		int kitId = addNewSubkitRequest.getKitBinding().getKitId();
		LOGGER.info("kitId {}", kitId);
		Kit kit = demoRepository.findById(kitId, Kit.class);
		SubkitBinding subkitBinding = addNewSubkitRequest.getKitBinding()
				.getSubkitBindings().get(0);
		int subkitTypeId = subkitBinding.getSubkitTypeId();
		LOGGER.info("subkitTypeId {}", subkitTypeId);
		Subkittype subkitType = demoRepository.findById(subkitTypeId,
				Subkittype.class);
		Subkit subkit = new Subkit();
		subkit.setDescription(subkitBinding.getSubkitDescription());
		subkit.setName(subkitBinding.getSubkitName());
		subkit.setKit(kit);
		subkit.setSubkittype(subkitType);
		kit.getSubkits().add(subkit);
		LOGGER.info("kit {}", kit);
		Requestmaster requestMaster = new Requestmaster();
		Date createAndUpdateDate = new Date();
		requestMaster.setCreateDateTime(createAndUpdateDate);
		requestMaster.setUpdateDateTime(createAndUpdateDate);
		requestMaster.setKit(kit);
		requestMaster.setKittype(kit.getKittype());
		requestMaster.setTransactionID(addNewSubkitRequest.getTransactionId());
		requestMaster.setTransactionSeq(addNewSubkitRequest
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
		//LOGGER.info("subkitBinding.getImage {}", subkitBinding.getImage());
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
		List<ItemBinding> itemBindings = new ArrayList<>();
		for (ItemInstance ii : itemInstances) {
			LOGGER.info("ItemInstance name=" + ii.getName());
			LOGGER.info("ItemInstance id=" + ii.getId());
			LOGGER.info("ItemInstance quantity=" + ii.getQuantity());
			//Persist to database
			Itemref itemRef = demoRepository.findById(Integer.valueOf(ii.getId()), Itemref.class);
			Item item = new Item();
			item.setItemref(itemRef);
			item.setSubkit(subkit);
			item.setUnitNum(ii.getQuantity());
			demoRepository.addObject(item);
			Itemtx itemTx = new Itemtx();
			itemTx.setItemref(itemRef);
			itemTx.setUnitNum(ii.getQuantity());
			itemtxs.add(itemTx);
			itemTx.setRequestimage(requestImage);
			//TODO: Persist the items/map to the response itemBindings
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setItemId(item.getId());
			itemBinding.setDescription(item.getItemref().getDescription());
			itemBinding.setName(item.getItemref().getName());
			itemBinding.setUnitNum(item.getUnitNum());
			itemBinding.setItemrefId(item.getItemref().getId());
			itemBindings.add(itemBinding);
		}
		requestImage.setItemtxs(itemtxs);
		//
		AddNewSubkitResponse addNewSubkitResponse = new AddNewSubkitResponse();
		addNewSubkitResponse.setRequestId(addNewSubkitRequest.getRequestId());
		addNewSubkitResponse.setTransactionId(addNewSubkitRequest
				.getTransactionId());
		addNewSubkitResponse.setTransactionSequenceNumber(addNewSubkitRequest
				.getTransactionSequenceNumber());
		KitBinding kitBinding = addNewSubkitRequest.getKitBinding();
		addNewSubkitResponse.setKitBinding(kitBinding);
		kitBinding.getSubkitBindings().get(0).setItemBindings(itemBindings);
		LOGGER.info("AddNewSubkitResponse {}", addNewSubkitResponse);
		return addNewSubkitResponse;
	}
	
	@RequestMapping(value = "/update-item-quantity/{itemId}/{quantity}", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody
	ItemBinding updateItemQuantity(@PathVariable("itemId") int itemId,@PathVariable("quantity") int quantity) {
		LOGGER.info("itemId {}", itemId);
		LOGGER.info("quantity {}", quantity);
		Item item = demoRepository.findById(itemId,Item.class);
		item.setUnitNum(quantity);
		ItemBinding itemBinding = mapper.map(item, ItemBinding.class);
		return itemBinding;
	}
	
	//TODO: Mark kit status to "inactive". enumerate
	@RequestMapping(value = "/inactivate-kit/{kitId}", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody
	void inactivateKitByKitId(@PathVariable("kitId") int kitId) {
		LOGGER.info("kitId {}", kitId);
		Kit kit = demoRepository.findById(kitId,Kit.class);
		kit.setStatus("Inactive");
		return;
	}
	
	@Transactional
	@RequestMapping("/update-subkit")
	public @ResponseBody
	UpdateSubkitResponse updateSubkit(@RequestBody final UpdateSubkitRequest updateSubkitRequest){

		LOGGER.info("UpdateSubkitRequest ID {}",
				updateSubkitRequest.getRequestId());
		int kitId = updateSubkitRequest.getKitBinding().getKitId();
		LOGGER.info("kitId {}", kitId);
		Kit kit = demoRepository.findById(kitId, Kit.class);
		SubkitBinding subkitBinding = updateSubkitRequest.getKitBinding()
				.getSubkitBindings().get(0);
		int subkitTypeId = subkitBinding.getSubkitTypeId();
		int subkitId = subkitBinding.getSubkitId();
		LOGGER.info("subkitTypeId {}", subkitTypeId);
		LOGGER.info("subkitId {}", subkitId);
		Subkit subkit = demoRepository.findById(subkitId,
				Subkit.class);
		Subkittype subkitType = demoRepository.findById(subkitTypeId,
				Subkittype.class);
		subkit.setDescription(subkitBinding.getSubkitDescription());
		subkit.setName(subkitBinding.getSubkitName());
		subkit.setSubkittype(subkitType);
		LOGGER.info("Remove items from subkitId {}", subkitId);
		int itemsRemoved = demoRepository.removeAllItemsBySubkitId(subkitId);
		LOGGER.info("itemsRemoved {}", itemsRemoved);
		LOGGER.info("kit {}", kit);
		Requestmaster requestMaster = new Requestmaster();
		Date createAndUpdateDate = new Date();
		requestMaster.setCreateDateTime(createAndUpdateDate);
		requestMaster.setUpdateDateTime(createAndUpdateDate);
		requestMaster.setKit(kit);
		requestMaster.setKittype(kit.getKittype());
		requestMaster.setTransactionID(updateSubkitRequest.getTransactionId());
		requestMaster.setTransactionSeq(updateSubkitRequest
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
		//LOGGER.info("subkitBinding.getImage {}", subkitBinding.getImage());
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
		List<ItemBinding> itemBindings = new ArrayList<>();
		for (ItemInstance ii : itemInstances) {
			LOGGER.info("ItemInstance name=" + ii.getName());
			LOGGER.info("ItemInstance id=" + ii.getId());
			LOGGER.info("ItemInstance quantity=" + ii.getQuantity());
			//Persist to database
			Itemref itemRef = demoRepository.findById(Integer.valueOf(ii.getId()), Itemref.class);
			Item item = new Item();
			item.setItemref(itemRef);
			item.setSubkit(subkit);
			item.setUnitNum(ii.getQuantity());
			demoRepository.addObject(item);
			Itemtx itemTx = new Itemtx();
			itemTx.setItemref(itemRef);
			itemTx.setUnitNum(ii.getQuantity());
			itemtxs.add(itemTx);
			itemTx.setRequestimage(requestImage);
			//TODO: Persist the items/map to the response itemBindings
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setItemId(item.getId());
			itemBinding.setDescription(item.getItemref().getDescription());
			itemBinding.setName(item.getItemref().getName());
			itemBinding.setUnitNum(item.getUnitNum());
			itemBinding.setItemrefId(item.getItemref().getId());
			itemBindings.add(itemBinding);
		}
		requestImage.setItemtxs(itemtxs);
		//
		UpdateSubkitResponse updateSubkitResponse = new UpdateSubkitResponse();
		updateSubkitResponse.setRequestId(updateSubkitRequest.getRequestId());
		updateSubkitResponse.setTransactionId(updateSubkitRequest
				.getTransactionId());
		updateSubkitResponse.setTransactionSequenceNumber(updateSubkitRequest
				.getTransactionSequenceNumber());
		KitBinding kitBinding = updateSubkitRequest.getKitBinding();
		updateSubkitResponse.setKitBinding(kitBinding);
		kitBinding.getSubkitBindings().get(0).setItemBindings(itemBindings);
		LOGGER.info("UpdateSubkitResponse {}", updateSubkitResponse);
		return updateSubkitResponse;
	}
	
}
