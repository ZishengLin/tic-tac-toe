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
// represent a class to sent as json
public class Console {
		private int[] oneDArray;

		public Console() {
			oneDArray = new int[9];
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < 9; i++){
				if(i % 3 == 0){
					sb.append('\n');
				}
				if(oneDArray[i] == 0){
					sb.append("*  ");
				}else if(oneDArray[i] == 1){
					sb.append("X  ");
				}else{
					sb.append("O  ");
				}
			}
			return sb.toString();
		}

		public void setArray(int row, int col){
			oneDArray[row * 3 + col] = 1;
		}
		// check whether anyone is win.
		public boolean win(){
			if(oneDArray[0] == 1 && oneDArray[1] == 1 && oneDArray[2] == 1
				|| oneDArray[0] == 2 && oneDArray[1] == 2 
				&& oneDArray[2] == 2){
				return true;
			}
			if(oneDArray[3] == 1 && oneDArray[4] == 1 && oneDArray[5] == 1 
				|| oneDArray[3] == 2 && oneDArray[4] == 2 
				&& oneDArray[5] == 2 ){
				return true;
			}	
			if(oneDArray[6] == 1 && oneDArray[7] == 1 && oneDArray[8] == 1 
				|| oneDArray[6] == 2 && oneDArray[7] == 2 
				&& oneDArray[8] == 2 ){
				return true;
			}
			if(oneDArray[0] == 1 && oneDArray[3] == 1 && oneDArray[6] == 1 
				|| oneDArray[0] == 2 && oneDArray[3] == 2 
				&& oneDArray[6] == 2){
				return true;
			}
			if(oneDArray[1] == 1 && oneDArray[4] == 1 && oneDArray[7] == 1 
				|| oneDArray[1] == 2 && oneDArray[4] == 2 
				&& oneDArray[7] == 2){
				return true;
			}
			if(oneDArray[2] == 1 && oneDArray[5] == 1 && oneDArray[8] == 1 
				|| oneDArray[2] == 2 && oneDArray[5] == 2 
				&& oneDArray[8] == 2){
				return true;
			}
			if(oneDArray[0] == 1 && oneDArray[4] == 1 && oneDArray[8] == 1 
				|| oneDArray[0] == 2 && oneDArray[4] == 2 
				&& oneDArray[8] == 2){
				return true;
			}
			if(oneDArray[4] == 1 && oneDArray[2] == 1 && oneDArray[6] == 1 
				|| oneDArray[4] == 2 && oneDArray[2] == 2 
				&& oneDArray[6] == 2){
				return true;
			}
			return false;
		}
		// check no one win
		public boolean draw(){
			for(int i = 0; i < 9; i++){
				if(oneDArray[i] == 0){
					return false;
				}

			}
			return !win();
		}
		// check the input string is valid or not.
		public boolean valid(String line1, String line2){
			if(!isNumeric(line1) || !isNumeric(line2)){
				return false;
			}
			int row = Integer.valueOf(line1) - 1;
			int col = Integer.valueOf(line2) - 1;
			return row >= 0 && row < 3 && col < 3 && col >= 0 
			&& oneDArray[row * 3 + col] == 0;
		}
		// check whether a string is number
		private boolean isNumeric(String str){
			for(int i = 0; i < str.length(); i++){
				if(str.charAt(i) > '9' || str.charAt(i) < '1'){
					return false;
				}
			}
			return true;
		}
	}
	
	
