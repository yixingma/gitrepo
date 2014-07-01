/**
 * 
 */
package com.invy.controller;

import java.util.List;

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
import com.invy.endpoint.CompareKitWithExistingRequest;
import com.invy.endpoint.CompareKitWithExistingResponse;
import com.invy.endpoint.KitBinding;

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
	
	@Transactional
	@RequestMapping("/compare-kit-with-existing")
	public @ResponseBody
	CompareKitWithExistingResponse compareKitWithExisting(
			@RequestBody final CompareKitWithExistingRequest compareKitWithExistingRequest) {
		//TODO: add implementation
		return null;
	}
}
