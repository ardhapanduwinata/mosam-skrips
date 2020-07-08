const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const User = require("./m-user");

router.get("/", (req, res, next) => {
    User.find()
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                user: docs.map(doc => {
                    return {
                        email: doc.email,
                        name: doc.name,
                        password: doc.password,
                        level: doc.level,
                        id: doc._id
                    }
                })
            }
            res.status(200).json(response);
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                erros: err,
            });
        });
});

router.post("/", (req, res, next) => {
    const user = new User({
        _id: new mongooose.Types.ObjectId(),
        email: req.body.email,
        name: req.body.name,
        password: req.body.password,
        level: req.body.level
    });
    User.findOne({
            email: req.body.email
        })
        .exec()
        .then(result => {
            console.log(result);
            if (!result) {
                user.save()
                    .then(result => {
                        res.status(201).json({
                            message: "Anda berhasil terdaftar, silahkan login terlebih dahulu",
                            email: result.email,
                            name: result.name,
                            id: result._id,
                            level: result.level,
                        })
                    })
            } else {
                res.json({
                    message: "Email telah dipakai! Silahkan menggunakan email lain"
                })
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                error: err,
            });
        });
});

router.delete("/:idUser", (req, res, next) => {
    const id = req.params.idUser;
    User.deleteOne({
            _id: id
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "User deleted",
                request: {
                    type: "POST",
                    url: "http://localhost:3000/register/",
                    body: {
                        email: "String",
                        name: "String",
                        password: "String"
                    }
                }
            })
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                erros: err,
            });
        })
});

module.exports = router;