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

import nl.ivonet.error.IsbnInvalidApiKeyException;

import static java.text.Normalizer.Form.NFD;
import static java.text.Normalizer.normalize;

/**
 * @author Ivo Woltring
 */
final class EndpointBuilder {


    private final String endpoint;

    /**
     * Private constructor for the builder.
     *
     * @param builder the {@link Builder} creating this object.
     */
    private EndpointBuilder(final Builder builder) {
        if (noSearchDefined(builder)) {
            throw new IllegalStateException("You must build a search.");
        }
        if ((builder.key == null) || builder.key.isEmpty()) {
            throw new IsbnInvalidApiKeyException("You must provide an API Key");
        }
        String endpoint = String.format(builder.url, builder.key, builder.collection, builder.search);
        if (builder.page > 1) {
            endpoint += (endpoint.contains("?") ? "&" : "?") + "page=" + builder.page;
        }
        this.endpoint = endpoint;
    }

    private boolean noSearchDefined(final Builder builder) {
        return (builder.collection == null) || (builder.search == null) || builder.search.isEmpty();
    }

    public String endpoint() {
        return endpoint;
    }

    /**
     * The Builder for {@link EndpointBuilder}.
     */
    static class Builder {
        private static final String API_URL = "http://isbndb.com/api/v2/json/%s/%s";
        private static final String STATS_OPTION = "opt=keystats";
        private static final String API_URL_SINGLE = API_URL + "/%s?" + STATS_OPTION;
        private static final String API_URL_COLLECTION = API_URL + "?q=%s&" + STATS_OPTION;
        private String key;
        private String collection;
        private String search;
        private int page;
        private String url;

        /**
         * Constructor of the {@link EndpointBuilder} class.
         */
        public Builder(final String key) {
            if (isEmpty(key)) {
                throw new IsbnInvalidApiKeyException("No API key provided");
            }
            this.url = "";
            this.key = key;
        }

        public Builder() {
            this.url = "";
        }

        private static String removeAccents(final String text) {
            return (text == null) ? null : normalize(text, NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        }

        public final Builder apikey(final String key) {
            this.key = key;
            return this;
        }

        public final Builder searchAuthors(final String value) {
            this.collection = "authors";
            search = prepareSearch(value);
            return this;
        }

        public final Builder searchAuthor(final String value) {
            this.collection = "author";
            search = prepareSingleSearch(value);
            return this;
        }

        public final Builder searchBooks(final String value) {
            this.collection = "books";
            search = prepareSearch(value);
            return this;
        }

        public final Builder searchBook(final String value) {
            this.collection = "book";
            search = prepareSingleSearch(value);
            return this;
        }

        public final Builder searchSubjects(final String value) {
            this.collection = "subjects";
            search = prepareSearch(value);
            return this;
        }

        public final Builder searchSubject(final String value) {
            this.collection = "subject";
            search = prepareSingleSearch(value);
            return this;
        }

        public final Builder searchPublishers(final String value) {
            this.collection = "publishers";
            search = prepareSearch(value);
            return this;
        }

        public final Builder searchPublisher(final String value) {
            this.collection = "publisher";
            search = prepareSingleSearch(value);
            return this;
        }

        public final Builder searchCategories(final String value) {
            this.collection = "categories";
            search = prepareCategorySearch(value);
            return this;
        }

        public final Builder searchCategory(final String value) {
            this.collection = "category";
            search = prepareSingleSearch(value);
            return this;
        }

        public final Builder page(final int page) {
            this.page = page;
            return this;
        }

        private boolean isEmpty(final String key) {
            return (key == null) || key.isEmpty();
        }

        private String prepareSearch(final String value) {
            return prepareSearch(value, "+");
        }

        private String prepareSingleSearch(final String value) {
            return prepareSearch(value, "_");
        }

        private String prepareCategorySearch(final String value) {
            return prepareSearch(value, ".");
        }

        private String prepareSearch(final String name, final String replacement) {
            return removeAccents(name.replace(" ", replacement)
                                     .toLowerCase());
        }

        /**
         * Builds the {@link EndpointBuilder} class.
         * <p/>
         * Should be called when finished building and the final product may be created. After calling this method the
         * resulting object will be immutable.
         *
         * @return {@link EndpointBuilder} instance.
         */
        public final EndpointBuilder build() {
            url = collection.endsWith("s") ? API_URL_COLLECTION : API_URL_SINGLE;
            return new EndpointBuilder(this);
        }

    }
}
