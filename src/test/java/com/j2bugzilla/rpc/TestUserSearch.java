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
import static org.mockito.Mockito.doAnswer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.User;

@RunWith(MockitoJUnitRunner.class)
public class TestUserSearch {
	
	@Mock
	private BugzillaConnector conn;
	
	@Test
	public void test() throws BugzillaException {
		UserSearch userSearch = new UserSearch("*");
		
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserSearch rpcMethod = (UserSearch)invocation.getArguments()[0];
				
				Map<Object, Object> hash = new HashMap<Object, Object>();
				Object[] userArray = new Object[3];
				
				Map<Object, Object> user1 = new HashMap<Object, Object>();
				user1.put("id", 100);
				user1.put("real_name", "J2Bugzilla Bot");
				user1.put("email", "j2bugzilla@gmail.com");
				user1.put("name", "j2bugzilla@gmail.com");
				
				Map<Object, Object> user2 = new HashMap<Object, Object>();
				user2.put("id", 200);
				user2.put("real_name", "Thomas Golden");
				user2.put("email", "thomas@example.org");
				user2.put("name", "thomas@example.org");
				
				Map<Object, Object> user3 = new HashMap<Object, Object>();
				user3.put("id", 300);
				user3.put("real_name", "Nandana Mihindukulasooriya");
				user3.put("email", "nandana@example.org");
				user3.put("name", "nandana@example.org");
				
				userArray[0]  = user1;
				userArray[1]  = user2;
				userArray[2]  = user3;
					
				hash.put("users", userArray);
				rpcMethod.setResultMap(hash);
				
				return null;
			}
			
		}).when(conn).executeMethod(userSearch);
		
		conn.executeMethod(userSearch);
		
		List<User> users = userSearch.getUsers();
		
		assertThat("User list should not be null", users, is(notNullValue()));
		assertThat("User list does not have 3 items", users.size(), is(3));
		
		boolean foundOne = false;
		boolean foundTwo = false;
		boolean foundThree = false;
		for (User user : users) {
			if(user.getId() == 100) {
				foundOne = true;
				assertThat("User login name is not set correctly", user.getLoginName(), is("j2bugzilla@gmail.com"));
				assertThat("User email is not set correctly", user.getEmail(), is("j2bugzilla@gmail.com"));
				assertThat("User real name is not set correctly", user.getRealName(), is("J2Bugzilla Bot"));
			} else if (user.getId() == 200) {
				foundTwo = true;
				assertThat("User login name is not set correctly", user.getLoginName(), is("thomas@example.org"));
				assertThat("User email is not set correctly", user.getEmail(), is("thomas@example.org"));
				assertThat("User real name is not set correctly", user.getRealName(), is("Thomas Golden"));
			} else if (user.getId() == 300) {
				foundThree = true;
				assertThat("User login name is not set correctly", user.getLoginName(), is("nandana@example.org"));
				assertThat("User email is not set correctly", user.getEmail(), is("nandana@example.org"));
				assertThat("User real name is not set correctly", user.getRealName(), is("Nandana Mihindukulasooriya"));
			}
		}
		
		assertThat(foundOne, is(true));		
		assertThat(foundTwo, is(true));		
		assertThat(foundThree, is(true));		
	}
	
	@Test
	public void testNoUsers() throws BugzillaException {
		UserSearch userSearch = new UserSearch("*");
		
		assertThat("User list returned before execution should be not null", userSearch.getUsers(), is(notNullValue()));
		assertThat("User list returned before execution should be empty", userSearch.getUsers().size(), is(0));
		
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserSearch rpcMethod = (UserSearch)invocation.getArguments()[0];
				
				Map<Object, Object> hash = new HashMap<Object, Object>();
				hash.put("users", new Object[0]);
				rpcMethod.setResultMap(hash);
				return null;
			}
		}).when(conn).executeMethod(userSearch);
		
		conn.executeMethod(userSearch);
		
		assertThat("User list returned before execution should be not null", userSearch.getUsers(), is(notNullValue()));
		assertThat("User list returned before execution should be empty", userSearch.getUsers().size(), is(0));
	}

}
