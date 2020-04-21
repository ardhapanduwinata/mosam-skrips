const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const User = require("./m-user");

router.get("/", (req, res, next) => {
    const email = req.body.email;
    User.findOne({
            email: email
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
                    const response = {
                        email: doc.email,
                        name: doc.name,
                    }
                    res.status(200).json({
                        message: "Anda berhasil masuk",
                        user: response
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

module.exports = router;