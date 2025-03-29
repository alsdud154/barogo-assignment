package com.barogo.assignment.application.account;

public interface TokenProvider {
	String createAccessToken(long id, String username, String name);
}
