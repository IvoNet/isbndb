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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ivo Woltring
 */
public class Isbndb {
    private static final Logger LOG = LoggerFactory.getLogger(Isbndb.class);
    private String apiKey;

    private Isbndb() {
        loadProperties();
    }

    public AuthorResponse authorById(final String name) {
        checkApi();
        return null;
    }

    public AuthorResponse authorsByName(final String name) {
        checkApi();
        return null;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    private void checkApi() {
        if (this.apiKey == null) {
            throw new InvalidApiKeyException("No API key was set");
        }
    }

    private void loadProperties() {
        try {
            final InputStream is = getClass().getClassLoader()
                                             .getResourceAsStream("isbndb.properties");
            if (is != null) {
                final Properties props = new Properties();
                props.load(is);
                this.apiKey = props.getProperty("key")
                                   .trim();
            }
        } catch (final IOException ignored) {
            LOG.info("Unable to read isbndb.properties with a 'key' property.");
        }
    }

    @SuppressWarnings("UtilityClass")
    private static final class Instance {
        static final Isbndb SINGLETON = new Isbndb();
    }

    public static Isbndb getInstance() {
        return Instance.SINGLETON;
    }
}
