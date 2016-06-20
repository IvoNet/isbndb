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
public class Response<T> {
    private String indexSearched;
    private int resultCount;
    private int pageCount;
    private int currentPage;
    private List<T> data;
    private Keystats keystats;
    private String error;

    public String getIndexSearched() {
        return this.indexSearched;
    }

    public int getResultCount() {
        return this.resultCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public List<T> getData() {
        return this.data;
    }

    public Keystats getKeystats() {
        return this.keystats;
    }

    public String getError() {
        return this.error;
    }
}
