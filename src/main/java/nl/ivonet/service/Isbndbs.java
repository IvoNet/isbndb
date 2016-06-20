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

package nl.ivonet.service;

import nl.ivonet.InvalidApiKeyException;
import nl.ivonet.boundary.AuthorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ivo Woltring
 */
public class Isbndbs {
    private static final Logger LOG = LoggerFactory.getLogger(Isbndbs.class);

    private static final String BASE_URL = "http://isbndb.com/api/v2/json/";
    private static final String STAT_URL = "http://isbndb.com/api/v2/json/%s/author/DONOTFINDME?opt=keystats";
    private String apiKey;


    public AuthorResponse authorByIsbn(final String isbn) {
        checkApi();
        return null;
    }

    private void checkApi() {
        if (this.apiKey == null) {
            throw new InvalidApiKeyException("No API key was set");
        }
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }


}
