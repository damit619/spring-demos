DECLARE 
  UsersCursor SYS_REFCURSOR;
  UsersTable USERS%ROWTYPE;
BEGIN
  
  GETUSERS(UsersCursor,'User1');
  
  LOOP
    
	FETCH UsersCursor INTO UsersTable;
	
    EXIT WHEN UsersCursor%NOTFOUND;
    dbms_output.put_line(UsersTable.FIRST_NAME);
  
  END LOOP;

  CLOSE UsersCursor;
  
END;

var r REFCURSOR;
execute JAVATIGER.GETUSERS(:r, 'User1');
print :r;