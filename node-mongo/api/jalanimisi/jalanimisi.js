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
                        status: doc.status,
                        dateselesai: doc.dateselesai,
                        claimpoin: doc.claimpoin
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
    JalaniMisi.find({
            user: mongooose.Types.ObjectId(req.params.user)
        })
        .populate("misi user")
        .exec()
        .then(doc => {
            // console.log("from database ", doc);
            const response = {
                count: doc.length,
                misiUser: doc.map(doc => {
                    return {
                        user: doc.user,
                        misi: doc.misi,
                        targettercapai: doc.targettercapai,
                        status: doc.status,
                        dateselesai: doc.dateselesai,
                        claimpoin: doc.claimpoin
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

router.patch("/", (req, res, next) => {
    const idmisi = req.body.misi;
    const iduser = req.body.user;

    JalaniMisi.find().where({
            user: mongooose.Types.ObjectId(iduser)
        }).where({
            misi: mongooose.Types.ObjectId(idmisi)
        })
        .exec()
        .then(result => {
            // console.log(result);
            JalaniMisi.updateOne({
                    user: mongooose.Types.ObjectId(iduser),
                    misi: mongooose.Types.ObjectId(idmisi)
                }, {
                    $set: {
                        status: req.body.status,
                        claimpoin: req.body.claimpoin,
                        dateselesai: req.body.dateselesai
                    }
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
        misi: mongooose.Types.ObjectId(req.body.misi),
        user: mongooose.Types.ObjectId(req.body.user),
        targettercapai: req.body.targettercapai,
        status: req.body.status,
        claimpoin: req.body.claimpoin
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