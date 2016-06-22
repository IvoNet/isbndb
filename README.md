# Java API to the isbndb.com json services

[isbndb](http://isbndb.com) is a nice resource for book / author metadata.
This module gives easy to use methods for accessing the json api
of this website.

## Prerequisites

* Java 8
* maven
* git
* internet connection
* isbndb api key (create an account and then a key)
* good cheer :-)

## Usage

Just add this to your maven project:

```xml
<dependency>
    <groupId>nl.ivonet</groupId>
    <artifactId>isbndb</artifactId>
    <version>0.2</version>
</dependency>
```

or

* clone this project
* maven build this project
* add the dependency to your project
* start using the `Isbndb` service

## Good to know

By placing a file called `isbndb.properties` on the classpath containing
a property called `api.key` with a valid Api key it will be picked up automatically
when the Isbndb service is instantiated.

e.g. `isbndb.properties`

`api.key=VALIDKEY`

You can of course override this by providing your own key programmatically.

## License
 
Copyright 2016 Ivo Woltring

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Open Stories

* As a User I WANT TO get a requested result page IN ORDER TO find the wanted result
* As a User I WANT TO be notified when I exceed my daily quota IN ORDER TO don't make unnecessary requests
* As a User I WANT TO IN ORDER TO
* As a User I WANT TO IN ORDER TO
