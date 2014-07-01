package hello;

import hello.pojo.ItemInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invy.database.DemoRepository;
import com.invy.database.jpa.data.Itemref;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Kittype;
import com.invy.database.jpa.data.Location;
import com.invy.database.jpa.data.Owner;

@Controller
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);
	@Autowired
	DemoRepository demoRepository;
	
	@RequestMapping("/greeting")
	public @ResponseBody
	Greeting greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		Greeting greetingInstance = new Greeting(counter.incrementAndGet(),
				String.format(template, name));
		// greetingInstance.getId();
		HelloJNI helloJNI = new HelloJNI();
		// helloJNI.sayHello();
		// Integer i = helloJNI.doubleInt(3);
		// System.out.println("i=" + i);
		// List<String> l = helloJNI.vectorList("This is a path");
		// for (String s : l) {
		// System.out.println("s=" + s);
		// }

		List<ItemInstance> l2 = helloJNI.getElementsFilePath(
				"D:\\Workspace\\sandbox\\invy\\Test Pattern 1.jpg", 1,1);

		for (ItemInstance s2 : l2) {
			System.out.println("s2=" + s2);
			LOG.info("s2 name=" + s2.getName());
			LOG.info("s2 id=" + s2.getId());
			LOG.info("s2 quantity=" + s2.getQuantity());
		}
		LOG.trace("Hello World!");
		LOG.debug("How are you today?");
		LOG.info("I am fine.");
		LOG.warn("I love programming.");
		LOG.error("I am programming.");
		return greetingInstance;
	}
	//THIS IS A TEST
	//TODO: Remove
	@RequestMapping(value = "/addProducts", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody
	Greeting addMultipleArticlesTocart(
			@RequestBody final List<Product> products, final Model model) {
		Greeting greetingInstance = new Greeting(counter.incrementAndGet(),
				String.format(template, "YOMAMA"));
		for (Product product : products) {
			LOG.info("product = " + product);
		}
		return greetingInstance;
	}

	@Transactional
	@RequestMapping(value = "/uploadKits", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody
	List<ItemInstance> uploadKits(@RequestBody final List<KitPojo> kits) {
		LOG.info("TX:"+TransactionSynchronizationManager.getCurrentTransactionName()+TransactionSynchronizationManager.isActualTransactionActive());
		List<Kit> kitDatas = demoRepository.searchKitsByUserId("jdoe");
		for(Kit kitData:kitDatas){
			LOG.info("kitData = "+kitData);
		}
		
//		Itemref newItemRef = new Itemref();
//		newItemRef.setDescription("#2 Item");
//		newItemRef.setName("ref2");
//		newItemRef.setUnitPrice(Double.valueOf(35));
//		Location location = new Location();
//		location.setName("root");
//		location.setType("root");
//		Location location = demoRepository.findById(1, Location.class);
//		LOG.info("Location = "+location);
//		Owner owner = new Owner();
//		owner.setFirstname("Ethan");
//		owner.setLastname("Ma");
//		owner.setLocation(location);
//		owner.setUserId("ema");
//		
//		demoRepository.addObject(owner);
		
//		Kittype kitType = new Kittype();
//		Set<Subkittype> subkitTypes = new HashSet<>();
//		Subkittype subkitType = new Subkittype();
//		subkitType.setDescription("Tray2");
//		subkitType.setName("Tray2");
//		subkitType.setSubkitSequence(2);
//		subkitType.setKittype(kitType);
//		subkitTypes.add(subkitType);
//		kitType.setName("kit1");
//		kitType.setDescription("First kit type");
//		kitType.setSubkittypes(subkitTypes);
		/*LOG.info("Add object");
		Location location = new Location();
		location.setDescription("HQ");
		location.setName("Headquarter");
		location.setType("HQ");
		Location location2 = demoRepository.findById(1, Location.class);
		location.setLocation(location2);
		demoRepository.addObject(location);
		Owner owner = new Owner();
		owner.setFirstname("June");
		owner.setLastname("Ma");
		owner.setLocation(location);
		owner.setUserId("jma");
		demoRepository.addObject(owner);
		
		Owner owner2 = demoRepository.searchOwnerByUserId("jma");
		LOG.info("owner2 = "+owner2);
		List<Kittype> kitTypes = demoRepository.getAllKitTypes();
		for(Kittype kitType1:kitTypes){
			LOG.info("kitType1 = "+kitType1);
		}
		LOG.info("Add object done!");
		List<Itemref> itemRefs = demoRepository.getAllItemrefs();
		for(Itemref itemRef:itemRefs){
			LOG.info("itemRef = "+itemRef);
		}
		List<Itemref> itemRefs2 = demoRepository.searchItemrefsByName("te");
		for(Itemref itemRef:itemRefs2){
			LOG.info("itemRef2 = "+itemRef);
		}*/
		HelloJNI helloJNI = new HelloJNI();
		List<ItemInstance> l2 = new ArrayList<ItemInstance>();
		for (KitPojo kit : kits) {
			LOG.info("kit = " + kit);
			LOG.info("kit description = " + kit.getDescription());
			// LOG.info("kit image = " + new String(kit.getImage()));
			// byte[] decoded = Base64.decodeBase64(kit.getImage());
			l2 = helloJNI.getElementsByteArray(kit.getImage(), 1,1);
			for (ItemInstance s2 : l2) {
				LOG.info("s2 name=" + s2.getName());
				LOG.info("s2 id=" + s2.getId());
				LOG.info("s2 quantity=" + s2.getQuantity());
			}
			/*
			 * File f = new File("d:/test.jpg"); try { OutputStream os = new
			 * FileOutputStream(f); IOUtils.write(kit.getImage(), os); } catch
			 * (FileNotFoundException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */

		}
		return l2;
	}
}