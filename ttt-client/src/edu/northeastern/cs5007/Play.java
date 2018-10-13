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

public class Play{
	Console console;
	Gson gson;
	Scanner scanner;
	CloseableHttpClient httpClient;
	String URL;

	public Play(Console console, Gson gson, Scanner scanner, 
		CloseableHttpClient httpClient,String URL){
		this.console = console;
		this.gson = gson;
		this.scanner = scanner;
		this.httpClient = httpClient;
		this.URL = URL;
	}
	// random start game
	public void startPlay(){

		try {
			
			Random rand = new Random();
			int ranNum = rand.nextInt(2);
			// if random number is 1 human first, else computer first.
			if(ranNum == 1){
				System.out.println("Please choose a grid in the board");
				System.out.println(console.toString());			
				System.out.print("row(1-3): ");
				String line1 = scanner.nextLine();
				System.out.print("column(1-3): ");
				String line2 = scanner.nextLine();
				while(!console.valid(line1, line2)){

					System.out.println("Wrong grid, choose again!");
					System.out.print("row(1-3): ");
					line1 = scanner.nextLine();
					System.out.print("column(1-3): ");
					line2 = scanner.nextLine();
				}
				int row = Integer.valueOf(line1);
				int column = Integer.valueOf(line2);
				console.setArray(row - 1, column - 1);
			}
			
			
			String json = gson.toJson(console);
			HttpPost postRequest = new HttpPost(URL);

			

			StringEntity input = new StringEntity(json);
			input.setContentType("application/json");

			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			System.out.println("----------------------------------------");
			System.out.println("The post request response status = " + 
				response.getStatusLine());
			System.out.println("----------------------------------------");
			System.out.println("==============================" 
				+ "==============================");	
			System.out.println("Start game, you are \'X\', computer is \'O\', "
				+"and empty is \'*\'");
			System.out.println("============================" 
				+ "================================");	

			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("HTTP error code: " 
					+ response.getStatusLine().getStatusCode());
				return;
			}

			String respPayLoad = "";
			byte[] respBuffer = new byte[1024];
			HttpEntity respEntity = response.getEntity();
			if (respEntity != null) {
				InputStream respInputStream = respEntity.getContent();
				try {
					int respCount = 0;
					BufferedInputStream respBIS = 
					new BufferedInputStream(respInputStream);
					while ((respCount = respBIS.read(respBuffer)) != -1) {
						String respChunk = 
						new String(respBuffer, 0, respCount);
						// System.out.println(respChunk);
						respPayLoad += respChunk;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					try {respInputStream.close();} 
					catch (Exception ignore) {}
				}
			}
			
			console = gson.fromJson(respPayLoad, Console.class);
      		//System.out.println(console.toString());	

			// httpClient.getConnectionManager().shutdown();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void roundPlay(){
		while (true) {
			//check whether computer win or draw.
			if(console.win()){
				System.out.println("You lose!");
				System.out.println(console.toString());
				break;
			}

			if(console.draw()){
				System.out.println("No one win!");
				System.out.println(console.toString());
				break;
			}

			try {

				System.out.println("Please choose a grid in the board");
				System.out.println(console.toString());			
				System.out.print("row(1-3): ");
				String line1 = scanner.nextLine();
				System.out.print("column(1-3): ");
				String line2 = scanner.nextLine();
				while(!console.valid(line1, line2)){

					System.out.println("Wrong grid, choose again!");
					System.out.print("row(1-3): ");
					line1 = scanner.nextLine();
					System.out.print("column(1-3): ");
					line2 = scanner.nextLine();
				}
				int row = Integer.valueOf(line1);
				int column = Integer.valueOf(line2);
				
				console.setArray(row - 1, column - 1);

				// check whether human win or draw
				if(console.win()){
					System.out.println("You win!");
					System.out.println(console.toString());
					break;
				}


				if(console.draw()){
					System.out.println("No one win!");
					System.out.println(console.toString());
					break;
					
				}
				String json = gson.toJson(console);
				
				HttpPost postRequest = new HttpPost(URL);

				

				StringEntity input = new StringEntity(json);
				input.setContentType("application/json");

				postRequest.setEntity(input);

				HttpResponse response = httpClient.execute(postRequest);

				System.out.println("----------------------------------------");
				System.out.println("The post request response status = " 
					+ response.getStatusLine());
				System.out.println("----------------------------------------");

				if (response.getStatusLine().getStatusCode() != 200) {
					System.out.println("HTTP error code: " + 
						response.getStatusLine().getStatusCode());
					return;
				}

				String respPayLoad = "";
				byte[] respBuffer = new byte[1024];
				HttpEntity respEntity = response.getEntity();
				if (respEntity != null) {
					InputStream respInputStream = respEntity.getContent();
					try {
						int respCount = 0;
						BufferedInputStream respBIS = 
						new BufferedInputStream(respInputStream);
						while ((respCount = respBIS.read(respBuffer)) != -1) {
							String respChunk = 
							new String(respBuffer, 0, respCount);
						// System.out.println(respChunk);
							respPayLoad += respChunk;
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally{
						try {respInputStream.close();} 
						catch (Exception ignore) {}
					}
				}

				console = gson.fromJson(respPayLoad, Console.class);
			// httpClient.getConnectionManager().shutdown();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}



			// String completePayload = "";
			
		}
		System.exit(0);

	}
	

}














