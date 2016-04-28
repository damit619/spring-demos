CREATE TABLE `app_users` 
(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`contact_id` VARCHAR(50) NOT NULL,
	`email` VARCHAR(100) NOT NULL,
	`first_name` VARCHAR(100) NULL DEFAULT NULL,
	`is_active` BIT(1) NULL DEFAULT NULL,
	`last_name` VARCHAR(100) NULL DEFAULT NULL,
	`password` LONGTEXT NOT NULL,
	`username` VARCHAR(100) NOT NULL,
	`version` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `UK_f2ksd6h8hsjtd57ipfq9myr64` (`username`)
);
/
CREATE TABLE `roles` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`role_name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `UK_8sewwnpamngi6b1dwaa88askk` (`role_name`)
);
/
CREATE TABLE `app_users_roles` 
(
	`user_id` BIGINT(20) NOT NULL,
	`role_id` BIGINT(20) NOT NULL,
	INDEX `FK_llk49hmk0shei9slpvgt97eki` (`role_id`),
	INDEX `FK_qm53b1dbxij7s2flq3vw7ac3e` (`user_id`),
	CONSTRAINT `FK_qm53b1dbxij7s2flq3vw7ac3e` FOREIGN KEY (`user_id`) REFERENCES `app_users` (`id`),
	CONSTRAINT `FK_llk49hmk0shei9slpvgt97eki` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);
/
CREATE TABLE oauth_client_details 
(
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);
/
CREATE TABLE `oauth_access_token` (
	`token_id` VARCHAR(255) NOT NULL,
	`authentication` LONGBLOB NULL,
	`authentication_id` VARCHAR(255) NULL DEFAULT NULL,
	`client_id` VARCHAR(255) NULL DEFAULT NULL,
	`defaultOAuth2AccessToken` TINYBLOB NULL,
	`refresh_token` VARCHAR(255) NULL DEFAULT NULL,
	`token` LONGBLOB NULL,
	`user_name` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`token_id`)
);
/
CREATE TABLE `oauth_refresh_token` (
	`token_id` VARCHAR(255) NOT NULL,
	`authentication` LONGBLOB NULL,
	`token` LONGBLOB NULL,
	PRIMARY KEY (`token_id`)
);