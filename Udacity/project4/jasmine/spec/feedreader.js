/* This is the spec file that Jasmine will read and contains
 * all of the tests that will be run against your application.
 */

/* We're placing all of our tests within the $() function,
 * since some of these tests may require DOM elements. We want
 * to ensure they don't run until the DOM is ready.
 */
$(function() {
    /* This is our first test suite - a test suite just contains
    * a related set of tests. This suite is all about the RSS
    * feeds definitions, the allFeeds variable in our application.
    */
    describe('RSS Feeds', function() {
        /* This is our first test - it tests to make sure that the
         * allFeeds variable has been defined and that it is not
         * empty. Experiment with this before you get started on
         * the rest of this project. What happens when you change
         * allFeeds in app.js to be an empty array and refresh the
         * page?
         */
        it('are defined', function() {
            expect(allFeeds).toBeDefined();
            expect(allFeeds.length).not.toBe(0);
        });


        /* loop through each feed to ensures it has a URL defined
         * and that the URL is not empty.
         */
         it('url is defined and not empty', function() {
           for (feed of allFeeds) {
             expect(feed.url).toBeDefined();
             expect(feed.url).not.toBe("");
           }
         });


        /* loop through each feed and ensures it has a name defined
         * and that the name is not empty.
         */
         it('name is defined and not empty', function() {
           for (feed of allFeeds) {
             expect(feed.name).toBeDefined();
             expect(feed.name).not.toBe("");
           }
         });
    });


    /*suite for menu named "The menu" */
    describe('The menu', function() {
      /* to ensures the menu is hidden by default */
       it('menu is hidden', function() {
         let check=document.body.classList.contains('menu-hidden');
           expect(check).toBe(true);
       });
       /*  ensures the menu changes visibility when the menu icon is clicked*/
       it('menu changes visibility after click', function() {
         let icon=$('.menu-icon-link');
         icon.click();
         let check=document.body.classList.contains('menu-hidden');
         expect(check).toBe(false);
         icon.click();
         let check2=document.body.classList.contains('menu-hidden');
         expect(check2).toBe(true);
        });
    });



    /* test suite named "Initial Entries" */
    describe('Initial Entries', function() {
      /* ensures when the loadFeed function is called and completes its work */

       beforeEach(function(done) {
       loadFeed(0,function ()
       {
          done();}
        );
       });
       it('Entries not empty', function(done) {
            let totalFeeds=document.getElementsByClassName('entry').length;
            expect(totalFeeds).toBeGreaterThan(0);
           done();
       });
      });


    /* test suite named "New Feed Selection" */
    describe('New Feed Selection', function() {
      /* ensures when a new feed is loaded */
      let firstSelection;
      beforeEach(function(done) {
      loadFeed(0,function ()
      {
        firstSelection=document.querySelectorAll('.entry');
        loadFeed(1,function()
      {
        done();
       });
      }
       );
      });
      it('check new feed load', function(done) {
           let secondSelection=document.querySelectorAll('.entry');
           expect(firstSelection).not.toEqual(secondSelection);
           done();
      });
      });

}());
