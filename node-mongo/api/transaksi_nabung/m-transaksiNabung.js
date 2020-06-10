const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    idUser: {
        type: String,
        required: true,
    },
    idBank: {
        type: String,
        required: true,
    },
    date: {
        type: String,
        required: true
    },
    jumlahBbesar: {
        type: Number
    },
    jumlahBsedang: {
        type: Number
    },
    jumlahBkecil: {
        type: Number
    }
});

module.exports = mongoose.model('transaksiNabung', userSchema);