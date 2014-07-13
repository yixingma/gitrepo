/**
 * 
 */
package com.invy.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.invy.database.DemoRepository;
import com.invy.database.jpa.data.Item;
import com.invy.database.jpa.data.Optitemtemplate;
import com.invy.database.jpa.data.Subkit;
import com.invy.database.jpa.data.Subkittype;
import com.invy.endpoint.CompareSubkitItemsToOptimalItemsResponse;
import com.invy.endpoint.ItemBinding;

/**
 * @author ema
 * 
 */
@Controller
public class TrayOptimizationController {
	@Autowired
	DemoRepository demoRepository;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrayOptimizationController.class);
	@Autowired
	MapperFacade mapper;

	@RequestMapping(value = "/get-optimal-itembindings/{subkitTypeId}/{templateId}", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public @ResponseBody
	List<ItemBinding> getOptimalItemBindings(
			@PathVariable("templateId") int templateId,
			@PathVariable("subkitTypeId") int subkitTypeId) {
		LOGGER.info("subkitTypeId {}", subkitTypeId);
		LOGGER.info("templateId {}", templateId);
		List<Optitemtemplate> optItems = demoRepository
				.searchOptitemsBySubkitAndTemplate(subkitTypeId, templateId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("optItems {}", optItems);
		}
		return mapper.mapAsList(optItems, ItemBinding.class);
	}

	@RequestMapping(value = "/get-itembindings/{subkitId}", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public @ResponseBody
	List<ItemBinding> getItemBindings(@PathVariable("subkitId") int subkitId) {
		LOGGER.info("subkitId {}", subkitId);
		List<Item> items = demoRepository.searchItemsBySubkitId(subkitId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("items {}", items);
		}
		return mapper.mapAsList(items, ItemBinding.class);
	}

	@RequestMapping(value = "/compare-subkit-items-to-optimalitems/{subkitId}/{templateId}", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public @ResponseBody
	CompareSubkitItemsToOptimalItemsResponse compareSubkitItemsToOptimalItems(
			@PathVariable("subkitId") int subkitId,
			@PathVariable("templateId") int templateId) {
		LOGGER.info("subkitId {}", subkitId);
		LOGGER.info("templateId {}", templateId);
		Subkit subkit = demoRepository.findById(Integer.valueOf(subkitId),
				Subkit.class);
		Set<Item> items = subkit.getItems();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("items {}", items);
		}
		Subkittype subkitType = subkit.getSubkittype();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("subkitType {}", subkitType);
		}
		List<Optitemtemplate> optItems = demoRepository
				.searchOptitemsBySubkitAndTemplate(subkitType.getId(),
						templateId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("optItems {}", optItems);
		}
		
		Set<Integer> originalItemRefIds = new HashSet<>();
		//List<ItemBinding> originalItemBindings = new ArrayList<>();
		Map<Integer,ItemBinding> originalItemBindingsMap = new HashMap<>();
		for (Item item : items) {
			originalItemRefIds.add(item.getItemref().getId());
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setDescription(item.getItemref().getDescription());
			itemBinding.setItemId(item.getId());
			itemBinding.setItemrefId(item.getItemref().getId());
			itemBinding.setName(item.getItemref().getName());
			itemBinding.setUnitNum(item.getUnitNum());
			originalItemBindingsMap.put(item.getItemref().getId(), itemBinding);
		}
		//List<ItemBinding> optItemBindings = new ArrayList<>();
		Set<Integer> optItemRefIds = new HashSet<>();
		Map<Integer,ItemBinding> optItemBindingsMap = new HashMap<>();
		Set<Integer> intersectDiffSet = new HashSet<>();
		for (Optitemtemplate optitemtemplate : optItems) {
			optItemRefIds.add(optitemtemplate.getItemref().getId());
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setDescription(optitemtemplate.getItemref().getDescription());
			itemBinding.setItemId(optitemtemplate.getId());
			itemBinding.setItemrefId(optitemtemplate.getItemref().getId());
			itemBinding.setName(optitemtemplate.getItemref().getName());
			itemBinding.setUnitNum(optitemtemplate.getOptUnitNum());
			optItemBindingsMap.put(optitemtemplate.getItemref().getId(), itemBinding);
			ItemBinding originialItemBinding = originalItemBindingsMap.get(optitemtemplate.getItemref().getId());
			if(originialItemBinding!=null && optitemtemplate.getOptUnitNum()!=originialItemBinding.getUnitNum()){
				intersectDiffSet.add(optitemtemplate.getItemref().getId());
			}
		}
		SetView<Integer> differenceItemRefIdSet = Sets.symmetricDifference(originalItemRefIds, optItemRefIds);
		LOGGER.info("differenceItemRefIdSet {}",differenceItemRefIdSet);
		LOGGER.info("intersectDiffSet {}",intersectDiffSet);
		differenceItemRefIdSet.copyInto(intersectDiffSet);
		LOGGER.info("intersectDiffSet {}",intersectDiffSet);
		CompareSubkitItemsToOptimalItemsResponse response = new CompareSubkitItemsToOptimalItemsResponse();
		response.setDiffItemRefIds(intersectDiffSet);
		response.setOptItems(optItemBindingsMap);
		response.setOriginalItems(originalItemBindingsMap);
		return response;
	}
}
