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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invy.database.DemoRepository;
import com.invy.database.jpa.data.Kittype;
import com.invy.domain.KitTypePojo;

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
		List<KitTypePojo> kitTypePojos = mapper.mapAsList(allKitTypes, KitTypePojo.class);
		LOGGER.info("allKitTypes ",allKitTypes);
		LOGGER.info("kitTypePojos ",kitTypePojos);
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("allKitTypes ",allKitTypes);
			LOGGER.debug("kitTypePojos ",kitTypePojos);
		}
		return kitTypePojos;
	}
}
