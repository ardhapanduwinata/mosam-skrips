const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const JalaniMisi = require("./m-jalanimisi");

router.get("/", (req, res, next) => {
    JalaniMisi.find().populate("misi user")
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                jalanimisi: docs.map(doc => {
                    return {
                        user: doc.user,
                        misi: doc.misi,
                        targettercapai: doc.targettercapai,
                        status: doc.status
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
    const user = req.params.user;
    JalaniMisi.find({
            user: user
        })
        .populate("misi user")
        .exec()
        .then(doc => {
            console.log("from database ", doc);
            const response = {
                count: doc.length,
                TansaksiNabung: doc.map(doc => {
                    return {
                        user: doc.user,
                        misi: doc.misi.detailmisi,
                        targettercapai: doc.targettercapai,
                        status: doc.status
                    }
                })
            }
            res.status(200).json(response);
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

    JalaniMisi.findOne({
            _id: id
        })
        .exec()
        .then(result => {
            console.log(result);

            JalaniMisi.updateMany({
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
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            })
        })
})

router.post("/", (req, res, next) => {
    const jalanimisi = new JalaniMisi({
        misi: req.body.misi,
        user: req.body.user,
        targettercapai: req.body.targettercapai,
        status: req.body.status
    })

    jalanimisi.save()
        .then(doc => {
            console.log(doc);
            res.status(200).json({
                message: "Misi baru berhasil dibuat!",
                misi: doc.misi,
                user: doc.user,
                targettercapai: doc.targettercapai,
                status: doc.status
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
    const user = req.params.id;
    JalaniMisi.deleteMany({
            user: user
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Misi milik user tersebut telah terhapus"
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