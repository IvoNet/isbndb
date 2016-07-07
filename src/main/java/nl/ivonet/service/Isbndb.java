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
import nl.ivonet.boundary.CategoryResponse;
import nl.ivonet.boundary.PublisherResponse;
import nl.ivonet.boundary.SubjectResponse;
import nl.ivonet.error.ErrorHandler;
import nl.ivonet.error.IsbndbRuntimeException;
import nl.ivonet.io.ApiKeyResource;
import nl.ivonet.io.GsonFactory;
import nl.ivonet.io.WebResource;
import nl.ivonet.service.EndpointBuilder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * The isbndb module.
 *
 * First obtain an api key from isbndb.com :-) before use.
 *
 * @author Ivo Woltring
 */
public class Isbndb {
    private static final Logger LOG = LoggerFactory.getLogger(Isbndb.class);
    private final WebResource web;
    private final Gson gson;
    private final ApiKeyResource apiKeyResource;
    private String apiKey;
    private boolean errorhandling;


    /**
     * Constructs a default Isbndb service.
     *
     * During construction a file called "isbndb.properties" is searched for on the
     * classpath. If found it will look foor a key "api.key" in that property file.
     * If found that will be the key it will use to do the service calls to isbndb.com
     */
    public Isbndb() {
        loadProperties();
        errorhandling = true;
        web = WebResource.getInstance();
        gson = GsonFactory.getInstance()
                          .gson();
        apiKeyResource = new ApiKeyResource();
    }

    /**
     * Same as the default constructor but with the exception of overriding the property file key
     * by providing it as a constructur argument.
     *
     * @param apiKey the isbndb api key
     */
    public Isbndb(final String apiKey) {
        this();
        this.apiKey = apiKey;
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
        return getAuthorResponse(getJson(new Builder().searchAuthor(id)));

    }

    /**
     * Broader search for authors based on a name.
     *
     * @param name String representation of the name to search for
     * @return {@link AuthorResponse}
     */
    public AuthorResponse authorsByName(final String name) {
        return authorsByName(name, 0);
    }

    /**
     * Broader search for authors based on a name.
     *
     * @param name String representation of the name to search for
     * @param page the page number to get
     * @return {@link AuthorResponse}
     */
    public AuthorResponse authorsByName(final String name, final int page) {
        return getAuthorResponse(getJson(new Builder().searchAuthors(name)
                                                      .page(page)));
    }

    public AuthorResponse getAuthorResponse(final String json) {
        final AuthorResponse response = this.gson.fromJson(json, AuthorResponse.class);
        response.setJson(json);
        return response;
    }

    /**
     * Searches for a single book based on an isbn10, isbn13 of book_id.
     *
     * @param id isbn10 / isbn13 or string respresentation id of the book
     * @return {@link BookResponse}
     */
    public BookResponse bookById(final String id) {
        final String json = getJson(new Builder().searchBook(encodeId(id)));
        return getBookResponse(json);
    }

    public BookResponse getBookResponse(final String json) {
        final BookResponse response = this.gson.fromJson(json, BookResponse.class);
        response.setJson(json);
        return response;
    }

    /**
     * Searches for a book collection based on a search string.
     *
     * @param name title or topic to search for
     * @return {@link BookResponse}
     */
    public BookResponse booksByName(final String name) {
        return booksByName(name, 0);
    }

    /**
     * Searches for a book collection based on a search string.
     *
     * @param name title or topic to search for
     * @param page the page number to get
     * @return {@link BookResponse}
     */
    public BookResponse booksByName(final String name, final int page) {
        return getBookResponse(getJson(new Builder().searchBooks(name)
                                                    .page(page)));
    }

    /**
     * Searches for publishers based on an id.
     *
     * @param name the publisher you are searching for.
     * @return {@link PublisherResponse}
     */
    public PublisherResponse publisherById(final String name) {
        final String json = getJson(new Builder().searchPublisher(name));
        return getPublisherResponse(json);
    }

    public PublisherResponse getPublisherResponse(final String json) {
        final PublisherResponse response = this.gson.fromJson(json, PublisherResponse.class);
        response.setJson(json);
        return response;
    }

    /**
     * Searches for publishers based on a search string.
     *
     * @param name the publisher you are searching for.
     * @return {@link PublisherResponse}
     */
    public PublisherResponse publishersByName(final String name) {
        return publishersByName(name, 0);
    }

