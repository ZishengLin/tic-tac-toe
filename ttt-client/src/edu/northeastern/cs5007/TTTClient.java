package edu.northeastern.cs5007;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.Random;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;



public class TTTClient {


	private static String URL = "http://localhost:8080/test";

	public static void main(String[] args) {
		Console console = new Console();
		Gson gson = new Gson();
		Scanner scanner = new Scanner(System.in);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		


		Play p = new Play(console,  gson,  scanner,  httpClient, URL);
		// random start game
		p.startPlay();
		// play game by round
		p.roundPlay();


	}
}


