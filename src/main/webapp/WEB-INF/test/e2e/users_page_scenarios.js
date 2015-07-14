'use strict';

var helper = require('../prot-helper');

describe('Users pages', function() {

  describe('/users page', function() {

    var userList;

    beforeEach(function() {
      browser.get('/#/users');

      userList = element.all(by.repeater('user in users'));
    });

    it('shows no more than 15 users per page', function() {
      expect(userList.count()).toBeLessThan(16);
    });


    it('filters user list as a user types into the search box', function() {
      var query = element(by.model('searchForUser.username'));

      query.sendKeys('mary');
      expect(userList.count()).toBe(1);

      query.clear();
      query.sendKeys('as');
      expect(userList.count()).toBe(2);
      query.clear();
    });

    it('provides link to each user', function() {
      element(by.linkText('alex')).click();

      expect(browser.getLocationAbsUrl()).toBe('/users/alex');
    });

    describe('as admin user', function() {
      var userStartCount, banStartCount, banButtons;

      beforeEach(function() {
        userList.count().then(function(originalCount) {
          userStartCount = originalCount;
        });

        banButtons = element.all(by.buttonText('Ban'));
        banButtons.count().then(function(originalCount) {
          banStartCount = originalCount;
        });
      });

      it('can see admin buttons', function(){
        helper.signIn('alex', '12345');

        expect(element(by.buttonText('Delete')).isPresent()).toBe(true);
        expect(element(by.buttonText('Ban')).isPresent()).toBe(true);
      });

      it('can delete user', function() {
        element(by.buttonText('Delete')).click();

        helper.acceptAlert();
 
        expect(userList.count()).toBe(userStartCount - 1);
      });

      it('can ban user', function() {        
        element(by.buttonText('Ban')).click();

        expect(banButtons.count()).toEqual(banStartCount - 1);

        //rollback changes
        element(by.buttonText('Unban')).click();
      });

      it('can change user role', function() {
        element(by.cssContainingText('option', 'USER')).click();
        element(by.buttonText('Set')).click();

        expect($("[ng-show='updated_user == user.username'].glyphicon-ok").isDisplayed()).toBeTruthy();
      });
    });
  });
});