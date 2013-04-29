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
public class UserSearchIT {
	
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
	
	public UserSearchIT(String url, int id) {
		this.url = url;
		this.id = id;
	}
	
	@Test
	public void testUserSearch() throws ConnectionException, BugzillaException {
		BugzillaConnector conn = new BugzillaConnector();
		conn.connectTo(url);
		
		LogIn logIn = new LogIn("j2bugzilla@gmail.com", "javabugzilla");
		conn.executeMethod(logIn);
		
		UserSearch getUser = new UserSearch("j2bugzilla@gmail.com");
		conn.executeMethod(getUser);
		
		List<User> users = getUser.getUsers();
		assertThat("User list should not be null", users, is(notNullValue()));
		assertThat("User list does not contain exactly one element", users.size(), is(1));
		
		User user = users.get(0);
		assertThat("User should not be null", users, is(notNullValue()));
		assertThat("User login name is not set correctly", user.getLoginName(), is("j2bugzilla@gmail.com"));
		assertThat("User email is not set correctly", user.getEmail(), is("j2bugzilla@gmail.com"));
		assertThat("User real name is not set correctly", user.getRealName(), is("J2Bugzilla"));
		
	}

}
