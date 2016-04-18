create or replace
PROCEDURE getUsers ( 
	userNameParam IN VARCHAR2,
	cursorParam OUT SYS_REFCURSOR)
IS
BEGIN

  OPEN cursorParam FOR
  SELECT * FROM USERS WHERE USERS.FIRST_NAME = userNameParam;

END;