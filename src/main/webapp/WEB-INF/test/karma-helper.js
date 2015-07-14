(function(){

    var TestConstants = function() {
        this.USERS_API = 'rest/api/v1/users';
        this.TASKS_API = 'rest/api/v1/tasks';

        this.USERS = [
            {"id":100000,"username":"alex","password":"12345","isEnabled":true,"isNonReadOnly":false,"creationDate":1421400237000,"email":"alex@mail.ru","roles":["ADMIN"],"country":"Bermuda","imageUrl":"img/user_photo_male.png","rating":12.56,"tasksSolved":45},
            {"id":100001,"username":"sashok","password":"123","isEnabled":true,"isNonReadOnly":false,"creationDate":1421400237000,"email":"sashok@mail.ru","roles":["MODERATOR"],"country":"Russian Federation","imageUrl":"img/user_photo_male.png","rating":3.54,"tasksSolved":12},
            {"id":100002,"username":"beast","password":"test","isEnabled":false,"isNonReadOnly":false,"creationDate":1421400237000,"email":"beast@mail.ru","roles":["USER"],"country":"United States of America(USA)","imageUrl":"img/user_photo_male.png","rating":2.45,"tasksSolved":22}
        ];

        this.TASKS = [
            {"id":"1","author":null,"tags":["foo","bar"],"authorityName":"admin1","creationDate":90874957109843,"type":1,"lifeStage":1,"taskDescription":null,"condition":"condition1","sourceCode":null,"tests":"tests1","rating":34.2,"votersAmount":0,"likedBy":[],"dislikedBy":[],"averageAttempts":0.0,"successfulAttempts":0,"failureAttempts":0},
            {"id":"2","author":null,"tags":["foo","bar"],"authorityName":"admin1","creationDate":90874957109843,"type":1,"lifeStage":1,"taskDescription":null,"condition":"condition1","sourceCode":null,"tests":"tests1","rating":34.2,"votersAmount":0,"likedBy":[],"dislikedBy":[],"averageAttempts":0.0,"successfulAttempts":0,"failureAttempts":0},
            {"id":"3","author":null,"tags":["foo","bar"],"authorityName":"admin1","creationDate":90874957109843,"type":1,"lifeStage":1,"taskDescription":null,"condition":"condition1","sourceCode":null,"tests":"tests1","rating":34.2,"votersAmount":0,"likedBy":[],"dislikedBy":[],"averageAttempts":0.0,"successfulAttempts":0,"failureAttempts":0}
        ];

        this.TASKS_OBJECT = { content: this.TASKS, total: this.TASKS.length, pageable: { page: 0 } };
        this.USERS_OBJECT = { content: this.USERS, total: this.USERS.length, pageable: { page: 0} };

        this.CURRENT_USER = {
            "id":100000,
            "username":"alex",
            "password":"$2a$10$zoEDFVz0ANfe6nNPBgmBq.ngG3l09wMhRxNLT0WuBGKsSA/rrAOCe",
            "isEnabled":true,
            "isNonReadOnly":true,
            "creationDate":1425717499000,
            "email":"alex@mail.ru",
            "roles":["ADMIN"],
            "country":"Russian Federation",
            "imageUrl":"img/user_photo_male.png",
            "rating":12.56,
            "tasksSolved":45,
            "signInProvider":null,
            "tasks":[]
        };

        this.MESSAGES = [
            {
                "name": "successful_reg",
                "text": "Регистрация прошла успешно. Войдите на сайт используя ваш логин и пароль."
            },
            {
                "name": "server_unavailable",
                "text": "Не удалось получить ответ от сервера."
            },
            {
                "name": "incorrect_current_password",
                "text": "Текущий пароль введен неправильно."
            },
            {
                "name": "empty_new_password",
                "text": "Новый пароль не должен быть пустым."
            },
            {
                "name": "image_required",
                "text": "Необходимо добавить файл с изображением."
            },
            {
                "name": "unknown_error",
                "text": "Произошла ошибка."
            },
            {
                "name": "tasks_solved",
                "text": "Решенные задачи."
            },
            {
                "name": "tasks_added",
                "text": "Добавленные задачи."
            },
            {
                "name": "successful_task_delete",
                "text": "Задача успешно удалена."
            },
            {
                "name": "task_not_found",
                "text": "Задачи с таким id не существует."
            },
            {
                "name": "task_verification",
                "text": "Задача ожидает проверки модератором."
            },
            {
                "name": "feedback_success",
                "text": "Сообщение успешно отправлено!"
            },
            {
                "name": "feedback_error",
                "text": "Произошла ошибка при отправке сообщения."
            },
            {
                "name": "incorrect_img_format",
                "text": "Должен быть файл в формате .jpg, .jpeg, .png или .gif."
            },
            {
                "name": "started_password_recovery",
                "text": "Дальнейшие инструкции по получению нового пароля были отправлены на указанный адрес."
            }
        ]
    };

    // get the instance you want to export
    var constants = new TestConstants();

    // if module.export is available ( NodeJS? ) go for it,
    // otherwise append it to the global object
    if (module && module.exports) {
        module.exports = constants;
    } else {
        window.constants = constants;
    }

})();