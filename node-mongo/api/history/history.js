const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Histori = require("./m-history");

router.get("/", (req, res, next) => {
    Histori.find().populate("transaksiNabung transaksiTukarTiket transaksiNaikBis")
        .exec()
        .then((docs) => {
            const response = {
                count: docs.length,
                historiUser: docs.map((doc) => {
                    return {
                        user: doc.user,
                        transaksiNabung: doc.transaksiNabung,
                        transaksiTukarTiket: doc.transaksiTukarTiket,
                        transaksiNaikBis: doc.transaksiNaikBis,
                        namaTransaksi: doc.namaTransaksi
                    };
                }),
            };
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
    const histori = new Histori({
        user: mongooose.Types.ObjectId(req.body.user),
        namaTransaksi: req.body.namaTransaksi,
        date: req.body.date
    });

    histori
        .save()
        .then((result) => {
            res.status(200).json({
                message: "Data Histori telah ditambahkan!"
            })
        })
        .catch((err) => {
            res.status(500).json({
                error: err,
                iniErrorKe: 2,
            });
        });
});

router.get("/:user", (req, res, next) => {
    const user = mongooose.Types.ObjectId(req.params.user);
    Histori.find({
            user: user,
        }).populate("transaksiNabung transaksiTukarTiket transaksiNaikBis")
        .exec()
        .then((doc) => {
            console.log("from database ", doc);
            const response = {
                count: doc.length,
                transaksiUser: doc.map((doc) => {
                    return {
                        user: doc.user,
                        namaTransaksi: doc.namaTransaksi,
                        date: doc.date
                    };
                }),
            };
            res.status(200).json(response);
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                error: err,
            });
        });
});

router.delete("/:user", (req, res, next) => {
    const user = req.params.user;
    Histori.deleteMany({
            user: mongooose.Types.ObjectId(user),
        })
        .exec()
        .then((result) => {
            res.status(200).json({
                message: "Histori User tersebut telah dihapus",
            });
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                erros: err,
            });
        });
});

module.exports = router;