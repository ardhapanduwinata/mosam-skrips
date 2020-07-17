const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Nabung = require("./m-transaksiNabung");
const Tabungan = require("../tabungan/m-tabungan")

router.get("/", (req, res, next) => {
    Nabung.find()
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                TransaksiNabung: docs.map(doc => {
                    return {
                        _id: doc._id,
                        bank: doc.bank,
                        user: doc.user,
                        date: doc.date,
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

router.post("/", (req, res, next) => {

    const nabung = new Nabung({
        _id: new mongooose.Types.ObjectId(),
        user: mongooose.Types.ObjectId(req.body.user),
        bank: mongooose.Types.ObjectId(req.body.bank),
        date: req.body.date,
        jmlbotolA: req.body.jmlbotolA,
        jmlbotolB: req.body.jmlbotolB,
        jmlgelas: req.body.jmlgelas
    })

    nabung.save()
        .then(result => {
            Tabungan.findOne({
                    user: req.body.user
                })
                .exec()
                .then(doc => {
                    const jmlbar = {
                        jmlbotolA: result.jmlbotolA + doc.jmlbotolA,
                        jmlbotolB: result.jmlbotolB + doc.jmlbotolB,
                        jmlgelas: result.jmlgelas + doc.jmlgelas
                    }

                    Tabungan.updateMany({
                        user: req.body.user
                    }, {
                        $set: jmlbar
                    }).then(hasil => {
                        console.log(hasil);
                        res.status(200).json({
                            message: "Data tabungan telah ditambahkan!",
                            date: req.body.date,
                            jmlbotolA: req.body.jmlbotolA,
                            jmlbotolB: req.body.jmlbotolB,
                            jmlgelas: req.body.jmlgelas
                        })
                    }).catch(err => res.status(500).json({
                        error: err,
                        iniErrorKe: 1
                    }))
                }).catch(err => {
                    res.status(500).json({
                        error: err,
                        iniErrorKe: 2
                    })
                })
        })
        .catch(err => {
            res.status(500).json({
                error: err,
                iniErrorKe: 3
            })
        })
});

router.get("/:user", (req, res, next) => {
    const user = req.params.user;
    Nabung.find().where({
            user: mongooose.Types.ObjectId(user)
        })
        .then(doc => {
            console.log("from database ", doc);
            const response = {
                count: doc.length,
                TransaksiNabung: doc.map(doc => {
                    return {
                        _id: doc._id,
                        bank: doc.bank,
                        user: doc.user,
                        date: doc.date,
                        jmlbotolA: doc.jmlbotolA,
                        jmlbotolB: doc.jmlbotolB,
                        jmlgelas: doc.jmlgelas
                    }
                })
            }
            res.status(200).json(response);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
})

router.delete("/:user", (req, res, next) => {
    const id = req.params.user;
    Nabung.deleteMany({
            user: mongooose.Types.ObjectId(id)
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Tansaksi Nabung user tersebut terhapus"
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