const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const User = require("./m-user");

router.post("/", (req, res, next) => {
  const email = req.body.email;
  User.findOne({
      email: email,
    })
    .exec()
    .then((doc) => {
      console.log("From database", doc);
      if (!doc) {
        return res.status(404).json({
          message: "Email anda salah",
        });
      } else {
        if (req.body.password != doc.password) {
          return res.status(500).json({
            message: "Password anda salah",
          });
        } else {
          res.status(200).json({
            message: "Anda berhasil masuk",
            email: doc.email,
            name: doc.name,
            level: doc.level,
            _id: doc._id
          });
        }
      }
    })
    .catch((err) => {
      console.log(err);
      res.status(404).json({
        message: "Email anda salah",
      });
      res.status(404).json({
        message: "Email anda salah",
      });
    });
});

router.get("/:userId", (req, res, next) => {
  const id = req.params.userId;
  User.findById(id)
    .select("_id email name")
    .exec()
    .then(doc => {
      console.log("from database ", doc);
      if (doc) {
        res.status(200).json({
          _id: doc._id,
          name: doc.name,
          email: doc.email,
          level: doc.level
        });
      } else {
        res.status(404).json({
          message: "no user found"
        });
      }
    })
    .catch(err => {
      console.log(err);
      res.status(500).json({
        error: err
      });
    })
})

module.exports = router;