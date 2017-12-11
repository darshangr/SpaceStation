package com.rootminusone.spacestationpasses;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.rootminusone.spacestationpasses.model.PassesResponse;
import com.rootminusone.spacestationpasses.model.Response;
import com.rootminusone.spacestationpasses.utils.Util;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Test;

import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gangares on 12/10/17.
 */

public class StationPassesTest{

    private static PassesResponse passesResponse;

    @BeforeClass
    public static void setUp() throws Exception {
        String fileName  = StationPassesTest.class.getClassLoader().getResource("passes_response.json").toString();
        passesResponse = new Gson().fromJson(new JsonReader(new FileReader(fileName)), PassesResponse.class);
    }

    @Test
    public void isPassesTimeInSeconds() {
        String defaultValue = "1";
            if (passesResponse != null && passesResponse.getResponse() != null) {
                List<Response> responseList = passesResponse.getResponse();
                defaultValue = String.valueOf(responseList.get(1).getDuration());
            }
        assertEquals(Util.convertToSeconds(defaultValue), "5");
    }


}
