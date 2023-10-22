package com.demo.homemate;



import com.demo.homemate.entities.Customer;
import com.demo.homemate.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoginCheckTest {
    @Autowired
    private CustomerRepository customerRepository;
    public boolean login(String usernanme, String pass){
        Customer customer = customerRepository.findByUsername(usernanme);

        if(customer.getUsername().equals(usernanme)){
            return true;
        }
       else return false;
    }
@Test
    void checkLoginWrongPassword(){
    /*UserService userService1 = new UserService();*/
    assertTrue(login("giahuy","pass"));

}
}