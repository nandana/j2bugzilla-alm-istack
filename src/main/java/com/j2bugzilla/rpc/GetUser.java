/*
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.j2bugzilla.base.BugzillaMethod;
import com.j2bugzilla.base.User;

public class GetUser implements BugzillaMethod {
	
	/**
	 * The method Bugzilla will execute via XML-RPC
	 */
	private static final String METHOD_NAME = "User.get";
	
	private Map<Object, Object> params = new HashMap<Object, Object>();
	
	private Map<Object, Object> hash = Collections.emptyMap();
	
	public GetUser(int id) {
		params.put("ids", new Integer[] { id });
	}
	
	public GetUser(String loginName){
		params.put("names", new String[] { loginName });
	}

	@Override
	public void setResultMap(Map<Object, Object> hash) {
		this.hash = hash;
	}

	@Override
	public Map<Object, Object> getParameterMap() {
		return Collections.unmodifiableMap(params);
	}

	@Override
	public String getMethodName() {
		return METHOD_NAME;
	}
	
	public User getUser(){
		Object users = hash.get("users");
		if(users == null) { return null; }
		
		Object[] arr = (Object[])users;
		if(arr.length == 0) { return null; }
		
		@SuppressWarnings("unchecked")//Cast to form specified by webservice
		Map<Object, Object> userMap = (Map<Object, Object>)arr[0];
		User user = new User();
		user.setId((Integer)userMap.get("id"));
		user.setLoginName((String)userMap.get("name"));
		user.setEmail((String)userMap.get("email"));
		user.setRealName((String)userMap.get("real_name"));
		
		return user;
	}

}
