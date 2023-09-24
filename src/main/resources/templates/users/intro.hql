INSERT INTO users (username, name, surname, age, password)
VALUES ('AAAAA', 'Alex', 'Freeman', 30, '123456');


INSERT INTO roles (role)
VALUES
    ('ROLE_USER'),
    ('ROLE_ADMIN');


INSERT INTO users (username, name, surname, age, password)
VALUES
    ('новый_пользователь', 'Имя', 'Фамилия', 25, 'пароль');



-- ID пользователя, которому вы хотите назначить роли
DECLARE @userId INT
SET @userId = (SELECT id FROM users WHERE username = 'новый_пользователь')

-- ID роли 'ROLE_USER'
    DECLARE @userRoleId INT
SET @userRoleId = (SELECT id FROM roles WHERE role = 'ROLE_USER')

-- ID роли 'ROLE_ADMIN'
    DECLARE @adminRoleId INT
SET @adminRoleId = (SELECT id FROM roles WHERE role = 'ROLE_ADMIN')

-- Назначить роли пользователю
INSERT INTO users_roles (user_id, role_id)
VALUES
    (@userId, @userRoleId),
    (@userId, @adminRoleId);







