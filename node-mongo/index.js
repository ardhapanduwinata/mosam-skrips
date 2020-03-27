//import package
console.log("halo ini harusnya jalan paling awal!");

var mongodb = require("mongodb");
var ObjectID = mongodb.ObjectID;
var crypto = require("crypto");
var express = require("express");
var bodyParser = require("body-parser");

//kebutuhan password
//membuat fungsi untuk random salt

var genRandomString = function(length) {
  return crypto
    .randomBytes(Math.ceil(length / 2))
    .toString("hex") /* convert ke format hexa */
    .slice(0, length);
};

var sha512 = function(password, salt) {
  var hash = crypto.createHmac("sha512", salt);
  hash.update(password);
  var value = hash.digest("hex");
  return {
    salt: salt,
    passwordHash: value
  };
};

function saltHashPassword(userPassword) {
  var salt = genRandomString(16); //membuat 16 karakter random
  var passwordData = sha512(userPassword, salt);
  return passwordData;
}

function checkHashPassword(userPassword, salt) {
  var passwordData = sha512(userPassword, salt);
  return passwordData;
}

//membuat service express
var app = express();
app.use(bodyParser.json());
app.use(
  bodyParser.urlencoded({
    extended: true
  })
);

//membuat client mongodb
var MongoClient = mongodb.MongoClient;

//koneksi URL
var url = "mongodb://localhost:27017";

MongoClient.connect(
  url,
  {
    useNewUrlParser: true
  },
  function(err, client) {
    if (err) console.log("Unable to connect to the mongoDB server.Error", err);
    else {
      //login
      app.post("/login", (request, response, next) => {
        var post_data = request.body;
        console.log("ini lewat req body");

        var email = post_data.email;
        console.log("ini lewat email");
        var userPassword = post_data.password;
        console.log("ini lewat password");

        var db = client.db("belajarnodemongo");
        console.log("ini masuk db");

        //mengecek adanya email
        db.collection("user")
          .find({
            email: email
          })
          .count(function(err, number) {
            console.log("ini harusnya ngecek email di db");
            console.log("ini harusnya " + number);
            console.log("emailnya apa? " + email);
            if (number === 0) {
              console.log("disini kalau email gak ketemu");
              response.send("Email not exists");
              console.log("Email not exists");
            } else {
              //memasukkan data
              console.log("disini masuk else kalo emailnya masuk");
              db.collection("user").findOne({}, { email: email }, function(
                err,
                user
              ) {
                console.log("ini harusnya NEMU email di db");
                var salt = user.salt; //mendapatkan salt user
                var hashed_password = checkHashPassword(userPassword, salt)
                  .passwordHash; //hash password dengan menggunakan salt
                var encrypted_password = user.password; //mendapatkan password dari user
                if (hashed_password == encrypted_password) {
                  response.send("Login Success");
                  console.log("Login Success");
                } else {
                  response.send("Wrong Password");
                  console.log("Wrong Password");
                }
              });
            }
          });
        db.close;
      });

      //register
      app.post("/register", (request, response, next) => {
        var post_data = request.body;

        var plaint_password = post_data.password;
        var hash_data = saltHashPassword(plaint_password);

        var password = hash_data.passwordHash; //menyimpan password hash
        var salt = hash_data.salt; //menyimpan salt

        var name = post_data.name;
        var email = post_data.email;

        var insertJson = {
          email: email,
          password: password,
          salt: salt,
          name: name
        };
        var db = client.db("belajarnodemongo");

        //mengecek adanya email
        db.collection("user")
          .find({
            email: email
          })
          .count(function(err, number) {
            if (number != 0) {
              response.json("Email already exists");
              console.log("Email already exists");
            } else {
              //memasukkan data
              db.collection("user").insertOne(insertJson, function(error, res) {
                response.json("Registration Success");
                console.log("Registration Success");
              });
            }
          });
      });

      //memulai web server
      app.listen(3000, () => {
        console.log(
          "Connected to MongoDB Server, Webservice Running on port 3000"
        );
      });
    }
  }
);
