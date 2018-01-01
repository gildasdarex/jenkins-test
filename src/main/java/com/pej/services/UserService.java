package com.pej.services;

import com.pej.domains.Utilisateur;

public interface UserService {
	boolean authenticate(String username, String password);
	Utilisateur getAuthenticatedUser(String username, String password);
    Utilisateur findByUsername(String username);
    void save(Utilisateur user);
}
