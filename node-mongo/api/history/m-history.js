const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true,
    },
    transaksiNabung: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "transaksiNabung"
    },
    transaksiTukarTiket: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "transaksiTukarTiket"
    },
    transaksiNaikBis: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "transaksiNaikBis"
    },
    namaTransaksi: {
        type: String,
    },
    date: {
        type: String
    }
});

module.exports = mongoose.model('Histori', userSchema);