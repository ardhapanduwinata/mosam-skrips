const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    detailmisi: {
        type: String,
        require: true
    },
    targetcapaian: {
        type: Number,
        require: true
    },
    jumlahpoin: {
        type: Number,
        require: true
    },
    tabeldibutuhkan: {
        type: String,
        require: true
    }
});

module.exports = mongoose.model('Misi', userSchema);