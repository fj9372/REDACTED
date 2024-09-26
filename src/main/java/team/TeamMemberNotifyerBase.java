package team;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import History.TeamsSnapshot;

import java.util.List;
import NUTRiAPP.User;

public class TeamMemberNotifyerBase implements notiferTeam {
    private Map<String, List<User>> teamLoopkup;

    public TeamMemberNotifyerBase() {
        teamLoopkup = new HashMap<String, List<User>>();
    }

    @Override

    public void notiftyInivteToTeam(String message, String to, int id) throws Exception{
        URL newUrl = new URL("http://localhost:5000/teamusers");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String postData = "{\n" +
                "\"message\": \"" + message + "\",\n" +
                "\"receiver\": \"" + to + "\",\n" +
                "\"teamid\": " + id + "\n" +
                "}";
        outputStream.write(postData.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
    }

    @Override
    public void notifyTeamAccpeted(User user, User inviter) {
        teamLoopkup.put(user.getName(), teamLoopkup.get(inviter.getName()));
        teamLoopkup.get(user.getName()).add(user);
    }

    @Override
    public void notifyTeamHistory(User user) {
        try {
            for (String member: getTeamMembers(user.getTeam())){
                URL newUrl = new URL("http://localhost:5000/notification");
            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            OutputStream outputStream = conn.getOutputStream();
            String postData = "{\n"+
                    "\"message\": \"" + user.getName() + " just logged a workout." + "\",\n" +
                    "\"receiver\": \"" + member + "\"\n" +
                    "}";
            outputStream.write(postData.getBytes());
            outputStream.flush();
            outputStream.close();
            conn.getResponseCode();
            }}
        catch (Exception e){}
    }

    @Override
    public void nofifyTeamLogout(User user) {
        try {
        for (String member : getTeamMembers(user.getTeam())){
            URL newUrl = new URL("http://localhost:5000/notification");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String postData = "{\n"+
                "\"message\": \"" + user.getName() + " has logged out" + "\",\n" +
                "\"receiver\": \"" + member + "\"\n" +
                "}";
        outputStream.write(postData.getBytes());
        outputStream.flush();
        outputStream.close();
        conn.getResponseCode();
        }}
    catch (Exception e){}
    }

    @Override
    public void notifyTeamChallenge(User user) {
        try {
            for (String member : getTeamMembers(user.getTeam())){
                URL newUrl = new URL("http://localhost:5000/notification");
            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            OutputStream outputStream = conn.getOutputStream();
            String postData = "{\n"+
                    "\"message\": \"" + user.getName() + " has started a challenge" + "\",\n" +
                    "\"receiver\": \"" + member + "\"\n" +
                    "}";
            outputStream.write(postData.getBytes());
            outputStream.flush();
            outputStream.close();
            conn.getResponseCode();
            }}
        catch (Exception e){}
        }
    

    public static List<String> getTeamMembers(int teamID) throws Exception {
        String newUrl = "http://localhost:5000/teamusers/" + teamID;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<String> members = new ArrayList<>();
        while ((data = in.readLine()) != null) {
            if (!data.trim().contains("[") && !data.trim().contains("]")) {
                String newData = data.replace(",", "").replace("\"", "").trim();
                members.add(newData);
            }
        }
        in.close();
        return members;
    }

    public static List<String> getChallengeRanking(int teamID) throws Exception {
        String newUrl = "http://localhost:5000/challenge/" + teamID;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<String> members = new ArrayList<>();
        while ((data = in.readLine()) != null) {
            if (!data.trim().contains("[") && !data.trim().contains("]")) {
                String newData = data.replace(",", "").replace("\"", "").trim();
                members.add(newData);
            }
        }
        in.close();
        return members;
    }

    public static void leaveTeam(String name) throws Exception {
        String newUrl = "http://localhost:5000/team/" + name;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.getResponseCode();
    }

    public static void createTeam(String username, String name) throws Exception {
        URL newUrl = new URL("http://localhost:5000/team");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String postData = "{\n"+
                "\"name\": \"" + username + "\",\n" +
                "\"teamName\": \"" + name + "\"\n" +
                "}";
        outputStream.write(postData.getBytes());
        outputStream.flush();
        outputStream.close();
        conn.getResponseCode();
    }

    @Override
    public TeamsSnapshot createSnapshot(int teamId) {
        try {
            return new TeamsSnapshot(getTeamMembers(teamId));
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public void restoreFromSnapshot(TeamsSnapshot snapshot, int teamId) {
        try {
            syncTeams(snapshot.getLookup(), teamId);
        } catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public void syncTeams(List<String> teamMemebrs, int teamId) throws Exception {
        URL newUrl = new URL("http://localhost:5000/sync/teams/" + teamId);
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = teamMemebrs.toString();
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        conn.getResponseCode();
    }
}
