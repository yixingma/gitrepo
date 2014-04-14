package hello;

import hello.pojo.ItemInstance;

import java.util.List;
import java.util.Map;

public class HelloJNI {
	static {
		//System.loadLibrary("hello"); // Load native library at runtime
										// hello.dll (Windows) or libhello.so
		System.loadLibrary("PartsDetector");								// (Unixes)
	}

	// Declare a native method sayHello() that receives nothing and returns void
//	protected native void sayHello();
//
//	protected native int doubleInt(int inInt);
//	
//	protected native List<String> vectorList(String inPath);
//
//	protected native List<String> getNumberOfElements(String filePath);
	
	protected native List<ItemInstance> getElementsByteArray(byte[] img, int kitNum);
    
    protected native List<ItemInstance> getElementsFilePath(String imagePath, int kitNum);
}
