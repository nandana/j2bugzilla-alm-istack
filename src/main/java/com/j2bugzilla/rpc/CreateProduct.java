/*
 * Copyright 2011 Thomas Golden
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaMethod;
import com.j2bugzilla.base.Product;

/**
 * The <code>CreateProduct</code> class allows clients to create new products in a Bugzilla installation.
 */
public class CreateProduct implements BugzillaMethod {
	
	/**
	 * The method Bugzilla will execute via XML-RPC
	 */
	private static final String METHOD_NAME = "Product.create";
	
	private Map<Object, Object> params = new HashMap<Object, Object>();
	private Map<Object, Object> hash = new HashMap<Object, Object>();
	
	/**
	 * Creates a new {@link ReportBug} object to add a newly created {@link Bug}
	 * to the appropriate Bugzilla installation
	 * 
	 * @param bug A new {@link Bug} that should be tracked
	 */
	public CreateProduct(Product product) {
		// Copy only the not null values.
		for(Map.Entry<Object, Object> entry : product.getParameterMap().entrySet()) {
			if (entry.getValue() != null) {
				params.put(entry.getKey(), entry.getValue());
			}
		}
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
	
	/**
	 * Returns the ID of the newly-reported {@link Product}, or -1 if no such
	 * ID can be determined. Reasons for this might include calling this method
	 * before passing the {@link CreateProduct} object to the 
	 * {@link com.j2bugzilla.base.BugzillaConnector#executeMethod(BugzillaMethod) executeMethod()} method, or if there 
	 * was an exception while adding the {@link Product}
	 * 
	 * @return the ID of the new {@link Product}
	 */
	public int getID() {
		if(hash.containsKey("id")) {
			Integer i = (Integer)hash.get("id");
			return i.intValue();
		} else {
			return -1;
		}
	}
	
	

}
