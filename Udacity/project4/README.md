# Project Overview

This project was given by Udacity it is a web-based application that reads RSS feeds. The original project already included [Jasmine](http://jasmine.github.io/) and even the first test suite is written.
After going throgh the code files and understand the logic the rest of the suites completed by me :).


# Test Suites Overview 
* `"RSS Fessd"` suite
 * Loops through each feed in the `allFeeds` object and ensures it has a URL defined _and_ that the URL is not empty
 * Loops through each feed in the `allFeeds` object and ensures it has a name defined and that the name is not empty
* `"The menu"` suite  
 * Ensures the menu element is hidden by default
 * Ensures the menu changes visibility when the menu icon is clicked
* `"Initial Entries"` suite
 * Ensures when the `loadFeed` function is called and completes its work, there is at least a single `.entry` element within the `.feed`      container
* `"New Feed Selection"` suite
 * Ensures when a new feed is loaded by the `loadFeed` function that the content actually changes
# How to run the Reader
* open the file index.html with your broweser
* if you see green and 7 spec, 0 failures this mean the test does not face any problem
* if you see any red and number of failures more than zero this is mean you, there is something wrong and fail the test
* all the tests suites in this file  _jasmine/spec/feedreader.js_
* you can update the code in app.js or feedreader.js to have failures

