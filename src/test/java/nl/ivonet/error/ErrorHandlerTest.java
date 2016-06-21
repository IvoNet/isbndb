/*
 * Copyright 2016 Ivo Woltring <WebMaster@ivonet.nl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.ivonet.error;

import com.google.gson.Gson;
import nl.ivonet.parser.GsonFactory;
import nl.ivonet.utility.Utils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ivo Woltring
 */
public class ErrorHandlerTest {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = GsonFactory.getInstance()
                          .gson();

    }

    @Test(expected = IsbnInvalidApiKeyException.class)
    public void handleApiKey() throws Exception {
        final String error = Utils.readTestResourceAsString("ErrorInvalidApiKey.json");
        gson.fromJson(error, ErrorHandler.class)
            .handle();
    }

    @Test(expected = IsbndbNotFoundException.class)
    public void handleNotFound() throws Exception {
        final String error = Utils.readTestResourceAsString("ErrorNotFound.json");
        gson.fromJson(error, ErrorHandler.class)
            .handle();
    }

    @Test(expected = IsbndbEmptyQueryException.class)
    public void handleEmptyQuery() throws Exception {
        final String error = Utils.readTestResourceAsString("ErrorEmptyQuery.json");
        gson.fromJson(error, ErrorHandler.class)
            .handle();
    }

    @Test(expected = IsbndbRuntimeException.class)
    public void handleUnknown() throws Exception {
        final String error = Utils.readTestResourceAsString("ErrorUnknown.json");
        gson.fromJson(error, ErrorHandler.class)
            .handle();
    }

}