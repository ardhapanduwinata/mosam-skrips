const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Poin = require("./m-poin");

router.get("/", (req, res, next) => {
    Poin.find()
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                poin: docs.map(doc => {
                    return {
                        _id: doc._id,
                        userId: doc.user,
                        jmlPoin: doc.jmlPoin,
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
    Poin.findOne({
            user: userId
        })
        .exec()
        .then((doc) => {
            // console.log("From database", doc);
            if (!doc) {
                return res.status(404).json({
                    message: "Data Poin tidak ditemukan!",
                });
            } else {
                res.status(200).json({
                    message: "Data ditemukan!",
                    _id: doc._id,
                    userId: doc.user,
                    jmlPoin: doc.jmlPoin,
                    jmlVoucher: doc.jmlVoucher,
                });
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(404).json({
                message: "Data Voucher tidak ditemukan!",
            });
        });
});

router.patch("/:user", (req, res, next) => {
    const id = req.params.user;

    Poin.updateOne({
            user: mongooose.Types.ObjectId(id)
        }, {
            $set: req.body
        })
        .exec()
        .then(doc => {
            res.status(200).json({
                message: "Data berhasil di Update!",
                jmlPoin: req.body.jmlPoin
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
    const poin = new Poin({
        _id: new mongooose.Types.ObjectId(),
        user: mongooose.Types.ObjectId(req.body.user),
        jmlPoin: req.body.jmlPoin
    })

    poin.save()
        .then(doc => {
            console.log(doc);
            res.status(200).json({
                message: "Tabungan Poin berhasil dibuat!",
                idTabungan: doc._id,
                idUser: doc.user,
                jmlPoin: doc.jmlPoin
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
    Poin.deleteOne({
            user: user
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Tabungan Poin terhapus"
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