package com.wolfesoftware.gds;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;
import static spark.Spark.stop;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;

public class GoogleDriveSharer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveSharer.class);
	Endpoints endpoints = new Endpoints();
	Map<String,String> users = new HashMap<>();
	

	public static void main(String[] args) {
		HashMap<String, String> users = new HashMap<String,String>();
		users.put(args[0], args[1]);
		new GoogleDriveSharer(users);
	}

	public GoogleDriveSharer(Map<String,String> users) {
		this.users = users;
		port(4568);
		staticFileLocation("/public");
		createFilters();
		createEndpoints();
	}
	
	public void exit() {
		stop();
	}

	private void createEndpoints() {
		get("/", (request, response) -> {
			return endpoints.getIndexPage(request, response);
		});
		get("/login", (request, response) -> {
			return endpoints.getLoginPage(request, response);
		});
		get("/robots.txt", (request, response) -> {
			return endpoints.getRobots(request, response);
		});
		get("/upload", (request, response) -> {
			return endpoints.upload(request, response);
		});
		post("/uploadPicture", (request, response) -> {
		    return endpoints.saveFile(request, response);
		});
		get("/test", (request, response) -> {
			request.session(true).attribute("folder", com.wolfesoftware.gds.configuration.Configuration.get("test.folder.in.google.drive"));
			response.redirect("/", 302);
			return "You are now using this site in test mode, recreate session to remove that";
		});
		post("/login", (request, response) -> {
			String username = request.queryParams("uname");
			String password = request.queryParams("psw");
			LOGGER.info("Username attempting login:" + username);
			if (isValidUser(username, password)) {
					request.session(true).attribute("user", "true");
					response.redirect("/", 302);
					return null;
			} else {
				return endpoints.getLoginPage(request, response);
			}
		});
	}

	private boolean isValidUser(String username, String password) {
		if (null!=username && null!=password) {
			String pass = users.get(username);
			if (null !=pass && pass.equals(password)) {
				return true;
			}
		}
		return false;
	}

	private void createFilters() {
		before((request, response) -> {
			doBeforeFilter(request, response);
		});
	}

	private void doBeforeFilter(Request request, Response response) {
		LOGGER.info(request.ip() + " " + request.uri());
		if (null==request.session(true).attribute("folder")) {
			request.session(true).attribute("folder", com.wolfesoftware.gds.configuration.Configuration.get("folder.in.google.drive"));
		}
		if (null == request.session(true).attribute("user") && !request.uri().equals("/login")) {
			response.redirect("/login", 302);
		}
	}

}