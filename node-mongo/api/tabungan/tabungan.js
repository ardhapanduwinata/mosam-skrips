const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Tabungan = require("./m-tabungan");

router.get("/", (req, res, next) => {
    Tabungan.find()
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                tabungan: docs.map(doc => {
                    return {
                        _id: doc._id,
                        userId: doc.user,
                        jmlbotolA: doc.jmlbotolA,
                        jmlbotolB: doc.jmlbotolB,
                        jmlgelas: doc.jmlgelas
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

router.get("/:user", (req, res, next) => {
    const userId = req.params.user;
    Tabungan.findOne({
            user: userId
        })
        .exec()
        .then((doc) => {
            console.log("From database", doc);
            if (!doc) {
                return res.status(404).json({
                    message: "Tabungan tidak ditemukan!",
                });
            } else {
                res.status(200).json({
                    message: "Data ditemukan!",
                    _id: doc._id,
                    userId: doc.user,
                    jmlbotolA: doc.jmlbotolA,
                    jmlbotolB: doc.jmlbotolB,
                    gelas: doc.gelas
                });
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(404).json({
                message: "Tabungan tidak ditemukan!",
            });
        });
});

router.post("/", (req, res, next) => {
    const tabungan = new Tabungan({
        _id: new mongooose.Types.ObjectId(),
        user: req.body.user,
        jmlbotolA: req.body.jmlbotolA,
        jmlbotolB: req.body.jmlbotolB,
        jmlgelas: req.body.jmlgelas
    })

    tabungan.save()
        .then(doc => {
            console.log(doc);
            res.status(200).json({
                message: "Tabungan berhasil dibuat!",
                idTabungan: doc._id,
                idUser: doc.user,
                jmlbotolA: doc.jmlbotolA,
                jmlbotolB: doc.jmlbotolB,
                jmlgelas: doc.jmlgelas
            })
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        })

});

router.delete("/:idUser", (req, res, next) => {
    const user = req.params.idUser;
    Tabungan.deleteOne({
            user: user
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Tabungan terhapus"
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