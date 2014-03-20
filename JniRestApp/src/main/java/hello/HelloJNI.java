package hello;

import java.util.List;

public class HelloJNI {
	static {
		//System.loadLibrary("hello"); // Load native library at runtime
										// hello.dll (Windows) or libhello.so
		System.loadLibrary("PartsDetector");								// (Unixes)
	}

	// Declare a native method sayHello() that receives nothing and returns void
	protected native void sayHello();

	protected native int doubleInt(int inInt);
	
	protected native List<String> vectorList(String inPath);

	protected native List<String> getNumberOfElements(String filePath);
}
