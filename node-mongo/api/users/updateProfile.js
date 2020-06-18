const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const User = require("./m-user");

router.patch("/:userId", (req, res, next) => {
    const id = req.params.userId;

    User.findOne({
            email: req.body.email
        })
        .exec()
        .then((result) => {
            console.log(result);
            if (!result) {
                User.updateMany({
                        _id: id
                    }, {
                        $set: req.body
                    })
                    .exec().then((result) => {
                        res.status(200).json({
                            message: "Profile updated",
                            request: "GET",
                            url: "http://localhost:3000/login/" + id,
                            name: req.body.name,
                            email: req.body.email
                        });
                    }).catch((err) => {
                        console.log(err);
                        res.status(500).json({
                            // error: err,
                        });
                    });
            } else {
                res.json({
                    message: "Email telah digunakan! Silahkan gunakan email lain"
                });
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                // error: err,
            });
        });
});

module.exports = router;