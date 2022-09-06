package com.example.maruf.a2svc.configs;

import com.example.maruf.a2svc.services.SheetsServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
public class SheetsConfig {
    private final SheetsServiceUtil sheetsServiceUtil;

    @Autowired
    public SheetsConfig(SheetsServiceUtil sheetsServiceUtil) {
        this.sheetsServiceUtil = sheetsServiceUtil;
    }

    public void test(){
        try{
            sheetsServiceUtil.getUsers();
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (URISyntaxException e) {
            System.out.println("URI Exception");
        }

    }
}
