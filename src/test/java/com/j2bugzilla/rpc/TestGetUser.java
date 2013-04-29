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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;

import java.util.HashMap;
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
public class TestGetUser {
	
	@Mock
	private BugzillaConnector conn;
	
	@Test
	public void test() throws BugzillaException {
		GetUser getUser = new GetUser(100);
		
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				GetUser rpcMethod = (GetUser)invocation.getArguments()[0];
				
				Map<Object, Object> hash = new HashMap<Object, Object>();
				Object[] userArray = new Object[1];
				
				Map<Object, Object> user = new HashMap<Object, Object>();
				user.put("id", 100);
				user.put("real_name", "J2Bugzilla Bot");
				user.put("email", "j2bugzilla@gmail.com");
				user.put("name", "j2bugzilla@gmail.com");
				
				userArray[0]  = user;
					
				hash.put("users", userArray);
				rpcMethod.setResultMap(hash);
				
				return null;
			}
			
		}).when(conn).executeMethod(getUser);
		
		conn.executeMethod(getUser);
		
		User user = getUser.getUser();
		
		assertThat("User should not be null", user, is(notNullValue()));
		assertThat("User id is not set correctly", user.getId(), is(100));
		assertThat("User login name is not set correctly", user.getLoginName(), is("j2bugzilla@gmail.com"));
		assertThat("User email is not set correctly", user.getEmail(), is("j2bugzilla@gmail.com"));
		assertThat("User real name is not set correctly", user.getRealName(), is("J2Bugzilla Bot"));
		
	}
	
	@Test
	public void testNoUsers() throws BugzillaException {
		GetUser getUser = new GetUser(-1);
		
		assertNull("Product returned before execution should be null", getUser.getUser());
		
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				GetUser rpcMethod = (GetUser)invocation.getArguments()[0];
				
				Map<Object, Object> hash = new HashMap<Object, Object>();
				hash.put("users", new Object[0]);
				rpcMethod.setResultMap(hash);
				return null;
			}
		}).when(conn).executeMethod(getUser);
		
		conn.executeMethod(getUser);
		
		assertNull("User returned should be null", getUser.getUser());
	}

}
