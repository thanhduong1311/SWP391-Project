package com.demo.homemate;

import com.demo.homemate.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestChangePassword {
    @Autowired
    private UserService userService;

    @Test
    void UTCID01() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","",""));
    }
    @Test
    void UTCID02() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","","12345678"));
    }
    @Test
    void UTCID03() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","",""," #Duong111"));
    }
    @Test
    void UTCID04() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","","#Duong111"));
    }
    @Test
    void UTCID05() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","abc12",""));
    }
    @Test
    void UTCID06() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","abc12","12345678"));
    }
    @Test
    void UTCID07() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","abc12"," #Duong111"));
    }
    @Test
    void UTCID08() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","abc12","#Duong111"));
    }
    @Test
    void UTCID09() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","@Duong123",""));
    }
    @Test
    void UTCID10() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","@Duong123","12345678"));
    }
    @Test
    void UTCID11() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","@Duong123"," #Duong111"));
    }
    @Test
    void UTCID12() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","@Duong123","#Duong111"));
    }
    @Test
    void UTCID13() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","#Duong111",""));
    }
    @Test
    void UTCID14() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","#Duong111","12345678"));
    }
    @Test
    void UTCID15() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","#Duong111"," #Duong111"));
    }
    @Test
    void UTCID16() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","","#Duong111","#Duong111"));
    }
    @Test
    void UTCID17() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","",""));
    }
    @Test
    void UTCID18() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","","12345678"));
    }
    @Test
    void UTCID19() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123",""," #Duong111"));
    }
    @Test
    void UTCID20() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","","#Duong111"));
    }
    @Test
    void UTCID21() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","abc12",""));
    }
    @Test
    void UTCID22() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","abc12","12345678"));
    }
    @Test
    void UTCID23() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","abc12"," #Duong111"));
    }
    @Test
    void UTCID24() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","abc12","#Duong111"));
    }
    @Test
    void UTCID25() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","@Duong123",""));
    }
    @Test
    void UTCID26() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","@Duong123","12345678"));
    }
    @Test
    void UTCID27() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","@Duong123"," #Duong111"));
    }
    @Test
    void UTCID28() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","@Duong123","#Duong111"));
    }
    @Test
    void UTCID29() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","#Duong111",""));
    }
    @Test
    void UTCID30() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","#Duong111","12345678"));
    }
    @Test
    void UTCID31() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","#Duong111"," #Duong111"));
    }
    @Test
    void UTCID32() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","123","#Duong111","#Duong111"));
    }
    @Test
    void UTCID33() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","",""));
    }
    @Test
    void UTCID34() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","","12345678"));
    }
    @Test
    void UTCID35() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678",""," #Duong111"));
    }
    @Test
    void UTCID36() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","","#Duong111"));
    }
    @Test
    void UTCID37() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","abc12",""));
    }
    @Test
    void UTCID38() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","abc12","12345678"));
    }
    @Test
    void UTCID39() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","abc12"," #Duong111"));
    }
    @Test
    void UTCID40() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","abc12","#Duong111"));
    }
    @Test
    void UTCID41() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","@Duong123",""));
    }
    @Test
    void UTCID42() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","@Duong123","12345678"));
    }
    @Test
    void UTCID43() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","@Duong123"," #Duong111"));
    }
    @Test
    void UTCID44() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","@Duong123","#Duong111"));
    }
    @Test
    void UTCID45() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","#Duong111",""));
    }
    @Test
    void UTCID46() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","#Duong111","12345678"));
    }
    @Test
    void UTCID47() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","#Duong111"," #Duong111"));
    }
    @Test
    void UTCID48() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","12345678","#Duong111","#Duong111"));
    }
    @Test
    void UTCID49() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","",""));
    }
    @Test
    void UTCID50() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","","12345678"));
    }
    @Test
    void UTCID51() {
        assertEquals(false,userService.checkChangePassword("thanhduong01", " @Duong1233",""," #Duong111"));
    }
    @Test
    void UTCID52() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","","#Duong111"));
    }
    @Test
    void UTCID53() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","abc12",""));
    }
    @Test
    void UTCID54() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","abc12","12345678"));
    }
    @Test
    void UTCID55() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","abc12"," #Duong111"));
    }
    @Test
    void UTCID56() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","abc12","#Duong111"));
    }
    @Test
    void UTCID57() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","@Duong123",""));
    }
    @Test
    void UTCID58() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","@Duong123","12345678"));
    }
    @Test
    void UTCID59() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","@Duong123"," #Duong111"));
    }
    @Test
    void UTCID60() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","@Duong123","#Duong111"));
    }
    @Test
    void UTCID61() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","#Duong111",""));
    }
    @Test
    void UTCID62() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","#Duong111","12345678"));
    }
    @Test
    void UTCID63() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","#Duong111"," #Duong111"));
    }
    @Test
    void UTCID64() {
        assertEquals(false,userService.checkChangePassword("thanhduong01"," @Duong123","#Duong111","#Duong111"));
    }
    @Test
    void UTCID65() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","",""));
    }
    @Test
    void UTCID66() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","","12345678"));
    }
    @Test
    void UTCID67() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123",""," #Duong111"));
    }
    @Test
    void UTCID68() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","","#Duong111"));
    }
    @Test
    void UTCID69() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","abc12",""));
    }
    @Test
    void UTCID70() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","abc12","12345678"));
    }
    @Test
    void UTCID71() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","abc12"," #Duong111"));
    }
    @Test
    void UTCID72() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","abc12","#Duong111"));
    }
    @Test
    void UTCID73() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","@Duong123",""));
    }
    @Test
    void UTCID74() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","@Duong123","12345678"));
    }
    @Test
    void UTCID75() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","@Duong123"," #Duong111"));
    }
    @Test
    void UTCID76() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","@Duong123","#Duong111"));
    }
    @Test
    void UTCID77() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","#Duong111",""));
    }
    @Test
    void UTCID78() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","#Duong111","12345678"));
    }
    @Test
    void UTCID79() {
        assertEquals(false,userService.checkChangePassword("thanhduong01","@Duong123","#Duong111"," #Duong111"));
    }
    @Test
    void UTCID80() {
        assertEquals(true,userService.checkChangePassword("thanhduong01","@Duong123","#Duong111","#Duong111"));
    }

}
