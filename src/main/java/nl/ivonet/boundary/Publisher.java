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

import java.util.List;

/**
 * @author Ivo Woltring
 */
public class Publisher {
    private String publisherId;
    private String name;
    private String nameLatin;
    private String location;
    private int bookCount;
    private List<String> bookIds;
    private List<String> categoryIds;

    public String getPublisherId() {
        return this.publisherId;
    }

    public String getName() {
        return this.name;
    }

    public String getNameLatin() {
        return this.nameLatin;
    }

    public String getLocation() {
        return this.location;
    }

    public List<String> getBookIds() {
        return this.bookIds;
    }

    public List<String> getCategoryIds() {
        return this.categoryIds;
    }

    public int getBookCount() {
        return this.bookCount;
    }
}
