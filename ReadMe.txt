Login/register has been implemented
validations are pending
only email if duplicate validation is done
password hashing not done


Registration creates an entry in the USER,ADDRESS,ROLE and the default role is customer (<userID> "CUSTOMER")
Login checks the credentials and lets you login


homepage has a menu with 3 buttons for the user roles:
customer,staff and manager
The user's role determines whether the buttons work or show an error

1 user with the manager role should be created
register a user then insert a record into the ROLE table with the same userID.

The ROLE table can have upto 3 records per user
<userID> "CUSTOMER"
<userID> "STAFF"
<UserID> "MANAGER"