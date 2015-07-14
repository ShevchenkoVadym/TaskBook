# README #
* develop - рабочая ветка
* master - ветка релизов

# RULES #

* Никаких rebase
* Никаких git pull as new branch
* **Перед коммитом всегда сливайте последние изменения** (git pull)
* Если слить изменения не получается - **кладете свои изменения на полку (put changes on the shelf)**, а потом достаете
* **Перед коммитом всегда смотрите какие изменения заливаете**

# Переменные окружения #
При первоначальной настройке проекта надо задать в системе переменную окружения "TASKBOOK".
Эта переменная хранит путь до директории в системе.
В ней хранятся директории логов, библиотеки и т.п.
Без этой переменной не будет работать тестировщик задач TaskTesterUtility.

В директорию %TASKBOOK%\lib (или $TASKBOOK/lib) необходимо положить следующие библиотеки для тестирования: junit-4.11.jar и hamcrest-all-1.3.jar

В директорию %TASKBOOK%\lib нужно положить файл test-sandbox.policy   .Содержание файла:
grant { permission java.lang.RuntimePermission "accessDeclaredMembers"; };
(Политика безопасности для песочницы тестировщика)

# Настройки для сервера Tomcat !!! #
https://www.dropbox.com/s/dkd077fntt8y2z3/keyStoreTomcat.keystore?dl=0  - файл хранилище ключей keystore,
server.xml доп настройки -
<Connector SSLEnabled='true' keystoreFile='C:\prog\keyStoreTomcat.keystore'
keystorePass='yi690dNqHD' port='8443' scheme='https' secure='true' sslProtocol='TLS'/>
если кто использует jetty - проблем с запуском не будет, если tomcat - нужно добавить в выбранное Вами место keystore и указать ссответствующий путь к нему в доп. настройках server.xml, server.xml обычно находиться в паке \conf папки tomcat [19:10:21] Sergio Shapoval: у меня указанный файл с dropbox лежит в C:\prog\keyStoreTomcat.keystore
d6246a3a257ed6960704809e20142db00c2b6583

# Юнит-тестирование AngularJS с Karma и Jasmine #

Для оптимизации структуры проекта папка со всем необходимым для тестирования вынесена за пределы приложения angular (папка html).

[Karma](http://karma-runner.github.io/0.10/index.html) - консольный инструмент для запуска тестов согласно конфиг файлу. Тесты гоняются в разных браузерах, умеет следить за изменениями исходников и генерировать покрытие кода.

[Jasmine](http://jasmine.github.io/1.3/introduction.html) - библиотека для тестирования.

Unit тесты являются основным способом тестирования фронт-енда по причине того, что они обрабатываются быстро и могут работать в фоновом режиме и не требуют обновления версии проекта запущенного в томкате. 
При этом юнит тесты работают не с бек-ендом, а лишь с его копией.

В папке node_modules лежат необходимые библиотеки для тестирования.
В папке examples размещаются тесты.

Для работы необходимо установленный node.js. Проверить установлен ли нод в системе:
```
#!bash
node --version

```

Ниже приведен набор команд для установки нода(если необходимо) и инструментов тестирования в системе Linux:
```
#!bash
apt-get install nodejs-legacy npm
nodejs --version
npm --version

```

Для установки набора инструментов для тестирования:
```
#!bash
npm install karma -g
npm install phantomjs -g
npm install karma-cli -g

```
**В папке src/main/webapp/WEB-INF/test**
```
#!bash
npm install karma-jasmine@2_0 jasmine-core karma-chrome-launcher karma-phantomjs-launcher istanbul --save

```

Перед запуском karma необходимо создать переменные окружения, в которых будет указано размещение браузеров, которые должна использовать Karma - сейчас в конфиге указан только хром:
```
#!bash
CHROME_BIN="полный_путь\chromium.exe" (chrome.exe для windows)

```

Запуск karma:

в папке src/main/webapp/WEB-INF/test
```
#!bash
karma start

```
Откроется браузер - его можно свернуть и оставить в фоновом режиме. Карма будет следить за изменениями файлов и перезапускать тесты при их изменениях.

# e2e тестирование с Protractor и Jasmine #

e2e тесты эмулируют действия пользователя - открывают браузер, ходят по ссылкам, заполняют формы и т.д. 
Такие тесты необходимо лишь для функционала, которые невозможно закрыть юнит тестами (например в нашем проекте это процесс регистрации пользователя) или критически важного функционала.

Установка:
```
#!bash
npm install -g protractor

```

Запуск тестов (при запущенном проекте):
В папке src/main/webapp/WEB-INF/test
```
#!bash
protractor prot.conf.js

```