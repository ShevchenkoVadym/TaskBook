'use strict';

describe('Home Page', function() {

  describe('list view', function() {
    beforeEach(function() {
      browser.get('/');
    });

    it('shows only 10 best users', function() {
      var userList = element.all(by.repeater('user in users'));

      expect(userList.count()).toBeLessThan(11);
    });

    it('shows only 10 best tasks', function() {
      var taskList = element.all(by.repeater('task in tasks'));

      expect(taskList.count()).toBeLessThan(11);
    });
  });

  describe("item link", function() {
    
    it('has link to each user', function() {
      browser.get('/');
      element(by.linkText('alex')).click();

      expect(browser.getLocationAbsUrl()).toBe('/users/alex');
    });

    it('has link to each task', function() {
      browser.get('/');
      element(by.linkText('Я никогда не буду работать за копейки')).click();
    });

  });
});
