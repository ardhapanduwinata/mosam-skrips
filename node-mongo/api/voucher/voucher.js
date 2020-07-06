const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Voucher = require("./m-voucher");

router.get("/", (req, res, next) => {
    Voucher.find()
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                voucher: docs.map(doc => {
                    return {
                        _id: doc._id,
                        userId: doc.user,
                        jmlVoucher: doc.jmlVoucher
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
    Voucher.findOne({
            user: userId
        })
        .exec()
        .then((doc) => {
            console.log("From database", doc);
            if (!doc) {
                return res.status(404).json({
                    message: "Data Voucher tidak ditemukan!",
                });
            } else {
                res.status(200).json({
                    message: "Data ditemukan!",
                    _id: doc._id,
                    userId: doc.user,
                    jmlVoucher: doc.jmlVoucher
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

    Voucher.findOne({
            user: id
        })
        .exec()
        .then(result => {
            console.log(result);

            Voucher.updateMany({
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
    const voucher = new Voucher({
        _id: new mongooose.Types.ObjectId(),
        user: req.body.user,
        jmlVoucher: req.body.jmlVoucher
    })

    voucher.save()
        .then(doc => {
            console.log(doc);
            res.status(200).json({
                message: "Tabungan Voucher berhasil dibuat!",
                idTabungan: doc._id,
                idUser: doc.user,
                jmlVoucher: doc.jmlVoucher
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
    Voucher.deleteOne({
            user: user
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Tabungan Voucher terhapus"
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