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
import nl.ivonet.boundary.BookResponse;
import nl.ivonet.boundary.PublisherResponse;
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
    private static final String STATS_OPTION = "opt=keystats";
    private static final String API_URL_SINGLE = API_URL + "/%s?" + STATS_OPTION;
    private static final String API_URL_COLLECTION = API_URL + "?q=%s&" + STATS_OPTION;
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
     * The best results are gotten when presented as "firstname_lastname".
     * If "firstname lastname" is given I will try to create an id from it.
     * Or of course an authorId as obtained by another call will work best of all :-)
     * Diacritics will be removed.
     *
     * @param id the string representation of the name to search
     * @return {@link AuthorResponse}
     */
    public AuthorResponse authorById(final String id) {
        final String search = removeAccents(id.replace(" ", "_")
                                                .toLowerCase());
        return getAuthor(search);
    }

    /**
     * Broader search for authors based on a name.
     *
     * @param name String representation of the name to search for
     * @return {@link AuthorResponse}
     */
    public AuthorResponse authorsByName(final String name) {
        final String search = removeAccents(name.replace(" ", "+")
                                                .toLowerCase());
        return getAuthors(search);
    }

    /**
     * Searches for a single book based on an isbn10, isbn13 of book_id.
     *
     * @param id isbn10 / isbn13 or string respresentation id of the book
     * @return {@link BookResponse}
     */
    public BookResponse bookById(final String id) {
        final String search = removeAccents(id.replace(" ", "_")
                                              .toLowerCase());
        return getBook(search);
    }

    /**
     * Searches for a book collection based on a search string.
     *
     * @param name title or topic to search for
     * @return {@link BookResponse}
     */
    public BookResponse booksByName(final String name) {
        final String search = removeAccents(name.replace(" ", "+")
                                                .toLowerCase());
        return getBooks(search);
    }

    public PublisherResponse publishersByName(final String name) {
        final String search = removeAccents(name.replace(" ", "+")
                                                .toLowerCase());
        return getPublishers(search);
    }

    private PublisherResponse getPublishers(final String search) {
        return this.gson.fromJson(getJsonCollection("publishers", search), PublisherResponse.class);
    }

    private BookResponse getBooks(final String search) {
        return this.gson.fromJson(getJsonCollection("books", search), BookResponse.class);
    }

    private BookResponse getBook(final String search) {
        return this.gson.fromJson(getJsonSingle("book", search), BookResponse.class);
    }

    private AuthorResponse getAuthors(final String search) {
        return this.gson.fromJson(getJsonCollection("authors", search), AuthorResponse.class);
    }

    private AuthorResponse getAuthor(final String search) {
        return this.gson.fromJson(getJsonSingle("author", search), AuthorResponse.class);
    }

    private String getJsonSingle(final String collection, final String search) {
        return getJson(String.format(API_URL_SINGLE, apiKey, collection, search));
    }

    private String getJsonCollection(final String collection, final String search) {
        return getJson(String.format(API_URL_COLLECTION, apiKey, collection, search));
    }

    private String getJson(final String url) {
        checkApiKey();
        final String json = web.getJson(url);
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
