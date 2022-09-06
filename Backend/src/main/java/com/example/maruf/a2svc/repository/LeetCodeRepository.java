package com.example.maruf.a2svc.repository;

import com.example.maruf.a2svc.models.RootResponse;
import com.example.maruf.a2svc.models.User;
import com.example.maruf.a2svc.repository.UserRepository;
import com.example.maruf.a2svc.services.SheetsServiceUtil;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class LeetCodeRepository {
    private final UserRepository userRepository;
    private final SheetsServiceUtil sheetsServiceUtil;
    private OkHttpClient client = new OkHttpClient();

    @Autowired
    public LeetCodeRepository(UserRepository userRepository, SheetsServiceUtil sheetsServiceUtil) {
        this.userRepository = userRepository;
        this.sheetsServiceUtil = sheetsServiceUtil;
    }

    RootResponse getTheDamnStat(String username) throws IOException {
        String url = "https://leetcode.com/graphql";
        String query = "{ matchedUser(username: \"" + username + "\") {\r\nusername\r\nsubmitStats: submitStatsGlobal {\r\nacSubmissionNum {\r\ndifficulty\r\ncount\r\nsubmissions\r\n}\r\n}\r\n}\r\n}";
        Request request = new Request.Builder().url(url + "?query=" + URLEncoder.encode(query)).get().build();
        try (Response response = client.newCall(request).execute()) {
            String swweeet = response.body().string();
            RootResponse data = new Gson().fromJson(swweeet, RootResponse.class);
            return data;
        }
    }


    @Scheduled(cron = "0 */10 * * * *")
    public void refreshUsers() {
        System.out.println("Refresh users called!");
        try {
            List<String> usernames = sheetsServiceUtil.getUsers();
            for (String username : usernames) {

                try {
                    RootResponse uu = getTheDamnStat(username);
                    Optional<User> oldUser = userRepository.findByName(username);
                    User newuser = new User(username,
                            uu.data.matchedUser.submitStats.acSubmissionNum.get(1).count,
                            uu.data.matchedUser.submitStats.acSubmissionNum.get(2).count,
                            uu.data.matchedUser.submitStats.acSubmissionNum.get(3).count,
                            uu.data.matchedUser.submitStats.acSubmissionNum.get(0).count,
                            uu.data.matchedUser.submitStats.acSubmissionNum.get(0).submissions,
                            new Date()
                    );
                    if ((oldUser.isPresent())) {
                        newuser.setId(oldUser.get().getId());
                        newuser.setTitle(oldUser.get().getTitle());
                        userRepository.save(newuser);
                    } else {
                        userRepository.save(newuser);
                    }
                    System.out.println("processed "  + username);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println("THE LUCK");
        }
    }
}
