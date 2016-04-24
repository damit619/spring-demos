CREATE TABLE `userconnection` (
	`userId` VARCHAR(255) NOT NULL,
	`providerId` VARCHAR(255) NOT NULL,
	`providerUserId` VARCHAR(255) NOT NULL DEFAULT '',
	`rank` INT(11) NOT NULL,
	`displayName` VARCHAR(255) NULL DEFAULT NULL,
	`profileUrl` VARCHAR(512) NULL DEFAULT NULL,
	`imageUrl` VARCHAR(512) NULL DEFAULT NULL,
	`accessToken` VARCHAR(255) NOT NULL,
	`secret` VARCHAR(255) NULL DEFAULT NULL,
	`refreshToken` VARCHAR(255) NULL DEFAULT NULL,
	`expireTime` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`userId`, `providerId`, `providerUserId`),
	UNIQUE INDEX `UserConnectionRank` (`userId`, `providerId`, `rank`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;
