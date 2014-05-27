/**
 * 
 */
package com.invy.controller;

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

import com.invy.database.DemoRepository;
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
import com.invy.endpoint.RegisterNewKitRequest;
import com.invy.endpoint.RegisterNewKitResponse;
import com.invy.endpoint.SubkitBinding;

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
		Kittype kitType = demoRepository.findById(registerNewKitRequest
				.getKitBinding().getKitTypeId(), Kittype.class);
		// TODO: get from kitType in memory
		Subkittype subkitType = demoRepository.findById(registerNewKitRequest
				.getKitBinding().getSubkitBindings().get(0).getSubkitTypeId(),
				Subkittype.class);
		Owner owner = demoRepository.searchOwnerByUserId(registerNewKitRequest
				.getKitBinding().getOwnerId());
		LOGGER.info("requestMaster {}", requestMaster);
		LOGGER.info("kitType {}", kitType);
		LOGGER.info("subkitType {}", subkitType);
		LOGGER.info("owner {}", owner);
		requestMaster.setKittype(kitType);
		requestMaster.getRequestimages().iterator().next()
				.setSubkittype(subkitType);
		requestMaster.getKit().setOwner(owner);
		requestMaster.getKit().setKittype(kitType);
		requestMaster.getKit().getSubkits().iterator().next()
				.setSubkittype(subkitType);
		demoRepository.addObject(requestMaster);
		//TODO: Call image processing library
		//TODO: Map result to registerNewKitResponse
		RegisterNewKitResponse registerNewKitResponse = new RegisterNewKitResponse();
		registerNewKitResponse.setRequestId(registerNewKitRequest
				.getRequestId());
		registerNewKitResponse.setTransactionId(registerNewKitRequest
				.getTransactionId());
		registerNewKitResponse
				.setTransactionSequenceNumber(registerNewKitRequest
						.getTransactionSequenceNumber());
		LOGGER.info("RegisterNewKitResponse {}", registerNewKitResponse);
		return registerNewKitResponse;
	}

	@RequestMapping(value = "/get-leftover-subkittypes/{kitId}", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public @ResponseBody
	List<SubkitTypePojo> getLeftoverSubkittypes(@PathVariable("kitId") int kitId) {
		Kit kit = demoRepository.findById(kitId, Kit.class);
		if(kit==null){
			throw new RuntimeException("Kit {"+kitId+"} does not exist!");
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
		LOGGER.info("kitId {}",kitId);
		Kit kit = demoRepository.findById(kitId, Kit.class);
		SubkitBinding subkitBinding = addNewSubkitRequest.getKitBinding().getSubkitBindings().get(0);
		int subkitTypeId = subkitBinding.getSubkitTypeId();
		LOGGER.info("subkitTypeId {}",subkitTypeId);
		Subkittype subkitType = demoRepository.findById(subkitTypeId, Subkittype.class);
		Subkit subkit = new Subkit();
		subkit.setDescription(subkitBinding.getSubkitDescription());
		subkit.setName(subkitBinding.getSubkitName());
		subkit.setKit(kit);
		subkit.setSubkittype(subkitType);
		kit.getSubkits().add(subkit);
		LOGGER.info("kit {}",kit);
		Requestmaster requestMaster = new Requestmaster();
		Date createAndUpdateDate = new Date();
		requestMaster.setCreateDateTime(createAndUpdateDate);
		requestMaster.setUpdateDateTime(createAndUpdateDate);
		requestMaster.setKit(kit);
		requestMaster.setKittype(kit.getKittype());
		requestMaster.setTransactionID(addNewSubkitRequest.getTransactionId());
		requestMaster.setTransactionSeq(addNewSubkitRequest.getTransactionSequenceNumber());
		Requestimage requestImage = new Requestimage();
		requestImage.setCreateDateTime(createAndUpdateDate);
		requestImage.setUpdateDateTime(createAndUpdateDate);
		requestImage.setSubkittype(subkitType);
		requestImage.setRequestmaster(requestMaster);
		requestImage.setRequestImage(subkitBinding.getImage());
		LOGGER.info("subkitBinding.getImage {}",subkitBinding.getImage());
		Set<Requestimage> requestImages = new HashSet<>();
		requestImages.add(requestImage);
		requestMaster.setRequestimages(requestImages);
		demoRepository.addObject(requestMaster);
		LOGGER.info("PERSIST DONE");
		AddNewSubkitResponse addNewSubkitResponse = new AddNewSubkitResponse();
		addNewSubkitResponse.setRequestId(addNewSubkitRequest.getRequestId());
		addNewSubkitResponse.setTransactionId(addNewSubkitRequest
				.getTransactionId());
		addNewSubkitResponse.setTransactionSequenceNumber(addNewSubkitRequest
				.getTransactionSequenceNumber());
		addNewSubkitResponse.setKitBinding(addNewSubkitRequest.getKitBinding());
		LOGGER.info("AddNewSubkitResponse {}", addNewSubkitResponse);
		return addNewSubkitResponse;
	}
}
