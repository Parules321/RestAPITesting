package com.RestfulBookerApiTesting;

import com.github.javafaker.Faker;

public class ExploreFaker {

	public static void main(String[] args) {
		Faker faker = new Faker();
		
		System.out.println(faker.address().fullAddress());
		System.out.println(faker.animal().name());
	
		
	

	}

}