    /**
     * Searches for publishers based on a search string.
     *
     * @param name the publisher you are searching for.
     * @param page the page number to get
     * @return {@link PublisherResponse}
     */
    public PublisherResponse publishersByName(final String name, final int page) {
        return getPublisherResponse(getJson(new Builder().searchPublishers(name)
                                                         .page(page)));
    }

    /**
     * Searches for subject based on a search string.
     *
     * @param id the subject you are searching for.
     * @return {@link SubjectResponse}
     */
    public SubjectResponse subjectById(final String id) {
        final String json = getJson(new Builder().searchSubject(id));
        return getSubjectResponse(json);
    }

    public SubjectResponse getSubjectResponse(final String json) {
        final SubjectResponse response = this.gson.fromJson(json, SubjectResponse.class);
        response.setJson(json);
        return response;
    }

    /**
     * Searches for subjects based on a search string.
     *
     * @param name the subjects you are searching for.
     * @return {@link SubjectResponse}
     */
    public SubjectResponse subjectsByName(final String name) {
        return subjectsByName(name, 0);
    }

    /**
     * Searches for subjects based on a search string.
     *
     * @param name the subjects you are searching for.
     * @param page the page number to get
     * @return {@link SubjectResponse}
     */
    public SubjectResponse subjectsByName(final String name, final int page) {
        return getSubjectResponse(getJson(new Builder().searchSubjects(name)
                                                       .page(page)));
    }

    /**
     * Searches for category based on a search string.
     *
     * @param id the category you are searching for.
     * @return {@link CategoryResponse}
     */
    public CategoryResponse categoryById(final String id) {
        return getCategoryResponse(getJson(new Builder().searchCategory(id)));
    }

    public CategoryResponse getCategoryResponse(final String json) {
        final CategoryResponse response = this.gson.fromJson(json, CategoryResponse.class);
        response.setJson(json);
        return response;
    }

    /**
     * Searches for categories based on a search string.
     *
     * @param name the categories you are searching for.
     * @return {@link CategoryResponse}
     */
    public CategoryResponse categoriesByName(final String name) {
        return categoriesByName(name, 0);
    }

    /**
     * Searches for categories based on a search string.
     *
     * @param name the categories you are searching for.
     * @param page the page number to get
     * @return {@link CategoryResponse}
     */
    public CategoryResponse categoriesByName(final String name, final int page) {
        return getCategoryResponse(getJson(new Builder().searchCategories(name)
                                                        .page(page)));
    }

    /**
     * To set the isbndb.com api key with.
     *
     * Be sure the key has been set before you start asking questions to the service.
     *
     * @param apiKey an isbndb api key
     */
    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Disables the throwing of runtime exceptions when
     * functional errors occur.
     *
     * This leaves the receiver to interpret the errors for
     * themselves.
     *
     * The default is that errorhandling is enabled.
     */
    public void disableErrorhandling() {
        this.errorhandling = false;
    }

    /**
     * Disables the throwing of runtime exceptions when
     * functional errors occur.
     *
     * This is default enabled.
     */
    public void enableErrorhandling() {
        this.errorhandling = true;
    }

    private boolean multipleIds(final String input) {
        return input.contains("/");
    }

    private String encode(final String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new IsbndbRuntimeException(e.getMessage(), e);
        }
    }

    private String encodeId(final String value) {
        String input = value.replace("-", "")
                            .replace(" ", "")
                            .replace(".", "")
                            .replace("[", "")
                            .replace("]", "")
                            .replace("ISBN", "");

        if (multipleIds(input)) {
            // TODO: 28-06-2016 Dirty! sometimes an isbn is represented like this `isbn10/isbn13` this gives problems
            // in the url. Might be better to throw an exception?!
            input = input.split("/")[0];
        }
        return encode(input);
    }


    private synchronized String getJson(final Builder builder) {

        ErrorHandler errorHandler;
        String json;
        while (true) {
            json = neverNull(web.getJson(builder.apikey(apiKey)
                                                .build()
                                                .endpoint()));
            errorHandler = gson.fromJson(json, ErrorHandler.class);
            if (errorHandler.noError()) {
                break;
            }
            if (errorHandler.limitReached()) {
                if (apiKeyResource.hasMore()) {
                    apiKey = apiKeyResource.next();
                    continue;
                }
            }
            break;
        }
        if (errorhandling) {
            errorHandler.handle();
        }
        return json;
    }

    private String neverNull(final String json) {
        return (json != null) ? json : "{\"error\":\"Answer was NULL\"}";
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
}
