--liquibase formatted sql

--changeset Tharuka:1
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

--changeset Tharuka:2
 INSERT INTO product(id,no_of_units_per_carton,name,carton_cost)
            VALUES
            (1,20,'Penguin-ears',175),
            (2,5,'Horseshoe',825);

--changeset Tharuka:3
INSERT INTO `users` (`id`, `email`, `password`, `username`) VALUES (1, 'admin@gmail.com', '$2a$10$wlMBgSZ8B/qNglg.8XcsYuXOGsEqJ7rQQrQKkeXmc4dVKTR.aOy72', 'admin');
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES (1, 3);


