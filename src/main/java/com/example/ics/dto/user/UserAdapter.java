package com.example.ics.dto.user;

public class UserAdapter extends CustomUserDetails {
    private SiteUser user;

    public UserAdapter(SiteUser user) {
        super(user);
        this.user = user;
    }
}
