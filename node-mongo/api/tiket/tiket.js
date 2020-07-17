const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Tiket = require("./m-tiket");

router.get("/", (req, res, next) => {
    Tiket.find()
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                tiket: docs.map(doc => {
                    return {
                        _id: doc._id,
                        userId: doc.user,
                        jmlTiket: doc.jmlTiket
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
    Tiket.findOne({
            user: mongooose.Types.ObjectId(userId)
        })
        .exec()
        .then((doc) => {
            console.log("From database", doc);
            if (!doc) {
                return res.status(404).json({
                    message: "Data Tiket tidak ditemukan!",
                });
            } else {
                res.status(200).json({
                    message: "Data ditemukan!",
                    _id: doc._id,
                    userId: doc.user,
                    jmlTiket: doc.jmlTiket
                });
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(404).json({
                message: "Data Tiket tidak ditemukan!",
            });
        });
});

router.patch("/:user", (req, res, next) => {
    const id = req.params.user;

    Tiket.findOne({
            user: mongooose.Types.ObjectId(id)
        })
        .exec()
        .then(result => {
            console.log(result);

            Tiket.updateMany({
                    user: id
                }, {
                    $set: req.body
                })
                .exec()
                .then(doc => {
                    res.status(200).json({
                        message: "Data berhasil di Update!"
                    })
                })
                .catch(err => {
                    console.log(err);
                    res.status(500).json({
                        error: err
                    })
                })
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            })
        })
})

router.post("/", (req, res, next) => {
    const tiket = new Tiket({
        _id: new mongooose.Types.ObjectId(),
        user: mongooose.Types.ObjectId(req.body.user),
        jmlTiket: req.body.jmlTiket
    })

    tiket.save()
        .then(doc => {
            console.log(doc);
            res.status(200).json({
                message: "Tabungan Tiket berhasil dibuat!",
                idTabungan: doc._id,
                idUser: doc.user,
                jmlTiket: doc.jmlTiket
            })
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        })

});

router.delete("/:user", (req, res, next) => {
    const user = req.params.user;
    tiket.deleteMany({
            user: user
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Tabungan Tiket terhapus"
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