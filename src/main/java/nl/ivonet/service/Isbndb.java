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

import com.google.gson.Gson;
import nl.ivonet.boundary.AuthorResponse;
import nl.ivonet.error.ErrorHandler;
import nl.ivonet.error.IsbnInvalidApiKeyException;
import nl.ivonet.io.GsonFactory;
import nl.ivonet.io.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.text.Normalizer.Form.NFD;
import static java.text.Normalizer.normalize;

/**
 * @author Ivo Woltring
 */
public class Isbndb {
    private static final Logger LOG = LoggerFactory.getLogger(Isbndb.class);

    private static final String API_URL = "http://isbndb.com/api/v2/json/%s/%s";
    private static final String API_URL_SINGLE = API_URL + "/%s";
    private static final String STATS_OPTION = "opt=keystats";
    private static final String API_URL_SINGLE_STAT = API_URL_SINGLE + "?" + STATS_OPTION;
    private final WebResource web;
    private final Gson gson;
    private String apiKey;

    public Isbndb() {
        loadProperties();
        web = WebResource.getInstance();
        gson = GsonFactory.getInstance()
                          .gson();
    }

    /**
     * The best results are gotten when presented as "firstname_lastname" or "firstname lastname".
     * Or of course an authorId as obtained by another call :-)
     * Diacritics will be removed.
     */
    public AuthorResponse authorById(final String name) {
        final String search = removeAccents(name.replace(" ", "_")
                                                .toLowerCase());
        return getAuthor(search);
    }


    private AuthorResponse getAuthor(final String search) {
        return this.gson.fromJson(getJsonSingle("author", search), AuthorResponse.class);
    }

    public AuthorResponse authorsByName(final String name) {
        return null;
    }

    private String getJsonSingle(final String collection, final String search) {
        checkApiKey();
        final String json = web.getJson(String.format(API_URL_SINGLE_STAT, apiKey, collection, search));
        gson.fromJson(json, ErrorHandler.class)
            .handle();
        return json;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    private void checkApiKey() {
        if ((this.apiKey == null) || this.apiKey.isEmpty()) {
            throw new IsbnInvalidApiKeyException("No API key was set");
        }
    }

    private void loadProperties() {
        try {
            final InputStream is = getClass().getClassLoader()
                                             .getResourceAsStream("isbndb.properties");
            if (is != null) {
                final Properties props = new Properties();
                props.load(is);
                this.apiKey = props.getProperty("api.key")
                                   .trim();
            }
        } catch (final IOException ignored) {
            LOG.info("Unable to read isbndb.properties with a 'api.key' property.");
        }
    }

    public static String removeAccents(final String text) {
        return (text == null) ? null : normalize(text, NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
