package com.atom.ssedemo.demo;

import com.github.javafaker.Faker;

/**
 * @author Atom
 */
public class FakerDemo {
    public static void main(String[] args) {
        Faker faker = new Faker();
        String chineseName = faker.name().fullName();
        System.out.println(chineseName);
    }
}
