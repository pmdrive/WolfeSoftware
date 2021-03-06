package com.wolfesoftware.sailfish.http.requests;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.http.requests.GetRequest;
import com.wolfesoftware.sailfish.http.responsehandler.QuickCloseResponseHandler;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory.ResponseHandlers;

public class GetRequestTest {

	@Mock
	HttpClient httpClient;
	@Mock
	StatusLine statusLine;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldMakeAGetRequest() throws Exception {
		when(httpClient.execute(isA(HttpGet.class), isA(QuickCloseResponseHandler.class))).thenReturn(statusLine);
		new GetRequest("http://www.some-url.com").makeRequest(httpClient, new ResponseHandlerFactory(ResponseHandlers.QUICKCLOSE));
		verify(httpClient, times(1)).execute(isA(HttpGet.class), isA(QuickCloseResponseHandler.class));
	}

}
