import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Github10Trend {

	public static StringBuffer Baglanti(String url) throws IOException {

		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response;

	}
	
	private static final String file = ("dosya.txt");
	public static void Yazdırma(String dosya) throws IOException {
		
		File f = new File(file);
		if(f.exists() == false || f.isDirectory()) 
			return;
		
		try (FileWriter fileWriter = new FileWriter(file, true);
				BufferedWriter bWriter = new BufferedWriter(fileWriter);
				PrintWriter out = new PrintWriter(bWriter)) {
				out.println(dosya);
			} 
			catch (IOException e) {
				System.out.println("AddLog Error!");
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		try {
			String reponame = "commons-lang";
			String url = "https://api.github.com/repos/apache/commons-lang/contributors?sort=commit";
			StringBuffer response = Baglanti(url);
			JSONArray myrepo = new JSONArray(response.toString());
			List<Object> repo = new ArrayList<Object>();
			{
				for (int i = 0; i < 10; i++) {
					Values value = new Values();
					Bilgiler bilgi = new Bilgiler();
					value.setUsers(myrepo.getJSONObject(i).get("login").toString());
					value.setUrl(myrepo.getJSONObject(i).get("url").toString());
					value.setContributions(myrepo.getJSONObject(i).get("contributions").toString());
					StringBuffer response2 = Baglanti(value.getUrl());
					JSONObject urlrepo = new JSONObject(response2.toString());
					bilgi.setCompany(urlrepo.get("company").toString());
					bilgi.setLocation(urlrepo.get("location").toString());
					Yazdırma(".  repo: " + reponame +", user: " + value.getUsers() + ", location: " + bilgi.getLocation() + ", company: " + bilgi.getCompany() + ", contributions: " +value.getContributions());
					System.out.println("repo: " + reponame +", user: " + value.getUsers() + ", location: " + bilgi.getLocation() + ", company: " + bilgi.getCompany() + ", contributions: " +value.getContributions());
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
