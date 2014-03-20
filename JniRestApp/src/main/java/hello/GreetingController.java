package hello;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public @ResponseBody Greeting greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
    	Greeting greetingInstance = new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    	//greetingInstance.getId();
    	HelloJNI helloJNI = new HelloJNI();
    	helloJNI.sayHello();
    	Integer i = helloJNI.doubleInt(3);
    	System.out.println("i="+i);
    	List<String> l = helloJNI.vectorList("This is a path");
    	for(String s:l){
    		System.out.println("s="+s);
    	}
    	
    	List<String> l2 = helloJNI.getNumberOfElements("D:\\Workspace\\sandbox\\invy\\Test Pattern 1.jpg");
    	for(String s2:l2){
    		System.out.println("s2="+s2);
    	}
        return greetingInstance;
    }
  
}