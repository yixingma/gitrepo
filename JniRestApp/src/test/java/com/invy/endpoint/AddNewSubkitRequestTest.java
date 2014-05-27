package com.invy.endpoint;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddNewSubkitRequestTest {
	@Test
	public void testJacksonJsonRegisterNewKitRequestGen() throws JsonGenerationException,JsonMappingException,IOException{
		AddNewSubkitRequest request = new AddNewSubkitRequest();
		request.setCreateByUsername("ema");
		request.setRequestId(1);
		request.setTransactionId("UUID33");
		request.setTransactionSequenceNumber(1);
		KitBinding kitBinding = new KitBinding();
		kitBinding.setKitId(1);
		kitBinding.setKitName("test");
		kitBinding.setKitDescription("This is a test.");
		kitBinding.setOwnerId("ema");
		kitBinding.setSetupComplete(false);
		kitBinding.setKitTypeId(1);
		List<SubkitBinding> skb = new ArrayList<>();
		SubkitBinding subkitBinding = new SubkitBinding();
		subkitBinding.setSubkitDescription("This is another subkit.");
		subkitBinding.setSubkitName("2nd subkit");
		subkitBinding.setSubkitTypeId(3);
		skb.add(subkitBinding);
		kitBinding.setSubkitBindings(skb);
		request.setKitBinding(kitBinding);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File("d:\\AddNewSubkitRequest-02.json"), request);
		assertNotNull(mapper.writeValueAsString(request));
	}
}
