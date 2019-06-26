package org.jeavio.meetin.backend.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {

    @Id
    public String id;

    public String firstName;
    public String lastName;
    public Date dob;
    
    public Customer() {}

    public Customer(Date date) throws ParseException {
    	this.dob=new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-21");
    }
    
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + (dob!=null?new SimpleDateFormat("E MMM dd,yyyy hh:mm a").format(dob):null) + "]";
	}


}