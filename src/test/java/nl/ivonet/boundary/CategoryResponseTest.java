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

package nl.ivonet.boundary;

import com.google.gson.Gson;
import nl.ivonet.dependency.GsonFactory;
import org.junit.Before;
import org.junit.Test;

import static nl.ivonet.utility.Utils.readTestResourceAsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivo Woltring
 */
public class CategoryResponseTest {
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        this.gson = GsonFactory.getInstance()
                               .gson();
    }

    @Test
    public void categoriesResponse() throws Exception {
        final String json = readTestResourceAsString("CategoriesResponse.json");
        final CategoryResponse response = this.gson.fromJson(json, CategoryResponse.class);
        assertTrue(response.getData() != null);
        assertTrue(response.getError() == null);
        assertThat(response.getData()
                           .size(), is(response.getResultCount()));
        assertThat(response.getData()
                           .get(0)
                           .getChildIds()
                           .size(), is(3));
    }

    @Test
    public void categoryResponse() throws Exception {
        final String json = readTestResourceAsString("CategoryResponse.json");
        final CategoryResponse response = this.gson.fromJson(json, CategoryResponse.class);
        assertTrue(response.getData() != null);
        assertTrue(response.getError() == null);
        assertThat(response.getData()
                           .size(), is(1));
        assertThat(response.getData()
                           .get(0)
                           .getParentId(), is("genres"));

    }
}