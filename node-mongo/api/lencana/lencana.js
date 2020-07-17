const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Lencana = require("./m-lencana");
const Nabung = require("../transaksi_nabung/nabung");

router.get("/", (req, res, next) => {
    Lencana.find().populate("user")
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                poin: docs.map(doc => {
                    return {
                        _id: doc._id,
                        _id: doc._id,
                        user: doc.user,
                        jmltransaksibotolA: doc.jmltransaksibotolA,
                        jmltransaksibotolB: doc.jmltransaksibotolB,
                        jmltransaksigelas: doc.jmltransaksigelas,
                        badge: doc.badge
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
    Lencana.findOne({
            user: userId
        }).populate("user")
        .exec()
        .then((doc) => {
            console.log("From database", doc);
            if (!doc) {
                return res.status(404).json({
                    message: "Data Lencana tidak ditemukan!",
                });
            } else {
                res.status(200).json({
                    message: "Data ditemukan!",
                    _id: doc._id,
                    user: doc.user,
                    jmltransaksibotolA: doc.jmltransaksibotolA,
                    jmltransaksibotolB: doc.jmltransaksibotolB,
                    jmltransaksigelas: doc.jmltransaksigelas,
                    badge: doc.badge
                });
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(404).json({
                message: "Data Lencana tidak ditemukan!",
            });
        });
});

router.patch("/:user", (req, res, next) => {
    const id = req.params.user;

    Lencana.updateOne({
            user: mongooose.Types.ObjectId(id)
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

router.post("/", (req, res, next) => {
    const lencana = new Lencana({
        _id: new mongooose.Types.ObjectId(),
        user: mongooose.Types.ObjectId(req.body.user)
    })

    lencana.save()
        .then(doc => {
            console.log(doc);
            res.status(200).json({
                message: "Lencana user tersebut berhasil dibuat!",
                user: doc.user,
                jmltransaksibotolA: doc.jmltransaksibotolA,
                jmltransaksibotolB: doc.jmltransaksibotolB,
                jmltransaksigelas: doc.jmltransaksigelas,
                badge: doc.badge
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
    Lencana.deleteOne({
            user: user
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Lencana user terhapus"
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