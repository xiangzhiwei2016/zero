package single;


import org.apache.http.client.protocol.HttpClientContext;

public class HttpContextSingle {
	private static HttpContextSingle instance = null;
	
	public HttpClientContext httpClientContext = null;
	
	// 单例
	public static HttpContextSingle getInstance() {
		if (instance == null) {
			instance = new HttpContextSingle();
		}
		return instance;
	}
}
