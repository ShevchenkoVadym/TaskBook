'use strict';

var helper = require('../prot-helper');

describe('Login scenario', function() {

  describe('negative cases', function() {

    var username, email;

    beforeEach(function() {
      username = 'test' + Math.floor((Math.random() * 1000) + 1);
      email = 'test' + Math.floor((Math.random() * 1000) + 1) + '@example.com';
      
      browser.get('/');
      element(by.buttonText('Регистрация')).click();
    });

    describe('when username is already taken', function() {
      it('shows formError', function() {
        helper.fillRegForm('alex', email, '123');

        element(by.buttonText('Создать аккаунт')).click();
        browser.sleep(1000);

        expect($("[ng-hide='formError === undefined'].form-message").isDisplayed()).toBeTruthy();
        expect($("[ng-hide='formError === undefined'].form-message").getText()).toBe('Ошибка:\nЛогин занят.');
      });
    });

    describe('when email is already taken', function() {
      it('shows formError', function() {
        helper.fillRegForm('12nlksd8723', 'alex@mail.ru', '123');

        element(by.buttonText('Создать аккаунт')).click();
        browser.sleep(1000);

        expect($("[ng-hide='formError === undefined'].form-message").isDisplayed()).toBeTruthy();
        expect($("[ng-hide='formError === undefined'].form-message").getText()).toBe('Ошибка:\nАдрес эл.почты уже используется.');
      });
    });

    describe('when password doesnt match confirmation', function() {
      it('shows error', function() {
        element(by.model('password_verify')).sendKeys('1');

        expect($("[ng-show='new_user.password != password_verify'].text-center").isDisplayed()).toBeTruthy();
        expect($("[ng-show='new_user.password != password_verify'].text-center").getText()).toBe('Пароль и подтверждение должны совпадать');
      });
    });

    describe('when form is not filled', function() {
      it('shows alert', function() {
        helper.fillRegForm('alex', email, '');

        expect($("[ng-hide='!formNewUser.$error.required'].form-message").isDisplayed()).toBeTruthy();
        expect($("[ng-hide='!formNewUser.$error.required'].form-message").getText()).toBe('Все поля обязательны к заполнению.');
      });
    });
  });//<-- negative cases

  describe('positive cases', function() {

    var username, email;

    beforeEach(function() {
      username = 'test' + Math.floor((Math.random() * 1000) + 1);
      email = 'test' + Math.floor((Math.random() * 1000) + 1) + '@example.com';
    });

    describe('when not on login page', function() {
      var stored_username1;

      describe('when form is ready', function() {
        it('hides required fields alert', function() {
          browser.get('/');

          element(by.buttonText('Регистрация')).click();
          helper.fillRegForm(username, email, '123');
          stored_username1 = username;

          expect($("[ng-hide='!formNewUser.$error.required'].form-message").isDisplayed()).toBe(false);
        });
      });
      
      describe('after form submit', function() {
        it('redirects to login', function() {
          element(by.buttonText('Создать аккаунт')).click();
          browser.sleep(1000);

          expect(browser.getLocationAbsUrl()).toBe('/login');
        });

        it('shows flash message', function() {
          expect($("[ng-show='flash.getMessage()'].flash-message").isDisplayed()).toBeTruthy();
          expect($("[ng-show='flash.getMessage()'].flash-message").getText()).toBe('Регистрация прошла успешно. Войдите на сайт используя ваш логин и пароль.');
        });
      });

      describe('on login page', function() {
        it('allows new user to sign in', function() {
          element(by.model('login_try.username')).sendKeys(stored_username1);
          element(by.model('login_try.password')).sendKeys('123');
          element(by.buttonText('Войти')).click();
          browser.sleep(1000);

          expect(element(by.buttonText('Вход')).isDisplayed()).toBe(false);
          expect(element(by.buttonText('Регистрация')).isDisplayed()).toBe(false);
          expect(element(by.buttonText('Выход')).isDisplayed()).toBeTruthy();
        });

        it('redirects to user profile after sign in', function() {
          expect(browser.getLocationAbsUrl()).toBe('/users/' + stored_username1);
          element(by.buttonText('Выход')).click();
        });
      });
    });//<-- when not on login page

    describe('when on login page', function() {

      var formInfoList, stored_username2;

      describe('when form is ready', function() {
        it('hides required fields alert', function() {
          browser.get('/#/login');

          element(by.linkText('Регистрация')).click();
          helper.fillRegForm(username, email, '123');
          stored_username2 = username;

          expect($("[ng-hide='!formNewUser.$error.required'].form-message").isDisplayed()).toBe(false);
          element(by.buttonText('Создать аккаунт')).click();
          browser.sleep(1000);
        });
      });

      describe('after reg form submit', function() {
        it('allows new user to sign in', function() {
          browser.get('/#/login');

          element(by.model('login_try.username')).sendKeys(stored_username2);
          element(by.model('login_try.password')).sendKeys('123');
          element(by.buttonText('Войти')).click();
          browser.sleep(1000);

          expect(element(by.buttonText('Вход')).isDisplayed()).toBe(false);
          expect(element(by.buttonText('Регистрация')).isDisplayed()).toBe(false);
          expect(element(by.buttonText('Выход')).isDisplayed()).toBeTruthy();
        });

        it('redirects to user profile after sign in', function() {
          expect(browser.getLocationAbsUrl()).toBe('/users/' + stored_username2);
          element(by.buttonText('Выход')).click();
        });
      });//<- after reg form submit
    });//<- when on login page
  });//<- positive cases
});//<- registration scenarios