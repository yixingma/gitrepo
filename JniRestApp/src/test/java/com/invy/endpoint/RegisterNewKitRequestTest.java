/**
 * 
 */
package com.invy.endpoint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ema
 *
 */
public class RegisterNewKitRequestTest {
	@Test
	public void testJacksonJsonRegisterNewKitRequestGen(){
		RegisterNewKitRequest request = new RegisterNewKitRequest();
		request.setCreateByUsername("ema");
		request.setRequestId(1);
		request.setTransactionId("UUID");
		request.setTransactionSequenceNumber(1);
		KitBinding kitBinding = new KitBinding();
		kitBinding.setKitName("test");
		kitBinding.setKitDescription("This is a test.");
		kitBinding.setOwnerId("ema");
		kitBinding.setSetupComplete(false);
		kitBinding.setKitTypeId(1);
		List<SubkitBinding> skb = new ArrayList<>();
		SubkitBinding subkitBinding = new SubkitBinding();
		subkitBinding.setSubkitDescription("This is a subkit.");
		subkitBinding.setSubkitName(":");
		subkitBinding.setSubkitTypeId(1);
		skb.add(subkitBinding);
		kitBinding.setSubkitBindings(skb);
		request.setKitBinding(kitBinding);
		ObjectMapper mapper = new ObjectMapper();
		try {
			 
			// convert user object to json string, and save to a file
			mapper.writeValue(new File("d:\\mytest.json"), request);
	 
			// display to console
			System.out.println(mapper.writeValueAsString(request));
	 
		} catch (JsonGenerationException e) {
	 
			e.printStackTrace();
	 
		} catch (JsonMappingException e) {
	 
			e.printStackTrace();
	 
		} catch (IOException e) {
	 
			e.printStackTrace();
	 
		}
	}
}
