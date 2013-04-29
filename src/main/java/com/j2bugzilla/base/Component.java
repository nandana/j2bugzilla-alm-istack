package com.j2bugzilla.base;

/**
 * A {@code Component} object represents a component of a product category on the Bugzilla installation. 
 * Each {@code Component} is associated with exactly one {@code Product} and contains a unique id, 
 * a name, and other parameters.
 */
public class Component {
	
	public static final int INVALID_ID = -1;
	
	private final int id;
	
	private final String name;
	
	
	/**
	 * Creates a new {@link Component} object with the specified unique ID and name.
	 * @param id An {@code integer} representing the unique component ID.
	 * @param name A {@code String} representing the unique component name.
	 */
	public Component(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Returns the ID of this {@link Component}. If the product version id was not retrieved 
	 * from the Web service, returns -1.
	 * @return An integer representing the ID of this component.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the name of this {@link Component}.
	 * @return A {@code String} representing the component name.
	 */
	public String getName() {
		return name;
	}
}
