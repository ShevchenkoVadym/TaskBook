'use strict';

var helper = require('../prot-helper');

describe('Login scenario', function() {

  var username, password;
    
  beforeEach(function() {
    username = 'alex';
    password = '12345';
  });

  describe('negative cases', function() {
    beforeEach(function() {
      browser.get('/');
    });

    describe('when username is not correct', function() {
      it('shows formError', function() {
        helper.signIn('123', password);

        expect($("[ng-hide='formError === undefined'].form-message").isDisplayed()).toBeTruthy();
        expect($("[ng-hide='formError === undefined'].form-message").getText()).toBe('Ошибка:\nНеверная комбинация логина и пароля.');
      });
    });

    describe('when password is not correct', function() {
      it('shows formError', function() {
        helper.signIn(username, '123');

        expect($("[ng-hide='formError === undefined'].form-message").isDisplayed()).toBeTruthy();
        expect($("[ng-hide='formError === undefined'].form-message").getText()).toBe('Ошибка:\nНеверная комбинация логина и пароля.');
      });
    });

    describe('when username is not present', function() {
      it('disables submit button', function() {
        element(by.buttonText('Вход')).click();

        element(by.model('login_try.password')).sendKeys('123');
        expect(element(by.buttonText('Войти')).isEnabled()).toBe(false);
      });
    });

    describe('when password is not present', function() {
      it('disables submit button', function() {
        element(by.buttonText('Вход')).click();

        element(by.model('login_try.username')).sendKeys('123');
        expect(element(by.buttonText('Войти')).isEnabled()).toBe(false);
      });
    });
    
  });//<-- negative cases

  describe('positive cases', function() {

    describe('after sign in', function() {

      it('changes buttons in header', function() {
        browser.get("/");
        helper.signIn('alex', '12345');

        expect(element(by.buttonText('Вход')).isDisplayed()).toBe(false);
        expect(element(by.buttonText('Регистрация')).isDisplayed()).toBe(false);
        expect(element(by.buttonText('Выход')).isDisplayed()).toBeTruthy();

        element(by.buttonText('Выход')).click();
      });

      describe('when not on login page', function() {
        it('does not redirect', function() {
          browser.get("/");
          helper.signIn('alex', '12345');
          
          expect(browser.getLocationAbsUrl()).toBe('/');

          element(by.buttonText('Выход')).click();
        });
      });//<-- when not on login page

      describe('when on login page', function() {
        it('redirects to user profile', function() {
          browser.get("/#/login");
          element(by.model('login_try.username')).sendKeys(username);
          element(by.model('login_try.password')).sendKeys(password);
          element(by.buttonText('Войти')).click();
          
          expect(browser.getLocationAbsUrl()).toBe('/users/alex');

          element(by.buttonText('Выход')).click();
        });
      });//<- when on login page
    });//<- after sign in
  });//<- positive cases
});//<- registration scenarios