package com.staed.beans;

import java.util.ArrayList;
import java.util.List;
import com.staed.stores.ColumnNames;
import com.staed.stores.FieldValueWrapper;

/**
 * A BEAN representing an Employee identified by their email
 * Contains their name, password, type (title grouping), and a reference to
 * the emails of their supervisor, department head, and benefit coordinators
 */
public class Employee implements Bean {
    private String email;
    private String password;
    private String name;
    private int typeId;
    
    private String superEmail;
    private String headEmail;
    private String benCoEmail;
    

    public Employee(String email, String password, String name, int typeId, String superEmail, String headEmail, String benCoEmail) {
    	this.email = email;
    	this.password = password;
    	this.name = name;
    	this.typeId = typeId;
    	
    	this.superEmail = superEmail;
    	this.headEmail = headEmail;
    	this.benCoEmail = benCoEmail;
    }
    
    @Override
	public List<FieldValueWrapper> toFieldValueWrappers() {
    	List<FieldValueWrapper> list = new ArrayList<>();
    	list.add(new FieldValueWrapper(ColumnNames.EMPLOYEEIDENTIFIER, email));
    	list.add(new FieldValueWrapper(ColumnNames.EMPLOYEEPASSWORD, password));
    	list.add(new FieldValueWrapper(ColumnNames.NAME, name));
    	list.add(new FieldValueWrapper(ColumnNames.EMPLOYEETYPEID, typeId));
    	list.add(new FieldValueWrapper(ColumnNames.SUPERVISOR, superEmail));
    	list.add(new FieldValueWrapper(ColumnNames.DEPTHEAD, headEmail));
    	list.add(new FieldValueWrapper(ColumnNames.BENCO, benCoEmail));
    	return list;
    }

    @Override
    public String toString() {
        return "Employee [ Email: " + email + ", Name: " + name + ", TypeId: "
        		+ typeId + ", Super Email: " + superEmail + ", Head Email: "
        		+ headEmail + ", BenCo Email: " + benCoEmail + "]";
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

	public String getSuperEmail() {
		return superEmail;
	}

	public void setSuperEmail(String superEmail) {
		this.superEmail = superEmail;
	}

	public String getHeadEmail() {
		return headEmail;
	}

	public void setHeadEmail(String headEmail) {
		this.headEmail = headEmail;
	}

	public String getBenCoEmail() {
		return benCoEmail;
	}

	public void setBenCoEmail(String benCoEmail) {
		this.benCoEmail = benCoEmail;
	}
}