package com.demo.homemate;


import com.demo.homemate.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestLogin {

    
    @Autowired
    private UserService userService;

    @Test
    void UTCID01() {
        assertEquals(false, userService.checkLogin("",""));
    }

    @Test
    void UTCID02() {
        assertEquals(false, userService.checkLogin("","abc"));
    }

    @Test
    void UTCID03() {
        assertEquals(false, userService.checkLogin("","12345678"));
    }

    @Test
    void UTCID04() {
        assertEquals(false, userService.checkLogin(""," @Duong123"));
    }

    @Test
    void UTCID05() {
        assertEquals(false, userService.checkLogin("","@Duong123"));
    }

    @Test
    void UTCID06() {
        assertEquals(false, userService.checkLogin("huytranhagia",""));
    }

    @Test
    void UTCID07() {
        assertEquals(false, userService.checkLogin("huytranhagia","abc"));
    }

    @Test
    void UTCID08() {
        assertEquals(false, userService.checkLogin("huytranhagia","12345678"));
    }

    @Test
    void UTCID09() {
        assertEquals(false, userService.checkLogin("huytranhagia"," @Duong123"));
    }

    @Test
    void UTCID10() {
        assertEquals(false, userService.checkLogin("huytranhagia","@Duong123"));
    }

    @Test
    void UTCID11() {
        assertEquals(false, userService.checkLogin(" thanhduong01",""));
    }

    @Test
    void UTCID12() {
        assertEquals(false, userService.checkLogin(" thanhduong01","abf"));
    }

    @Test
    void UTCID13() {
        assertEquals(false, userService.checkLogin(" thanhduong01","12345678"));
    }

    @Test
    void UTCID14() {
        assertEquals(false, userService.checkLogin(" thanhduong01"," @Duong123"));
    }

    @Test
    void UTCID15() {
        assertEquals(false, userService.checkLogin(" thanhduong01","@Duong123"));
    }

    @Test
    void UTCID16() {
        assertEquals(false, userService.checkLogin("thanhduong01",""));
    }

    @Test
    void UTCID17() {
        assertEquals(false, userService.checkLogin("thanhduong01","abc"));
    }

    @Test
    void UTCID18() {
        assertEquals(false, userService.checkLogin("thanhduong01","12345678"));
    }

    @Test
    void UTCID19() {
        assertEquals(false, userService.checkLogin("thanhduong01"," @Duong123"));
    }

    @Test
    void UTCID20() {
        assertEquals(true, userService.checkLogin("thanhduong01","@Duong123"));
    }


}
