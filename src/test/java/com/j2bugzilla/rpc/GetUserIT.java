/*
 * Copyright 2013 Center for Open Middleware
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.j2bugzilla.rpc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.base.User;

@RunWith(Parameterized.class)
public class GetUserIT {
	
	@Parameters
	public static List<Object[]> getUrls() {
		
		return Arrays.asList(new Object[][] { 
				{"https://landfill.bugzilla.org/bugzilla-3.6-branch/", 41356}, 
				{"https://landfill.bugzilla.org/bugzilla-4.0-branch/", 43420}, 
				{"https://landfill.bugzilla.org/bugzilla-4.2-branch/", 43420},                  
				{"https://landfill.bugzilla.org/bugzilla-4.4-branch/", 57861} });
	}
	
	private String url;
	
	private int id;
	
	public GetUserIT(String url, int id) {
		this.url = url;
		this.id = id;
	}
	
	/**
	 * Test for {@link GetUser "GetUser"} method
	 * scenario: Get user with a login name
	 */
	@Test
	public void testGetUserByLogin() throws ConnectionException, BugzillaException {
		BugzillaConnector conn = new BugzillaConnector();
		conn.connectTo(url);
		
		LogIn logIn = new LogIn("j2bugzilla@gmail.com", "javabugzilla");
		conn.executeMethod(logIn);
		
		GetUser getUser = new GetUser("j2bugzilla@gmail.com");
		conn.executeMethod(getUser);
		
		User user = getUser.getUser();
		assertThat("User should not be null", user, is(notNullValue()));
		assertThat("User id is not set correctly", user.getId(), is(id));
		assertThat("User login name is not set correctly", user.getLoginName(), is("j2bugzilla@gmail.com"));
		assertThat("User email is not set correctly", user.getEmail(), is("j2bugzilla@gmail.com"));
		assertThat("User real name is not set correctly", user.getRealName(), is("J2Bugzilla"));
		
	}
	
	/**
	 * Test for {@link GetUser "GetUser"} method
	 * scenario: Get user with a user id
	 */
	@Test
	public void testGetUserByID() throws ConnectionException, BugzillaException {
		BugzillaConnector conn = new BugzillaConnector();
		conn.connectTo(url);
		
		LogIn logIn = new LogIn("j2bugzilla@gmail.com", "javabugzilla");
		conn.executeMethod(logIn);
		
		GetUser getUser = new GetUser(id);
		conn.executeMethod(getUser);
		
		User user = getUser.getUser();
		assertThat("User should not be null", user, is(notNullValue()));
		assertThat("User id should is not set correctly", user.getId(), is(id));
		assertThat("User login name is not set correctly", user.getLoginName(), is("j2bugzilla@gmail.com"));
		assertThat("User email should  is not set correctly", user.getEmail(), is("j2bugzilla@gmail.com"));
		assertThat("User real name should is not set correctly", user.getRealName(), is("J2Bugzilla"));
		
	}
	
	

}
