const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Nabung = require("./m-transaksiNabung");

router.post("/", (req, res, next) => {
    const nabung = new transaksiNabung({
        _id: new mongooose.Types.ObjectId(),
        user: req.body.user,
        bank: req.body.bank,
        date: req.body.date,
        jmlbotolA: req.body.jmlbotolA,
        jmlbotolB: req.body.jmlbotolB,
        jmlgelas: req.body.jmlgelas
    })

    User.findOne({
            user: user,
        })
        .exec()
        .then((doc) => {
            console.log("From database", doc);
            if (!doc) {
                return res.status(404).json({
                    message: "User tidak ditemukan!",
                });
            } else {
                nabung.save()
                    .then(result => {
                        console.log(result);
                        res.status(201).json({
                            _id: result._id,
                            user: result.user,
                            bank: result.bank,
                            date: result.date,
                            jmlbotolA: result.jmlbotolA,
                            jmlbotolB: result.jmlbotolB,
                            jmlgelas: result.jmlgelas
                        })
                    })
                    .catch(err => {
                        res.status(500).json({
                            error: err
                        })
                    })
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});

router.get("/:user", (req, res, next) => {
    const user = req.params.user;
    User.find({
            user: user
        })
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