const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Misi = require("./m-misi");

router.get("/", (req, res, next) => {
    Misi.find()
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                misi: docs.map(doc => {
                    return {
                        _id: doc._id,
                        detailmisi: doc.detailmisi,
                        targetcapaian: doc.targetcapaian,
                        jumlahpoin: doc.jumlahpoin,
                        tabeldibutuhkan: doc.tabeldibutuhkan
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

router.get("/:id", (req, res, next) => {
    const id = req.params.id;
    Misi.findOne({
            _id: id
        })
        .exec()
        .then((doc) => {
            console.log("From database", doc);
            if (!doc) {
                return res.status(404).json({
                    message: "Data Misi tidak ditemukan!",
                });
            } else {
                res.status(200).json({
                    message: "Data ditemukan!",
                    _id: doc._id,
                    detailmisi: doc.detailmisi,
                    targetcapaian: doc.targetcapaian,
                    jumlahpoin: doc.jumlahpoin,
                    tabeldibutuhkan: doc.tabeldibutuhkan
                });
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(404).json({
                message: "Data Misi tidak ditemukan!",
            });
        });
});

router.patch("/:id", (req, res, next) => {
    const id = req.params.id;
    Misi.updateOne({
            _id: id
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
    const misi = new Misi({
        _id: new mongooose.Types.ObjectId(),
        detailmisi: req.body.detailmisi,
        targetcapaian: req.body.targetcapaian,
        jumlahpoin: req.body.jumlahpoin,
        tabeldibutuhkan: req.body.tabeldibutuhkan
    })

    misi.save()
        .then(doc => {
            console.log(doc);
            res.status(200).json({
                message: "Misi baru berhasil dibuat!",
                _id: doc._id,
                detailmisi: doc.detailmisi,
                targetcapaian: doc.targetcapaian,
                jumlahpoin: doc.jumlahpoin,
                tabeldibutuhkan: doc.tabeldibutuhkan
            })
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        })

});

router.delete("/:id", (req, res, next) => {
    const id = req.params.id;
    Misi.deleteOne({
            _id: id
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Misi telah terhapus"
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