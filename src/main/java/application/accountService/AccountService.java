package application.accountService;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import application.user.UserProfile;

import java.util.*;


@Service
@Component
public class AccountService {
    private Map<String, UserProfile> profiles = new HashMap<>();


    public void addUser(UserProfile userProfile) {
        profiles.put(userProfile.getEmail(), userProfile);
    }

    public int getSize() {
        return profiles.size();
    }

    public UserProfile getUser(String id) {
        return profiles.get(id);
    }

    public boolean isSignUp(String email, String password) {
        return profiles.get(email).getPassword().equals(password);
    }

    public List<UserProfile> sort() {
        final List<UserProfile> userProfiles = (ArrayList<UserProfile>) profiles.values();
        userProfiles.sort((o1, o2) -> {
            if (o2.getRating() == o1.getRating()) {
                return 0;
            } else if (o2.getRating() > o1.getRating()) {
                return 1;
            } else {
                return -1;
            }
        });
        return userProfiles;
    }
}
