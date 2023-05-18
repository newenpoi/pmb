package com.openclassrooms.newenpoi.pmb.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {
    
	@Autowired
    private MockMvc mock;

    @Test
    @Disabled
    void testConnexionGet() throws Exception {
    	fail("Not implemented yet...");
    }

    @Test
    @Disabled
    void testGetCustomLoginForm() throws Exception {
    	fail("Not implemented yet...");
    }
}
