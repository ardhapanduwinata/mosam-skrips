const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const NaikBis = require("./m-naikbis");

router.get("/", (req, res, next) => {
    NaikBis.find().populate("user terminal")
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                naikbis: docs.map(doc => {
                    return {
                        _id: doc._id,
                        user: doc.user,
                        date: doc.date,
                        terminal: doc.terminal
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
    NaikBis.find({
            user: mongooose.Types.ObjectId(userId)
        }).populate("user terminal")
        .exec()
        .then((doc) => {
            console.log("From database", doc);
            const response = {
                count: doc.length,
                naikbis: doc.map((doc) => {
                    return {
                        _id: doc._id,
                        user: doc.user,
                        date: doc.date,
                        terminal: doc.terminal
                    };
                }),
            };
            res.status(200).json(response);
        })
        .catch((err) => {
            console.log(err);
            res.status(404).json({
                message: "User tidak ditemukan!",
            });
        });
});

router.post("/", (req, res, next) => {
    const naikbis = new NaikBis({
        _id: new mongooose.Types.ObjectId(),
        user: mongooose.Types.ObjectId(req.body.user),
        date: req.body.date,
        terminal: mongooose.Types.ObjectId(req.body.terminal)
    })

    naikbis.save()
        .then(doc => {
            console.log(doc);
            res.status(200).json({
                message: "Data naik bis telah disimpan!"
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
    NaikBis.deleteMany({
            user: mongooose.Types.ObjectId(user)
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Data transaksi naik bis user tersebut terhapus"
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