BANGALORE SMART CITY GUIDE — JAVA SWING + MYSQL

REQUIREMENTS
1. JDK 8 or newer (JDK 8 supports this source; MySQL Connector/J version must also support your Java version).
2. MySQL Server and MySQL Workbench (or another MySQL client).
3. MySQL Connector/J JDBC driver JAR.

DATABASE SETUP
1. Start MySQL.
2. Open smart_city.sql in MySQL Workbench and execute the full script.
3. Open DBConnection.java.
4. Change PASSWORD = "YOUR_MYSQL_PASSWORD" to your local MySQL root password.
   If your MySQL account has no password, use an empty string: "".
5. Add the MySQL Connector/J JAR to your project's classpath/libraries.

COMPILE AND RUN (command line)
Put all .java files in the same folder and place mysql-connector-j-<version>.jar there.
Windows example (replace the JAR name with your actual file):

javac -cp ".;mysql-connector-j-<version>.jar" *.java
java -cp ".;mysql-connector-j-<version>.jar" BCityUI

In IntelliJ/Eclipse/NetBeans, add the connector JAR as a project library, then run BCityUI.java.

HOW TO TEST
1. Run smart_city.sql.
2. Set your database password and add the JDBC driver.
3. Run BCityUI.java.
4. Select Sign Up and create an account.
5. Log in using the account you created.
6. Open Tourist, Hotels or Transport. Information is read from the city_info table.
7. Open Map and search for any place.

NOTES
- The city_info rows are sample project data. Verify details before using them in a real service.
- This is a student/demo implementation. SHA-256 is used to keep the example dependency-light, but plain SHA-256 is not appropriate for production password storage. Use Argon2id, bcrypt, or PBKDF2 with a unique salt in a real application.
- Google Maps requires an internet connection. The rest of the application needs the local MySQL server.
