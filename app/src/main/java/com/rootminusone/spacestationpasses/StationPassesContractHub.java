package com.rootminusone.spacestationpasses;

import com.rootminusone.spacestationpasses.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gangares on 12/10/17.
 */

/**
 * This class has all the interface methods that are needed to communicate between different components of passes app and follows MVP convention
 */
public interface StationPassesContractHub {

    interface View {
        void displayPasses(ArrayList<Response> passesList);
        void displayError(String errorMessage);
    }

    interface Presenter {
        void loadPasses(Map<String, String> queryParams);
    }
}
