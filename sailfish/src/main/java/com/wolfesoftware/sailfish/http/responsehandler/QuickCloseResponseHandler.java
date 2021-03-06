package com.wolfesoftware.sailfish.http.responsehandler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class QuickCloseResponseHandler implements ResponseHandler<StatusLine> {

	public StatusLine handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		return response.getStatusLine();
	};

}