package controller;

import java.io.BufferedReader;
import java.io.IOException;

import accountService.AccountService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import user.UserProfile;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Solovyev on 09/02/2017.
 */
@RestController
@RequestMapping(value = "/auth")
public class UserAuthController {

    private final AccountService accountService;

    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    public void signOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (accountService.isLogIn(req.getSession().getId())){
            accountService.deleteSession(req.getSession().getId());
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "succes");
        jsonObject.addProperty("key",  "200");
        resp.getWriter().write(jsonObject.toString());
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public void signIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder jb = new StringBuilder();
        String line;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        UserProfile userProfile = gson.fromJson(jb.toString(), UserProfile.class);

        if (accountService.isSignUp(userProfile.getEmail())) {
            jsonObject.addProperty("message", "succes");
            jsonObject.addProperty("key",  "200");
            if (accountService.isLogIn(req.getSession().getId())) {
                accountService.addSession(req.getSession().getId(), userProfile);
            }
            resp.getWriter().write(jsonObject.toString());
        } else {
            jsonObject.addProperty("message", "you don't sugnUp");
            jsonObject.addProperty("key",  "400");
            resp.getWriter().write(jsonObject.toString());
        }
    }


    @RequestMapping(value = "/getInfoUser", method = RequestMethod.POST)
    public void getInfoUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder jb = new StringBuilder();
        String line;
        BufferedReader reader = req.getReader();

        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        line = gson.fromJson(jb.toString(), String.class);
        if (accountService.isLogIn(line)){
            jsonObject.addProperty("User", gson.toJson(accountService.getUser(line)));
            jsonObject.addProperty("key", "200");
            resp.getWriter().write(gson.toJson(jsonObject.toString()));
        } else {
            jsonObject.addProperty("key", "400");
            resp.getWriter().write(gson.toJson(jsonObject.toString()));
        }
    }

    @RequestMapping(value = "/setInfoUser", method = RequestMethod.POST)
    public void setInfoUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder jb = new StringBuilder();
        String line;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }
        Gson gson = new Gson();
        UserProfile userProfile = gson.fromJson(jb.toString(), UserProfile.class);
        accountService.getUser(userProfile.getEmail()).setLogin(userProfile.getLogin());
        accountService.getUser(userProfile.getEmail()).setPassword(userProfile.getPassword());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "succes");
        jsonObject.addProperty("key",  "200");
        resp.getWriter().write(jsonObject.toString());
    }


    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public void signUp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder jb = new StringBuilder();
        String line;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        UserProfile userProfile = gson.fromJson(jb.toString(), UserProfile.class);

        if (accountService.isSignUp(userProfile.getEmail())) {
            jsonObject.addProperty("message", "you signUp");
            jsonObject.addProperty("key", "200");
            resp.getWriter().write(jsonObject.toString());
        } else {
            accountService.addSession(req.getSession().getId(), userProfile);
            jsonObject.addProperty("message", "success");
            jsonObject.addProperty("key", "200");
            resp.getWriter().write(jsonObject.toString());

            accountService.addUser(userProfile);
        }
    }

    @Autowired
    public UserAuthController(AccountService accountService) {
        this.accountService = accountService;
    }
}