package hello;

import hello.pojo.ItemInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

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
				"D:\\Workspace\\sandbox\\invy\\Test Pattern 1.jpg", 1);

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

	@RequestMapping(value = "/uploadKits", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody
	List<ItemInstance> uploadKits(@RequestBody final List<Kit> kits) {
		HelloJNI helloJNI = new HelloJNI();
		List<ItemInstance> l2 = new ArrayList<ItemInstance>();
		for (Kit kit : kits) {
			LOG.info("kit = " + kit);
			LOG.info("kit description = " + kit.getDescription());
			// LOG.info("kit image = " + new String(kit.getImage()));
			// byte[] decoded = Base64.decodeBase64(kit.getImage());
			l2 = helloJNI.getElementsByteArray(kit.getImage(), 1);
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