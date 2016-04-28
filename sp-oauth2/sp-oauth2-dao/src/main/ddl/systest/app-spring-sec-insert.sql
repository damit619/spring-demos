INSERT INTO `roles` (`id`, `role_name`) VALUES
	(1, 'ROLE_API');
/
INSERT INTO `roles` (`id`, `role_name`) VALUES
	(2, 'ROLE_POST');
/
INSERT INTO `roles` (`id`, `role_name`) VALUES
	(3, 'ROLE_CREATE_USER');
/
INSERT INTO `app_users` (`id`, `contact_id`, `email`, `first_name`, `is_active`, `last_name`, `password`, `username`, `version`) VALUES
	(1, 'N/A', 'raghu@abc.com', NULL, b'1', NULL, '$2a$10$s0HI6h1pqVKjBsmPozdIrePzNGZIbqd8eaEMUI8cNleMiWBUVFIM2', 'appapi', 0);
/
INSERT INTO `app_users` (`id`, `contact_id`, `email`, `first_name`, `is_active`, `last_name`, `password`, `username`, `version`) VALUES
	(2, 'N/A', 'sumit.vasudeva@abc.com', NULL, b'1', NULL, '$2a$10$MZ2Ojhq4OqQxeHActnCbR.dVNLQzS9vE9riUXGsUChk2/ZZ5wiOCi', 'test', 0);
/
INSERT INTO `app_users` (`id`, `contact_id`, `email`, `first_name`, `is_active`, `last_name`, `password`, `username`, `version`) VALUES
	(3, 'N/A', 'rachit.b@abc.com', NULL, b'1', NULL, '$2a$10$L0/p5M8Tln4mtl3Ud6DvXunRAZNachE1bsmx3HxvPfvqJ1mGhwa3W', 'dcapi', 0);
/
INSERT INTO `app_users_roles` (`user_id`, `role_id`) VALUES
	(1, 1);
/
INSERT INTO `app_users_roles` (`user_id`, `role_id`) VALUES
	(2, 1);
/
INSERT INTO `app_users_roles` (`user_id`, `role_id`) VALUES
	(3, 2);
/
INSERT INTO `app_users_roles` (`user_id`, `role_id`) VALUES
	(3, 3);