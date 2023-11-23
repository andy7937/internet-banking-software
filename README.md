This repository is for a practice online banking software called FinSecure. Inspiration for the website is similar to westpac online banking website.

git push --set-upstream origin nameofbranch


Sources used in project:

inspiration for website
https://www.westpac.co.nz/personal/ways-to-bank/westpac-one-online-banking/

dashboard template
https://codepen.io/havardob/pen/ExvwGBr

home template
https://www.w3schools.com/w3css/tryit.asp?filename=tryw3css_templates_interior_design&stacked=h

fin logo
https://www.freepik.com/premium-vector/shark-fin-shield-logo_29525401.htm

bank image
https://unsplash.com/photos/grey-concrete-building-2_K82gx9Uk8

How to open the website on localhost
http://localhost:8080/home

testing account tranfers postman
https://blue-equinox-247912.postman.co/workspace/Team-Workspace~3b1ca28c-7f14-4b4b-8673-3ab50344c2c0/request/30509100-a22686d3-9d20-44d5-b4ec-03d80eec3f31


RUN USING THIS CODE
mvn spring-boot:run








learning points in project:
hashing passwords to make sure passwords are secure
stored by using BCrypt, adding the salt in the front, and adding password hashed in the back
when you want to verify the password, get the password from the db, get the salt, and then verify
by hashing the password entered, compare that with the password on the db without salt

storing on database
storing user data on mySql, how to make new tables with the required information
t
how to user html files and start projects on github 
