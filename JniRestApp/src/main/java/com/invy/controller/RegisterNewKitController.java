/**
 * 
 */
package com.invy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invy.database.DemoRepository;
import com.invy.database.jpa.data.Kittype;

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

	@RequestMapping("/get-all-kittypes")
	public @ResponseBody
	List<Kittype> getAllKittypes() {
		List<Kittype> allKitTypes = demoRepository.getAllKitTypes();
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("allKitTypes ",allKitTypes);
		}
		return allKitTypes;
	}
}
